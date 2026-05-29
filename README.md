# 🧠 Neural Network Visualizer

An educational project that aims to build and visualize a neural network from scratch using Java.

The goal of this project is not only to use artificial intelligence but also to understand how neural networks work internally by implementing every component step by step and visualizing the learning process.

---

# 🎯 Project Goals

This project is designed to help understand:

- Artificial Neurons
- Weights
- Biases
- Activation Functions
- Forward Propagation
- Loss Functions
- Backpropagation
- Gradient Descent
- Neural Network Training

Instead of relying on frameworks such as TensorFlow or PyTorch, the neural network will be implemented manually to better understand the underlying concepts.

---

# 🚀 Current Progress

## Phase 1: Neural Foundations

### ✅ Completed

- Neuron implementation
- Random weight initialization
- Random bias initialization
- Sigmoid activation function
- Forward propagation

### 🔄 In Progress

- Layer implementation

### 📋 Planned

- Neural Network implementation
- Loss calculation
- Backpropagation
- Gradient Descent
- XOR training
- Visualization system
- Real-time training graphs
- Interactive controls

---

# 🧩 Implemented Components

## Neuron

The neuron currently supports:

- Multiple inputs
- Individual weights for each input
- Bias value
- Weighted sum calculation
- Sigmoid activation function
- Output generation

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

# 🏗️ Planned Architecture

```text
Input Layer
      ↓
Hidden Layer
      ↓
Output Layer
```

Example:

```text
Input Layer

(x1)    (x2)
  \      /
   \    /
   (N1) (N2)
      \ /
      (N3)

Output Layer
```

---

# 🎨 Future Visualization Features

The final application will visualize:

- Neurons
- Connections
- Weights
- Forward propagation
- Backpropagation
- Gradient flow
- Loss reduction
- Training progress

Example:

```text
Input → Hidden → Output
```

with real-time animations.

---

# 🛠️ Tech Stack

- Java
- Maven
- JavaFX (planned)
- Object-Oriented Programming

---

# 📅 Roadmap

## Phase 1

- [x] Neuron
- [ ] Layer
- [ ] Neural Network

## Phase 2

- [ ] Loss Function
- [ ] Gradient Descent
- [ ] Backpropagation

## Phase 3

- [ ] XOR Training
- [ ] Dataset Support
- [ ] Accuracy Tracking

## Phase 4

- [ ] JavaFX Visualization
- [ ] Weight Visualization
- [ ] Loss Graph
- [ ] Interactive Training Controls

## Phase 5

- [ ] CNN Visualizer
- [ ] Transformer Visualizer
- [ ] Attention Visualization

---

# 🎓 Learning Objective

By the end of this project, the goal is to fully understand:

- How neurons work
- How neural networks make predictions
- How errors are calculated
- How weights are updated
- How learning actually happens inside AI systems

This project focuses on understanding AI fundamentals rather than simply using existing AI libraries.

---

# 👨‍💻 Author

**Mohamed Ali Benouarzeg**

Android Developer • Kotlin Multiplatform Developer • AI Enthusiast

GitHub: https://github.com/mhxify