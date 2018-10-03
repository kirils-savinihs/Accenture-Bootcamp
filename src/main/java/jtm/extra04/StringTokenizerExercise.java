package jtm.extra04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*-
 * 
 * This class represents string tokenizer exercise.
 */
public class StringTokenizerExercise {

//Solution without use of StringTokenizer class because i didn't know such a thing existed QQ

//	public String[] splitString(String text, String delimiter) {
//		ArrayList<StringBuilder> list = new ArrayList<StringBuilder>();
//		list.add(new StringBuilder());
//
//		int counter = 0;
//		boolean match;
//
//		if (text.contains(delimiter)) {
//			for (int i = 0; i < text.length(); i++) {
//
//				match = true;
//				for (int y = 0; y < delimiter.length(); y++) {
//					if (text.charAt(i + y) != delimiter.charAt(y)) {
//						match = false;
//
//					}
//				}
//				if (!match) {
//					list.get(counter).append(text.charAt(i));
//				} else {
//					i += delimiter.length() - 1;
//					counter++;
//					list.add(new StringBuilder());
//				}
//			}
//
//			StringBuilder[] strbldrs = list.toArray(new StringBuilder[list.size()]);
////			for (StringBuilder s :strbldrs)
////			{
////				
////			}
//
//		} else {
//			String[] err = { "ERROR" };
//			return err;
//		}
//
//		// TODO # 1 Split passed text by given delimiter and return array with
//		// split strings.
//		// HINT: Use System.out.println to better understand split method's
//		// functionality.
//		StringBuilder[] strb = list.toArray(new StringBuilder[list.size()]);
//
//		String[] strings = new String[strb.length];
//		for (int i = 0; i < strb.length; i++) {
//			strings[i] = strb[i].toString();
//		}
//
//		return strings;
//
//	}

	

	public String[] splitString(String text, String delimiter) {
	
	StringTokenizer strt = new StringTokenizer (text,delimiter);
	ArrayList <String> strList = new ArrayList<String>();	
	while (strt.hasMoreTokens())
	{
		strList.add(strt.nextToken());
	}
	
	return strList.toArray(new String[strList.size()]);

}
	
	public List<String> tokenizeString(String text, String delimiter) {
		// TODO # 2 Tokenize passed text by given delimiter and return list with
		// tokenized strings.
		List<String> list = new ArrayList<>();
		
		StringTokenizer strt = new StringTokenizer(text,delimiter);
		
		while (strt.hasMoreTokens())
		{
			list.add(strt.nextToken());
		}
		
		
		
		return list;
	}

	public List<Student> createFromFile(String filepath, String delimiter) {
		File students = new File(filepath);
		List<Student> list = new ArrayList<Student>();
		BufferedReader in = null;
		// TODO # 3 Implement method which reads data from file and creates
		// Student objects with that data. Each line from file contains data for
		// 1 Student object.
		// Add students to list and return the list. Assume that all passed data
		// and
		// files are correct and in proper form.
		// Advice: Explore StringTokenizer or String split options.
		return list;
	}

	public static void main(String[] args) {
		String testString = "abcdVRMabcdVRMjfhsVRM";

		StringTokenizerExercise strTok = new StringTokenizerExercise();

		String[] test = strTok.splitString(testString, "VRM");

		for (String str : test) {
			System.out.println(str);
		}
		


	}

}
