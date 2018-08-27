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
	public String[] splitString(String text, String delimiter) {
		String[] list = null;
		// TODO # 1 Split passed text by given delimiter and return array with
		// split strings.
		// HINT: Use System.out.println to better understand split method's
		// functionality.
		return list;
	}

	public List<String> tokenizeString(String text, String delimiter) {
		// TODO # 2 Tokenize passed text by given delimiter and return list with
		// tokenized strings.
		List<String> list = new ArrayList<>();
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

}
