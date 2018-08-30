package jtm.activity08;

// TODO implement basic mathematical operations with int numbers in range
// of [-10..+10] (including)
// Note that:
// 1. input range is checked using assertions (so if they are disabled, inputs can be any int)
// 2. outputs are always checked and exception is thrown if it is outside proper range

public class SimpleCalc {

	// TODO specify that method can throw SimpleCalcException
	public static int add(int a, int b) throws SimpleCalcException {
		validateInput(a, b);
		validateOutput(a, b, "+");
		return a + b;
	}

	// TODO specify that method can throw SimpleCalcException
	public static int subtract(int a, int b) throws SimpleCalcException {
		validateInput(a, b);
		validateOutput(a, b, "-");
		return a - b;
	}

	// TODO specify that method can throw SimpleCalcException
	public static int multiply(int a, int b) throws SimpleCalcException {
		validateInput(a, b);
		validateOutput(a, b, "*");
		return a * b;
	}

	// TODO specify that method can throw SimpleCalcException
	public static int divide(int a, int b) throws SimpleCalcException {
		validateInput(a, b);
		validateOutput(a, b, "/");
		return a / b;
	}

	// TODO Validate that inputs are in range of -10..+10 using assertions
	// Use following messages for assertion description if values are not in
	// range:
	// "input value a: A is below -10"
	// "input value a: A is above 10"
	// "input value b: B is below -10"
	// "input value b: B is above 10"
	// "input value a: A is below -10 and b: B is below -10"
	// "input value a: A is above 10 and b: B is below -10"
	// "input value a: a is below -10 and b: B is above 10"
	// "input value a: a is above 10 and b: B is above 10"
	//
	// where: A and B are actual values of a and b.
	//
	// hint:
	// note that assert allows only simple boolean expression
	// (i.e. without &, |, () and similar constructs).
	// therefore for more complicated checks use following approach:
	// if (long && complicated || statement)
	// assert false: "message if statement not fulfilled";
	//
	private static void validateInput(int a, int b) {

		if ((a > 10 || a < -10) && b <= 10 && b >= -10) // only A is incorrect
		{
			if (a > 10) {
				assert false : "input value a: " + a + " is above 10";
			} else {
				assert false : "input value a: " + a + " is below -10";
			}

		} else if ((b > 10 || b < -10) && a <= 10 && a >= -10) // only B is incorrect
		{
			if (b > 10) {
				assert false : "input value b: " + b + " is above 10";
			} else {
				assert false : "input value b: " + b + " is below -10";
			}

		} else if ((b > 10 || b < -10) && (a > 10 || a < -10)) // both A and B are incorrect
		{
			if (a < -10 && b < -10) // both below - 10
			{
				assert false : "input value a: " + a + " is below -10 and b: " + b + " is below -10";
			} else if (a > 10 && b < -10) // a above b below
			{
				assert false : "input value a: " + a + " is above 10 and b: " + b + " is below -10";
			} else if (a < -10 && b > 10) // a below b above
			{
				assert false : "input value a: " + a + " is below -10 and b: " + b + " is above 10";
			} else if (a > 10 && b > 10)// a above b above
			{
				assert false : "input value a: " + a + " is above 10 and b: " + b + " is above 10";
			}

		}

	}

	// TODO use this method to check that result of operation is also in
	// range of -10..+10.
	// If result is not in range:
	// throw SimpleCalcException with message:
	// "output value a oper b = result is above 10"
	// "output value a oper b = result is below -10"
	// where oper is +, -, *, /
	// Else:
	// return result
	// Hint:
	// If division by zero is performed, catch original exception and create
	// new SimpleCalcException with message "division by zero" and add
	// original division exception as a cause for it.
	private static int validateOutput(int a, int b, String operation) throws SimpleCalcException {
		String assStr = "output value " + a + " " + operation + " " + b + " = ";
		switch (operation) {
		case "+":
			assStr += (a + b) + " is ";
			if (a + b > 10)
				throw new SimpleCalcException(assStr + "above 10");
			else if (a + b < -10)
				throw new SimpleCalcException(assStr + "below -10");
			break;
		case "-":
			assStr += (a - b) + " is ";
			if (a - b > 10)
				throw new SimpleCalcException(assStr + "above 10");
			else if (a - b < -10)
				throw new SimpleCalcException(assStr + "below -10");
			break;
		case "*":
			assStr += (a * b) + " is ";
			if (a * b > 10)
				throw new SimpleCalcException(assStr + "above 10");
			else if (a * b < -10)
				throw new SimpleCalcException(assStr + "below -10");
			break;
		case "/":
			try {
				assStr += (a / b) + " is ";
				assert a / b <= 10 : assStr + "above 10";
				assert a / b >= -10 : assStr + "below -10";
				break;
			} catch (ArithmeticException ae) {
				if (b==0)
				throw new SimpleCalcException("division by zero", ae);
			}
		}

		return 0;
	}
}
