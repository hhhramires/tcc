import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import GridSearchCV


# Função para formatar os valores como dinheiro
def money_formatter(x, pos):
    return f'R${x:,.2f}'  # Formato brasileiro, ajustando para R$ e duas casas decimais


arquivo_treino = '2021_2022.csv'
arquivo_validation = '2023.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('dataset/entradas/' + arquivo_treino, sep=',')
test_df = pd.read_csv('dataset/entradas/' + arquivo_validation, sep=',')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_val = test_df[['week_of_month', 'month']]
y_val = test_df['value']

# Parametros para o modelo Random Forest Regressor com GridSearch
param_grid = {
    'n_estimators': [100, 200, 300, 400],
    'max_depth': [3, 4, 5],
    'min_samples_split': [2, 5, 10],
    'min_samples_leaf': [1, 3, 5]}

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

cv_results = grid_search.cv_results_
for mean_score, params in zip(cv_results['mean_test_score'], cv_results['params']):
    print(f'Mean Score: {mean_score}, Params: {params}')

# Após o grid_search.fit(X_train, y_train)
best_params = grid_search.best_params_
print(f'Best hyperparameters: {best_params}')

# Criando rótulos personalizados para o eixo X no formato 'week_of_month-month'
x_labels = [f"{week}-{month}" for week, month in zip(X_val['week_of_month'], X_val['month'])]

# Plotando os resultados
plt.figure(figsize=(15, 7))
plt.title('Entradas de Dinheiro', fontsize=16)
plt.plot(x_labels, y_val, marker='o', label='Real', markersize=8, linewidth=2)
plt.plot(x_labels, y_pred, marker='o', label='Predito', markersize=8, linewidth=2)
plt.xlabel('Semana - Mês', fontsize=14)  # Legenda do eixo X com fonte maior
plt.ylabel('Valor em Dinheiro', fontsize=14)  # Legenda do eixo Y com fonte maior
plt.legend(fontsize=14)  # Aumenta o tamanho da fonte da legenda
plt.gca().yaxis.set_major_formatter(
    mtick.FuncFormatter(money_formatter))  # Aplicando o formatador de dinheiro ao eixo Y
plt.grid(True)
plt.yticks(fontsize=14)
plt.xticks(rotation=45, fontsize=14)  # Rotaciona os rótulos do eixo X e ajusta o tamanho da fonte
plt.show()
