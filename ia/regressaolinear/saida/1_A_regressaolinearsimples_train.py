import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import GridSearchCV
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import PolynomialFeatures, StandardScaler


# Função para formatar os valores como dinheiro
def money_formatter(x, pos):
    return f'R${x:,.2f}'  # Formato brasileiro, ajustando para R$ e duas casas decimais


arquivo_treino = '2021_2022.csv'
arquivo_validation = '2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('../../dataset/saidas/' + arquivo_treino, sep=',')
validation_df = pd.read_csv('../../dataset/saidas/' + arquivo_validation, sep=',')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_val = validation_df[['week_of_month', 'month']]
y_val = validation_df['value']

# Criando um pipeline com PolynomialFeatures e LinearRegression
pipeline = Pipeline([
    ('poly', PolynomialFeatures()),
    ('scaler', StandardScaler()),
    ('lin_reg', LinearRegression())
])

# Definindo os parâmetros para o GridSearchCV
param_grid = {
    'poly__degree': [1, 2, 3, 4]
}

# Configurando o GridSearchCV
grid_search = GridSearchCV(
    estimator=pipeline,
    param_grid=param_grid,
    cv=5,
    scoring='neg_mean_squared_error',
    n_jobs=-1,
    verbose=1)

# Treinando o modelo com GridSearchCV
grid_search.fit(X_train, y_train)

# Melhores parâmetros e melhor modelo
print("Best parameters:", grid_search.best_params_)
best_model = grid_search.best_estimator_

# Fazendo previsões para o ano de 2023
y_pred = best_model.predict(X_val)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_val, y_pred)
print(f'Mean Squared Error: {mse}')

cv_results = grid_search.cv_results_
for mean_score, params in zip(cv_results['mean_test_score'], cv_results['params']):
    print(f'Mean Score: {mean_score}, Params: {params}')

# Após o grid_search.fit(X_train, y_train)
best_params = grid_search.best_params_
print(f'Best hyperparameters: {best_params}')

# Criando rótulos personalizados para o eixo X no formato 'week_of_month-month'
x_labels = [f"{week}-{month}" for week, month in zip(X_val['week_of_month'], X_val['month'])]

# Plotando os resultados
plt.figure(figsize=(15, 9))
plt.title('Saídas de Dinheiro - treino 2021 e 2022, validação 2023 ', fontsize=20)
plt.plot(x_labels, y_val, marker='o', label='Real', markersize=6, linewidth=2)
plt.plot(x_labels, y_pred, marker='o', label='Predito', markersize=6, linewidth=2)
plt.xlabel('Semana - Mês', fontsize=20)
plt.ylabel('Valor em Dinheiro', fontsize=20)
plt.legend(fontsize=20)
plt.gca().yaxis.set_major_formatter(mtick.FuncFormatter(money_formatter))
plt.grid(True)
plt.yticks(fontsize=14)
plt.xticks(rotation=90, fontsize=14)
plt.show()
