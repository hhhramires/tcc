import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeRegressor
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt
import re

def extract_years(filename: str):
    # Usar expressão regular para encontrar todos os números no nome do arquivo
    years = re.findall(r'\d{4}', filename)

    # Converter para inteiros e retornar como uma lista de números
    return [int(year) for year in years]

modelo = 'Modelo Baseados em Árvores de Decisão - Decision Tree Regressor'
arquivo_treino = 'train_2022_2023.csv'
arquivo_teste = 'test_2024.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/separado/' + arquivo_treino, sep=';')
test_df = pd.read_csv('dataset/separado/' + arquivo_teste, sep=';')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_test = test_df[['week_of_month', 'month']]
y_test = test_df['value']

# Criando e treinando o modelo Decision Tree Regressor
model = DecisionTreeRegressor(random_state=42)
model.fit(X_train, y_train)

# Fazendo previsões para o ano de 2023
y_pred = model.predict(X_test)

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



