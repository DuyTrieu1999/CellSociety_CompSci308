Author
---
* Duy Trieu (dvt5)
* Haotian Wang (hw186)
* Vincent Liu (jl729)

Part 1
---
1. What is an implementation decision that your design is 
encapsulating (i.e., hiding) for other areas of the program?
    * Instance variables of many classes are hidden from other classes
    * Vincent encapsulates the rules from the Main class. User can
    add more JavaFX Nodes components through the Main class.

2. What inheritance hierarchies are you intending to build within 
your area and what behavior are they based around?
    * I am responsible for the Backend components of our project. I 
    intend to have my Cell class acts as a super class for other types
    of Cell to inherit from. The inheritance is based on the changes of
    colors, and the behaviors that pertain to each simulation.  

3. What parts within your area are you trying to make closed and 
what parts open to take advantage of this polymorphism you are creating?
    * I make the Grid class closed and the Cell class opened so that
    when I want to implement another simulation, I would make another
    class that extends on the Cell class, and keep the rules of updating
    closed inside the Grid class.

4. What exceptions (error cases) might occur in your area and how will 
you handle them (or not, by throwing)?
    * Common exceptions might occur include NullPointerException, XMLException,
    or ArrayOutOfBoundException. These exceptions are solved by controlling 
    the spawning of objects; reading from files correctly, and updating
    the rules in simulations.

5. Why do you think your design is good (also define what your measure 
of good is)?
    * I design my backend system such that I can put in more 


Part2
---
1. How is your area linked to/dependent on other areas of the project?
    * My area controls the the rules of simulation controlled by the Grid 
    class, as well as creating the different types of Cell for simulation.
    My part would read work on the XML file reader for information on the 
    parameters. The UI area would then show the simulations.

2. Are these dependencies based on the other class's behavior or implementation?
    The Grid class would depend on the Cell class, on how the Cell updates the
    colors and behaviors. The Grid class creates a Cell[][] array that stores and
    updates the different Cells, and the UI would create a GridPane to showcase
    the Cells.

3. How can you minimize these dependencies?
    I would minimize the dependencies between the backend and the UI parts in
    our project. This could be done by only updating the Grid class when I link
    the backend and the frontend.

4. Go over one pair of super/sub classes in detail to see if there is room for 
improvement. Focus on what things they have in common (these go in the superclass) 
and what about them varies (these go in the subclass).


Part3
---
1. Come up with at least five use cases for your part (most likely these will be 
useful for both teams).
    

2. What feature/design problem are you most excited to work on?


3. What feature/design problem are you most worried about working on?