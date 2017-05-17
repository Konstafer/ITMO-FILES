import numpy as np

def coef(points):

    '''x : array of data points
       y : array of f(x)  '''

    n = len(points)
    a = []

    for i in range(n):
        a.append(points[i][1])

    for j in range(1, n):
        for i in range(n-1, j-1, -1):
            a[i] = float(a[i]-a[i-1])/float(points[i][0]-points[i-j][0])

    return np.array(a) # return an array of coefficient

def eval(a, points, r):

    '''     a : array returned by function coef()
       points : array of data points
            r : the node to interpolate at  '''

    n = len( a ) - 1
    temp = a[n]

    for i in range( n - 1, -1, -1 ):
        temp = temp * ( r - points[i][0] ) + a[i]
    return temp # return the y_value interpolation