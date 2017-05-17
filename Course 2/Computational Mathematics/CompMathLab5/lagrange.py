def lagrange (points, r): #point, arg x
    res=0
    n = len(points)
    for j in range(n):
        numerator=1; denominator=1
        for i in range(n):
            if i==j:
                numerator *= 1 ; denominator *= 1;
            else:
                numerator *= (r-points[i][0])
                denominator *= (points[j][0]-points[i][0])
        res += points[j][1]*numerator/denominator
    return res