package jtm;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.IExecutionDataVisitor;
import org.jacoco.core.data.ISessionInfoVisitor;
import org.jacoco.core.data.SessionInfo;

public class TestUtils {
	private static Logger logger = Logger.getLogger(TestUtils.class);
	public static final String lf = System.lineSeparator();

	// Holder for temporary store of read(InputStream is) value
	private static String threadValue = "";

	public static List<String> listMethods(Object objectOrClasspath) {
		Method[] methods = null;
		List<String> methodList = new ArrayList<>();
		try {
			if (objectOrClasspath instanceof String)
				methods = Class.forName((String) objectOrClasspath).getMethods();
			else
				methods = objectOrClasspath.getClass().getDeclaredMethods();

		} catch (ClassNotFoundException e) {
			logger.error("Could not find class: " + objectOrClasspath);
		} catch (SecurityException e) {
			logger.error("Could not get access to class: " + objectOrClasspath);
		} catch (Throwable e) {
			handleErrorAndFail(e);
		}
		for (Method m : methods) {
			methodList.add(m.toString());
		}
		Collections.sort(methodList);
		logger.debug(objectOrClasspath + " method list (collection): " + methodList);
		return methodList;
	}

	/**
	 * Checks list of constructors and methods of the class
	 * 
	 * @param neededList
	 *            — array of needed methods and constructors, to filter out
	 *            unnecessary (inherited) methods
	 * @param objectOrClasspath
	 *            — reference to tested object or classpath as String of the
	 *            tested class
	 * @return — List of constructors and methods as paragraph
	 */

	public static String checkMethods(String[] neededList, Object objectOrClasspath) {

		List<String> actualMethodList, actualConstructorList = null, tempList;
		if (objectOrClasspath instanceof String) {
			actualMethodList = listMethods(objectOrClasspath);
			actualConstructorList = listConstructors((String) objectOrClasspath);
		} else
			actualMethodList = listMethods(objectOrClasspath);
		tempList = new ArrayList<>();
		// TODO maybe next check for constructors is unnecessary (added for
		// backward compatibility)
		if (objectOrClasspath instanceof String) {
			for (String neededMethod : neededList) {
				for (String actualMethod : actualConstructorList) {
					if (neededMethod.equals(actualMethod))
						tempList.add(actualMethod);
				}
			}
		}
		for (String neededMethod : neededList) {
			for (String actualMethod : actualMethodList) {
				if (neededMethod.equals(actualMethod))
					tempList.add(actualMethod);
			}
		}
		return toParagraph(tempList);

	}

	/**
	 * @param neededList
	 *            — list of needed entries to be checked, delimited with
	 *            linebreaks
	 * @param actualList
	 *            — list of actual entries (may have more entries than checked),
	 *            delimited with linebreaks
	 * @return — list of matching (i.e. join of needed and actual) entries,
	 *         delimited with linebreaks
	 */
	public static String checkLists(String neededList, String actualList) {
		List<String> tempList = new ArrayList<>();
		for (String neededEntry : neededList.split("\n")) {
			for (String actualEntry : actualList.split("\n")) {
				if (neededEntry.equals(actualEntry))
					tempList.add(actualEntry);
			}
		}
		return toParagraph(tempList);
	}

	private static List<String> listConstructors(String classpath) {
		Constructor<?>[] cons = null;
		List<String> clist = new ArrayList<>();
		try {
			cons = Class.forName(classpath).getConstructors();
		} catch (ClassNotFoundException e) {
			logger.error("Could not find class: " + classpath);
		} catch (SecurityException e) {
			logger.error("Could not get access to class: " + classpath);
		} catch (Throwable e) {
			handleErrorAndFail(e);
		}
		for (Constructor<?> m : cons) {
			clist.add(m.toString());
		}
		Collections.sort(clist);
		logger.debug(classpath + " constructor list (collection): " + clist);
		return clist;
	}

