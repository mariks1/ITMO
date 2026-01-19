import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


data = pd.read_csv("diabetes.csv")
print(data.describe())


missing_values = data.isnull().sum()
print("Отсутствующие значения в каждой колонне:", missing_values, sep='\n')

labels_to_remove = ['Glucose', 'BloodPressure', 'SkinThickness', 'BMI']
zero_values = (data[labels_to_remove] == 0).sum()
print("Нулевые значения в каждой колонне:", zero_values, sep='\n')

rows_to_delete = (data[labels_to_remove] == 0).any(axis=1).sum()
data = data[(data[labels_to_remove] != 0).all(axis=1)]
print("Удалённые ряды:", rows_to_delete)
print(data.describe())

data = (data - data.min()) / (data.max() - data.min())
print(data.describe())

desc = data.describe()

for col in data.columns:
    if col == 'Outcome':
        continue
    plt.figure()
    data[col].hist(bins=len(data[col].unique()))
    
    mean = desc[col]['mean']
    std = desc[col]['std']
    min = desc[col]['min']
    q_25 = desc[col]['25%']
    median = desc[col]['50%']
    q_75 = desc[col]['75%']
    max = desc[col]['max']
    
    plt.axvline(mean, color='r', linestyle='dashed', linewidth=2)
    plt.text(mean, plt.ylim()[1]*0.9, 'Mean', color='r', ha='center')
    
    plt.axvline(mean + std, color='g', linestyle='dashed', linewidth=1)
    plt.text(mean + std, plt.ylim()[1]*0.8, 'Mean + σ', color='g', ha='center')
    
    plt.axvline(mean - std, color='g', linestyle='dashed', linewidth=1)
    plt.text(mean - std, plt.ylim()[1]*0.8, 'Mean - σ', color='g', ha='center')
    
    plt.axvline(min, color='b', linestyle='dashed', linewidth=2)
    plt.text(min, plt.ylim()[1]*0.9, 'Min', color='b', ha='center')
    
    plt.axvline(q_25, color='y', linestyle='dashed', linewidth=1)
    plt.text(q_25, plt.ylim()[1]*0.7, '25%', color='c', ha='center')
    
    plt.axvline(median, color='y', linestyle='dashed', linewidth=1)
    plt.text(median, plt.ylim()[1]*0.7, '50%', color='m', ha='center')
    
    plt.axvline(q_75, color='y', linestyle='dashed', linewidth=1)
    plt.text(q_75, plt.ylim()[1]*0.7, '75%', color='y', ha='center')
    
    plt.axvline(max, color='b', linestyle='dashed', linewidth=2)
    plt.text(max, plt.ylim()[1]*0.9, 'Max', color='k', ha='center')
    
    plt.title(col)
    plt.show()


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


class LogisticRegression:
    def __init__(self, learning_rate=0.01, num_iterations=1000, fit_intercept=True, threshold=0.5, optimization_method='gradient_descent'):
        self.theta = None
        self.learning_rate = learning_rate
        self.num_iterations = num_iterations
        self.fit_intercept = fit_intercept
        self.threshold = threshold
        self.optimization_method = optimization_method
        
    def __add_intercept(self, x):
        intercept = np.ones((x.shape[0], 1))
        return np.concatenate((intercept, x), axis=1)
    
    def sigmoid(self, z):
        return 1 / (1 + np.exp(-z))
    
    def log_loss(self, h, y):
        # Добавляем небольшое значение для предотвращения логарифма от 0
        eps = 1e-15
        h = np.clip(h, eps, 1 - eps) # задаем ограничения
        return (-y * np.log(h) - (1 - y) * np.log(1 - h)).mean()
    
    def fit_gradient_descent(self, x, y):
        for i in range(self.num_iterations):
            z = np.dot(x, self.theta)
            h = self.sigmoid(z)
            gradient = np.dot(x.T, (h - y)) / y.size
            self.theta -= self.learning_rate * gradient
            
            if i % 100 == 0:
                loss = self.log_loss(h, y)
                print(f'Итерация {i}: loss {loss}')
                
    def fit_newton(self, x, y):
        for i in range(self.num_iterations):
            z = np.dot(x, self.theta)
            h = self.sigmoid(z)
            gradient = np.dot(x.T, (h - y)) / y.size
            # Гессиан 
            diag = h * (1 - h)
            H = np.dot(x.T, x * diag[:, np.newaxis]) / y.size
            
            # Обновление весов
            try:
                delta = np.linalg.inv(H).dot(gradient)
            except np.linalg.LinAlgError:
                print("Гессиан вырожден. Прекращаем обучение.")
                break
            
            self.theta -= delta
            
            if i % 100 == 0:
                loss = self.log_loss(h, y)
                print(f'Итерация {i}: loss {loss}')
                
    def fit(self, x, y):
        if self.fit_intercept:
            x = self.__add_intercept(x)
            
            # Инициализация весов нулями
            self.theta = np.zeros(x.shape[1])
            
            # В зависимости от метода оптимизации выбираем способ обучения
            if self.optimization_method == 'gradient_descent':
                self.fit_gradient_descent(x, y)
            elif self.optimization_method == 'newton':
                self.fit_newton(x, y)
            else:
                raise ValueError(f"Неизвестный метод оптимизации: {self.optimization_method}")
            
    def predict_prob(self, x):
        if self.fit_intercept:
            x = self.__add_intercept(x)
            
        return self.sigmoid(np.dot(x, self.theta))
    
    def predict(self, x):
        return self.predict_prob(x) >= self.threshold
    

