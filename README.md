cell society
====

This project implements a cellular automata simulator.

Names: Duy Trieu, Samuel Appiah-Kubi, Austin Kao

### Timeline

Start Date: 09/17/2018

Finish Date: 09/24/2018

Hours Spent: 45 Hours

### Primary Roles
Duy Trieu: SimulationUI and Models
Samuel Appiah-Kubi: Simulation Backend and Models
Austin Kao: Simulation Backend and XML

### Resources Used
https://en.wikipedia.org/wiki/Wa-Tor
https://natureofcode.com/book/chapter-7-cellular-automata/
https://www2.cs.duke.edu/courses/fall18/compsci308/assign/02_cellsociety/nifty/shiflet-fire/
https://www2.cs.duke.edu/courses/fall18/compsci308/assign/02_cellsociety/nifty/mccown-schelling-model-segregation/

### Running the Program

MainSimulation class: MainSimulation.java

Data files needed: Game_of_Life.xml, WaTor.xml, Segregation.xml, Spreading_Fire.xml

Interesting data files: None

Features implemented: Slider Feature

Assumptions or Simplifications: In FireCell, assume fire starts at multiple random locations at the same time

Known Bugs: Incomplete logic and implementation in PredatorPrey Cell and Grid

Extra credit: 


### Notes
Simulation backend is modeled on the Cell.java and Grid.java. For each simulation model, a new cell and grid type is created that extends the base classes. 
Available Simulations are GameOfLife, WaTor Predator Prey, Spreading of Fire, Schelling's Model of Segregation.
Simulation UI presents the options of selecting which model to run.
Play button in UI runs the simulation iteratively.
Step button in UI runs the simulation in a stepwise manner.
Reset button in UI resets the initialized values in the grid to rerun the simulation.
Slider features are provided to change the grid size and simulation speed.

### Impressions
The statistical potentials of the models generated are very effective in solving problems through model simulations.


