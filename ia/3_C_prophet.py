import re

import matplotlib.pyplot as plt
import pandas as pd
from fbprophet import Prophet
from sklearn.metrics import mean_squared_error


def extract_years(filename: str):
    # Usar expressão regular para encontrar todos os números no nome do arquivo
    years = re.findall(r'\d{4}', filename)

    # Converter para inteiros e retornar como uma lista de números
    return [int(year) for year in years]


modelo = 'Modelo de Séries Temporais - Prophet'
arquivo_treino = 'train_data_valor_2021_2022.csv'
arquivo_teste = 'test_data_valor_2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=';')
test_df = pd.read_csv('dataset/real/' + arquivo_teste, sep=';')

# Renomeando as colunas para o formato esperado pelo Prophet
train_df.rename(columns={'date': 'ds', 'value': 'y'}, inplace=True)
test_df.rename(columns={'date': 'ds', 'value': 'y'}, inplace=True)

# Convertendo a coluna 'ds' para datetime
train_df['ds'] = pd.to_datetime(train_df['ds'])
test_df['ds'] = pd.to_datetime(test_df['ds'])

# Criando e ajustando o modelo Prophet
model = Prophet()
model.fit(train_df)

# Fazendo previsões para o período do dataset de teste
future = test_df[['ds']]  # Usando as datas do conjunto de teste para previsão
forecast = model.predict(future)

# Calculando o erro quadrático médio
mse = mean_squared_error(test_df['y'], forecast['yhat'])
print(f'Mean Squared Error: {mse}')

# Visualizando os resultados
plt.figure(figsize=(10, 6))
plt.plot(test_df['ds'], test_df['y'], label='Valores Reais', marker='o')
plt.plot(forecast['ds'], forecast['yhat'], label='Previsões', marker='x')
plt.title('Previsão de Gastos - Prophet')
plt.xlabel('Data')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
