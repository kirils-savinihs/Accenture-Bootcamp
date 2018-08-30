package jtm.activity08;

public class SimpleCalcException extends Exception {
	
	

	public SimpleCalcException(String string)
	{
		super(string);
	}
	
	public SimpleCalcException (String string, Throwable cause)
	{
		super(string,cause);
	}

}
