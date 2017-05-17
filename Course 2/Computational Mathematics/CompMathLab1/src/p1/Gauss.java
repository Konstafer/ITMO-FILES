package p1;

 class Gauss {

    static public void primarilyStep(Matrix matrix) {
        matrix.makeUpperTriangular();
    }

    static public Double[] backwardStep(Matrix matrix) {
        Double[] xList = new Double[matrix.getSize()];
        for (int i = 0; i < matrix.getSize(); ++i) {
            xList[i] = null;
        }
        double currentB = 0;
        for (int i = matrix.getSize() - 1; i >= 0; --i) {
            currentB = matrix.B()[i];
            for (int j = matrix.getSize() - 1; j > i; --j) {
                currentB -= matrix.row(i)[j] * xList[j];
            }
            double x = currentB / matrix.row(i)[i];
            xList[i] = x;
        }
        return xList;
    }

    static public Double[] findResiduals(Matrix matrix, Double[] xList) {
        double xSum;
        double currentResidual;
        Double[] residuals = new Double[matrix.getSize()];

        for (int i = 0; i < matrix.getSize(); ++i) {
            xSum = 0;
            currentResidual = 0;
            double[] arr = matrix.row(i);

            for (int j = 0; j < matrix.getSize(); ++j) {
                xSum += xList[j] * arr[j];
                currentResidual = matrix.B()[i] - xSum;
            }
            residuals[i] = currentResidual;
        }
        return residuals;
    }

    static public int findIndexOfMaxElement(double[] array, int startWith) {
        if (array.length == 0) {
            return -1;
        }

        double maxElem = array[startWith];
        int maxElementIndex = startWith;

        for (int j = 0; j < array.length; ++j) {
            if (j < startWith) {
                continue;
            }
            if (Math.abs(array[j]) > maxElem) {
                maxElem = Math.abs(array[j]);
                maxElementIndex = j;
            }
        }

        return maxElementIndex;
    }

    static public Double[] equate(Matrix matrix) {
        primarilyStep(matrix);
        Double[] xList = backwardStep(matrix);
        return xList;
    }

}
