package com.dyskolus.x12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class X12CompositeElement implements X12Element,Iterable<X12Element> {
	private List<X12Element> elements;
	private final Context ctx;
	
	protected X12CompositeElement(Context ctx) {
		this.ctx = ctx;
	}
	
	public X12CompositeElement(Context ctx, String... ces) {
		this.ctx = ctx;
		elements = new ArrayList<X12Element>();
		for(String s : ces) {
			elements.add(new X12StringElement(s));
		}
	}
	
	public Iterator<X12Element> iterator() {
		return getElements().iterator();
	}

	public int length() {
		int len=0;
		for(X12Element xe : elements) {
			len += xe.length();
			len++; // The composite separator.
		}
		return len;
	}
	
	public List<X12Element> getElements() {
		return this.elements;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Character sep = null;
		for(X12Element ele : this.getElements()) {
			if(sep != null) sb.append(sep);
			sb.append(ele.toString());
			
			sep = ctx.getCompositeElementSeparator();
		}
		return sb.toString();
	}

}
