package jtm.extra03;

import java.util.ArrayList;
import java.util.Arrays;

public class PracticalNumbers {

	// TODO Read article https://en.wikipedia.org/wiki/Practical_number
	// Implement method, which returns practical numbers in given range
	// including
	
	public int getFactorial(int a)
	{
		if (a<=1)
			return 1; 
		else return a*getFactorial(a-1);
		
	}
	
	public class numAndItsDivisors
	{
		public final int num;
		public final ArrayList<Integer> divisors;
		
		numAndItsDivisors(int num)
		{
			this.num = num;
			this.divisors = PracticalNumbers.getDivisors(num);
		}
	}
	
	
	public String getPracticalNumbers(int from, int to) {
//		check if all numbers that are less than the number are sums of the number's divisors
//		if all of them are then the number is a practical numbers
		
		
		ArrayList <numAndItsDivisors> nums = new ArrayList <numAndItsDivisors>();
		ArrayList <Integer> practicalNums = new ArrayList <Integer>();
		
		for (int i =from;i<=to;i++)
		{
			nums.add(new numAndItsDivisors(i));
		}
		
		for (numAndItsDivisors x: nums) // for each number [from;to]
		{
			int checkedInts = 0;
			for (int i = 1;i< x.num;i++) // for each number less than the potential practical number
			{
				if (x.divisors.contains(i))
				{
					
				}
				else 
				{
					for (int r=1;r<=x.divisors.size();r++)
					{
						for (int z=1;z<=( 1/*formula for combinations where order doesnt matter*/);z++)
						{
							
						}
					}
					
				}
				
					
					
				
				
				
			}
			
			if (checkedInts == x.num-1)
			{
				practicalNums.add(x.num);
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		return "";
	}
	
	public static ArrayList<Integer> getDivisors(int a)
	{
		ArrayList<Integer> divisors = new ArrayList<Integer>();
		
		for (int i=1;i<=a;i++)
		{
			if (a%i == 0 && a!=i)
			divisors.add(i);
		}
		
		return divisors;
	}
	
	
	

}