	public static String getConstructors(String classpath) {
		StringBuilder list = new StringBuilder("");
		List<String> methodList = listConstructors(classpath);
		for (String item : methodList)
			list.append(item + '\n');
		if (list.length() > 1)
			list.deleteCharAt(list.length() - 1);
		logger.debug(classpath + " constructor list (as string):\n" + list.toString());
		return list.toString();
	}

	public static String toParagraph(Object list) {
		String tmp = null;
		if (list instanceof String)
			tmp = (String) list;
		if (list instanceof String[])
			tmp = Arrays.toString((String[]) list);
		if (list instanceof List)
			tmp = list.toString();
		if (tmp == null)
			tmp = "";
		tmp = tmp.replaceAll("\\[|\\]", "").replace(", ", "\n");
		return tmp;
	}

	/**
	 * @param object
	 *            to which method should be invoked (if null, new object is
	 *            created
	 * @param className
	 *            classpath and name of the used class
	 * @param methodName
	 *            invoked method
	 * @return value of the method result
	 */
	public static Object invokeGetMethod(Object object, String className, String methodName) {
		Class<?> thisClass;
		try {
			thisClass = Class.forName(className);

			// Create object of passed class name, if null
			if (object == null)
				object = thisClass.newInstance();

			// Get reference to the method
			Method thisMethod = object.getClass().getMethod(methodName);

			// Execute method
			Object result = thisMethod.invoke(object);
			return result;

		} catch (Throwable e) {
			handleErrorAndFail(e);
		}
		return null;
	}

	/**
	 * @param className
	 *            – full class name
	 * @param methodName
	 *            — name of the static method
	 * @param params
	 *            — optional parameters for method (or null)
	 */
	public static void invokeStaticMethod(String className, String methodName, Object params) {
		Class<?> thisClass;
		Method meth;
		try {
			thisClass = Class.forName(className);
			// Get reference to the method
			if (params == null) {
				meth = thisClass.getMethod(methodName);
				meth.invoke(null); // static method doesn't have an instance
			} else {
				meth = thisClass.getMethod(methodName, String[].class);
				meth.invoke(null, params);
			}
		} catch (Throwable e) {
			handleErrorAndFail(e);
		}
	}

	/**
	 * @param object
	 *            to which method should be invoked (if null, new object is
	 *            created
	 * @param className
	 *            used class with full classpath
	 * @param methodName
	 *            invoked method
	 * @param value
	 *            passed value to be set
	 */

	@SuppressWarnings("unchecked")
	public static <E1, E2> void invokeSetMethod(E1 object, String className, String methodName, E2 value) {
		Class<?> thisClass;
		try {
			thisClass = Class.forName(className);
			// Create object of passed class name, if null
			if (object == null)
				object = (E1) thisClass.newInstance();

			// Method[] methods = thisClass.getMethods();
			// for (Method m : methods)
			// logger.debug("Method:" + m.getName());

			Class<?> partypes[] = new Class[1];
			// Deal with primitive types
			if (value instanceof Integer)
				partypes[0] = Integer.TYPE;
			if (value instanceof Double)
				partypes[0] = Double.TYPE;
			if (value instanceof Float)
				partypes[0] = Float.TYPE;
			if (value instanceof Boolean)
				partypes[0] = Boolean.TYPE;
			if (value instanceof Long)
				partypes[0] = Long.TYPE;
			if (value instanceof Character)
				partypes[0] = Character.TYPE;
			// Use reflection otherwise
			if (partypes[0] == null)
				partypes[0] = value.getClass();

			Object arglist[] = new Object[1];
			arglist[0] = value;

			// Get reference to the method
			Method thisMethod = object.getClass().getMethod(methodName, partypes);

			// Execute method
			thisMethod.invoke(object, arglist);

		} catch (Throwable e) {
			handleErrorAndFail(e);
		}

	}

