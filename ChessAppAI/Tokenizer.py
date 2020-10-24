from tensorflow.keras.preprocessing.text import Tokenizer
import numpy
class Token:
    def __init__(self):
        file = open('ChessAppAI/unique.txt')
        file = file.read().replace("\n"," ").split(' ')
        self.t = Tokenizer(split=' ')
        self.t.fit_on_texts(file)
    def tokenize(self,dataset):
        arr = self.t.texts_to_matrix(dataset)
        return arr, len(self.t.word_index)