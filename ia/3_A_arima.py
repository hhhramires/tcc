import re

import matplotlib.pyplot as plt
import pandas as pd
from sklearn.metrics import mean_squared_error
from statsmodels.tsa.arima.model import ARIMA


def extract_years(filename: str):
    # Usar expressão regular para encontrar todos os números no nome do arquivo
    years = re.findall(r'\d{4}', filename)

    # Converter para inteiros e retornar como uma lista de números
    return [int(year) for year in years]


modelo = 'Modelo de Séries Temporais - ARIMA (AutoRegressive Integrated Moving Average)'
arquivo_treino = 'train_data_valor_2021_2022.csv'
arquivo_teste = 'test_data_valor_2023.csv'

# arquivo_treino = 'train_data_valor_2021_2022.csv'
# arquivo_teste = 'test_data_valor_2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=',')
test_df = pd.read_csv('dataset/real/' + arquivo_teste, sep=',')

# Convertendo a coluna 'date' para datetime e definindo como índice
train_df['date'] = pd.to_datetime(train_df['date'])
test_df['date'] = pd.to_datetime(test_df['date'])

train_df.set_index('date', inplace=True)
test_df.set_index('date', inplace=True)

# Série temporal de treino
y_train = train_df['value']
y_test = test_df['value']

# Ajustando o modelo ARIMA
# Aqui, você pode ajustar os parâmetros p, d, q conforme necessário.
# A configuração (p=1, d=1, q=1) é um ponto de partida comum.
model = ARIMA(y_train, order=(1, 1, 1))
arima_model = model.fit()

# Fazendo previsões para o período do dataset de teste
#y_pred = arima_model.predict(start=y_test.index[0], end=y_test.index[-1], typ='levels')

# Usando índices inteiros para prever o mesmo número de períodos do conjunto de teste
y_pred = arima_model.predict(start=len(y_train), end=len(y_train) + len(y_test) - 1)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(12, 6))
plt.plot(range(len(y_test)), y_test, label=f'Valores Reais {extract_years(arquivo_teste)}', marker='o')
plt.plot(range(len(y_pred)), y_pred, label=f'Previsões {extract_years(arquivo_teste)}', marker='x')
plt.title(
    f'Previsão de ENTRADAS para {extract_years(arquivo_teste)} - Treino {extract_years(arquivo_treino)} - {modelo}')
plt.xlabel('Semana do Ano')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()