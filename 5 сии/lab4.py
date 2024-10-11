import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import random
import math

# Чтение данных
data = pd.read_csv('diabetes.xls')

# Обработка отсутствующих значений
data.iloc[:, :-1] = data.iloc[:, :-1].groupby(data['Outcome']).apply(lambda x: x.replace(0, x.median()))
print(data.describe())

# Визуализация статистики по датасету 
rows_count = data.shape[0]
bins_count = 1 + int(math.log(rows_count, 2))

fig = plt.figure(figsize=(9, 9))
for i, column in enumerate(data.columns):
    ax = fig.add_subplot(4, 4, i + 1)
    hist = ax.hist(data[column], bins=bins_count, edgecolor="black")
    ax.axvline(data[column].mean(), color="red")
    ax.axvline(data[column].mean() - data[column].std(), color="yellow")
    ax.axvline(data[column].mean() + data[column].std(), color="yellow")
    ax.set_title(column, fontsize=16)

fig.tight_layout()
plt.show()

X = data.drop(columns=["Outcome"])
y = data["Outcome"].astype(int)
X = (X - X.mean()) / (X.max() - X.min())

# Визуализация 3D-графика
r_columns = random.sample(list(X.columns), 3)
df = X[r_columns].join(y)

fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(111, projection='3d')

colors = {0: "blue", 1: "red"}
for label in df['Outcome'].unique():
    subset = df[df['Outcome'] == label]
    ax.scatter(subset[r_columns[0]], subset[r_columns[1]], subset[r_columns[2]], 
               color=colors[label], label=f'Class {label}', alpha=0.6)

ax.set_title('3D Scatter plot of three features colored by Outcome')
ax.set_xlabel(r_columns[0])
ax.set_ylabel(r_columns[1])
ax.set_zlabel(r_columns[2])
ax.legend()
plt.show()


# Преобразование данных в матрицу и вычисление ковариационной матрицы
X_mat = np.asarray(X)
cov = np.cov(X_mat, rowvar=False)

# Вычисление собственных значений и векторов
eig_vals, eig_vectors = np.linalg.eig(cov)
eig = [[eig_vals[i], eig_vectors[:, i]] for i in range(len(eig_vals))]
eig.sort(key=lambda t: t[0], reverse=True)

# Первые три вектора
pca = np.array([eig[i][1] for i in range(3)])
X_reduced = np.matmul(X_mat, pca.T)
X_reduced = pd.DataFrame(X_reduced, columns=["pca1", "pca2", "pca3"]).join(y.rename('Outcome'))

# Визуализация PCA
fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(111, projection='3d')

for label in X_reduced['Outcome'].unique():
    values = X_reduced[X_reduced['Outcome'] == label]
    ax.scatter(values['pca1'], values['pca2'], values['pca3'], color=colors[label], label=f'Class {label}')

ax.set_title('3D PCA Scatter Plot')
ax.set_xlabel('PCA 1')
ax.set_ylabel('PCA 2')
ax.set_zlabel('PCA 3')
ax.legend()
ax.view_init(elev=-140, azim=60)
plt.show()


# Разделение на тренировачный и тестовый наборы данных
np.random.seed(42)
shuffled_indices = np.random.permutation(data.index)
test_size = int(0.2 * len(data))
test_indices = shuffled_indices[:test_size]
train_indices = shuffled_indices[test_size:]

test_data = data.loc[test_indices]
train_data = data.loc[train_indices]

X_train = train_data.drop('Outcome', axis=1)
y_train = train_data['Outcome']
X_test = test_data.drop('Outcome', axis=1)
y_test = test_data['Outcome']

# Реализация метода k-ближайших соседей
class KNearestNeighbors:
    def __init__(self, n_neighbors=5):
        self.n_neighbors = n_neighbors

    def fit(self, X_train, y_train):
        self.X_train, self.y_train = X_train, y_train

    def _euclidean_distances(self, x_test_i):
        return np.sqrt(np.sum((self.X_train - x_test_i) ** 2, axis=1))

    def _make_prediction(self, x_test_i):
        x_test_i = np.asarray(x_test_i)
        distances = self._euclidean_distances(x_test_i)   
        k_nearest_indexes = np.argsort(distances)[:self.n_neighbors]
        targets = self.y_train.iloc[k_nearest_indexes].values.astype(int)  
        return np.bincount(targets).argmax()

    def predict(self, X_test):
        X_test = np.asarray(X_test)
        return np.array([self._make_prediction(x) for x in X_test])

# Матрицы ошибок
def confusion_matrix(y_true, y_pred):
    classes = np.unique(np.concatenate((y_true, y_pred)))
    cm = pd.DataFrame(0, index=classes, columns=classes)
    for true, pred in zip(y_true, y_pred):
        cm.loc[true, pred] += 1
    return cm
    
# Применение метода
def evaluate_knn(X_train, y_train, X_test, y_test, k_values):
    results = {}
    for k in k_values:
        knn = KNearestNeighbors(n_neighbors=k)
        knn.fit(X_train, y_train)
        y_pred = knn.predict(X_test)
        cm = confusion_matrix(y_test, y_pred)
        results[k] = cm
        print(f'Confusion Matrix for k={k}:\n{cm}\n')
    return results

# Фиксированный набор признаков
fixed_features = ['Pregnancies', 'Glucose', 'BMI', 'Age']
X_train_fixed = X_train[fixed_features]
X_test_fixed = X_test[fixed_features]

# Модель 1: случайно выбранные признаки
random_features = random.sample(list(X_train.columns), 3)
X_train_random = X_train[random_features]
X_test_random = X_test[random_features]


print("Оценка модели с фиксированным набором признаков:")
k_values = [3, 5, 10, 50, 100]
evaluate_knn(X_train_fixed, y_train, X_test_fixed, y_test, k_values)

print("Оценка модели с случайно выбранными признаками:")
evaluate_knn(X_train_random, y_train, X_test_random, y_test, k_values)
