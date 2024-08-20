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

modelo = 'Modelo de Séries Temporais - LSTM (Long Short-Term Memory)'
arquivo_treino = 'train_data_valor_2021_2022.csv'
arquivo_teste = 'test_data_valor_2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=';')
test_df = pd.read_csv('dataset/real/' + arquivo_teste, sep=';')

# Convertendo a coluna 'date' para datetime e definindo como índice
train_df['date'] = pd.to_datetime(train_df['date'])
test_df['date'] = pd.to_datetime(test_df['date'])

train_df.set_index('date', inplace=True)
test_df.set_index('date', inplace=True)

# Normalizando os dados
scaler = MinMaxScaler(feature_range=(0, 1))
scaled_train_data = scaler.fit_transform(train_df['value'].values.reshape(-1, 1))
scaled_test_data = scaler.transform(test_df['value'].values.reshape(-1, 1))

# Definindo o número de passos de tempo (timesteps)
timesteps = 30

# Criando as sequências para a LSTM
def create_sequences(data, timesteps):
    X, y = [], []
    for i in range(timesteps, len(data)):
        X.append(data[i-timesteps:i, 0])
        y.append(data[i, 0])
    return np.array(X), np.array(y)

X_train, y_train = create_sequences(scaled_train_data, timesteps)
X_test, y_test = create_sequences(scaled_test_data, timesteps)

# Remodelando X para o formato esperado pelo LSTM: (samples, timesteps, features)
X_train = X_train.reshape((X_train.shape[0], X_train.shape[1], 1))
X_test = X_test.reshape((X_test.shape[0], X_test.shape[1], 1))

# Criando o modelo LSTM
model = Sequential()
model.add(LSTM(units=50, return_sequences=True, input_shape=(X_train.shape[1], 1)))
model.add(LSTM(units=50))
model.add(Dense(1))

# Compilando o modelo
model.compile(optimizer='adam', loss='mean_squared_error')

# Treinando o modelo
model.fit(X_train, y_train, epochs=20, batch_size=32)

# Fazendo previsões
predicted_values = model.predict(X_test)

# Desnormalizando os valores previstos para a escala original
predicted_values = scaler.inverse_transform(predicted_values)
y_test = scaler.inverse_transform(y_test.reshape(-1, 1))

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, predicted_values)
print(f'Mean Squared Error: {mse}')

# Visualizando os resultados
plt.figure(figsize=(10, 6))
plt.plot(test_df.index[timesteps:], y_test, label='Valores Reais', marker='o')
plt.plot(test_df.index[timesteps:], predicted_values, label='Previsões', marker='x')
plt.title('Previsão de Gastos - LSTM')
plt.xlabel('Data')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()