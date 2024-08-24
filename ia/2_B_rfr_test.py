import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error


# Função para formatar os valores como dinheiro
def money_formatter(x, pos):
    return f'R${x:,.2f}'  # Formato brasileiro, ajustando para R$ e duas casas decimais


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

# Criando rótulos personalizados para o eixo X no formato 'week_of_month-month'
x_labels = [f"{week}-{month}" for week, month in zip(X_test['week_of_month'], X_test['month'])]

# Plotando os resultados
plt.figure(figsize=(15, 7))
plt.title('Entradas de Dinheiro', fontsize=16)
plt.plot(x_labels, y_test, marker='o', label='Real', markersize=8, linewidth=2)
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
