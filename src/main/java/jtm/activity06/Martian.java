package jtm.activity06;

public class Martian implements Humanoid, Cloneable, Alien {

	private int weight;
	private Object backpack;

	public Martian(int weight) {
		this.weight = weight;
	}

	@Override
	public void eatHuman(Humanoid humanoid) {
		if (humanoid.isAlive().equals("Alive")) {
			this.setWeight(this.getWeight() + humanoid.getWeight());
			humanoid.killHimself();
		}

	}

	@Override
	public int getLegCount() {
		return Alien.LEG_COUNT;
	}

	@Override
	public void setBackpack(String item) {
		this.backpack = item;

	}

	@Override
	public Object getBackpack() {
		return clone(backpack);
	}

	@Override
	public void setBackpack(Object item) {
		if (item == this) {
			this.backpack = null;
		} else {
			this.backpack = item;
		}

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
		return "I AM IMMORTAL!";
	}

	@Override
	public int getArmCount() {
		return Humanoid.ARM_COUNT;
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
		} else if (current instanceof String) {
			String clone = new String((String) current);
			return clone;
		} else if (current instanceof Human) {
			Human clone = new Human(((Human) current).getWeight());
			clone.setBackpack(((Human) current).getBackpack());
			return clone;
		} else if (current instanceof Object) {
			return new Object();
		}
		return null;
	}

	@Override
	public String isAlive() {
		return "I AM IMMORTAL!";
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.weight + " [" + backpack + "] ";
	}

}
