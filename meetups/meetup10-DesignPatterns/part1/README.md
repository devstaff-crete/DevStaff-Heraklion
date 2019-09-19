# Part 1: Design Patterns For Fun And Profit

## Getting Started

In here you can find the slides and the example presented. In order to execute the example and/or run its test you need Java and JUnit. The example is taken from the book [Refactoring Patterns by Joshua Kerievsky](http://www.amazon.com/Refactoring-Patterns-Joshua-Kerievsky/dp/0321213351).

In the example, there's a list of orders each containing products. The purpose of the program is to transform this list to XML. This is done by the _OrderSerializer_ class as shown in the presentation.

You should start looking at the _initial_ package. In there, you will find the OrderSerializer in its initial form. Two refactorings are applied, first extracting a _composite_ and then creating a _builder_ and this is how the packages are actually named. _OrderSerializer_ has a different name in all three packages. The reason behind this is solely to increase readers' comprehension. If this was production code, we would have in the end just three classes, _TagNode_, _TagBuilder_ and _OrderSerializer_.

## Reading Material

Here are some good books about design patterns:

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612/)
* [Head First Design Patterns](http://www.amazon.com/Head-First-Design-Patterns-Freeman/dp/0596007124)
* There is a series of books under the title Pattern-Oriented Software Architecture, here is the [first book]( http://www.amazon.com/Pattern-Oriented-Software-Architecture-System-Patterns/dp/0471958697)
* [Patterns of Enterprise Application Architecture](http://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](http://www.amazon.com/Enterprise-Integration-Patterns-Designing-Deploying/dp/0321200683)

Here are two books about refactoring and how you combine with design patterns:

* [Refactoring: Improving the Design of Existing Code](http://www.amazon.com/Refactoring-Improving-Design-Existing-Code/dp/0201485672)
* [Refactoring Patterns by Joshua Kerievsky](http://www.amazon.com/Refactoring-Patterns-Joshua-Kerievsky/dp/0321213351)
