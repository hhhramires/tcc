import pandas as pd
import numpy as np
from statsmodels.tsa.statespace.sarimax import SARIMAX
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt
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


# Criando uma coluna 'period' que representa a combinação de mês e semana como um único índice
train_df['period'] = train_df['month'].astype(str) + '-' + train_df['week_of_month'].astype(str)
test_df['period'] = test_df['month'].astype(str) + '-' + test_df['week_of_month'].astype(str)

# Usando essa coluna como índice da série temporal
train_df.set_index('period', inplace=True)
test_df.set_index('period', inplace=True)

# Série temporal de treino
y_train = train_df['value']
y_test = test_df['value']

# Ajustando o modelo SARIMA
# A escolha dos parâmetros (p, d, q, P, D, Q, s) requer análise prévia dos dados.
# Aqui estou usando valores padrão que podem precisar de ajuste.
model = SARIMAX(y_train, order=(1, 1, 1), seasonal_order=(1, 1, 1, 12))
sarima_model = model.fit(disp=False)

# Fazendo previsões para 2023
y_pred = sarima_model.predict(start=len(y_train), end=len(y_train) + len(y_test) - 1, dynamic=False)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(10, 6))
plt.plot(range(len(y_test)), y_test, label=f'Valores Reais {extract_years(arquivo_teste)}', marker='o')
plt.plot(range(len(y_pred)), y_pred, label=f'Previsões {extract_years(arquivo_teste)}', marker='x')
plt.title(f'Previsão de ENTRADAS para {extract_years(arquivo_teste)} - Treino {extract_years(arquivo_treino)} - {modelo}')
plt.xlabel('Semana do Ano')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
