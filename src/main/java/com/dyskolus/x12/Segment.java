/*
   Copyright [2011] [Prasad Balan]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package com.dyskolus.x12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents an X12 segment.
 *
 * @author Prasad Balan
 * @version $Id: $Id
 */
public class Segment implements Iterable<X12Element> {
	private static final String EMPTY_STRING = "";
	
	private Context context;
	/** Element 0 in the segment list is the segment name */
	private List<X12Element> elements_ = new ArrayList<X12Element>();

	/**
	 * The constructor takes a {@link Context} object as input. The context
	 * object represents the delimiters in a X12 transaction.
	 *
	 * @param c
	 *            the context object
	 */
	public Segment(Context c) {
		this.context = c;
	}
	
	public Segment(Context c, X12Element segmentName) {
		this(c);
		this.addElement(segmentName);
	}
	
	/**
	 * Adds {@link java.lang.String} element to the segment. The element is added at
	 * the end of the elements in the current segment.
	 *
	 * @param e
	 *            the element to be added
	 * @return boolean
	 */
	public boolean addElement(String e) {
		return getElements().add(new X12StringElement(e));
	}
	
	public boolean addElement(X12Element e) {
		return this.getElements().add(e);
	}

	/**
	 * Adds {@link java.lang.String} with elements to the segment. The elements are
	 * added at the end of the elements in the current segment. e.g.
	 * {@code addElements("ISA*ISA01*ISA02");}
	 *
	 * @param s the element to add.
	 * @return boolean
	 */
	public boolean addElements(String s) {
		String[] elements = s.split("\\" + context.getElementSeparator());
		return this.addElements(elements);
	}

	/**
	 * Adds {@link java.lang.String} elements to the segment. The elements are added
	 * at the end of the elements in the current segment. e.g.
	 * {@code addElements("ISA", "ISA01", "ISA02");}
	 *
	 * @param es elements to add.
	 * @return boolean false when some values could not be added (@see java.util.Collection.add() ). Note:
	 * all values will be tried.
	 */
	public boolean addElements(String... es) {
		boolean returnValue = true;
		for (String s : es) {
			X12Element ele;
			if(s == null) {
				ele = null;
			} else {
				ele = new X12StringElement(s);
			}
			if (!this.getElements().add(ele)) {
				returnValue = false;
			}
		}
		return returnValue;
	}
	
	public boolean addElement(X12Element... ele) {
		boolean returnValue = true;
		for(X12Element e : ele) {
			returnValue |= this.getElements().add(e);
		}
		return returnValue;
	}


	/**
	 * Adds strings as a composite element to the end of the segment.
	 *
	 * @param ces
	 *            sub-elements of a composite element
	 * @return boolean
	 */
	public boolean addCompositeElement(String... ces) {
		StringBuilder dump = new StringBuilder();
		for (String s : ces) {
			dump.append(s);
			dump.append(context.getCompositeElementSeparator());
		}
		return this.getElements().add(new X12StringElement(dump.substring(0, dump.length() - 1)));
	}

	/**
	 * Inserts {@link java.lang.String} element to the segment at the specified
	 * position
	 *
	 * @param e
	 *            the element to be added
	 * @return boolean true if element matches the element at the index provided.
	 * @param index a int.
	 */
	public boolean addElement(int index, X12Element e) {
		this.getElements().add(index, e);
		return getElements().get(index).equals(e);
	}
	
	public boolean addElement(int index, String e) {
		return this.addElement(index, new X12StringElement(e));
	}

	/**
	 * Inserts strings as a composite element to segment at specified position
	 *
	 * @param ces
	 *            sub-elements of a composite element
	 * @param index a int.
	 */
	public void addCompositeElement(int index, String... ces) {
		this.getElements().add(index, new X12CompositeElement(this.context, ces));
	}

	/**
	 * Returns the context object
	 *
	 * @return Context object
	 */
	public Context getContext() {
		return this.context;
	}

	/**
	 * Returns the {@link java.lang.String} element at the specified position.
	 *
	 * @param index
	 *            position
	 * @return the element at the specified position.
	 */
	public X12Element getElement(int index) {
		return getElements().get(index);
	}

	/**
	 * Getter for the field {@link java.util.List}&lt;{@link java.lang.String}&gt;
	 *
	 * @return List of elements
	 */
	public List<X12Element> getElements() {
		return this.elements_;
	}
	
	/**
	 * Returns and {@link java.util.Iterator}&lt;{@link java.lang.String}&gt;
	 * to the elements in the segment.
	 *
	 * @return {@link java.util.Iterator}&lt;{@link java.lang.String}&gt;
	 */
	public Iterator<X12Element> iterator() {
		return getElements().iterator();
	}

