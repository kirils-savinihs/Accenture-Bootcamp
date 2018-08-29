package jtm.activity05;

import jtm.activity04.Road;
import jtm.activity04.Transport;

public class Ship extends Transport {
	protected byte numberOfSails;

	public Ship(String id, byte sails) {
		super(id, 0, 0);
		this.numberOfSails = sails;
	}

	@Override
	public String move(Road road) {
		if (!(road.getClass() == WaterRoad.class)) {
			return "Cannot sail on " + road.toString();
		} else
			return this.getId() + " " + this.getClass().getSimpleName() + " is sailing on " + road.toString() + " with "
					+ this.numberOfSails + " sails";
	}

}
