import pandas as pd
import numpy as np
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/separado/trein.csv', sep=';')
test_df = pd.read_csv('dataset/separado/test.csv', sep=';')

# Separando as features e o target nos conjuntos de treino e teste
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']

X_test = test_df[['week_of_month', 'month']]
y_test = test_df['value']

# Normalizando os dados
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Criando o modelo da rede neural
model = Sequential()
model.add(Dense(32, input_dim=2, activation='relu'))  # Camada de entrada + oculta
model.add(Dense(16, activation='relu'))  # Camada oculta
model.add(Dense(1, activation='linear'))  # Camada de saída

# Compilando o modelo
model.compile(optimizer='adam', loss='mean_squared_error')

# Treinando o modelo
model.fit(X_train_scaled, y_train, epochs=100, verbose=1)

# Fazendo previsões no conjunto de teste
y_pred = model.predict(X_test_scaled)

# Avaliando o modelo
mse = mean_squared_error(y_test, y_pred)
print(f"Erro Quadrático Médio no conjunto de teste: {mse}")

# Fazendo uma previsão para uma semana e mês futuro
semana_futura = pd.DataFrame({'week_of_month': [1], 'month': [3]})  # Exemplo de semana 1 de março
semana_futura_scaled = scaler.transform(semana_futura)
valor_previsto = model.predict(semana_futura_scaled)
print(f"Valor previsto para a semana 1 de março: {valor_previsto[0][0]}")

# Plotando o gráfico dos valores reais vs. previstos no conjunto de teste
plt.figure(figsize=(10, 6))
plt.plot(y_test.values, label='Valor Real', marker='o')
plt.plot(y_pred, label='Valor Previsto', marker='x')
plt.title('Comparação entre Valor Real e Valor Previsto (Rede Neural)')
plt.xlabel('Índice')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
