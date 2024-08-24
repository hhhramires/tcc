import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import pandas as pd
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.layers import Conv1D, Dense, Flatten
from tensorflow.keras.models import Sequential


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

# Normalizando os dados
scaler = MinMaxScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_val)

# Remodelando os dados para o formato (samples, time steps, features) para a CNN
X_train_scaled = X_train_scaled.reshape((X_train_scaled.shape[0], X_train_scaled.shape[1], 1))
X_test_scaled = X_test_scaled.reshape((X_test_scaled.shape[0], X_test_scaled.shape[1], 1))

# Construindo o modelo CNN
model = Sequential()
model.add(Conv1D(filters=64, kernel_size=2, activation='relu', input_shape=(X_train_scaled.shape[1], 1)))
model.add(Flatten())
model.add(Dense(50, activation='relu'))
model.add(Dense(1))
model.compile(optimizer='adam', loss='mse')

# Treinando o modelo
model.fit(X_train_scaled, y_train, epochs=200, verbose=1)

# Fazendo previsões
y_pred = model.predict(X_test_scaled)

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
