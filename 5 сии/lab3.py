import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# Реализация линейной регрессии
class LinearRegression:
    def fit(self, X, y):
        X = np.insert(X, 0, 1, axis=1)
        XT_X_inv = np.linalg.inv(X.T @ X)
        weights = np.linalg.multi_dot([XT_X_inv, X.T, y])
        self.bias, self.weights = weights[0], weights[1:]
    
    def predict(self, X_test):
        return X_test @ self.weights + self.bias

# Z-нормализация
def z_normalization(data):
    norm_data = data.copy()
    data_col = norm_data.columns    
    for col in data_col:
        mean = norm_data[col].mean()
        std = norm_data[col].std()
        norm_data[col] = (norm_data[col] - mean) / std
    return norm_data

# Коэффициент детерминации
def r2_score(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    return 1 - (ss_res / ss_tot)

# Загрузка данных
data = pd.read_csv('Student_Performance.csv')

# Кодирование категориальных признаков
data["Extracurricular Activities"] = data["Extracurricular Activities"].apply(lambda x: 1 if x == "Yes" else 0)

# Заполнение пропусков 
data = data.apply(lambda col: col.replace(0, col.median()))

# Статистика
print("Основные статистики по датасету:")
print(data.describe())

# Визуализация
data.hist(figsize=(10, 8))
plt.show()

# Нормализованные данный
normalized_data = z_normalization(data)

# Разделение данных на обучающий и тестовый наборы данных
np.random.seed(42)
shuffled_indices = np.random.permutation(normalized_data.index)
test_size = int(0.2 * len(normalized_data))
test_indices = shuffled_indices[:test_size]
train_indices = shuffled_indices[test_size:]

test_data = normalized_data.loc[test_indices]
train_data = normalized_data.loc[train_indices]

X_train = train_data.drop('Performance Index', axis=1)
y_train = train_data['Performance Index']
X_test = test_data.drop('Performance Index', axis=1)
y_test = test_data['Performance Index']


# Модель 1: Все признаки
linear_regression_all = LinearRegression()
linear_regression_all.fit(X_train, y_train)
y_pred_all  = linear_regression_all.predict(X_test)
r2_all = r2_score(y_test, y_pred_all)


# Модель 2: Без "Hours Studied"
X_train_model_2 = X_train.drop('Hours Studied', axis=1)
X_test_model_2 = X_test.drop('Hours Studied', axis=1)

linear_regression_model_2 = LinearRegression()
linear_regression_model_2.fit(X_train_model_2, y_train)
y_pred_model_2 = linear_regression_model_2.predict(X_test_model_2)
r2_no_extracurricular = r2_score(y_test, y_pred_model_2)


# Модель 3: Используем только "Hours Studied" и "Previous Scores"
X_train_model_3 = X_train[['Hours Studied', 'Previous Scores']]
X_test_model_3 = X_test[['Hours Studied', 'Previous Scores']]

linear_regression_model_3 = LinearRegression()
linear_regression_model_3.fit(X_train_model_3, y_train)
y_pred_model_3 = linear_regression_model_3.predict(X_test_model_3)
r2_model_3 = r2_score(y_test, y_pred_model_3)


# Модель 4: Используем только "Hours Studied"
X_train_model_4 = X_train[['Hours Studied']]
X_test_model_4 = X_test[['Hours Studied']]

linear_regression_model_4 = LinearRegression()
linear_regression_model_4.fit(X_train_model_4, y_train)
y_pred_model_4 = linear_regression_model_4.predict(X_test_model_4)
r2_model_4 = r2_score(y_test, y_pred_model_4)


print(f"Модель 1 (Все признаки) - R^2: {r2_all}")
print(f"Модель 2 (Без 'Hours Studied') - R^2: {r2_no_extracurricular}")
print(f"Модель 3 (Hours Studied и Previous Scores) - R^2: {r2_model_3}")
print(f"Модель 4 (Только Hours Studied) - R^2: {r2_model_4}")


# Синтетический признак
X_train_model_5 = X_train.copy()
X_test_model_5 = X_test.copy()

X_train_model_5["Hours_eff"] = X_train_model_5["Hours Studied"] + X_train_model_5["Sleep Hours"]
X_test_model_5["Hours_eff"] = X_test_model_5["Hours Studied"] + X_test_model_5["Sleep Hours"]

linear_regression_model_5 = LinearRegression()
linear_regression_model_5.fit(X_train_model_5, y_train)
y_pred_model_5 = linear_regression_model_5.predict(X_test_model_5)
r2_model_5 = r2_score(y_test, y_pred_model_5)

print(f"Модель 5 - R^2: {r2_model_5}")
