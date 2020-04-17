# FeatureGating

## Description
 
 Evaluate a conditional expression to determine if a user with a certain set of attributes is eligible to use a feature
 
 ### Outline
  The conditional expression is evaluated by tokenizing it and using the ```Stack``` data structure to convert it from an infix notation of operators and operands to a postfix notation
 
 #### Infix Notation
 ```Operand1 operator Operand2```
 
 #### Postfix Notation
 ```Operand1 Operand2 operator```
 
 An important assumption here is the operators used here are binary operators only. Using the knowledge of precedence of operators, the expression is finally evaluated to a ```Boolean``` value
 
 ### Supported Operators and Features
 The given operators - ```>```, ```>=```, ```<=```, ```<```, ```==```, ```!=```, ```BETWEEN```, ```ALLOF```, ```NONEOF```, ```AND``` and ```OR``` have been added
 
 Supported data types - ```Integer```, ```Double```, ```String```, ```List``` and ```Boolean```
 
 ### Environment
 Use any IDE and import the project as a ```maven``` project. The JDK used is ```1.8```
 
 #### Testing
 ```Main.java``` contains a sample run of the kind of expressions and user attributes you can use to test the feature gating module. Add more expressions accordingly to test further
 
 ### References
 - Evaluate an expression -
 http://faculty.cs.niu.edu/~hutchins/csci241/eval.htm
 
 - Operator precedence -
 https://introcs.cs.princeton.edu/java/11precedence/
 
