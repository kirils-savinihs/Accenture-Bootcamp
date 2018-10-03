package jtm.extra03;

import java.util.ArrayList;
import java.util.Arrays;

public class PracticalNumbers {

	// TODO Read article https://en.wikipedia.org/wiki/Practical_number
	// Implement method, which returns practical numbers in given range
	// including

	static void log(String str) {
		System.out.println(str);
	}

	public static boolean isPractical(int num) {
		ArrayList<Integer> factorList = new ArrayList<Integer>();

		for (int i = 1; i < num; i++) {
			if (num % i == 0) {
				factorList.add(i);
			}
		}

		Integer[] factors = factorList.toArray(new Integer[factorList.size()]); //All factors of num

		//for each number less than num
		for (int i = num - 1; i > 0; i--) {
			int temp = i;

			//for each factor that is less than the number itself, starting with the highest
			for (int j = factors.length - 1; j >= 0; j--) {

				//if the number is > 0
				if (temp > 0) {
					//subtract the factor
					temp -= factors[j];
				}
				//if the number is <0
				else if (temp < 0) {
					//add the factor
					temp += factors[j];
				}
				//if the number is 0
				else {	
					//stop
					break;
				}

			}
			if (temp != 0) {
				//not practical
				return false;
			}

		}
		//practical
		return true;
	}

	public String getPracticalNumbers(int from, int to) {

		StringBuilder practical = new StringBuilder();
		boolean first = true;

		practical.append("[");
		for (int i = from; i <= to; i++) {
			if (isPractical(i)) {
				if (first) {
					practical.append(Integer.toString(i));
					first = false;
				} else
					practical.append(", " + Integer.toString(i));
			}
		}
		practical.append("]");

		return practical.toString();
	}

	public static void main(String[] args) {
		isPractical(4);
	}

}