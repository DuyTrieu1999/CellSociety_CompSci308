# **Cell Society : Inheritance Review Questions**

> **Name: Amy Kim(yk154), Samuel(sa285)**

**Part 1**
1. What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?

* We created three abstract classes; each Cell and Simulation classes per simulation type.
* We create one super class of a cell from which sub cell classes extend from; Grid class and simulation classes are modified based on the cell type that is selected.

2. What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

* Cell and Simulation classes of Simulation type(ex, game of life, segregation,etc) extend the cell abstract class. And trying to go over the loops of grid (list of cell), to get current cell and its neighbors. 
* Inheritance hierarchies only exist in cells. The cell super class hold the curr and previous states, specific row and column number. The extended sub classes define the specific rules that should apply to it and it's neighbors.

3. What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?

* Grid abstract class is closed and we can extend Cell abstract class. 
* Cell super class is closed for modification, but the states and rules features are open for extension.

4. What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

* As we're starting with abstract class, we faced error when we're trying to extend class or getting variables in other class. We start to use protected variables to be easily approached in other classes.
* Rules for edge cell cases are yet to be defined, so we will throw an indexoutofbounds exception to handle that.

5. Why do you think your design is good (also define what your measure of good is)?

* Abstract simulation and cell classes allow us to build upon them and add more to their subclasses.
* Communication among our classes is done effectively.

**Part 2**
1. How is your area linked to/dependent on other areas of the project?

Our area is dependent on other areas of the project. We both are working on Simulation of the project. 

2. Are these dependencies based on the other class's behavior or implementation?

* As we're using abstract classes and build upon them and add more on subclasses, the subclasses are based on cell abstract class. But we have different packages per types of simulation and different rules, it has less dependencies on the other class's.
* Aside from the cell classes in our inheritance hierarchy, dependencies are between cell and grid(number of cells are instantiated in grid and the neighbors of the cell are stored in the specific cell). 

3. How can you minimize these dependencies?


* The update dependency of the cell neighbors can entirely be defined for just the grid and exclude the cell classes from storing them.


4. Go over one pair of super/sub classes in detail to see if there is room for improvement. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).

* For flexiblity, using inheritance could be one of alternatives of using Abstract class.



**Part 3**

1. Come up with at least five use cases for your part (most likely these will be useful for both teams).
 
* initialize Grid
    * initializing grid
* set/getCurrentstate
    * Build or Get current state of the cell.
*  set/getNextstate, 
    * Update or get next state of the cell.
*  checkNeighbors and IsinBound
    *  check Neighbors within list and if it is in bound.
* updateCell, getCellType
* Have Cell, Grid and Simulation as base classes that each model extend from
* 

2. What feature/design problem are you most excited to work on?

* Flexibility between abstract and sub classes
* Enough abstraction to extend for any new model that we need to build

3. What feature/design problem are you most worried about working on?

* Keep checking neighbors within list.
* Keeping grid as a single base class