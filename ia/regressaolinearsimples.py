import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/separado/train.csv', sep=';')
test_df = pd.read_csv('dataset/separado/test.csv', sep=';')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_test = test_df[['week_of_month', 'month']]
y_test = test_df['value']

# Criando e treinando o modelo de Regressão Linear
model = LinearRegression()
model.fit(X_train, y_train)

# Fazendo previsões para o ano de 2023
y_pred = model.predict(X_test)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(10, 6))
plt.plot(range(len(y_test)), y_test, label='Valores Reais 2023', marker='o')
plt.plot(range(len(y_pred)), y_pred, label='Previsões 2023', marker='x')
plt.title('Previsão de Gastos para 2023')
plt.xlabel('Semana do Ano')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
