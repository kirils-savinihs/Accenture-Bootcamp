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
		float necFuel = ((road.getDistance()) * (this.getConsumption() / 100));
		if (road.getClass() == Road.class) {
			if (this.getFuelInTank() >= necFuel) {
				this.setFuelInTank(this.getFuelInTank() - necFuel);
				return this.getId() + " " + this.getClass().getSimpleName() + " is driving on " + road.toString() + " with "
						+ this.numberOfWheels + " wheels";
			} else {
				return "Cannot drive on " + road.toString();
			}

		} else if (road.getClass() == WaterRoad.class) {
			return this.getId() + " " + this.getClass().getSimpleName() + " is sailing on " + road.toString() + " with "
					+ this.numberOfSails + " sails";
		} else {
			return "Unknown road type";
		}

	}

}
