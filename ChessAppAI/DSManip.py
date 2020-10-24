import numpy


def prerpareDatasets(t,dataset):
    (tokenized, length) = t.tokenize(dataset.split(' '))
    token = tokenized[:-1]
    token = token[numpy.newaxis, ...]
    print(token.shape)
    y = tokenized[len(tokenized) - 1][-1]
    y = y[numpy.newaxis, ...]
    return token,y,length+1