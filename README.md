# Live Long and Prosper

## Overview
"Live Long and Prosper" is an AI agent project designed to optimize resource management and achieve prosperity goals through various heuristic search algorithms. The project implements Greedy and A* search algorithms with multiple heuristic functions to guide the agent's actions efficiently.

## Features
- **Heuristic Functions:** Implements Greedy and A* heuristics, ensuring admissible estimates that never overestimate the cost to reach the goal.
- **Resource Management:** Manages food, materials, and energy efficiently while considering agent's budget constraints.
- **Optimization Algorithms:** Utilizes Greedy and A* search algorithms to find the optimal path to the goal state.
- **Performance Metrics:** Tracks and prints metrics such as RAM usage, CPU utilization, expanded nodes, and completeness of the solution.

## Algorithms and Heuristics
### Greedy 1
Calculates the prosperity left to reach the goal and divides it by the prosperity gained from each build action. Determines the minimal cost by considering material requests and build actions.

### Greedy 2
Similar to Greedy 1 but focuses on the total money needed for builds, considering the minimal cost of both build types.

### A* 1
Combines the path cost and heuristic cost to estimate the total steps needed to reach the goal. Considers material requests and build actions to calculate the minimal cost.

### A* 2
Uses the same concept as A* 1, but focuses on the total money needed for builds, adding the minimal cost to the path cost.

## Main Functions
### GenericSearch
The core class that holds the main functions of the search algorithm. It initializes variables, tracks expansion count, and manages the nodes and childNodes queues. Implements the following key operations:
- **Node Expansion:** Explores potential paths from the current node.
- **Request Handling:** Manages resource requests and their delivery.
- **Build Actions:** Applies build actions and checks resource constraints.
- **Redundancy Check:** Ensures paths that do not lead to a goal state are not explored again.
- **Solution Formulation:** Generates the solution path if a goal state is reached.

## How to Run
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/livelongandprosper.git
    ```
