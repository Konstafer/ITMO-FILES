'''
Вычислительная математика
Лабораторная работа №4
Вариант: Эйлера
'''
import matplotlib.pyplot as plt
import numpy as np
from splines import find_splines, calc_spline
from euler import euler

def first_test_data():
    return {
        "equation": lambda x, y: x ** 2 - 2 * y,
        "solution": lambda x: 0.75*np.e**(-2*x) + 0.5*x**2 - 0.5*x + 0.25,
        "condition": (0, 1)
    }


def second_test_data():
    return {
        "equation": lambda x, y: -x**3 + 5*x**2 + 5*y,
        "solution": lambda x: 1/625 * (125*x**3 - 550*x**2 - 220*x + 669*np.e**(5*x) - 44),
        "condition": (0, 1)
    }


def third_test_data():
    return {
        "equation": lambda x, y: np.sin(x) - 5*y,
        "solution": lambda x: 1/26*(27*np.e**(-5*x) + 5*np.sin(x) - np.cos(x)),
        "condition": (0, 1)
    }


def fourth_test_data():
    return {
        "equation": lambda x, y: 2*y + np.cos(x),
        "solution": lambda x: 1/5*(7*np.e**(2*x) + np.sin(x) - 2*np.cos(x)),
        "condition": (0, 1)
    }


def build_graph(points, splines, func, title, i):
    t1 = np.arange(points[0][0], points[-1][0], 0.01)
    plt.subplot(220 + i)
    print(func)
    plt.plot(t1, func(t1))
    plt.plot(t1, [calc_spline(splines, x) for x in t1])
    plt.title(title + str(i))


# ======================================================== #
test = first_test_data()
e = 0.1
last_x = 1
test_number = 1
# initial values of x, y
condition = test["condition"]
# array of points from finding the solution
pivot_points = euler(condition[0], condition[1], last_x, e, test["equation"])
# solution
splines_solution = find_splines(pivot_points)

build_graph(pivot_points, splines_solution, test["solution"], "Cauchy", test_number)


# ======================================================== #
test = second_test_data()
e = 0.1
last_x =2
test_number = 2
# initial values of x, y
condition = test["condition"]
# array of points from finding the solution
pivot_points = euler(condition[0], condition[1], last_x, e, test["equation"])
# solution
splines_solution = find_splines(pivot_points)
build_graph(pivot_points, splines_solution, test["solution"], "Cauchy", test_number)


# ======================================================== #
test = third_test_data()
e = 2
last_x = 20
test_number = 3
# initial values of x, y
condition = test["condition"]
# array of points from finding the solution
pivot_points = euler(condition[0], condition[1], last_x, e, test["equation"])
# solution
splines_solution = find_splines(pivot_points)
build_graph(pivot_points, splines_solution, test["solution"], "Cauchy", test_number)


# ======================================================== #
test = fourth_test_data()
e = 0.01
last_x = 4
test_number = 4
# initial values of x, y
condition = test["condition"]
# array of points from finding the solution
pivot_points = euler(condition[0], condition[1], last_x, e, test["equation"])
# solution
splines_solution = find_splines(pivot_points)
build_graph(pivot_points, splines_solution, test["solution"], "Cauchy", test_number)

plt.show()