import itertools

learning_rates = [1, 0.5, 0.1, 0.01, 0.001]
num_iterations = [10, 100, 1000, 10000]
optimization_methods = ['gradient_descent', 'newton']

parameter_combinations = list(itertools.product(learning_rates, num_iterations, optimization_methods))

def calculate_metrics(y_true, y_pred):
    TP = np.sum((y_true == 1) & (y_pred == 1))
    TN = np.sum((y_true == 0) & (y_pred == 0))
    FP = np.sum((y_true == 0) & (y_pred == 1))
    FN = np.sum((y_true == 1) & (y_pred == 0))
    
    confusion_matrix = np.array([[TN, FP], [FN, TP]])
    accuracy = (TP + TN) / (TP + TN + FP + FN + 1e-15)
    precision = TP / (TP + FP + 1e-15)
    recall = TP / (TP + FN + 1e-15)
    
    f1_score = 2 * (precision * recall) / (precision + recall + 1e-15)
    
    return confusion_matrix, accuracy, precision, recall, f1_score

# Преобразуем данные в NumPy массивы
x_train_np = X_train.values
x_test_np = X_test.values
y_train_np = y_train.values
y_test_np = y_test.values

results = []

for lr, num_iter, method in parameter_combinations:
    print(f"Модель с параметрами: learning_rate={lr}, num_iterations={num_iter}, optimization_method={method}")
    model = LogisticRegression(learning_rate=lr, num_iterations=num_iter, threshold=0.5, optimization_method=method)
    model.fit(x_train_np, y_train_np)
    y_pred = model.predict(x_test_np).astype(int)
    
    # Вычисляем метрики
    cm, acc, prec, rec, f1 = calculate_metrics(y_test_np, y_pred)
    
    # Сохраняем результаты
    results.append({
        'optimization_method': method,
        'learning_rate': lr,
        'num_iterations': num_iter,
        'accuracy': acc,
        'precision': prec,
        'recall': rec,
        'f1_score': f1
    })
    
results_df = pd.DataFrame(results)
print(results_df)

import seaborn as sns

sns.set_context("talk", font_scale=0.5)
sns.color_palette("bright")

params = ['accuracy', 'precision', 'recall', 'f1_score']

for param in params:
    g = sns.catplot(x='optimization_method', y=param, hue='learning_rate', col='num_iterations', data=results_df, kind='bar', height=4, aspect=0.6)
    g.fig.subplots_adjust(top=0.85)
    g.fig.suptitle(f"{param} по методам оптимизации и скорости обучения")
    plt.show()


# Функция для выбора лучшей модели на основе f1_socre и accuracy
def select_best_model(res):
    # Сначала сортируем по f1-Score, затем по accuracy
    sorted_df = res.sort_values(by=['f1_score', 'accuracy'], ascending=False)
    best = sorted_df.iloc[0]
    return best

# Выбираем лучшую модель
best_model = select_best_model(results_df)

# Печатаем информацию о лучшей модели
print("Лучшая модель:")
print(f"Метод оптимизации: {best_model['optimization_method']}")
print(f"Коэффициент обучения: {best_model['learning_rate']}")
print(f"Количество итераций: {best_model['num_iterations']}")
print(f"Accuracy: {best_model['accuracy']:.4f}")
print(f"Precision: {best_model['precision']:.4f}")
print(f"Recall: {best_model['recall']:.4f}")
print(f"F1-Score: {best_model['f1_score']:.4f}")