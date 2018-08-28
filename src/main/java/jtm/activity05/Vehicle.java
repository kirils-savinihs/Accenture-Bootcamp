package jtm.activity05;

import jtm.activity04.Road;
import jtm.activity04.Transport;

public class Vehicle extends Transport {

	protected int numberOfWheels;

	public Vehicle(String id, float consumption, int tankSize, int wheels) {
		super(id, consumption, tankSize);
		this.numberOfWheels = wheels;
	}

	@Override
	public String move(Road road) {
		float necFuel = ((road.getDistance()) * (this.getConsumption() / 100));
		if ((!(road.getClass() == Road.class)) || this.getFuelInTank() < necFuel) {
			return "Cannot drive on " + road.toString();
		} else {
			this.setFuelInTank(this.getFuelInTank() - necFuel);
			return this.getId() + " " + this.getClass().getSimpleName() + " is driving on " + road.toString() + " with "
					+ this.numberOfWheels + " wheels";

		}
	}

}
