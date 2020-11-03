from tensorflow.keras.preprocessing.text import Tokenizer
import numpy
class Token:
    def __init__(self):
        file = open('C:\\Users\\RP\\PycharmProjects\\ChessAppAI\\ChessAppAI\\unique.txt')
        file = file.read().replace("\n"," ").split(' ')
        file = [move.split('.')[1] for move in file if len(move.split('.'))>1]
        self.t = Tokenizer(split=' ',filters=' ')
        self.t.fit_on_texts(file)
    def tokenize(self,dataset):
        arr = self.t.texts_to_matrix(dataset)
        return arr, len(self.t.word_index)