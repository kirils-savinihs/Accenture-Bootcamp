package jtm.activity11;

import java.util.LinkedList;

/**
 * This is frontend class for array filler. It uses ArrayFiller to fill array of
 * integers using sequental or concurrent (parallel) approach
 */
public class ArrayFillerManager {
	static int[] array; // array to be filled
	static int latency; // emulated latency in ms
	static int minValue, maxValue; // min and max (including) of the
									// array cell
	private static LinkedList<Thread> threads; // list of threads when parallel
	// filling is used

	// HINT feel free to use main() method to call setUp(), fillStupidly() etc.
	// for debugging purposes if unit tests doesn't show enough information,
	// what exactly in implementation seems wrong.
	// Note that main() method will not be used in unit tests.

	public static int[] setUp(int arraySize, int latency, int minValue, int maxValue) {
		// TODO save passed values in prepared structure
		// initialize array with passed size
		// initialize list of threads
		// return reference to the initialized array
		ArrayFillerManager.array = new int[arraySize];
		ArrayFillerManager.latency = latency;
		ArrayFillerManager.minValue = minValue;
		ArrayFillerManager.maxValue = maxValue;
		threads = new LinkedList<Thread>();

		return ArrayFillerManager.array;
	}

	public static void fillStupidly() {
		// TODO create cycle which creates new ArrayFiller objects
		// with filling range of only ONE cell at a time (i.e. range from..to is
		// 0..0, then 1..1, etc.) and invoke .run() method for these objects.
		// Note, that invocation of .run() method directly executes it in
		// current (main) thread.
		// Note that this method emulates, what would happen if you would send
		// just small portions of data through media with latency.

		for (int i = 0; i < array.length; i++) {
			ArrayFiller stupidArrayFiller = new ArrayFiller(latency, minValue, maxValue, i, i);
			stupidArrayFiller.run();
		}

	}

	public static void fillSequentially() {
		// TODO create cycle which creates one ArrayFiller object
		// with filling range for ALL array but executed just in SINGLE thread
		// by invocation of .run() method directly.
		// Note that this method emulates, what would happen if you would do
		// proper "buffering" with large amount of data, but do execution just
		// in single thread.

		ArrayFiller sequentialArrayFiller = new ArrayFiller(latency, minValue, maxValue);
		sequentialArrayFiller.run();
	}

	public static void fillParalelly() {
		// TODO create cycle which creates new ArrayFiller objects
		// with any range and pass them as references to the Thread constructor.
		// Add newly created Thread objects into threads list and start them
		// threads using .start() method. Note that invocation of .start() for
		// thread object creates new concurrent thread in application
		// Note that, to pass tests, this implementation should run faster than
		// fillSequentally() method.
		//
		// HINT: Don't forget to check that separately started threads are
		// actually finished by calling .join() method for them.
		// Note that this method emulates, what would happen if you do proper
		// buffering and scaling of the execution.

		int chunks[];

		chunks = new int[5];
		chunks[0] = 0;

//		chunks[1] = ((array.length/4)-1)*1;
//		chunks[2] = ((array.length/4)-1)*2;
//		chunks[3] = ((array.length/4)-1)*3;

		for (int i = 1; i <= 3; i++) {
			chunks[1] = ((array.length / 4) - 1) * i;
		}

		chunks[4] = array.length - 1;

		for (int i = 0; i < 4; i++) {
			Thread thr = new Thread(new ArrayFiller(latency, minValue, maxValue, chunks[i], chunks[i + 1]));
			threads.add(thr);
			thr.start();
		}

		for (Thread i : threads) {
			try {
				i.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		setUp(10, 10, 20, 30);
		fillStupidly();

		for (int i : array) {
			System.out.println(i);
		}
		System.out.println();

		fillSequentially();
		for (int i : array) {
			System.out.println(i);
		}
		System.out.println();

		fillParalelly();
		for (int i : array) {
			System.out.println(i);
		}

	}

}
