# Entradas e Saidas
# Saldo semanal
import matplotlib.pyplot as plt
import pandas as pd

# df = pd.read_csv('dados.csv', sep=',')
df = pd.read_csv('../regressaolinear/compilado/dados.csv', sep=',')

# Número de grupos de barras
n_groups = len(df)

# Criação de índices para cada grupo de barras
index = range(n_groups)

# Largura das barras
bar_width = 0.25

# Configurando e plotando o gráfico
plt.figure(figsize=(12, 8))
plt.bar(index, df['entradas'], bar_width, color='blue', label='Entradas')
plt.bar([i + bar_width for i in index], df['saidas'], bar_width, color='red', label='Saídas')
plt.bar([i + bar_width * 2 for i in index], df['resultado'], bar_width, color='green', label='Resultado')

plt.xlabel('Semana - Mês', fontsize=12)
plt.ylabel('Valor em Dinheiro', fontsize=12)
plt.title('Comparação de Entradas, Saídas e Resultado por Semana', fontsize=15)
plt.xticks([i + bar_width for i in index], df['semana_mes'], rotation=90)  # Centralizando os rótulos
plt.legend()

plt.grid(True, which='both', linestyle='--', linewidth=0.5)
plt.show()
