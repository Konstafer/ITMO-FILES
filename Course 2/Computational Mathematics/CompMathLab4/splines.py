def next_h(point, previous_point):
    return point[0] - previous_point[0]

def find_shuttle_coefficients(points):
    def next_alpha(A, B, C, alpha=0):
        return -B / (A * alpha + C)
    def next_beta(A, B, C, F, alpha=0, beta=0):
        return (F - A * beta) / (A * alpha + C)
    def find_A_B_C_F(point):
        index = points.index(point)
        prev_point = points[index - 1]
        pprev_point = points[index - 2]
        h = next_h(point, prev_point)
        left_h = next_h(prev_point, pprev_point)
        return {
            "A": left_h,
            "C": 2 * (left_h + h),
            "B": h,
            "F": 3 * ( (point[1] - prev_point[1]) / h - (prev_point[1] - pprev_point[1]) / left_h)
        }
    def fill_coefficient(alphas_betas, coefficients):
        complete_coefficients = []
        for i in range(len(alphas_betas)):
            j = i
            complete_coefficients.append({
                "A": coefficients[j]["A"],
                "B": coefficients[j]["B"],
                "C": coefficients[j]["C"],
                "F": coefficients[j]["F"],
                "alpha": alphas_betas[i]["alpha"],
                "beta":alphas_betas[i]["beta"]
            })
        return complete_coefficients

    # array of hash maps
    coefficients = []
    for i in range(2, len(points)):
        coefficients.append(find_A_B_C_F(points[i]))

    alphas_betas = [
        {
            "alpha": next_alpha(coefficients[0]["A"], coefficients[0]["B"], coefficients[0]["C"]),
            "beta": next_beta(coefficients[0]["A"], coefficients[0]["B"], coefficients[0]["C"], coefficients[0]["F"])
        }
    ]
    for i in range(3, len(points)):
        coofs = coefficients[i - 3]
        # compensate to easily pick alphas and betas
        j = i - 3
        prev_alpha = alphas_betas[j]["alpha"]
        prev_beta = alphas_betas[j]["beta"]
        alphas_betas.append(
            {
                "alpha": next_alpha(coofs["A"], coofs["B"], coofs["C"], prev_alpha),
                "beta": next_beta(coofs["A"], coofs["B"], coofs["C"], coofs["F"], prev_alpha, prev_beta)
            }
        )

    return fill_coefficient(alphas_betas, coefficients)

def find_splines(points):

    def get_coefficient_a(previous_point):
        '''
        :param point: cortege (x, y)
        :return: coefficient a
        '''
        return previous_point[1]
    def get_coefficient_b(point, previous_point, coefficient_c, next_c):
        h = next_h(point, previous_point)
        return (point[1] - previous_point[1]) / h - h * (next_c + 2 * coefficient_c) / 3
    def get_coefficient_d(coefficient_c, next_c, point, previous_point):
        h = next_h(point, previous_point)
        return (next_c - coefficient_c) / (3 * h)
    def get_coefficient_c(Alpha, Beta, A, C, F):
        return (F - A * Beta) / (C + A * Alpha)

    a = [0 for i in range(len(points) - 1)]
    b = [0 for i in range(len(points) - 1)]
    c = [0 for i in range(len(points) - 1)]
    d = [0 for i in range(len(points) - 1)]

    coefficients = find_shuttle_coefficients(points)

    # to make code shorter
    coofs = coefficients[len(coefficients) - 1]

    # x(n) = (F(n) - A(n)Beta(n)) / (C(n) + A(n)Alpha(n)
    c[-1] = (coofs["F"] - coofs["A"] * coofs["beta"]) / (coofs["C"] + coofs["A"] * coofs["alpha"])

    # find all c's
    for i in reversed(range(1, len(c) - 1)):
        coofs = coefficients[i]
        c[i] = coofs["alpha"]*c[i + 1] + coofs["beta"]

    for i in range(1, len(b)):
        b[i - 1] = get_coefficient_b(points[i], points[i - 1], c[i - 1], c[i])
        d[i - 1] = get_coefficient_d(c[i - 1], c[i], points[i], points[i - 1])
        a[i - 1] = points[i - 1][1]

    h_n = next_h(points[-1], points[-2])

    a[-1] = points[-2][1]
    b[-1] = (points[-1][1] - points[-2][1])/h_n - (2/3)*(h_n)*c[-1]
    d[-1] = -(c[-1] / (3 * h_n))

    result = []
    for i in range(len(a)):
        result.append({
            "x": points[i][0],
            "a": a[i],
            "b": b[i],
            "c": c[i],
            "d": d[i]
        })
    return result

def calc_spline(splines, x):
    distribution = sorted(spline["x"] for spline in splines)
    index = 0
    for i in range(len(distribution)):
        if x >= distribution[i]:
            index = i
    spline = splines[index]
    dx = x - spline["x"]
    return spline["a"] + spline["b"] * dx + spline["c"] * dx**2 + spline["d"] * dx**3