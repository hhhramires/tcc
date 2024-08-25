# Entradas e Saidas
# Saldo semanal
import matplotlib.pyplot as plt
import pandas as pd

entradas_df = pd.read_csv('../dataset/entradas/2024.csv', sep=',')
saidas_df = pd.read_csv('../dataset/saidas/2024.csv', sep=',')

# Renomeando as colunas para 'entradas' e 'saidas'
entradas_df.rename(columns={'value': 'entradas'}, inplace=True)
saidas_df.rename(columns={'value': 'saidas'}, inplace=True)

# Juntando os DataFrames usando merge() em múltiplas colunas
df_joined = pd.merge(entradas_df, saidas_df, on=['week_of_month', 'month'])

# Combinando as colunas 'week_of_month' e 'month' em 'semana_mes'
df_joined['semana_mes'] = df_joined['week_of_month'].astype(str) + '-' + df_joined['month'].astype(str)

# Removendo as colunas 'week_of_month' e 'month'
df_joined.drop(columns=['week_of_month', 'month'], inplace=True)

# Calculando a coluna 'resultado'
df_joined['resultado'] = df_joined['entradas'] - df_joined['saidas']

# Reordenando as colunas para ter 'semana_mes' como a primeira
df_joined = df_joined[['semana_mes', 'entradas', 'saidas', 'resultado']]

print(df_joined.head())

df_joined.to_csv('dados.csv', index=False)

# Número de grupos de barras
n_groups = len(df_joined)

# Criação de índices para cada grupo de barras
index = range(n_groups)

# Largura das barras
bar_width = 0.25

# Configurando e plotando o gráfico
plt.figure(figsize=(12, 8))
plt.bar(index, df_joined['entradas'], bar_width, color='blue', label='Entradas')
plt.bar([i + bar_width for i in index], df_joined['saidas'], bar_width, color='red', label='Saídas')
plt.bar([i + bar_width * 2 for i in index], df_joined['resultado'], bar_width, color='green', label='Resultado')

plt.xlabel('Semana - Mês', fontsize=12)
plt.ylabel('Valor em Dinheiro', fontsize=12)
plt.title('Comparação de Entradas, Saídas e Resultado por Semana', fontsize=15)
plt.xticks([i + bar_width for i in index], df_joined['semana_mes'], rotation=90)  # Centralizando os rótulos
plt.legend()

plt.grid(True, which='both', linestyle='--', linewidth=0.5)
plt.show()
