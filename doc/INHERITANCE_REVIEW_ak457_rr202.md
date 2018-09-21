Inheritance Review
====

Author: Austin Kao (ak457)  
Partner: Rahul Ramesh (rr202)

### Part 1
* What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?  
My design encapsulates XML files so that other areas of the program only need to know the file format to read from the files, but not know what each specific piece of information in the file.
* What inheritance hierarchies are you intending to build within your area and what behavior are they based around?  
I intend to have a Cell class act as a super class, and have multiple simulation classes extend the Cell class. Each simulation class will have define different parameters and different rules to simulate when the specific simulation is created.
* What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?  
I am trying to make the Cell and Grid classes closed for modification, but anyone should be able to extend the classes to create a new simulation using them.
Also, the XML files should ideally be closed since I currently plan to have them define the parameters for each type of cell. These specific parameters can be read from the file to create any type of cell desired.
* What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?  
Some errors that might occur are that the format of the XML file is wrong or the way the XML file is read is wrong. Also, there could be typos or an omission of a certain field in the XML file.
* Why do you think your design is good (also define what your measure of good is)?  
A good design should be easy to explain, document, extend, and change. It should be flexible enough to accommodate most changes that a user wants to make.
My design is good because once a format has been established, new XML files that represent new CA simulations can be easily created.
Also, certain aspects of existing simulations can be modified through the simulation subclasses without affecting the behaviors of the main classes.
###Part 2
* How is your area linked to/dependent on other areas of the project?  
The parameters defined in the XML files is dependent on the fields in the Cell and Grid classes. Also, the format of the XML file will affect how it is read by other classes to generate a simulation.
* Are these dependencies based on the other class's behavior or implementation?  
Yes, these dependencies are based on the implementation and behavior of other classes. The fields defined in the Cell class directly affect what sort of information should be stored in an XML file.
* How can you minimize these dependencies?  
We can minimize these dependencies by establishing a format for XML files after finishing the implementations of the Cell and Grid classes. Then, after establishing the format of the files, we can code how the XML files are read.
* Go over one pair of super/sub classes in detail to see if there is room for improvement. 
Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).  
The Cell superclass and the Segregation model subclass share the Cell fields. They vary in their updateCell() method. We can improve the Cell class by giving it more methods so that the Segregation model has more methods to work with.

###Part 3
* Come up with at least five use cases for your part (most likely these will be useful for both teams).  
1. An empty XML file  
2. An XML file with impossible parameters  
3. An XML file with an incorrect format  
4. An XML file with correct parameters  
5. An XML file for another simulation
* What feature/design problem are you most excited to work on?  
I am most excited to work on the Model of Segregation model, since it an interesting simulation to me.
* What feature/design problem are you most worried about working on?  
I am most worried about working on the code to read the XML file since I am unsure of exactly how to implement it.