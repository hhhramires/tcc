import re

import matplotlib.pyplot as plt
import pandas as pd
from sklearn.ensemble import GradientBoostingRegressor
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import GridSearchCV, RandomizedSearchCV



def extract_years(filename: str):
    # Usar expressão regular para encontrar todos os números no nome do arquivo
    years = re.findall(r'\d{4}', filename)

    # Converter para inteiros e retornar como uma lista de números
    return [int(year) for year in years]


modelo = 'Modelo Baseados em Árvores de Decisão - Gradient Boosting Regressor'
arquivo_treino = 'train_2021_2022.csv'
arquivo_teste = 'test_2023.csv'

# arquivo_treino = 'train_2022_2023.csv'
# arquivo_teste = 'test_2024.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=',')
test_df = pd.read_csv('dataset/real/' + arquivo_teste, sep=',')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_test = test_df[['week_of_month', 'month']]
y_test = test_df['value']

# Definindo os parâmetros para a busca
param_grid = {
    'n_estimators': [100, 200, 300, 400],
    'learning_rate': [0.01, 0.05, 0.1, 0.2],
    'max_depth': [3, 4, 5],
    'min_samples_split': [2, 5, 10],
    'min_samples_leaf': [1, 3, 5],
    'subsample': [0.6, 0.8, 1.0]}

# Usando GridSearchCV para encontrar os melhores parâmetros
grid_search = GridSearchCV(
    estimator=GradientBoostingRegressor(random_state=42),
    param_grid=param_grid,
    scoring='neg_mean_squared_error',
    cv=5,
    n_jobs=-1,
    verbose=1)

# Treinando o modelo
grid_search.fit(X_train, y_train)

# Obtendo o melhor modelo
best_model = grid_search.best_estimator_

# Fazendo previsões para o ano de 2023
y_pred = best_model.predict(X_test)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(12, 6))
plt.plot(range(len(y_test)), y_test, marker='o')
plt.plot(range(len(y_pred)), y_pred, marker='x')
plt.xlabel('Índice')
plt.ylabel('Valor')
plt.legend()
plt.grid(True)
plt.show()
