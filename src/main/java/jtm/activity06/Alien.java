package jtm.activity06;

public interface Alien {

	/**
	 * Aliens have 7 legs
	 */
	final static int LEG_COUNT = 7;

	/**
	 * 
	 * @return weight of the alien
	 */
	public int getWeight();

	/**
	 * @param weight
	 *            sets weight of the alien
	 */
	public void setWeight(int weight);

	/**
	 * @param humanoid
	 *            represents human, needed to be eaten. Human is being killed
	 *            when it is eaten an alien gains his weight
	 */
	public void eatHuman(Humanoid humanoid);

	/**
	 * @return number of arms for Alien
	 */
	public int getLegCount();


	/**
	 * @return content of the backpack by making clone of the item
	 * 
	 *         HINT: use if (backpack instanceof ...) to check actual type of
	 *         content and then construct new duplicated objects of necessary
	 *         type to return.
	 * 
	 */
	public Object getBackpack();

	/**
	 * @param item
	 *            to be stored into backpack following types can be stored into
	 *            backpack: 1. Object, e.g. String, 2. Humanoid, 3. Alien.
	 */
	public void setBackpack(Object item);

	/**
	 * @return "I AM IMMORTAL!" because alien is immortal
	 */
	public String isAlive();
}
