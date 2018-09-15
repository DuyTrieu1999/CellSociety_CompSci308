cell society
====

##Introduction:

* Problem: how to simulate these real world scenarios(the above chosen systems) 
and observe how they interact with each other over a long period of time based 
on the specific features that affect each entity. 

* Design goals: Model a general cell that any real world scenario can extend from. 
This cell abstraction contains enough generic features(class fields) and cell 
actions(class methods) possible. Model a generic grid that can create any number of 
grids required for the specific real world scenario being modeled.

* Primary architecture: The architecture of our project would consist of a Cell.java 
class that serves as a super class for different simulations of CAs we want to make. 
The Grid.java class would be closed for internal modification and to update the Cells. 
 
 
##Overview:

We plan to create two general classes, Cell and Grid, to represent a general CA simulation. 
Cell represents a specific cell and the information tied to the cell. Grid represents the 
overall grid of cells. There will also be two classes used to create the front-end interface, 
SimulationUI, which contains buttons, dropdowns, the user input fields, and the pane for cells, 
and Main, which will create a stage, scene, grid, and launch the application. Any specific CA 
simulation will extend the Cell class. Each simulation will override the update() method to 
enforce its own set of rules on the simulation.



##User Interface:

For our Cell Society, the users would be able to determine the size (row & column) of 
the grid. There will be a drop down that users can determine what simulation they want 
to run, which will choose a class of simulation that will be run. We would have three 
buttons: PLAY, RESET, and PAUSE, that will let the users control the current Cell simulation. 

![UI sketch design](UI_Design.png "An alternate design")

###Design Details:

* **Cell**
    * currState(field)
    * prevState(field)
    * image(field)
    * update(method)
    * getCurrState(method)
    * getPrevState(method)
* **Grid**
    * myGrid(field)
    * rows(field)
    * columns(field)
    * fillGrid(method)
    * getRow(method)
    * getColumn(method)
* **SimulationUI**
    * pauseButton, playButton, resetButton
    * dropDown
    * rowInputField, columnInputField
    * paneForCells
    * pauseGame(method)
    * resetGame(method)
    * playGame(method)
* **Main**
    * createScene(method)
    

###Design Considerations:
* Deliberation on where we want to create the grid for the cells. 
The Grid class, SimulationUI class and Main class are all possible options. 
A possible way we could do is to call the Grid, and the SimulatioUI class inside the Main class.

* The storage of the previous cell states were ambiguous. The options were going for the instance 
where each cell stores its own previous and current state or have a new pointer point to the whole 
previous generation 2D array. We settled for having former option since the latter seems more 
complicated to implement. 

* Another design consideration we made is to make a seperate class for each buttons in the UI interface. 
The pros this design brings is the ease in navigating around the buttons; the cons would be that since 
each buttons only controls the flow of simulation, it would be better to put them in one class.

###Team Responsibilities: