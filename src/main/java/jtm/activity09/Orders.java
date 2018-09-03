package jtm.activity09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/*- TODO #2
 * Implement Iterator interface with Orders class
 * Hint! Use generic type argument of iterateable items in form: Iterator<Order>
 * 
 * TODO #3 Override/implement public methods for Orders class:
 * - Orders()                — create new empty Orders
 * - add(Order item)            — add passed order to the Orders
 * - List<Order> getItemsList() — List of all customer orders
 * - Set<Order> getItemsSet()   — calculated Set of Orders from list (look at description below)
 * - sort()                     — sort list of orders according to the sorting rules
 * - boolean hasNext()          — check is there next Order in Orders
 * - Order next()               — get next Order from Orders, throw NoSuchElementException if can't
 * - remove()                   — remove current Order (order got by previous next()) from list, throw IllegalStateException if can't
 * - String toString()          — show list of Orders as a String
 * 
 * Hints:
 * 1. To convert Orders to String, reuse .toString() method of List.toString()
 * 2. Use built in List.sort() method to sort list of orders
 * 
 * TODO #4
 * When implementing getItemsSet() method, join all requests for the same item from different customers
 * in following way: if there are two requests:
 *  - ItemN: Customer1: 3
 *  - ItemN: Customer2: 1
 *  Set of orders should be:
 *  - ItemN: Customer1,Customer2: 4
 */

public  class Orders implements Iterator<Order> {
	/*-
	 * TODO #1
	 * Create data structure to hold:
	 *   1. some kind of collection of Orders (e.g. some List)
	 *   2. index to the current order for iterations through the Orders in Orders
	 *   Hints:
	 *   1. you can use your own implementation or rely on .iterator() of the List
	 *   2. when constructing list of orders, set number of current order to -1
	 *      (which is usual approach when working with iterateable collections).
	 */
	List<Order> orders;
	Iterator<Order> it;

	public void add(Order order) {
		orders.add(order);
		it =orders.iterator();
	}

	public Set<Order> getItemsSet() {
		
		List<Order> hashset = new ArrayList<Order>(orders);
//		Iterator<Order> hashIt1 = hashset.iterator();
//		Iterator<Order> hashIt2 = hashset.iterator();
//		Order hashIt1Next;
//		Order hashIt2Next;
		
		if (orders.isEmpty())
		{
			return new TreeSet<Order>(hashset);
		}
		
		
		for (int i=0;i<hashset.size();i++)
		{
			for (int y=0;y<hashset.size();y++)
			{
				
				if (!hashset.get(i).equals(hashset.get(y))&&hashset.get(i).name.equals(hashset.get(y).name) )
				{
					List <String> temp = new ArrayList <String>();
					
					temp.add(hashset.get(y).customer);
					temp.add(hashset.get(i).customer);
					Collections.sort(temp);
					boolean first=true;
					for (String x:temp)
					{
						if (first)
						{
							hashset.get(y).customer =x;
							first = false;
						}
						else
						{
							hashset.get(y).customer+=","+x;
						}
					}
					
					

					hashset.get(y).count+=hashset.get(i).count;
					hashset.remove(i);
				}
				
				
			}
		}
		
		Collections.sort(hashset);
		Set <Order> treeset = new TreeSet<Order>(hashset);
		return treeset;
		
	}

	public List<Order> getItemsList() {
		return	new ArrayList<Order>(orders);
	}

	public Orders() {
		orders = new ArrayList<Order>();
		it = orders.iterator();
	}

	public void sort() {
		Collections.sort(orders);
		it = orders.iterator();
	}

	public void remove() {
		it.remove();
		it = orders.iterator();
	}

	public String toString() {
		return orders.toString();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public Order next() {
		return it.next();
	}

}
