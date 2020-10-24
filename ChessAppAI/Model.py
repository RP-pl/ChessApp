import numpy
import tensorflow.keras as keras

from ChessAppAI.DSManip import prerpareDatasets
from ChessAppAI.Tokenizer import Token

s=["W1.d4 B1.d5 W2.c4 B2.e6 W3.Nc3 B3.Nf6 W4.cxd5 B4.exd5 W5.Bg5 B5.Be7 W6.e3 B6.Ne4 W7.Bxe7 B7.Nxc3 W8.Bxd8 B8.Nxd1 W9.Bxc7 B9.Nxb2 W10.Rb1 B10.Nc4 W11.Bxc4 B11.dxc4 W12.Ne2 B12.O-O W13.Nc3 B13.b6 W14.d5 B14.Na6 W15.Bd6 B15.Rd8 W16.Ba3 B16.Bb7 W17.e4 B17.f6 W18.Ke2 B18.Nc7 W19.Rhd1 B19.Ba6 W20.Ke3 B20.Kf7 W21.g4 B21.g5 W22.h4 B22.h6 W23.Rh1 B23.Re8 W24.f3 B24.Bb7 W25.hxg5 B25.fxg5 W26.d6 B26.Nd5 W27.Nxd5 B27.Bxd5 W28.Rxh6 B28.c3 W29.d7 B29.Re6 W30.Rh7 B30.Kg8 W31.Rbh1 B31.Bc6 W32.Rh8 B32.Kf7 W33.Rxa8 B33.Bxd7 W34.Rh7 W1.e4 B1.d5 W2.exd5 B2.Qxd5 W3.Nc3 B3.Qa5 W4.d4 B4.Nf6 W5.Nf3 B5.c6 W6.Ne5 B6.Bf5 W7.g4 B7.Be4 W8.f3 B8.Bd5 W9.a3 B9.Nbd7 W10.Be3 B10.Nxe5 W11.dxe5 B11.Nxg4 W12.Bd4 B12.e6 W13.b4 B13.Qd8 W14.Nxd5 B14.Qxd5 W15.c4 B15.Ne3 W16.cxd5 B16.Nxd1 W17.dxc6 B17.bxc6 W18.Rxd1 B18.Be7 W19.Ba6 B19.O-O W20.Ke2 B20.Rab8 W21.Rc1 B21.Rfd8 W22.Rhd1 B22.c5 W23.Bxc5 B23.Rxd1 W24.Rxd1 B24.Bxc5 W25.bxc5 B25.g6 W26.c6 B26.Rb2 W27.Rd2","W1.e4 B1.e5 W2.Nf3 B2.Nc6 W3.Bc4 B3.Bc5 W4.c3 B4.Nf6 W5.d3 B5.d6 W6.Bb3 B6.O-O W7.Nbd2 B7.Be6 W8.O-O B8.Qd7 W9.Re1 B9.Rfe8 W10.Nf1 B10.Ne7 W11.Ng3 B11.Bg4 W12.h3 B12.Be6 W13.Bg5 B13.Kh8 W14.Bxf6 B14.gxf6 W15.d4 B15.exd4 W16.cxd4 B16.Bb4 W17.Re3 B17.Rg8 W18.d5 B18.Bxh3 W19.Qd4 B19.Rg6 W20.Qxb4 B20.c5 W21.Qc3 B21.Bg4 W22.Bc2 B22.Rh6 W23.Nh2 B23.b5 W24.b4 B24.Rc8 W25.Bd3 B25.c4 W26.Bc2 B26.Bh5 W27.Nxh5 B27.Rxh5 W28.Qxf6 B28.Kg8 W29.Bd1"]

t = Token()
x,y,length = prerpareDatasets(t,s[0])

model = keras.models.Sequential([
    keras.layers.Conv1D(512,kernel_size=2,activation='elu',padding='SAME',input_shape=(None,length)),
    keras.layers.GRU(256,activation='elu',return_sequences=True),
    keras.layers.GRU(128,activation='elu',return_sequences=False),
    keras.layers.Flatten(),
    keras.layers.Dense(length,activation='elu'),
    keras.layers.Reshape((1,length))
])
model.compile(optimizer=keras.optimizers.Adadelta(),loss=keras.losses.sparse_categorical_crossentropy,metrics=keras.metrics.LogCoshError())
model.fit(x,y,epochs=10)

print(t.t.index_word[numpy.argmax(model.predict(x))])