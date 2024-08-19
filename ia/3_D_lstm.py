import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM
import re

def extract_years(filename: str):
    # Usar expressão regular para encontrar todos os números no nome do arquivo
    years = re.findall(r'\d{4}', filename)

    # Converter para inteiros e retornar como uma lista de números
    return [int(year) for year in years]

modelo = 'Modelo de Séries Temporais Decision Tree Regressor'
arquivo_treino = 'train_2021_2022.csv'
arquivo_teste = 'test_2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/separado/' + arquivo_treino, sep=';')
test_df = pd.read_csv('dataset/separado/' + arquivo_teste, sep=';')

# Combinar mês e semana para criar um índice temporal
train_df['date'] = pd.to_datetime(train_df['month'].astype(str) + '-2021') + pd.to_timedelta((train_df['week_of_month'] - 1) * 7, unit='d')
test_df['date'] = pd.to_datetime(test_df['month'].astype(str) + '-2023') + pd.to_timedelta((test_df['week_of_month'] - 1) * 7, unit='d')

# Configurar o índice de data
train_df.set_index('date', inplace=True)
test_df.set_index('date', inplace=True)

# Selecionar os valores de treino e teste
train_values = train_df['value'].values.reshape(-1, 1)
test_values = test_df['value'].values.reshape(-1, 1)

# Normalizar os dados
scaler = MinMaxScaler(feature_range=(0, 1))
train_scaled = scaler.fit_transform(train_values)
test_scaled = scaler.transform(test_values)

# Criar as sequências para o LSTM
def create_sequences(data, seq_length):
    X = []
    y = []
    for i in range(len(data) - seq_length):
        X.append(data[i:i+seq_length])
        y.append(data[i+seq_length])
    return np.array(X), np.array(y)

# Definir o comprimento da sequência
seq_length = 4

# Criar sequências de treino e teste
X_train, y_train = create_sequences(train_scaled, seq_length)
X_test, y_test = create_sequences(test_scaled, seq_length)

# Construir o modelo LSTM
model = Sequential()
model.add(LSTM(50, return_sequences=True, input_shape=(seq_length, 1)))
model.add(LSTM(50))
model.add(Dense(1))
model.compile(optimizer='adam', loss='mean_squared_error')

# Treinar o modelo
model.fit(X_train, y_train, epochs=20, batch_size=1, verbose=1)

# Fazer previsões
y_pred_scaled = model.predict(X_test)
y_pred = scaler.inverse_transform(y_pred_scaled)

# Avaliação do modelo
mse = mean_squared_error(test_values[seq_length:], y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(10, 6))
plt.plot(test_df.index[seq_length:], test_values[seq_length:], label='Valores Reais 2023', marker='o')
plt.plot(test_df.index[seq_length:], y_pred, label='Previsões 2023', marker='x')
plt.title('Previsão de Gastos para 2023 - LSTM')
plt.xlabel('Data')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
