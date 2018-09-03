package jtm.activity09;

import java.util.Iterator;

/*- TODO #1
 * Implement Comparable interface with Order class
 * Hint! Use generic type of comparable items in form: Comparable<Order>
 * 
 * TODO #2 Override/implement necessary methods for Order class:
 * - public Order(String orderer, String itemName, Integer count) — constructor of the Order
 * - public int compareTo(Order order) — comparison implementation according to logic described below
 * - public boolean equals(Object object) — check equality of orders
 * - public int hashCode() — to be able to handle it in some hash... collection 
 * - public String toString() — string in following form: "ItemName: OrdererName: Count"
 * 
 * Hints:
 * 1. When comparing orders, compare their values in following order:
 *    - Item name
 *    - Customer name
 *    - Count of items
 * If item or customer is closer to start of alphabet, it is considered "smaller"
 * 
 * 2. When implementing .equals() method, rely on compareTo() method, as for sane design
 * .equals() == true, if compareTo() == 0 (and vice versa).
 * 
 * 3. Also Ensure that .hashCode() is the same, if .equals() == true for two orders.
 * 
 */

public class Order implements Comparable<Order> {
	String customer; // Name of the customer
	String name; // Name of the requested item
	int count; // Count of the requested items

	public Order(String orderer, String itemName, Integer count) {

		this.customer = orderer;
		this.name = itemName;
		this.count = count;
	}

	public boolean equals(Object object) {
		if (object instanceof Order && this.compareTo((Order) object) == 0)
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Order o) {

		int customerComp = this.customer.compareTo(o.customer);
		int nameComp = this.name.compareTo(o.name);
		Integer thisCount = new Integer(this.count);
		Integer oCount = new Integer(o.count);
		int countComp = thisCount.compareTo(oCount);

		if (customerComp == 0 && nameComp == 0 && countComp == 0)
			return 0;
		else if (customerComp < 0 || nameComp < 0 || countComp < 0)
			return -1;
		else
			return 1;

	}

	@Override
	public int hashCode() {
		int hashCode = this.name.hashCode() * this.customer.hashCode() * count;
		return hashCode;
	}

	@Override
	public String toString() {
		return this.name + ": " + this.customer + ": " + this.count;
	}

}
