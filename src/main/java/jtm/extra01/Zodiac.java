package jtm.extra01;

public class Zodiac {
	public final String name;
	public final int sDay;
	public final int sMonth;
	public final int eDay;
	public final int eMonth;


	public Zodiac(String name ,int sDay,int sMonth,int eDay, int eMonth) {
		this.sDay = sDay;
		this.sMonth = sMonth;
		this.eDay = eDay;
		this.eMonth = eMonth;
		this.name = name;
	}

	private static Zodiac[] zodiacs = 
		{	
			new Zodiac("Aries",21,3,20,4),
			new Zodiac ("Taurus",21,4,21,5),
			new Zodiac ("Gemini",22,5,21,6),
			new Zodiac ("Cancer",22,6,22,7),
			new Zodiac ("Leo",23,7,22,8),
			new Zodiac ("Vigro",23,8,23,9),
			new Zodiac ("Libra",24,9,23,10),
			new Zodiac ("Scorpio",24,10,22,11),
			new Zodiac ("Sagittarius",23,11,21,12),
			new Zodiac ("Capricorn",22,12,20,1),
			new Zodiac ("Aquarius",21,1,19,2),
			new Zodiac ("Pisces",20,2,20,3)
			};
	
	

	
	/**
	 * Determine the sign of the zodiac, use day and month.
	 * 
	 * @param day
	 * @param month
	 * @return zodiac
	 */
	public static String getZodiac(int day, int month) {
		String zodiac = null;
		// TODO #1: Implement method which return zodiac sign names
		// As method parameter - day and month;
		// Look at wikipedia:
		// https://en.wikipedia.org/wiki/Zodiac#Table_of_dates
		// Tropical zodiac, to get appropriate date ranges for signs
		for (Zodiac z:zodiacs)
		{
			
		}
		
		
		return zodiac;
	}

	public static void main(String[] args) {
		// HINT: you can use main method to test your getZodiac method with
		// different parameters
		System.out.println(getZodiac(1, 1));
		

	}

}
