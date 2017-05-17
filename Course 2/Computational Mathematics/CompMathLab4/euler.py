def euler(x, y, last_x, e, func):
    """
    :param x: starting x point
    :param y: f(x) - value of the function in the x
    :param last_x: last x which will be used in computations
    :param e: accuracy
    :param func: f(x) - function itself
    :return:
    """

    def get_prediction(prev_point, step):
        """
        calculates prediction of y coordinate
        :param prev_point: last point we found
        :param step: step which we will make
        :return: predicted y coordinate
        """
        return prev_point[1] + step * func(prev_point[0], prev_point[1])


    def get_y(prev_point, step):
        """
        :param prev_point: last point we found
        :param step: step which we will make
        :return Y - found coordinate
        """
        prediction = get_prediction(prev_point, step)

        return {
            "y": prediction,
        }

    points = [(x, y)]       # array of points
    h = 0.1                 # initial size of the step
    while points[-1][0] < last_x:
        prev = points[-1]   # last point in the array
        # python has no do while so we use this
        res = get_y(prev, h)
        while h > e:
            h /= 2
            if (h == 0):
                raise Exception("can't reach accuracy")
            res = get_y(prev, h)
        points.append((prev[0] + h, res["y"]))
        #restore step
        h = 0.1
    return points