package com.iris.webapp.model;

import java.util.List;

public class Foo {
	private List<String> checkedItems;
	private List<String> quantity;

	public List<String> getCheckedItems() {
		return checkedItems;
	}

	public void setCheckedItems(List<String> checkedItems) {
		this.checkedItems = checkedItems;
	}

	public List<String> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<String> quantity) {
		this.quantity = quantity;
	}
}