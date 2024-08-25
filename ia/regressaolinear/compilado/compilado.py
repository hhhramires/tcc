import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import PolynomialFeatures, StandardScaler


# Função para formatar os valores como dinheiro
def money_formatter(x, pos):
    return f'R${x:,.2f}'  # Formato brasileiro, ajustando para R$ e duas casas decimais


arquivo_treino = '2021_2022.csv'
arquivo_test = '2024.csv'

# Carregando os dados de treino e teste
train_entradas_df = pd.read_csv('../../dataset/entradas/' + arquivo_treino, sep=',')
test_entradas_df = pd.read_csv('../../dataset/entradas/' + arquivo_test, sep=',')

train_saidas_df = pd.read_csv('../../dataset/saidas/' + arquivo_treino, sep=',')
test_saidas_df = pd.read_csv('../../dataset/saidas/' + arquivo_test, sep=',')

# Separando as features (X) e o target (y)
X_entradas_train = train_entradas_df[['week_of_month', 'month']]
y_entradas_train = train_entradas_df['value']
X_entradas_val = test_entradas_df[['week_of_month', 'month']]
y_entradas_val = test_entradas_df['value']

X_saidas_train = train_saidas_df[['week_of_month', 'month']]
y_saidas_train = train_saidas_df['value']
X_saidas_val = test_saidas_df[['week_of_month', 'month']]
y_saidas_val = test_saidas_df['value']

# Criando um pipeline com PolynomialFeatures (grau fixo em 2) e LinearRegression
pipeline_entradas = Pipeline([
    ('poly', PolynomialFeatures(degree=2)),
    ('scaler', StandardScaler()),
    ('lin_reg', LinearRegression())
])

pipeline_saidas = Pipeline([
    ('poly', PolynomialFeatures(degree=2)),
    ('scaler', StandardScaler()),
    ('lin_reg', LinearRegression())
])

# Treinando o modelo com o conjunto de treino
pipeline_entradas.fit(X_entradas_train, y_entradas_train)
pipeline_saidas.fit(X_saidas_train, y_saidas_train)

# Fazendo previsões para o ano de 2023
y_entradas_pred = pipeline_entradas.predict(X_entradas_val)
y_saidas_pred = pipeline_saidas.predict(X_saidas_val)

# Criando rótulos personalizados para o eixo X no formato 'week_of_month-month'
x_labels = [f"{week}-{month}" for week, month in zip(X_entradas_val['week_of_month'], X_entradas_val['month'])]

# Combinando os arrays em um DataFrame
df = pd.DataFrame({
    'data': x_labels,
    'Coluna1': y_entradas_pred,
    'Coluna2': y_entradas_pred
})

print(df.head())

# Salvando o DataFrame em um arquivo CSV
df.to_csv('dados.csv', index=False)
print("Dados salvos em 'dados.csv'.")

# Plotando os resultados
plt.figure(figsize=(15, 9))
plt.title('Entradas de Dinheiro - treino 2021 e 2022, teste 01/2024 a 05/2024', fontsize=20)
# plt.plot(x_labels, y_val, marker='o', label='Real', markersize=6, linewidth=2)
plt.plot(x_labels, y_entradas_pred, marker='o', label='Entradas predito', markersize=6, linewidth=2)
plt.plot(x_labels, y_saidas_pred, marker='o', label='Saídas predito', markersize=6, linewidth=2)
plt.xlabel('Semana - Mês', fontsize=20)
plt.ylabel('Valor em Dinheiro', fontsize=20)
plt.legend(fontsize=20)
plt.gca().yaxis.set_major_formatter(mtick.FuncFormatter(money_formatter))
plt.grid(True)
plt.yticks(fontsize=14)
plt.xticks(rotation=90, fontsize=14)
plt.show()




