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

# Criando uma data sintética para cada período
# Assumindo que cada período começa no início do mês e as semanas são incrementais
train_df['date'] = pd.to_datetime(train_df['month'])
test_df['date'] = pd.to_datetime(test_df['month'])

# Preparando os dados para o Prophet
train_df = train_df.rename(columns={'date': 'ds', 'value': 'y'})
test_df = test_df.rename(columns={'date': 'ds', 'value': 'y'})

# Criando e treinando o modelo Prophet
model = Prophet(yearly_seasonality=True, weekly_seasonality=False, daily_seasonality=False)
model.fit(train_df)

# Fazendo previsões para o ano de 2023
future = test_df[['ds']]
forecast = model.predict(future)

# Extraindo as previsões
y_pred = forecast['yhat']

# Calculando o erro quadrático médio
mse = mean_squared_error(test_df['y'], y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(10, 6))
plt.plot(test_df['ds'], test_df['y'], label='Valores Reais 2023', marker='o')
plt.plot(test_df['ds'], y_pred, label='Previsões 2023', marker='x')
plt.title('Previsão de Gastos para 2023 - Prophet')
plt.xlabel('Data')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
