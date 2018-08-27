package jtm.extra08;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

// TODO Annotate class as an @Entity, to say that its instances can be stored in the database.
public class Invoice {

	// TODO Annotate id field as an @Id, to mark it as a primary key field.
	private Integer id;

	// TODO Annotate items field as @OneToMany(mappedBy = "invoice") to note
	// that current object is related to many Item objects using "invoice" field
	// of Item as a mapping value
	// Annotate items field with @OrderBy("id ASC") annotation to note that set
	// of them
	// should be sorted
	private Set<Item> items;

	// TODO add annotation @Temporal(TemporalType.TIMESTAMP) to generate
	// timestamp for invoice date
	private Date date;

	public Invoice() {
		// TODO initialize some sorted Set of items, when invoice is created
	}

	public void addItem(Item item) {
		// TODO add passed item to the items set
	}

	public Integer getId() {
		// TODO return requested value
		return 0;
	}

	public void setId(Integer id) {
		// TODO save passed value into invoice
	}

	// TODO annotate method with @OneToMany(mappedBy = "invoice") to define
	// relation between invoice and items
	public Set<Item> getItems() {
		// TODO return required value
		return null;
	}

	// TODO annotate method with @PrePersist to define that method should be
	// called before saving data
	protected void onPrePersist() {
		// TODO set date field to current system date
	}

	@Override
	public String toString() {
		// TODO return string in form "Invoice:ID, date:DATE, total
		// amount:AMOUNT, items:ITEMS\n
		// \t['Item1.toString()'...ItemN.toString()'...]
		// Where CAPITAL means actual value of field and item.toString() is
		// overriden toString() method for item
		// Hint: use SimpleDateFormat with ISO date format for date formatting
		return "";
	}

	public Float getTotalAmount() {
		// TODO return total amount of items in invoice
		return 0f;
	}

	public Integer getItemNo() {
		// TODO return number of items in invoice
		return 0;
	}

	// TODO note that comparison of Invoice as object is not implemented. You
	// should override equals() and hashcode() methods that Invoices with equal
	// values are considered equal.

}
