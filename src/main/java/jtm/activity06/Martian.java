package jtm.activity06;

import java.util.Vector;

public class Martian implements Humanoid, Cloneable, Alien {

	private int weight;
	private boolean alive;
	private Vector <Object> backpack = new Vector<Object>();

	public Martian(int weight) {
		this.weight = weight;
	}

	@Override
	public void eatHuman(Humanoid humanoid) {
		if (humanoid.isAlive() == "Alive") {
			this.weight += humanoid.getWeight();
			humanoid.killHimself();
		}

	}

	@Override
	public int getLegCount() {
		return Alien.LEG_COUNT;
	}


	@Override
	public int getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight=weight;

	}

	@Override
	public String killHimself() {
		return this.isAlive();
	}

	@Override
	public int getArmCount() {
		return this.ARM_COUNT;
	}

	@Override
	public Object getBackpack() {
		return this.backpack;
	}

	@Override
	public void setBackpack(Object item) {
		this.backpack.addElement(item);
	}


	@Override
	public String isAlive() {
		return "I AM IMMORTAL!";
	}

	@Override
	public void setBackpack(String item) {
		this.backpack.addElement(item);
		
	}

}
