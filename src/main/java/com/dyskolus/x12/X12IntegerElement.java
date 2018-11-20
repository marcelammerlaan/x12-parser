package com.dyskolus.x12;

public class X12IntegerElement implements X12Element {
	private final int content;
	
	public X12IntegerElement(int s) {
		this.content = s;
	}
	
	public String toString() {
		return ""+content;
	}
	
	@Override
	public int hashCode() {
		return content;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof X12IntegerElement) {
			return ((X12IntegerElement)o).content == this.content;
		}
		return false;
	}

	public int length() {
		return this.toString().length();
	}
	
	public static X12IntegerElement x12(int i) {
		return new X12IntegerElement(i);
	}
}
