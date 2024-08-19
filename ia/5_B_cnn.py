import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv1D, Dense, Flatten
import re

def extract_years(filename: str):
    # Usar expressão regular para encontrar todos os números no nome do arquivo
    years = re.findall(r'\d{4}', filename)

    # Converter para inteiros e retornar como uma lista de números
    return [int(year) for year in years]

modelo = 'Modelo de Redes Neurais - Redes Neurais Convolucionais (CNN)'
arquivo_treino = 'test_2024.csv'
arquivo_teste = 'test_2024.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=';')
test_df = pd.read_csv('dataset/real/' + arquivo_teste, sep=';')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']].values
y_train = train_df['value'].values
X_test = test_df[['week_of_month', 'month']].values
y_test = test_df['value'].values

# Normalizando os dados
scaler = MinMaxScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Remodelando os dados para o formato (samples, time steps, features) para a CNN
X_train_scaled = X_train_scaled.reshape((X_train_scaled.shape[0], X_train_scaled.shape[1], 1))
X_test_scaled = X_test_scaled.reshape((X_test_scaled.shape[0], X_test_scaled.shape[1], 1))

# Construindo o modelo CNN
model = Sequential()
model.add(Conv1D(filters=64, kernel_size=2, activation='relu', input_shape=(X_train_scaled.shape[1], 1)))
model.add(Flatten())
model.add(Dense(50, activation='relu'))
model.add(Dense(1))
model.compile(optimizer='adam', loss='mse')

# Treinando o modelo
model.fit(X_train_scaled, y_train, epochs=200, verbose=1)

# Fazendo previsões
y_pred = model.predict(X_test_scaled)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(10, 6))
plt.plot(range(len(y_test)), y_test, label='Valores Reais 2023', marker='o')
plt.plot(range(len(y_pred)), y_pred, label='Previsões 2023', marker='x')
plt.title('Previsão de Gastos para 2023 - CNN')
plt.xlabel('Período')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
