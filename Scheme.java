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
     *	         evaluate( "( + 4 ( * 2 5 ) 3 )" ) -> 17
     ******************************************************/
    public static String evaluate( String expr ) {
	Stack<String> mainStack = new LLStack<String>();

	for ( int i = 0; i < expr.length(); i++ ){
	    String chr = expr.substring(i,i+1);
	    // put elements below ) until open ( into a new stack to feed into unload()
	    if ( chr.equals(")") ) {
		Stack<String> miniStack = new LLStack<String>();
		while ( ! mainStack.peek().equals("(") ){
		    miniStack.push( mainStack.pop() );
		} // at exit, this parenthes is stored to be evaluated
		mainStack.pop(); // remove open parens
		mainStack.push( unload(miniStack) ); // push result of parens to stack
	    }
	    else
		mainStack.push(chr);
	}
	return mainStack.peek();

    }//end evaluate()


    /****************************************************** 
     * precond:  Assumes top of input stack is an operation.
     * postcond: Performs op on nums successively until the stack is empty.
     *           Returns the result of operation on sequence of operands as a number in String form.
     ******************************************************/
    public static String unload( Stack<String> numbers ) {
	String operation = numbers.pop();
	while ( size(numbers) >= 2 ){
	    if ( operation.equals("+") )
		numbers.push(Integer.toString( Integer.parseInt(numbers.pop()) + Integer.parseInt(numbers.pop())) );
	    else if ( operation.equals("-") )
		numbers.push(Integer.toString( Integer.parseInt(numbers.pop()) - Integer.parseInt(numbers.pop())) );
	    else if ( operation.equals("*") )
		numbers.push(Integer.toString( Integer.parseInt(numbers.pop()) * Integer.parseInt(numbers.pop())) );
	}
	return numbers.peek();
    }//end unload()

    // returns the size of input stack
    public static int size( Stack<String> stack ) {
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

	/*v~~~~~~~~~~~~~~MAKE MORE~~~~~~~~~~~~~~v
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
          ^~~~~~~~~~~~~~~~AWESOME~~~~~~~~~~~~~~~^*/
    }//main

}//end class Scheme
