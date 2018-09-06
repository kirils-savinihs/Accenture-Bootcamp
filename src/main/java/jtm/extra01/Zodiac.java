package jtm.extra01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Zodiac {
	public final String name;
	public final int sDay;
	public final int eDay;
	public final int sMonth;
	public final int eMonth;

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	public static final Zodiac[] zodiacs = { new Zodiac("Aries", 21, 3, 20, 4), new Zodiac("Taurus", 21, 4, 21, 5),
			new Zodiac("Gemini", 22, 5, 21, 6), new Zodiac("Cancer", 22, 6, 22, 7), new Zodiac("Leo", 23, 7, 22, 8),
			new Zodiac("Virgo", 23, 8, 23, 9), new Zodiac("Libra", 24, 9, 23, 10), new Zodiac("Scorpio", 24, 10, 22, 11),
			new Zodiac("Sagittarius", 23, 11, 21, 12), new Zodiac("Capricorn", 22, 12, 20, 1), new Zodiac("Aquarius", 21, 1, 19, 2),
			new Zodiac("Pisces", 20, 2, 20, 3) };

	public Zodiac(String name, int sDay, int sMonth, int eDay, int eMonth) {
		this.name = name;
		this.sDay = sDay;
		this.eDay = eDay;
		this.sMonth = sMonth;
		this.eMonth = eMonth;
	}

	public static Date startToDate(Zodiac z) throws ParseException {
		String dayString;
		if (z.sDay < 10) {
			dayString = "0" + Integer.toString(z.sDay);
		} else {
			dayString = Integer.toString(z.sDay);
		}

		String monthString;
		if (z.sMonth < 10) {
			monthString = "0" + Integer.toString(z.sMonth);

		} else {
			monthString = Integer.toString(z.sMonth);
		}

		return sdf.parse(dayString + "-" + monthString + "-2018");

	}

	public static Date endToDate(Zodiac z) throws ParseException {
		String dayString;
		if (z.sDay < 10) {
			dayString = "0" + Integer.toString(z.eDay);
		} else {
			dayString = Integer.toString(z.eDay);
		}

		String monthString;
		if (z.sMonth < 10) {
			monthString = "0" + Integer.toString(z.eMonth);

		} else {
			monthString = Integer.toString(z.eMonth);
		}

		return sdf.parse(dayString + "-" + monthString + "-2018");

	}

	/**
	 * Determine the sign of the zodiac, use day and month.
	 * 
	 * @param day
	 * @param month
	 * @return zodiac
	 */
	public static String getZodiac(int day, int month) {
		// TODO #1: Implement method which return zodiac sign names
		// As method parameter - day and month;
		// Look at wikipedia:
		// https://en.wikipedia.org/wiki/Zodiac#Table_of_dates
		// Tropical zodiac, to get appropriate date ranges for signs

		String dayString;
		String monthString;

		if (day < 10) {
			dayString = "0" + Integer.toString(day);
		} else {
			dayString = Integer.toString(day);
		}

		if (month < 10) {
			monthString = "0" + Integer.toString(month);
		} else {
			monthString = Integer.toString(month);
		}

		int x = 9;
		Date testDate;
		try {
			testDate = sdf.parse(dayString + "-" + monthString + "-2018");


			for (int i = 0;i<12;i++)
			{
				if (testDate.compareTo(endToDate(zodiacs[x])) == 0 ||testDate.compareTo(endToDate(zodiacs[x])) < 0) {
					return zodiacs[x].name;
				}
				
				if (x==11)
				{
					x=0;
				}
				else x++;
				
			}
			
			if (testDate.compareTo(sdf.parse("01-01-2019"))  < 0)
			{
				return zodiacs[9].name;
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "ERROR";

	}

	public static void main(String[] args) {
		// HINT: you can use main method to test your getZodiac method with
		// different parameters
		System.out.println(getZodiac(23,12));

	}

}
