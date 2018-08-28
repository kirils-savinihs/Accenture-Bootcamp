package jtm.activity06;

import java.util.Vector;

public class Human implements Humanoid {

	private int weight;
	private boolean alive;
	private Object backpack;

	public Human() {
		this.alive = true;
		this.weight = 42;
	}

	public Human(int weight) {
		this.alive = true;
		this.weight = weight;
	}

	@Override
	public int getWeight() {

		return this.weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight = weight;

	}

	@Override
	public String killHimself() {
		this.alive = false;
		return this.isAlive();
	}

	@Override
	public int getArmCount() {
		return Humanoid.ARM_COUNT;
	}

	@Override
	public String getBackpack() {
		return (String) this.backpack;

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.weight + " [" + backpack+"] ";
	}

	@Override
	public void setBackpack(String item) {
		this.backpack = item;

	}

	@Override
	public String isAlive() {
		if (alive)
			return "Alive";
		else
			return "Dead";

	}

}
