package jtm.activity11;

import java.util.Random;

public class ArrayFiller implements Runnable {

	int latency; // required latency time (in miliseconds) to simulate real
					// environment
	int minValue, maxValue; // min and max allowed values for array cells
	int from, to; // range which should be filled by this filler
	Random random; // Pseudo-random generator

	public ArrayFiller(int latency, int minValue, int maxValue) {
		// TODO from this constructor call another constructor with more
		// parameters and fill missing
		// values with fixed literals
		this(latency, minValue, maxValue, 0, -1);

	}

	public ArrayFiller(int latency, int minValue, int maxValue, int from, int to) {
		// TODO save passed values to created filler object
		// Create and initialize pseudo-random generator. See more at:
		// http://docs.oracle.com/javase/7/docs/api/java/util/Random.html

		this.latency = latency;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.from = from;
		this.to = to;

		random = new Random();
	}

	@Override
	public void run() {
		// TODO when invoked, put filler to sleep for required amount of latency
		// then fill ArrayFillerManager.array from..to cells with random values
		// in
		// minValue..maxValue range

		if (Thread.currentThread().isAlive()) {
			try {
				Thread.sleep(latency);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (this.to < 0) {
			this.to = (ArrayFillerManager.array.length) - 1;
		}

		for (int i = from; i <= to; i++) {
			ArrayFillerManager.array[i] = random.nextInt((maxValue - minValue) + 1) + minValue;
		}

	}

}
