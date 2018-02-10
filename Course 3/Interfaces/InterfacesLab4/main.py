import sys
from PyQt5 import QtGui
from PyQt5 import QtCore, QtWidgets
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QFileDialog, QMessageBox
from PyQt5.QtGui import *
from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
import time

class MainWindow(QtWidgets.QWidget):
    def __init__(self ,parent=None):
        super().__init__(parent)
        self.setWindowFlags(QtCore.Qt.FramelessWindowHint|QtCore.Qt.Tool)
        self.setAttribute(QtCore.Qt.WA_TranslucentBackground)
        desktop = QtWidgets.QApplication.desktop()
        screen01 = desktop.primaryScreen() # у меня 2 монитора, определяем главный
        res = desktop.screenGeometry(screen01)
        self.setFixedSize(res.width(), res.height())
        self.move(0, 0)
        grid = QtWidgets.QGridLayout(self)
        self.setLayout(grid)
        self.image = QtWidgets.QLabel('', self)
        pixmap = QPixmap('fig.png')
        self.image.setPixmap(pixmap)
        self.image.setFixedHeight(138)
        self.image.setFixedWidth(253)
        self.image.move((1300/2),0)
        self.speed = QtWidgets.QSpinBox(self)
        self.speed.setGeometry(30, 40, 100, 35)
        self.speed.valueChanged.connect(lambda size: self.speed.value())
        self.buttonExit = QtWidgets.QPushButton('Exit',self)
        self.buttonExit.setGeometry(30,80,50,20)
        self.buttonExit.clicked.connect(self.exitApp)

    def changeValue(self, value):
        self.speed.setValue(value)

    def exitApp(self):
        sys.exit()

    def move_label(self):
        x = -138
        while True:
            self.image.move((1300/2), x)
            QtWidgets.QApplication.processEvents()
            x += 0.5
            time.sleep(self.speed.value()/1000)
            print(self.speed.value()/1000)
            if x == 768:
                x = -138




if __name__ == '__main__':
    app = QtWidgets.QApplication(sys.argv)
    mainWindow=MainWindow()
    mainWindow.show()
    mainWindow.move_label()
    sys.exit(app.exec_())
