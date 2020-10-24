import numpy
import tensorflow.keras as keras

from ChessAppAI.DSManip import prerpareDatasets,getData
from ChessAppAI.Tokenizer import Token

model = keras.models.Sequential([
    keras.layers.GRU(1024,activation='elu',return_sequences=True,input_shape=(None,5098)),
    keras.layers.Dropout(0.25),
    keras.layers.GRU(512,activation='elu',return_sequences=True),
    keras.layers.Dropout(0.25),
    keras.layers.GRU(256,activation='elu',return_sequences=True),
    keras.layers.GRU(128,activation='elu',return_sequences=False),
    keras.layers.Flatten(),
    keras.layers.Dense(5098,activation='elu'),
    keras.layers.Reshape((1,5098))
])
model.compile(optimizer=keras.optimizers.Adadelta(),loss=keras.losses.sparse_categorical_crossentropy,metrics=keras.metrics.LogCoshError())
t = Token()
x,y = getData(t,0,5000)
model.fit(x,y,epochs=20,callbacks=[keras.callbacks.ModelCheckpoint(filepath="ChessAI.h5",save_weights_only=False,save_best_only=True,monitor='loss'),keras.callbacks.EarlyStopping(monitor='loss')])
test =keras.preprocessing.sequence.pad_sequences(x[0])
test = test[numpy.newaxis,...]
print(t.t.index_word[numpy.argmax(model.predict(test))])