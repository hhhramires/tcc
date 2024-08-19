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


modelo = 'Modelo Baseados em Árvores de Decisão - Decision Tree Regressor'
# arquivo_treino = 'train_2021_2022.csv'
# arquivo_teste = 'test_2023.csv'

arquivo_treino = 'train_2022_2023.csv'
arquivo_teste = 'test_2024.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=';')
test_df = pd.read_csv('dataset/real/' + arquivo_teste, sep=';')

# Criando uma data sintética para cada período
# Assumindo que cada período começa no início do mês e as semanas são incrementais
train_df['date'] = (
        pd.to_datetime(train_df['month'].astype(str) + '-2021') + pd.to_timedelta((train_df['week_of_month'] - 1) * 7,
                                                                                  unit='d'))
test_df['date'] = (
        pd.to_datetime(test_df['month'].astype(str) + '-2023') + pd.to_timedelta((test_df['week_of_month'] - 1) * 7,
                                                                                 unit='d'))

# Configurar o índice de data
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

# Fazendo previsões para 2023
y_pred = arima_model.predict(start=y_test.index[0], end=y_test.index[-1], typ='levels')

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(10, 6))
plt.plot(y_test.index, y_test, label='Valores Reais 2023', marker='o')
plt.plot(y_pred.index, y_pred, label='Previsões 2023', marker='x')
plt.title('Previsão de Gastos para 2023 - ARIMA')
plt.xlabel('Data')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
