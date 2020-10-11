# u7078355

Map data structures should not be used to contain a fixed number of attributes as we can always create custom objects
to accomplish the same purpose (and significantly more efficiently). Otherwise code is well formatted, and is a great
example of a helper method for the scoring of the board.

# u6744849

Good use of helper methods in other classes. Would be good to see Board go from a container of static methods to being
a custom datatype that can even replace the string representation of the board. Remember to avoid writing multiple
methods with nearly the same code - instead consider calling private helper methods that contain the logic of both
public methods. Finally remember with conditional predicates depending on the value of the same few variables to
simplify the statements by evaluating the operands (`Board.getPositionX(placementSequence)` etc) just once, assigning
to a variable and then performing checks on that variable. You also prevent performing redundant computations this way.

# Feedback for all members

To get the best marks for D2G, it is really important that you make good use of OOP structures in your code. This includes
both static and instance state + behaviour. I recommend you consider using objects to represent the state of the game
board, in order to replace string representations.