import matplotlib.pyplot as plt
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import GridSearchCV

arquivo_treino = '2021_2022.csv'
arquivo_validation = '2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/real/' + arquivo_treino, sep=',')
test_df = pd.read_csv('dataset/real/' + arquivo_validation, sep=',')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_val = test_df[['week_of_month', 'month']]
y_val = test_df['value']

# Parametros para o modelo Random Forest Regressor com GridSearch
param_grid = {
    'n_estimators': [50, 100, 200, 300, 400,600],
    'max_depth': [1, 2, 3, 4, 5, 6, 7],
    'min_samples_split': [2, 5, 10, 15],
    'min_samples_leaf': [1, 3, 5, 10, 15]}

# Configurando o GridSearchCV com RandomForestRegressor
grid_search = GridSearchCV(
    estimator=RandomForestRegressor(),
    param_grid=param_grid,
    scoring='neg_mean_squared_error',
    cv=5,
    n_jobs=-1,
    verbose=1
)

# Explora todas as combinações possíveis de hiperparâmetros
# Avalie o desempenho de cada combinação com validação cruzada
# Selecione o modelo com a melhor performance com base na métrica definida
grid_search.fit(X_train, y_train)

# Usando o melhor modelo encontrado pelo GridSearchCV
best_model = grid_search.best_estimator_

# Fazendo previsões para o ano de 2023 com o modelo otimizado
y_pred = best_model.predict(X_val)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_val, y_pred)
print(f'Mean Squared Error: {mse}')

# Plotando os resultados
plt.figure(figsize=(12, 6))
plt.plot(range(len(y_val)), y_val, marker='o')
plt.plot(range(len(y_pred)), y_pred, marker='x')
plt.grid(True)
plt.show()

cv_results = grid_search.cv_results_
for mean_score, params in zip(cv_results['mean_test_score'], cv_results['params']):
    print(f'Mean Score: {mean_score}, Params: {params}')

# Após o grid_search.fit(X_train, y_train)
best_params = grid_search.best_params_
print(f'Best hyperparameters: {best_params}')
