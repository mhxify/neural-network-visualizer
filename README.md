# 🧠 Neural Network Visualizer

An educational project that aims to build and visualize a neural network from scratch using Java.

The goal of this project is not only to use artificial intelligence but also to understand how neural networks work internally by implementing every component step by step and visualizing the learning process.

---

# 🎯 Project Goals

This project is designed to help understand:

* Artificial Neurons
* Weights
* Biases
* Activation Functions
* Forward Propagation
* Loss Functions
* Backpropagation
* Gradient Descent
* Neural Network Training

Instead of relying on frameworks such as TensorFlow or PyTorch, the neural network will be implemented manually to better understand the underlying concepts.

---

# 🚀 Current Progress

## Phase 1: Neural Foundations

### ✅ Completed

* Neuron implementation
* Random weight initialization
* Random bias initialization
* Sigmoid activation function
* Forward propagation
* Layer implementation
* Multi-neuron layer support
* Neural Network implementation
* Multi-layer forward propagation

### 🔄 In Progress

* Loss Function

### 📋 Planned

* Backpropagation
* Gradient Descent
* XOR training
* Visualization system
* Real-time training graphs
* Interactive controls

---

# 🧩 Implemented Components

## Neuron

The neuron currently supports:

* Multiple inputs
* Individual weights for each input
* Bias value
* Weighted sum calculation
* Sigmoid activation function
* Output generation

### Forward Pass Formula

```math
output = sigmoid(\sum(input_i \times weight_i) + bias)
```

### Data Flow

```text
Inputs
   ↓
Weights
   ↓
Bias
   ↓
Weighted Sum
   ↓
Sigmoid
   ↓
Output
```

---

## Layer

A layer is a collection of neurons that process the same inputs and produce multiple outputs.

Features:

* Multiple neurons
* Shared input processing
* Parallel forward propagation
* Output aggregation

Example:

```text
Inputs
   ↓
Neuron 1
Neuron 2
Neuron 3
   ↓
Outputs
```

Example Output:

```text
[0.49, 0.63, 0.80]
```

---

## Neural Network

A neural network is a collection of layers connected together.

Features:

* Multiple layers
* Sequential forward propagation
* Layer-to-layer communication
* Final prediction generation

Architecture Example:

```text
Input Layer
      ↓
Hidden Layer
      ↓
Output Layer
```

Data Flow Example:

```text
Input
  ↓
Layer 1
  ↓
Layer 2
  ↓
Output
```

Example:

```text
Input: [1.0, 0.0]

Layer 1 Output:
[0.49, 0.63, 0.80]

Layer 2 Output:
[0.34]
```

---

# 📚 Concepts Learned So Far

## Input

Data provided to the neuron.

Example:

```text
Age = 25
Salary = 50000
```

---

## Weight

Represents the importance of an input.

Example:

```text
Age Weight = 0.8
Salary Weight = 0.3
```

---

## Bias

An additional parameter that helps shift the neuron's decision boundary.

Example:

```text
output = input × weight + bias
```

---

## Sigmoid Activation

Converts any value into a number between 0 and 1.

Formula:

```math
σ(x) = 1 / (1 + e^(-x))
```

Example:

```text
sigmoid(10)  = 0.999
sigmoid(0)   = 0.5
sigmoid(-10) = 0.000
```

---

## Forward Propagation

The process of passing data through the neural network to generate predictions.

```text
Input
  ↓
Layer 1
  ↓
Layer 2
  ↓
Output
```

---

# 🏗️ Planned Architecture

```text
Input Layer
      ↓
Hidden Layer
      ↓
Output Layer
```

Extended Architecture:

```text
Input Layer
      ↓
Hidden Layer 1
      ↓
Hidden Layer 2
      ↓
Output Layer
```

---

# 🎨 Future Visualization Features

The final application will visualize:

* Neurons
* Connections
* Weights
* Biases
* Forward propagation
* Backpropagation
* Gradient flow
* Loss reduction
* Training progress
* Decision boundaries

Example:

```text
Input → Hidden → Output
```

with real-time animations.

---

# 🛠️ Tech Stack

* Java
* Maven
* JavaFX (planned)
* Object-Oriented Programming

---

# 📅 Roadmap

## Phase 1: Foundations

* [x] Neuron
* [x] Layer
* [x] Neural Network

## Phase 2: Learning Mechanics

* [ ] Loss Function
* [ ] Gradient Descent
* [ ] Backpropagation

## Phase 3: Training

* [ ] XOR Training
* [ ] Dataset Support
* [ ] Accuracy Tracking
* [ ] Model Evaluation

## Phase 4: Visualization

* [ ] JavaFX Visualization
* [ ] Weight Visualization
* [ ] Loss Graph
* [ ] Forward Propagation Animation
* [ ] Backpropagation Animation
* [ ] Interactive Training Controls

## Phase 5: Advanced Topics

* [ ] CNN Visualizer
* [ ] Transformer Visualizer
* [ ] Attention Visualization
* [ ] Custom Dataset Import

---

# 🎓 Learning Objective

By the end of this project, the goal is to fully understand:

* How neurons work
* How layers process information
* How neural networks make predictions
* How errors are calculated
* How gradients are computed
* How weights are updated
* How learning actually happens inside AI systems

This project focuses on understanding AI fundamentals rather than simply using existing AI libraries.

---

# 👨‍💻 Author

**Mohamed Ali Benouarzeg**

Android Developer • Kotlin Multiplatform Developer • AI Enthusiast

GitHub: https://github.com/mhxify
