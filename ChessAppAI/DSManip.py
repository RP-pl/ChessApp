import random

import numpy
import numpy as np
from tensorflow import keras
from tensorflow.keras.preprocessing.sequence import pad_sequences


def prerpareDatasets(t,dataset):
    (tokenized, length) = t.tokenize(dataset.split(' '))
    numb = random.randint(0,len(tokenized)-1)
    token = tokenized[:numb]
    print(token.shape)
    #token = token[numpy.newaxis, ...]
    y = tokenized[numb]
    #y = y[numpy.newaxis, ...]
    return token,y
def getData(t,start,end):
    s = open("C:\\Users\\RP\\PycharmProjects\\ChessAppAI\\f.txt", "r").read().split('\n')[start:end]
    x, y = list(), list()
    for element in s:
        x1, y1 = prerpareDatasets(t, element)
        x.append(x1)
        y.append(y1)
    x = keras.preprocessing.sequence.pad_sequences(x)
    y = keras.preprocessing.sequence.pad_sequences(y)
    return x,y
def prepareFit(t,fit):
        (tokenized, length) = t.tokenize(fit.split(' '))
        token = tokenized[:]
        token = keras.preprocessing.sequence.pad_sequences(token)
        token = token[numpy.newaxis, ...]
        return token