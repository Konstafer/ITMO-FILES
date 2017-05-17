# coding=utf-8
'''
Вычислительная математика
Лабораторная работа №5
Вариант 2
'''
from random import uniform
import matplotlib.pyplot as plt
import numpy as np
from lagrange import lagrange

def model_function(x):
    return np.cos(x)


def build_graph(points, i, title):
    t1 = np.arange(points[0][0], points[-1][0], 0.01)
    plt.subplot(220 + i)
    plt.plot(t1, model_function(t1))
    plt.plot(t1, [lagrange( points, r) for r in t1])
    for point in points:
        plt.plot(point[0], point[1], 'ro')
    plt.title(title)


def generate_point(func, min, max, amount):
    res = sorted([uniform(min, max) for x in range(amount)])
    return list(map(lambda x: (x, func(x)), res))

def chebyshev_nodes (func,k):
   res = []
   m = 0
   for m in range(k):
        res.append(0)
        res[m] = np.cos((2*m + 1) * np.pi / (2 * k))
   res.sort()
   return list(map(lambda x: (x, func(x)), res))


# ============ 3-4 points F(x) from 0 to 2Pi
pivot_points = chebyshev_nodes(func=model_function,k=2)
#lagrange_solution = lagrange(pivot_points)
build_graph(pivot_points,  1, "2 points")
# ============ 8-10 from 0 to 2Pi
pivot_points = chebyshev_nodes(model_function, 5)
#lagrange_solution  = lagrange (pivot_points)
build_graph(pivot_points,  2, "5 points")
# ============ Shuffle 1 dot
pivot_points = chebyshev_nodes(model_function, 10)
#lagrange_solution  = lagrange (pivot_points)
build_graph(pivot_points,  3, "10 points")
# ============
pivot_points = chebyshev_nodes(model_function, 50)
#lagrange_solution  = lagrange (pivot_points)
build_graph(pivot_points,  4, "50 points")

plt.show()
