package jtm.extra08;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//TODO Annotate class as an @Entity, to say that its instances can be stored in the database.
public class Item {
	// TODO Annotate id field as an @Id, to mark it as a primary key field.
	private Integer id;
	// TODO annotate invoice property with
	// @ManyToOne @JoinColumn to define that its value will be used as
	// many-to-one relation between item and invoice
	private Invoice invoice;
	private String name;
	private Integer count;
	private Float price;

	// TODO generate getters and setters for item properties
	@Override
	public String toString() {
		// TODO return string in form
		// 'Item:ID, name:NAME, price:PRICE, count:COUNT, total:TOTAL'
		// where capita names mean actual values of the item
		return "";
	}

	// TODO note that comparison of Item as object is not implemented. You
	// should override equals() and hashcode() methods that Items with equal
	// values are considered equal.

}
