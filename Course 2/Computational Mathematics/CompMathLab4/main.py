
from random import uniform
import matplotlib.pyplot as plt
import numpy as np
from splines import find_splines, calc_spline

def model_function(x):
    return x*x*x
def build_sin_graph(points, splines, i, title):
    t1 = np.arange(points[0][0], points[-1][0], 0.01)
    plt.subplot(220 + i)
    plt.plot(t1, model_function(t1))
    plt.plot(t1, [calc_spline(splines, x) for x in t1])
    for point in points:
        plt.plot(point[0], point[1], 'ro')
    plt.title(title)
def generate_point(func, min, max, amount):
    res = sorted([uniform(min, max) for x in range(amount)])
    return list(map(lambda x: (x, func(x)), res))


#============ 3-4 points sin(x) from 0 to 2Pi
pivot_points = generate_point(func=model_function, min=0, max=2 * np.pi, amount=4)
splines_solution = find_splines(pivot_points)
build_sin_graph(pivot_points, splines_solution, 1, "4 points")
#============ 8-10 from 0 to 2Pi
pivot_points = generate_point(model_function, 0, 2 * np.pi, 10)
splines_solution = find_splines(pivot_points)
build_sin_graph(pivot_points, splines_solution, 2, "10 points")
#============ Shuffle 1 dot
pivot_points[3] = (pivot_points[3][0], 10)
splines_solution = find_splines(pivot_points)
build_sin_graph(pivot_points, splines_solution, 3, "shuffle dot")
#============ 8-10 from 0 to 50Pi
pivot_points = generate_point(model_function, 0, 50 * np.pi, 10)
splines_solution = find_splines(pivot_points)
build_sin_graph(pivot_points, splines_solution, 4, "50Pi")
#=========================================

plt.show()