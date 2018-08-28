package jtm.activity06;

import java.util.Vector;

public class Martian implements Humanoid, Cloneable, Alien {

	private int weight;
	private boolean alive;
	private Object backpack;

	public Martian(int weight) {
		this.weight = weight;
	}

	@Override
	public void eatHuman(Humanoid humanoid) {
		if (humanoid.isAlive() == "Alive") {
			this.weight += humanoid.getWeight();
			humanoid.killHimself();
			this.backpack = humanoid;
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
		this.weight = weight;

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
	public Object getBackpack(){
		return this.backpack;
	}

	@Override
	public void setBackpack(Object item) {
		if (!(item == this)) {
			this.backpack = item;
		}

	}

	@Override
	public String isAlive() {
		return "I AM IMMORTAL!";
	}



	@Override
	public Object clone() throws CloneNotSupportedException {
		return clone(this);
	}

	private Object clone(Object current) {

		if (current instanceof Martian) {
			Martian clone = new Martian(((Martian) current).getWeight());
			clone.setBackpack(((Martian) current).getBackpack());
			return clone;
		}
		//current instanceof Human
		else {
			Human clone = new Human( ((Human)current).getWeight());
			clone.setBackpack(((Human)current).getBackpack());
			return clone;
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.weight + " [" + backpack+"] ";
	}

	@Override
	public void setBackpack(String item) {
		this.setBackpack((Object)item);
		
	}

}
