package com.dyskolus.x12;

import java.util.Date;

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
	
	public static X12StringElement x12(String s) {
		if(s == null) return null;
		return new X12StringElement(s);
	}
	
	private static String zeros(int pos, int nr) {
		String s = ""+nr;
		while(s.length() < pos) {
			s = "0"+s;
		}
		return s;
	}
	
	/** ISO JP (YYYYMMDD) date/time (201706291338) */
	public static X12StringElement x12(Date d) {
		String s = ""+(1900+d.getYear()) + "" + zeros(2,1+d.getMonth()) + "" + zeros(2, d.getDate()) + "" + zeros(2,d.getHours()) + "" + zeros(2, d.getMinutes());
		return x12(s);
	}
}
