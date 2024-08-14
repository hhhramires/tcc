import pandas as pd
import numpy as np
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import mean_squared_error

# Preparação dos dados
df = pd.DataFrame({
    'week_month': ['102', '203', '304', '401', '502', '103', '204', '305', '402', '503'],
    'value': [100, 200, 150, 300, 250, 120, 210, 160, 310, 260]
})
df['week'] = df['week_month'].str[:1].astype(int)
df['month'] = df['week_month'].str[1:].astype(int)

# Separando as features e o target
X = df[['week', 'month']]
y = df['value']

# Normalizando os dados
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Criando o modelo da rede neural
model = Sequential()
model.add(Dense(32, input_dim=2, activation='relu'))  # Camada de entrada + oculta
model.add(Dense(16, activation='relu'))  # Camada oculta
model.add(Dense(1, activation='linear'))  # Camada de saída

# Compilando o modelo
model.compile(optimizer='adam', loss='mean_squared_error')

# Treinando o modelo
model.fit(X_scaled, y, epochs=100, verbose=1)

# Fazendo previsões no conjunto de dados original
y_pred = model.predict(X_scaled)

# Avaliando o modelo
mse = mean_squared_error(y, y_pred)
print(f"Erro Quadrático Médio no conjunto de treino: {mse}")

# Fazendo uma previsão para a semana 1 de março (103)
semana_futura = pd.DataFrame({'week': [1], 'month': [3]})
semana_futura_scaled = scaler.transform(semana_futura)
valor_previsto = model.predict(semana_futura_scaled)
print(f"Valor previsto para a semana 1 de março: {valor_previsto[0][0]}")

# Plotando o gráfico dos valores reais vs. previstos
import matplotlib.pyplot as plt

plt.figure(figsize=(10, 6))
plt.plot(y, label='Valor Real', marker='o')
plt.plot(y_pred, label='Valor Previsto', marker='x')
plt.title('Comparação entre Valor Real e Valor Previsto (Rede Neural)')
plt.xlabel('Índice')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
