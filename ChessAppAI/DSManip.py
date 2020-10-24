import numpy
import numpy as np
from tensorflow import keras
from tensorflow.keras.preprocessing.sequence import pad_sequences


def prerpareDatasets(t,dataset):
    (tokenized, length) = t.tokenize(dataset.split(' '))
    token = tokenized[:-1]
    #token = token[numpy.newaxis, ...]
    print(token.shape)
    y = tokenized[len(tokenized) - 1][-1]
    y = y[numpy.newaxis, ...]
    return token,y
def getData(t,start,end):
    s = open("f.txt", "r").read().split('\n')[start:end]
    x, y = list(), list()
    for element in s:
        x1, y1 = prerpareDatasets(t, element)
        x.append(x1)
        y.append(y1)
    x = keras.preprocessing.sequence.pad_sequences(x)
    y = keras.preprocessing.sequence.pad_sequences(y)
    return x,y