import numpy
import tensorflow.keras as keras

from ChessAppAI import DSManip
from ChessAppAI.DSManip import getData
from ChessAppAI.Tokenizer import Token

model = keras.models.Sequential([
    keras.layers.GRU(2048,activation='elu',return_sequences=True,input_shape=(None,6488)),
    keras.layers.Dropout(0.5),
    keras.layers.GRU(1024,activation='elu',return_sequences=True),
    keras.layers.Dropout(0.25),
    keras.layers.GRU(256,activation='elu',return_sequences=True),
    keras.layers.Dropout(0.25),
    keras.layers.GRU(128,activation='elu',return_sequences=False),
    keras.layers.Flatten(),
    keras.layers.Dense(1024,activation='selu'),
    keras.layers.Dense(6488,activation='elu')
])
model = keras.models.load_model('ChessAI.h5')
model.compile(optimizer=keras.optimizers.Adadelta(learning_rate=0.002),loss=keras.losses.categorical_hinge,metrics=[keras.metrics.LogCoshError(),keras.metrics.Accuracy(),keras.metrics.binary_crossentropy])
t = Token()
i=6
x,y = getData(t,i*4000,(i+1)*4000)
model.summary()
model.fit(x,y,epochs=20,callbacks=[keras.callbacks.ModelCheckpoint(filepath="ChessAI.h5",save_weights_only=False,save_best_only=True,monitor='loss'),keras.callbacks.EarlyStopping(monitor='loss'),keras.callbacks.TensorBoard('logs/tendorboard')])
test = DSManip.prepareFit(t,"W1.e4 B1.e5 W2.Nc3")
print(model.predict(test))
print(numpy.argmax(model.predict(test)))
print(t.t.index_word[numpy.argmax(model.predict(test))])