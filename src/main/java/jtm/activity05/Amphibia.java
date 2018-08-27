package jtm.activity05;

import jtm.activity04.Road;
import jtm.activity04.Transport;

public class Amphibia extends Transport {

	private byte numberOfSails;
	private int numberOfWheels;

	public Amphibia(String id, float consumption, int tankSize, byte sails, int wheels) {
		super(id, consumption, tankSize);
		this.numberOfSails = sails;
		this.numberOfWheels = wheels;
	}

	@Override
	public String move(Road road) {
		if (road instanceof WaterRoad) {
			return this.getId() + " " + this.getClass().getSimpleName() + " is driving on " + road.toString() + " with "
					+ this.numberOfWheels + " wheels";
		} else  {
			return this.getId() + " " + this.getClass().getSimpleName() + " is sailing on " + road.toString() + " with "
					+ this.numberOfSails + " sails";
		}

	}

}