	/**
	 * @param string
	 *            of to classname
	 * @return reference to the created object of the class
	 */
	public static Object createObject(String classname) {
		Class<?> c;
		Object obj = null;
		try {
			c = Class.forName(classname);
			Constructor<?> cons = c.getConstructor();
			obj = cons.newInstance();
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
		return obj;
	}

	/**
	 * @param object
	 *            reference to the object
	 * @return comma delimited list of fields
	 * @throws Exception
	 */
	public String listFields(Object object) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(object.getClass().getName());
		sb.append(": ");
		for (Field f : (object.getClass().getDeclaredFields())) {
			sb.append(f.getName());
			sb.append("=");
			sb.append(f.get(object));
			sb.append(", ");
		}
		return sb.toString();
	}

	/**
	 * Non blocking read from input stream using controlled thread
	 * 
	 * @param is
	 *            — InputStream to read
	 * @param timeout
	 *            — timeout, should not be less that 10
	 * @return
	 */
	public static String read(final InputStream is, int timeout) {
		// Start reading bytes from stream in separate thread
		Thread thread = new Thread() {
			public void run() {
				byte[] buffer = new byte[1024]; // read buffer
				byte[] readBytes = new byte[0]; // holder of actually read bytes
				try {
					Thread.sleep(5);
					// Read available bytes from stream
					int size = is.read(buffer);
					if (size > 0)
						readBytes = Arrays.copyOf(buffer, size);
					// and save read value in static variable
					setValue(new String(readBytes, "UTF-8"));
				} catch (Exception e) {
					logger.error("Error reading input stream\nStack trace:\n" + TestUtils.stackTraceToString(e));
				}
			}
		};
		thread.start(); // Start thread
		try {
			thread.join(timeout); // and join it with specified timeout
		} catch (InterruptedException e) {
			System.err.println("Data were note read in " + timeout + " ms");
		}
		return getValue();
	}

	public static String read(InputStream is) {
		return read(is, 100);
	}

	private static synchronized void setValue(String value) {
		threadValue = value;
	}

	private static synchronized String getValue() {
		String tmp = new String(threadValue);
		setValue("");
		return tmp;
	}

	/**
	 * Standard handler of the unit test methods
	 * 
	 * @param e
	 */
	public static void handleErrorAndFail(Throwable e) {
		String message = handleError(e);
		fail(message);
	}

	/**
	 * Handles error and fails the unit test
	 * 
	 * @param e
	 *            — passed Throwable
	 * @param message
	 *            — custom message for fail
	 */
	public static void handleErrorAndFail(Throwable e, String message) {
		handleError(e);
		fail(message);
	}

	/**
	 * Handles test without failing it
	 * 
	 * @param e
	 *            — passed Throwable
	 * @return — custom message
	 */
	public static String handleError(Throwable e) {
		String message = "\n" + e.getClass().getName();
		String msg = e.getMessage();
		if (msg != null)
			message += ", message: " + e.getMessage();
		Throwable cause = e.getCause();
		while (cause != null) {
			message = message + "\ncause: " + cause.getClass().getName();
			msg = cause.getMessage();
			if (msg != null)
				message += ", message: " + msg;
			cause = cause.getCause();
		}
		message = message + "\nStack trace:\n" + stackTraceToString(e);
		logger.error(message);
		return message;
	}

	/**
	 * @param t
	 *            — Throwable
	 * @return String of stack trace in separate lines
	 */
	public static String stackTraceToString(Throwable t) {
		return Arrays.toString(t.getStackTrace()).replaceAll(", ", "\n");
	}

	public static String randomLatinWord() {
		Random rnd = new Random();
		int i = rnd.nextInt(6) + 2;
		String tmp = "";
		while (--i > 0) {
			tmp = tmp + (i % 2 == 0 ? "aeiouy" : "bcdfghjklmnpqrstvwxz").charAt(rnd.nextInt(6 + (i % 2) * 14));
		}
		return tmp;
	}

	public static String randomLatinName() {
		String tmp = randomLatinWord();
		tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
		return tmp;
	}

