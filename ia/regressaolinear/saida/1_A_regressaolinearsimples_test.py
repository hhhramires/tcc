import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures, StandardScaler
from sklearn.pipeline import Pipeline
from sklearn.metrics import mean_squared_error

# Função para formatar os valores como dinheiro
def money_formatter(x, pos):
    return f'R${x:,.2f}'  # Formato brasileiro, ajustando para R$ e duas casas decimais

arquivo_treino = '2021_2022.csv'
arquivo_test = '2024.csv'

# Carregando os dados de treino e teste
train_df = pd.read_csv('../../dataset/saidas/' + arquivo_treino, sep=',')
test_df = pd.read_csv('../../dataset/saidas/' + arquivo_test, sep=',')

# Separando as features (X) e o target (y)
X_train = train_df[['week_of_month', 'month']]
y_train = train_df['value']
X_val = test_df[['week_of_month', 'month']]
y_val = test_df['value']

# Criando um pipeline com PolynomialFeatures (grau fixo em 2) e LinearRegression
pipeline = Pipeline([
    ('poly', PolynomialFeatures(degree=2)),
    ('scaler', StandardScaler()),
    ('lin_reg', LinearRegression())
])

# Treinando o modelo com o conjunto de treino
pipeline.fit(X_train, y_train)

# Fazendo previsões para o ano de 2023
y_pred = pipeline.predict(X_val)

# Calculando o erro quadrático médio
mse = mean_squared_error(y_val, y_pred)
print(f'Mean Squared Error: {mse}')

# Criando rótulos personalizados para o eixo X no formato 'week_of_month-month'
x_labels = [f"{week}-{month}" for week, month in zip(X_val['week_of_month'], X_val['month'])]

# Plotando os resultados
plt.figure(figsize=(15, 9))
plt.title('Saídas de Dinheiro - treino 2021 e 2022, teste 01/2024 a 05/2024', fontsize=20)
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
