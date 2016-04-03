// Team -- Alan Chen and Calvin Vuong
// APCS2 pd10
// HW24 -- Schemin
// 2016-04-04

/*****************************************************
 * class Scheme
 * Simulates a rudimentary Scheme interpreter
 *
 * ALGORITHM for EVALUATING A SCHEME EXPRESSION:
      1. Steal underpants.
      2. ...
      5. Profit!
 *
 * STACK OF CHOICE: LLStack by Team
 * b/c the implementation is simple.
 ******************************************************/

public class Scheme {

    /****************************************************** 
     * precond:  Assumes expr is a valid Scheme (prefix) expression,
     *           with whitespace separating all operators, parens, and 
     *           integer operands.
     * postcond: Returns the simplified value of the expression, as a String
     * eg,
     *           evaluate( "( + 4 3 )" ) -> 7
     *	         evaluate( "( + 4 ( * 2 52 ) 3 )" ) -> 17
     ******************************************************/
    public static String evaluate( String expr ) {
	LLStack<String> mainStack = new LLStack<String>();

	//parse expr for numbers, operations, parentheses
	for ( int i = 0; i < expr.length(); i++ ){
	    String operations = "+-*", chars = expr.substring(i, i + 1);
	    
	    if ( chars.equals("(") || operations.indexOf(chars) > -1)
		mainStack.push(chars);

	    //while loop to sort through consecutive numeric values
	    else if ( isNumber(chars) ) {
		while ( isNumber(expr.substring(i + 1, i + 2)) ) {
		    chars += expr.substring(i + 1, i + 2);
		    i++;
		}
		System.out.println(chars);
		mainStack.push(chars);
	    }
	    
	    // put elements below ")" until the next "(" into a new stack
	    // to feed into unload()
	    else if ( chars.equals(")") ) {
		LLStack<String> miniStack = new LLStack<String>();

		while ( ! mainStack.peek().equals("(") ){
		    miniStack.push(mainStack.pop());
		} // at exit, this parenthesis is stored to be evaluated
		mainStack.pop(); // remove open parens
		// push result of parens to stack
		mainStack.push( unload(miniStack) );
	    }

	} //end for-loop
           
	return mainStack.peek();

    }//end evaluate()


    /****************************************************** 
     * precond:  Assumes top of input stack is an operation.
     * postcond: Performs op on nums successively until the stack is empty.
     *           Returns the result of operation on sequence of operands as a number in String form.
     ******************************************************/
    public static String unload( LLStack<String> numbers ) {
	String operation = numbers.pop();

	while ( size(numbers) >= 2 ) {
	   
	    if ( operation.equals("+") )
		numbers.push(Integer.toString( Integer.parseInt(numbers.pop()) +
					       Integer.parseInt(numbers.pop())));
	    else if ( operation.equals("-") )
		numbers.push(Integer.toString( Integer.parseInt(numbers.pop()) -
					       Integer.parseInt(numbers.pop())));
	    else if ( operation.equals("*") )
		numbers.push(Integer.toString( Integer.parseInt(numbers.pop()) *
					       Integer.parseInt(numbers.pop())));
	}
	
	return numbers.peek();
    }//end unload()

    // returns the size of input stack
    public static int size( LLStack<String> stack ) {
	int stackSize = 0;
	LLStack<String> tmpStack = new LLStack<String>();
	// count number of items
	while ( ! stack.isEmpty() ){
	    tmpStack.push(stack.pop());
	    stackSize += 1;
	}
	// repopulate original stack
	while ( ! tmpStack.isEmpty() ){
	    stack.push(tmpStack.pop());
	}
	return stackSize;
    } // end size

    
    public static boolean isNumber( String s ) {
        try {
	    Integer.parseInt(s);
	    return true;
	}
        catch( NumberFormatException e ) {
	    return false;
	}
    }

    //main method for testing
    public static void main( String[] args ) {

	String zoo1 = "( + 4 3 )";
	System.out.println(zoo1);
	System.out.println("zoo1 eval'd: " + evaluate(zoo1) );
	//...7

	String zoo2 = "( + 4 ( * 2 5 ) 3 )";
	System.out.println(zoo2);
	System.out.println("zoo2 eval'd: " + evaluate(zoo2) );
	//...17

	String zoo3 = "( + 4 ( * 2 5 ) 6 3 ( - 56 50 ) )";
	System.out.println(zoo3);
	System.out.println("zoo3 eval'd: " + evaluate(zoo3) );
	//...29

	String zoo4 = "( - 1 2 3 )";
	System.out.println(zoo4);
	System.out.println("zoo4 eval'd: " + evaluate(zoo4) );
	//...-4
	/*v~~~~~~~~~~~~~~MAKE MORE~~~~~~~~~~~~~~v
          ^~~~~~~~~~~~~~~~AWESOME~~~~~~~~~~~~~~~^*/
    }//main

}//end class Scheme
