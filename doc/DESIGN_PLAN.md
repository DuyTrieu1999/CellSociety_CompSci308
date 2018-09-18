cell society
====

##Introduction:

* Problem: Simulating real world scenarios and observing how cells interact with each other over a long period of time when given a certain set of features and parameters. 

* Design goals: Model a general cellular automata system that any real world scenario can extend from. 
This cell abstraction will contain enough generic features(class fields) and cell actions(class methods) possible.
Model a generic grid that can create any number of grids required for the specific real world scenario being modeled.

* Primary architecture: The architecture of our project will consist of a Cell class that serves as a super class for the different CA simulations we want to make as well as a Grid class that is designed to update the cells for each simulation accordingly.
The Grid class will be closed for internal modification, since it simply holds cells that update each other. The Cell class and its extension classes would be open for modification to create more simulations.
 
##Overview:
We plan to create two general classes, Cell and Grid, to represent a general CA simulation. Cell represents a specific cell and the information tied to the cell. 
Grid represents the overall grid of cells, and will contain a two dimensional Cell array.
There will also be two classes used for the front end: view.SimulationUI, which contains buttons, dropdowns, user input fields, and the pane for cells, and MainSimulation, which will create a stage, scene, grid, and launch the application.
Any specific CA simulation will extend the Cell class. Each simulation will override the update() method to enforce its own set of rules on the simulation.
![Overall Class Design](Overall_Design.JPG "Current Design")

##User Interface:

For our Cell Society, users will be able to determine the size (row & column) of 
the grid. There will be a drop down where users can determine what simulation they want 
to run, which will initialize the CA simulation upon selection. We will also have three 
buttons: PLAY, RESET, and PAUSE. These buttons will let the users control the current Cell simulation. 

![UI sketch design](UI_Design.png "An alternate design")

###Design Details:

* **Cell**
    * currState(field): Field that defines the current state of the cell
    * prevState(field): Field that defines the previous state of the cell. When the cell updates, it will store the currState fields into the prevState fields and use the prevState fields to find the next currState fields.
    * image(field): Stores the name of the image file to display for the cell
    * update(method): Updates the prevState and currState fields in the cell. Will examine the currState of neighboring cells to determine how the cell will behave.
    * getCurrState(method): Getter method for currState
    * getPrevState(method): Getter method for prevState
    * resources needed: image files for each of the different states of the cells
    * Can extend Cell to fit any number of simulations
* **Grid**
    * myGrid(field): A two-dimensional Cell array where the simulation takes place
    * rows(field): The total number of rows in the grid
    * columns(field): The total number of columns in the grid
    * fillGrid(method): A method that generates myGrid and the initial state of all cells
    * getRow(method): Getter method that returns the total number of rows
    * getColumn(method): Getter method that returns the total number of columns
    * The grid class contains the backend details and methods that organizes cells into a two-dimensional array, so making it a separate class helps organize the code
* **view.SimulationUI**
    * createPauseButton, createPlayButton, createResetButton: creates the specified button. Clicking a button will call the appropriate method.
    * createDropDown: creates a dropdown menu with the names of all available simulations to choose from. Selecting a simulation from the menu will automatically initialize the simulation.
    * rowInputField, columnInputField: Fields where the user will enter a specified number of rows and columns.
    * paneForCells: Creates a pane to display the two-dimensional grid. Will call on the Grid class to help determine how big the cells will be, and will call on the Cell class to determine which images to display.
    * pauseSimulation(method): Method that will pause the simulation
    * resetSimulation(method): Method that will revert the simulation back to its initial state
    * playSimulation(method): Method that will start a simulation or continue a paused simulation
    * This class creates the main front end components of the CA simulations, so keeping it separate from MainSimulation should help with organization
* **MainSimulation**
    * createScene(method): Sets a stage and scene according to the UI design.
    * start(): Specifies what the application should do when it launches. Will set up a start screen that welcomes the user and have a "Start" button to bring the user to the main UI screen.
* **Other simulation classes**
    * Schelling's Model of Segregation
    * Wa-Tor World Model of Predator-Prey Relationships
    * Spread of Fire
    * Each simulation will extend Cell
    * Each simulation will override the update() method in Cell to fit the rules of the simulation

###Design Considerations:
* There are several options for how we should create the grid and run different simulations. 
One option is to call and run the Cell simulations inside the Grid class. We would want to create the view.SimulationUI
class to handle the simulations of the Cell on the Grid class onscreen in this case.
Another option is to call both the Grid and the view.SimulationUI classes inside the MainSimulation class.
For our implementation, we decide to call the Grid class and view.SimulationUI class inside a MainSimulation 
class that runs the whole program.

* The way we will store the previous states of the cell is ambiguous. One option we considered was to use the instance 
where each cell stores its own previous and current state, and another option is to have a new pointer point to the entire state of the previous 2D array. We settled for the former option since the latter one seems more 
complicated to implement. 

* Another design consideration we were discussing is to make a separate class for each button in the UI interface. 
The pros that this design brings is the ease in determining what the buttons do. The cons of this decision would be that since 
each button only controls the flow of simulation, it would be better to put them in one class.

###Team Responsibilities:
* All three members will work on the implementations of the Cell and Grid classes together according to the design above.
* Each member of the team will take responsibility for developing the specific CA simulations extending from the Cell class.
Each specific simulation should model the original CA model as closely as possible.
*The simulation that each member is responsible for:
  1. Samuel Appiah-Kubi: Wa-Tor World model of predator-prey relationships
  2. Duy Trieu: Spreading of Fire
  3. Austin Kao: Schelling's model of segregation
* Duy Trieu will be responsible for developing the UI for the simulation. The functionality for the buttons as 
stated in the UI description above will be added when the team has finished implementing the Cell and Grid
classes.