	/**
	 * @return random string as a pronounceable name
	 */
	public static String randomWord() {
		Random rnd = new Random();
		int i = rnd.nextInt(6) + 2;
		String tmp = "";
		while (--i > 0) {
			tmp = tmp + (i % 2 == 0 ? "aeioōuy" : "bcčdfgģhjkķlļmnņpqrŗsštvwxzž").charAt(rnd.nextInt(6 + (i % 2) * 14));
		}
		return tmp;
	}

	public static String randomName() {
		String tmp = randomWord();
		tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
		return tmp;
	}

	public static String randomName(boolean isFemale) {
		String tmp = randomName();
		if (isFemale) {
			if (tmp.matches("(?i).*[bcčdfgģhjkķlļmnņpqrŗsštvwxzž]$"))
				tmp += randomWord("a", "e");
			else
				tmp += randomWord("na", "ne");
		} else {
			if (tmp.matches("(?i).*[bcčdfgģhjkķlļmnpqrŗsštvwxzž]$"))
				tmp += randomWord("is", "iņš");
			else
				tmp += randomWord("s", "š");
		}
		return tmp;
	}

	public static String randomWord(String... words) {
		return words[randomInt(words.length - 1)];
	}

	public static int randomInt(int range) {
		Random rnd = new Random();
		return rnd.nextInt(range + 1);
	}

	public static double randomDbl(double range) {
		Random rnd = new Random();
		return rnd.nextDouble() * range;
	}

	public static int randomInt(int range, int startFrom) {
		return randomInt(range) + startFrom;
	}

	public static String stripLineBreaks(String str) {
		return str.replaceAll("\r|\n", "");
	}

	public static String checkOS() {
		String os = System.getProperty("os.name");
		if (os.startsWith("Mac")) {
			logger.warn("This test uses GNU tools and some of tests may fail. If they do, please, run tests manually.");
			jtm.DialogWindow.main(new String[] {
					"This test uses GNU tools and some of tests may fail. If they do, please, run tests manually." });
			return "Mac";
		}
		if (os.startsWith("Windows")) {
			logger.warn("This test uses GNU tools and some of tests may fail. \n"
					+ "If they do, you can try to run scripts in Windows Subsystem for Linux or Cygwin, or run tests manually.");
			return "Windows";
		}
		return "Linux";
	}

	public static void checkMySQL() {
		String system = checkOS();
//		if (!"tcp\n".equals(executeCmd("netstat -ntl|grep 3306|awk '{print $1}'"))) {
//			jtm.DialogWindow.main(new String[] { "MySQL server is not working" });
//			fail("MySQL server is not working");
//			if (system.equals("Linux"))
//				System.exit(1);
//		}
	}

	/**
	 * Executes external process synchronously
	 * 
	 * @param command
	 *            array of command parameters
	 * @return CommandOutput with stdout and stderr fields
	 */

	public static CommandResult executeCmdResult(String command) {
		String[] cmd = new String[3];
		cmd[0] = "/bin/bash";
		cmd[1] = "-c";
		cmd[2] = command;
		return executeCmdResult(cmd);
	}

	/**
	 * Executes external process synchronously
	 * 
	 * @param cmd
	 *            array of command parameters
	 * @return CommandOutput with out and err fields
	 */
	public static CommandResult executeCmdResult(String[] cmd) {
		String error = "";
		String output = "";
		CommandResult commandOutput = null;
		try {
			Process pb = createProcess(cmd);
			output = getOutput(pb);
			error = getError(pb);
			pb.waitFor();

			if (!(output.equals("")))
				logger.debug("executeCmd(" + Arrays.toString(cmd) + ")\nOutput message: " + output);
			else
				logger.debug("executeCmd(" + Arrays.toString(cmd) + ") executed successfully");

			if (error != "")
				logger.error(error);

			commandOutput = new CommandResult(output, error);
		} catch (Throwable e) {
			handleError(e);
		}

		return commandOutput;
	}

