import tensorflow as tf
import numpy as np

n_steps = 2    # number of time steps 
n_inputs = 3   # number of inputs per time unit 
n_neurons = 5  # number of hidden units 

X_batch = np.array([
  # t = 0      t = 1
  [[0, 1, 2], [9, 8, 7]], # instance 0
  [[3, 4, 5], [0, 0, 0]], # instance 1
  [[6, 7, 8], [6, 5, 4]], # instance 2
  [[9, 0, 1], [3, 2, 1]], # instance 3
])

#sequence_length <= # of elements in each batch
seq_length_batch = np.array([2, 1, 2, 2])

X = tf.placeholder(dtype=tf.float32, shape=[None, n_steps, n_inputs])
seq_length = tf.placeholder(tf.int32, [None])

basic_cell = tf.nn.rnn_cell.BasicRNNCell(num_units=n_neurons)
outputs, states = tf.nn.dynamic_rnn(basic_cell, X, sequence_length=seq_length, dtype=tf.float32)

with tf.Session() as sess:
  sess.run(tf.global_variables_initializer())
  outputs_val, states_val = sess.run([outputs, states], 
                 feed_dict={X:X_batch, seq_length:seq_length_batch})

  print("X_batch     shape:", X_batch.shape)      # (4,2,3)
  print("outputs_val shape:", outputs_val.shape)  # (4,2,5)
  print("states_val  shape:", states_val.shape)   # (4,5)

  print("outputs_val:",outputs_val)
  print("----------------------------\n")
  print("states_val: ",states_val)

###################################################################
# outputs => output of ALL RNN states 
# states  => output of LAST ACTUAL RNN state (ignores zero vector)
# state = output[1] for full sequences
# state = output[0] for short sequences
###################################################################

#----------------------------
#outputs_val: 
#[[[-0.09700205  0.7671716   0.6775758   0.01522888  0.5460828 ]
#  [ 0.92776424 -0.5916748   0.67824966  0.99423325  0.9999991 ]]
#
# [[ 0.24040672  0.81568515  0.8890421   0.780813    0.99762475]
#  [ 0.          0.          0.          0.          0.        ]]
#
# [[ 0.5282535   0.8549201   0.9647311   0.9692446   0.99999046]
#  [ 0.9725177  -0.7165484   0.46688017  0.9411293   0.9999323 ]]
#
# [[ 0.81080747 -0.9926888   0.56612366  0.9561879   0.9997731 ]
#  [ 0.48786768 -0.7099759  -0.7283263   0.76442945  0.9971904 ]]]
#----------------------------
#states_val:  
#[[ 0.92776424 -0.5916748   0.67824966  0.99423325  0.9999991 ]
# [ 0.24040672  0.81568515  0.8890421   0.780813    0.99762475]
# [ 0.9725177  -0.7165484   0.46688017  0.9411293   0.9999323 ]
# [ 0.48786768 -0.7099759  -0.7283263   0.76442945  0.9971904 ]]
#----------------------------

