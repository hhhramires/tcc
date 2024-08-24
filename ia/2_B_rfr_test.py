import matplotlib.pyplot as plt
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error

arquivo_treino = '2021_2022.csv'
arquivo_teste = '2024.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/entradas/' + arquivo_treino, sep=',')
test_df = pd.read_csv('dataset/entradas/' + arquivo_teste, sep=',')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_test = test_df[['week_of_month', 'month']]
y_test = test_df['value']

# Parametros para o modelo Random Forest Regressor com GridSearch
best_params = {'max_depth': 3, 'min_samples_leaf': 2, 'min_samples_split': 10, 'n_estimators': 200}

# Criando uma nova instância do RandomForestRegressor com os melhores hiperparâmetros
best_model_direct = RandomForestRegressor(**best_params)

# Treinando o modelo diretamente com os melhores hiperparâmetros
best_model_direct.fit(X_train, y_train)

# Fazendo previsões para o ano de 2023 com o modelo otimizado
y_pred = best_model_direct.predict(X_test)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_test, y_pred)
print(f'Mean Squared Error: {mse}')

# Após o grid_search.fit(X_train, y_train)
print(f'Best hyperparameters: {best_params}')

new_data = pd.DataFrame({
    'week_of_month': [2],
    'month': [6]
})

# Prevendo o valor com o modelo treinado
new_prediction = best_model_direct.predict(new_data)
print(f'Predição para week_of_month=2 e month=6: {new_prediction[0]}')

# Plotando os resultados
plt.figure(figsize=(12, 6))
plt.plot(range(len(y_test)), y_test, marker='o')
plt.plot(range(len(y_pred)), y_pred, marker='x')
plt.grid(True)
plt.show()
