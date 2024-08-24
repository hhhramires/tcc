import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.ensemble import RandomForestRegressor, GradientBoostingRegressor, StackingRegressor
from sklearn.linear_model import Ridge
from sklearn.metrics import mean_squared_error
from sklearn.svm import SVR


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

# Definindo os modelos de base
base_models = [
    ('random_forest', RandomForestRegressor(n_estimators=100, random_state=42)),
    ('gradient_boosting', GradientBoostingRegressor(n_estimators=100, random_state=42)),
    ('svr', SVR(kernel='rbf'))
]

# Definindo o meta-modelo
meta_model = Ridge(alpha=1.0)

# Criando e treinando o Stacking Regressor
stacking_model = StackingRegressor(estimators=base_models, final_estimator=meta_model)
stacking_model.fit(X_train, y_train)

# Fazendo previsões para o ano de 2023
y_pred = stacking_model.predict(X_val)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_val, y_pred)
print(f'Mean Squared Error: {mse}')

# Criando rótulos personalizados para o eixo X no formato 'week_of_month-month'
x_labels = [f"{week}-{month}" for week, month in zip(X_val['week_of_month'], X_val['month'])]

# Plotando os resultados
plt.figure(figsize=(15, 7))
plt.title('Entradas de Dinheiro', fontsize=16)
plt.plot(x_labels, y_val, marker='o', label='Real', markersize=6, linewidth=2)
plt.plot(x_labels, y_pred, marker='o', label='Predito', markersize=6, linewidth=2)
plt.xlabel('Semana - Mês', fontsize=14)  # Legenda do eixo X com fonte maior
plt.ylabel('Valor em Dinheiro', fontsize=14)  # Legenda do eixo Y com fonte maior
plt.legend(fontsize=14)  # Aumenta o tamanho da fonte da legenda
plt.gca().yaxis.set_major_formatter(
    mtick.FuncFormatter(money_formatter))  # Aplicando o formatador de dinheiro ao eixo Y
plt.grid(True)
plt.yticks(fontsize=14)
plt.xticks(rotation=90, fontsize=12)  # Rotaciona os rótulos do eixo X e ajusta o tamanho da fonte
plt.show()