	/**
	 * Removes the element at the specified position in this list.
	 *
	 * @param index the index at which to remove the element.
	 * @return String element that was removed.
	 */
	public X12Element removeElement(int index) {
		return getElements().remove(index);
	}

	/**
	 * Removes empty and null elements at the end of segment 
	 */
	private void removeTrailingEmptyElements() {
		for (int i = getElements().size() - 1; i >= 0; i--) {
			if (getElements().get(i) == null || getElements().get(i).length() == 0) {
				getElements().remove(i);
			} else {
				break;
			}
		}		
	}
	
	/**
	 * Sets the context of the segment
	 *
	 * @param context
	 *            context object
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Replaces element at the specified position with the specified
	 * {@link java.lang.String}
	 *
	 * @param index
	 *            position of the element to be replaced
	 * @param s
	 *            new element with which to replace
	 */
	public void setElement(int index, X12Element s) {
		getElements().set(index, s);
	}
	
	public void setElement(int index, String s) {
		this.setElement(index,  new X12StringElement(s));
	}

	/**
	 * Replaces composite element at the specified position in segment.
	 *
	 * @param ces
	 *            sub-elements of a composite element
	 * @param index a int.
	 */
	public void setCompositeElement(int index, String... ces) {
		StringBuilder dump = new StringBuilder();
		for (String s : ces) {
			dump.append(s);
			dump.append(context.getCompositeElementSeparator());
		}
		getElements().set(index, new X12StringElement(dump.substring(0, dump.length() - 1)));
	}

	/**
	 * Returns number of elements in the segment.
	 *
	 * @return size
	 */
	public int size() {
		return getElements().size();
	}

	/**
	 * Returns the X12 representation of the segment.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		StringBuilder dump = new StringBuilder();
		for (X12Element s : this.getElements()) {
			if(s != null) { // Surpress empty elements.
				if(s instanceof X12CompositeElement) {
					dump.append(((X12CompositeElement)s).toString());
				} else {
					dump.append(s.toString());				
				}
				dump.append(context.getElementSeparator());
			}
		}
		if (dump.length() == 0) {
			return EMPTY_STRING;
		}
		return dump.substring(0, dump.length() - 1); // TODO: length()-1: strip of the last char. Could do it with a different loop condition for clarity.
	}

	/**
	 * Returns the X12 representation of the segment.
	 *
	 * @param bRemoveTrailingEmptyElements a flag for whether or not empty
	 *        trailing elements should be removed.
	 * @return {@link java.lang.String}
	 */
	public String toString(boolean bRemoveTrailingEmptyElements) {
		if (bRemoveTrailingEmptyElements)
			removeTrailingEmptyElements();
		return this.toString();
	}
	
	/**
	 * Returns the XML representation of the segment.
	 *
	 * @return {@link java.lang.String} XML representation of the segment.
	 */
	public String toXML() {
		return toXML(false, false);
	}

	private boolean containsXMLSpecials(X12Element x12) {
		String string = x12.toString();
		return string.contains("<") ||
				string.contains(">") ||
				string.contains("&");
	}

	/**
	 * Returns the XML representation of the segment.
	 *
	 * @param bRemoveTrailingEmptyElements a flag for whether or not empty
	 *        trailing elements should be removed.
	 * @return {@link java.lang.String} XML representation of the segment.
	 */
	public String toXML(boolean bRemoveTrailingEmptyElements, boolean includeNodeTypes) {
		if (bRemoveTrailingEmptyElements)
			removeTrailingEmptyElements();
		StringBuilder dump = new StringBuilder();
		dump.append("<");
		dump.append(this.getElements().get(0));
		if(includeNodeTypes) {
			dump.append(" type='Segment'");
		}
		dump.append(">");
		for (int i = 1; i < this.getElements().size(); i++) {
			dump.append("<");
			dump.append(this.getElements().get(0));			
			dump.append(String.format("%1$02d", i));
			if(includeNodeTypes) {
				dump.append(" type='"+this.getElements().get(0).getClass().getSimpleName()+"'");
			}
			// Dont's spray CDATA all over the place.
			if(containsXMLSpecials(this.getElements().get(i))) {
				dump.append("><![CDATA[");
				dump.append(this.getElements().get(i));
				dump.append("]]></");
			} else {
				dump.append(">");
				dump.append(this.getElements().get(i));
				dump.append("</");
			}
			dump.append(this.getElements().get(0));
			dump.append(String.format("%1$02d", i));
			dump.append(">");
		}
		dump.append("</");
		dump.append(this.getElements().get(0));
		dump.append(">");
		return dump.toString();
	}

}
