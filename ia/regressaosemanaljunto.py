import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error
import numpy as np
import matplotlib.pyplot as plt

# Carregando os dados
train_df = pd.read_csv('dataset/base/train.csv', sep=';')
test_df = pd.read_csv('dataset/base/test.csv', sep=';')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month']]
y_train = train_df['value']

X_test = test_df[['week_of_month']]
y_test = test_df['value']

# Criando o modelo de regressão linear
model = LinearRegression()

# Treinando o modelo
model.fit(X_train, y_train)

# Fazendo previsões no conjunto de teste
y_pred = model.predict(X_test)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f"Erro Quadrático Médio: {mse}")

# Mostrando as previsões ao lado dos valores reais
resultados_df = pd.DataFrame({'Real': y_test, 'Previsto': y_pred})
print(resultados_df)

# Plotando o gráfico comparativo
plt.figure(figsize=(10, 6))
plt.plot(y_test.values, label='Valor Real', marker='o')
plt.plot(y_pred, label='Valor Previsto', marker='x')
plt.title('Comparação entre Valor Real e Valor Previsto')
plt.xlabel('Índice do Teste')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