	/**
	 * Executes external process with bash interpreter synchronously
	 * 
	 * @param command
	 *            line of the bash script
	 * 
	 * @return output/error status
	 */
	public static String executeCmd(String command) {
		String[] cmd = new String[3];
		cmd[0] = "/bin/bash";
		cmd[1] = "-c";
		cmd[2] = command;
		return executeCmd(cmd);
	}

	/**
	 * Executes external process synchronously
	 * 
	 * @param cmd
	 *            array of command parameters
	 * @return output+error status as String
	 */
	public static String executeCmd(String[] cmd) {
		CommandResult result = executeCmdResult(cmd);
		String error = result.err;
		String output = result.out;
		if (!(output.equals("")))
			logger.debug("executeCmd(" + Arrays.toString(cmd) + ")\nOutput message: " + output);
		if (error != "") {
			error += ("Command output error message: " + error);
			output += error;
		}
		return output;
	}

	/**
	 * Creates separate (asynchronous) process with passed parameters
	 * 
	 * @param array
	 *            of params
	 * @return reference to created process
	 */
	public static Process createProcess(String[] params) {
		// A Runtime object has methods for dealing with the OS
		Runtime runtime = Runtime.getRuntime();
		// Process
		Process process = null;
		try {
			process = runtime.exec(params);
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
		return process;
	}

	/**
	 * @param process
	 *            , which is created by createProcess()
	 * @return string gathered from the process output
	 */
	public static String getOutput(Process process) {
		// See
		// http://web.archive.org/web/20140531042945/https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
		try (Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A")) {
			return s.hasNext() ? s.next() : "";
		}
	}

	/**
	 * @param process
	 *            , which is created by createProcess()
	 * @return string gathered from the process error output
	 */
	public static String getError(Process process) {
		try (Scanner s = new Scanner(process.getErrorStream()).useDelimiter("\\A")) {
			return s.hasNext() ? s.next() : "";
		}
	}

	/**
	 * Provide input data to a process
	 * 
	 * @param process
	 *            , which is created by createProcess()
	 * @param value
	 *            input value
	 */
	public static void setInput(Process process, String value) {
		PrintWriter inputWriter;
		try {
			inputWriter = new PrintWriter(process.getOutputStream());
			inputWriter.append(value);
			inputWriter.close();
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
	}

	/**
	 * Executes passed class using jacocoagent and filters out coverage
	 * statistics for just passed class names
	 * 
	 * @param className
	 *            full classpath of the compiled class file e.g.
	 *            jtm.activity14.DatabaseUnitTest
	 * @param pattern
	 *            regular expression of classes for which coverage statistic is
	 *            counted
	 * @return map of class names, which contain list of covered methods vs all
	 *         methods. e.g. [Class, [4,5]]
	 * @throws IOException
	 */
	public static Map<String, List<Integer>> getExecutionResults(final String jacocoJarPath, final String className,
			final String pattern) {
		final Map<String, List<Integer>> result = new HashMap<String, List<Integer>>();
		try {
			// Delete previous statistics file, if it was not deleted already
			// (e.g. test failed):
			executeCmd("rm jacoco.exec 2>/dev/null");

			// Execute http://eclemma.org/jacoco/trunk/doc/agent.html from the
			// command line:
			executeCmd(new String[] { "java", "-cp", getClasspath(), "-javaagent:" + jacocoJarPath, className });

			// Load statistics from produced file
			final FileInputStream in = new FileInputStream("jacoco.exec");
			final ExecutionDataReader reader = new ExecutionDataReader(in);
			reader.setSessionInfoVisitor(new ISessionInfoVisitor() {
				@Override
				public void visitSessionInfo(final SessionInfo info) {
				}
			});
			reader.setExecutionDataVisitor(new IExecutionDataVisitor() {
				@Override
				public void visitClassExecution(final ExecutionData data) {
					String cpath = data.getName();
					// If classpath matches pattern, add coverage results to the
					// map
					if (cpath.matches(pattern)) {
						int probes = data.getProbes().length;
						int hits = getHitCount(data.getProbes());
						ArrayList<Integer> values = new ArrayList<Integer>();
						values.add(hits);
						values.add(probes);
						logger.debug(data.getName() + " = " + hits + "/" + probes);
						result.put(cpath, values);
					}
				}
			});
			reader.read();
			in.close();
			// Remove execution statistics file
			executeCmd("rm jacoco.exec");
			return result;
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
		return result;
	}

	public static String getClasspath() {
		String path, home;
		path = "target/classes\ntarget/test-classes\nbin\nlib/*\n";
		path += executeCmd("cat .classpath|grep M2_REPO|cut -d\\\" -f4");
		home = System.getProperty("user.home");
		path = path.replace("M2_REPO", home + "/.m2/repository");
		path = path.replaceAll("\n", ":");
		return path;
	}

	/**
	 * Helper function to count total coverage from map of coverage statistics
	 * 
	 * @param results
	 *            — map of class names, which contain list of covered methods vs
	 *            all methods. e.g. [Class, [4,5]]
	 * @return — coverage result e.g. 0.95
	 */
	public static float getTotal(Map<String, List<Integer>> results) {
		float total = 0;
		float up, down;
		float count = 0;
		for (Entry<String, List<Integer>> entry : results.entrySet()) {
			up = entry.getValue().get(0);
			down = entry.getValue().get(1);
			total += up / down;
			count++;
		}
		total /= count;
		return total;
	}

	/**
	 * @param data
	 *            — array of probes for coverage result
	 * @return number of hit (tested) probes
	 */
	private static int getHitCount(final boolean[] data) {
		int count = 0;
		for (final boolean hit : data) {
			if (hit) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param path
	 *            path to the file
	 * @return content of the file as UTF-8 encoded string
	 */
	public static String readFile(String path) {
		String content = null;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			content = new String(encoded, StandardCharsets.UTF_8);
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
		return content;
	}

	public static void checkExtension(Object current, Object parent) {
		org.junit.Assert.assertEquals(
				current.getClass().getSimpleName() + " does not extend " + parent.getClass().getSimpleName(),
				checkExtension(current.getClass(), parent.getClass()), true);
	}

	public static boolean checkExtension(Class<?> current, Class<?> parent) {
		return (current.getSuperclass().equals(parent));

	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Clears logs of passed logger
	 * 
	 * @param logger
	 */
	public static void clearLogFile(Logger logger) {
		while (logger != null && !logger.getAllAppenders().hasMoreElements()) {
			logger = (Logger) logger.getParent();
		}

		if (logger == null) {
			return;
		}

		for (Enumeration e2 = logger.getAllAppenders(); e2.hasMoreElements();) {
			final Appender appender = (Appender) e2.nextElement();
			if (appender instanceof RollingFileAppender) {
				final RollingFileAppender rfa = (RollingFileAppender) appender;
				final File logFile = new File(rfa.getFile());
				if (logFile.length() > 0) {
					rfa.rollOver();
				}
			}
		}
	}

	/**
	 * @return current UTC date in ISO 8601 format
	 */
	public static String getDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		return df.format(new Date());
	}

	/**
	 * Sleep for passed amount of time in ms
	 * 
	 * @param time
	 */
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Check number of running Java processes, open warning window, if more than
	 * one process (except Junit test itself) is running. Call this method
	 * from @BeforeClass method in test.
	 */
	public static void checkJavaProcesses() {
		if (TestUtils.checkOS().equals("Linux")) {
			int processes = Integer.parseInt(TestUtils.executeCmd("ps -efaww|grep java|grep -v grep|wc -l|tr -d '\n'"))
					- 1;
			if (processes > 1) {
				String message = "There are " + processes
						+ " Java processes running. Check that previously executed test is finished properly.";
				DialogWindow.main(new String[] { message });
				logger.warn(message);
			}
		}
	}

}
