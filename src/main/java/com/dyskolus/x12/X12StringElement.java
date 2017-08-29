package com.dyskolus.x12;

public class X12StringElement implements X12Element {
	private final String content;
	
	public X12StringElement(String s) {
		if(s == null) throw new NullPointerException("Null string detected");
		this.content = s;
	}
	
	public String toString() {
		return content;
	}
	
	@Override
	public int hashCode() {
		return this.content.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof X12StringElement) {
			return ((X12StringElement)o).content.equals(this.content);
		}
		return false;
	}

	public int length() {
		return this.content.length();
	}
}
