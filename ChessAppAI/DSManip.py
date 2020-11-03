import random

import numpy
import numpy as np
from tensorflow import keras
from tensorflow.keras.preprocessing.sequence import pad_sequences


def prerpareDatasets(t,dataset):
    (tokenized, length) = t.tokenize([move.split('.')[1] for move in dataset.split(' ') if len(move.split('.'))>1])
    if len(tokenized)!=0:
        numb = random.randint(0,len(tokenized)-1)
        token = tokenized[:numb]
        y = tokenized[numb]
        print(token.shape)
        return token, y
    return None,None
def getData(t,start,end):
    s = open("C:\\Users\\RP\\PycharmProjects\\ChessAppAI\\f.txt", "r").read().split('\n')[start:end]
    x, y = list(), list()
    for element in s:
        x1, y1 = prerpareDatasets(t, element)
        if x1 is not None:
            x.append(x1)
            y.append(y1)
    x = keras.preprocessing.sequence.pad_sequences(x)
    y = keras.preprocessing.sequence.pad_sequences(y)
    return x,y
def prepareFit(t,fit):
        (tokenized, length) = t.tokenize([move.split('.')[1] for move in fit.split(' ') if len(move.split('.'))>1])
        token = tokenized[:]
        token = keras.preprocessing.sequence.pad_sequences(token)
        token = token[numpy.newaxis, ...]
        return token