package com.dyskolus.x12;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dyskolus.x12.Context;
import com.dyskolus.x12.Segment;

import java.util.List;

public class SegmentTest {

	@Test
	public void testSegmentEmpty() {
		Segment s = new Segment(new Context('~', '*', ':'));
		assertNotNull(s);
	}

	@Test
	//There is no test for AddElements returning false because
	//the underlying ArrayList will never return false on an add.
	public void testAddElementString() {
		Segment s = new Segment(new Context('~', '*', ':'));
		assertEquals(true, s.addElement("ISA"));
	}

	@Test
	public void testAddElementDuplicateString() {
		Context context = new Context('~', '*', ':');
		Segment s = new Segment(context);
		String value = "ISA";
		assertEquals(true, s.addElement(value));
		assertEquals(true, s.addElement(value));
	}

	@Test
	public void testAddElements() {
		Segment s = new Segment(new Context('~', '*', ':'));
		assertEquals(true, s.addElements("ISA", "ISA01", "ISA02"));
	}

	@Test
	public void testAddElementByIndex() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02");
		assertEquals(true, s.addElement(1, "ISA00"));
		assertEquals("ISA00", s.getElement(1).toString());
	}

	@Test
	public void testAddCompositeElementStringArray() {
		Segment s = new Segment(new Context('~', '*', ':'));
		assertEquals(true, s.addCompositeElement("AB", "CD", "EF"));
	}

	@Test
	public void testAddElementIntString() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02");
		assertEquals(true, s.addCompositeElement("ISA03_1", "ISA03_2", "ISA03_3"));
	}

	@Test
	public void testAddCompositeElementIntStringArray() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA04");
		s.addCompositeElement(3, "ISA03_1", "ISA03_2", "ISA03_3");
		assertEquals("ISA03_1:ISA03_2:ISA03_3", s.getElement(3).toString());
	}

	@Test
	public void testGetContext() {
		Segment s = new Segment(new Context('~', '*', ':'));
		assertEquals("[~,*,:]", s.getContext().toString());
	}

	@Test
	public void testGetElement() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03");
		assertEquals("ISA02", s.getElement(2).toString());
	}

	@Test
	public void testGetElements() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03");
		List<X12Element> actualElements = s.getElements();
		assertEquals(4, actualElements.size());
	}

	@Test
	public void testIterator() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03");
		assertNotNull(s.iterator());
	}

	@Test
	public void testRemoveElement() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03");
		s.removeElement(2);
		assertEquals("ISA*ISA01*ISA03", s.toString());
	}

	@Test
	public void testRemoveElementTwo() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03");
		s.removeElement(3);
		assertEquals("ISA*ISA01*ISA02", s.toString());
	}
	
	@Test
	public void testSetContext() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.setContext(new Context('s', 'e', 'c'));
		assertEquals("[s,e,c]", s.getContext().toString());
	}

	@Test
	public void testSetElement() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA04", "ISA04");
		s.setElement(3, "ISA03");
		assertEquals("ISA03", s.getElement(3).toString());
	}

	@Test
	public void testSetCompositeElement() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA04", "ISA04");
		s.setCompositeElement(3, "ISA03_1", "ISA03_2", "ISA03_3");
		assertEquals("ISA03_1:ISA03_2:ISA03_3", s.getElement(3).toString());
	}

	@Test
	public void testSize() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03", "ISA04");
		assertEquals(new Integer(5), new Integer(s.size()));
	}

	@Test
	public void testToString() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03", "ISA04");
		s.setCompositeElement(3, "ISA03_1", "ISA03_2", "ISA03_3");
		assertEquals("ISA*ISA01*ISA02*ISA03_1:ISA03_2:ISA03_3*ISA04", s.toString());

	}

	@Test
	public void testToStringRemoveTrailingEmptyElements() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03", "ISA04", "", "", "");
		assertEquals("ISA*ISA01*ISA02*ISA03*ISA04", s.toString(true));
	}

	@Test
	public void testToStringRemoveTrailingNullElements() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03", "ISA04", null, null, null);
		assertEquals("ISA*ISA01*ISA02*ISA03*ISA04", s.toString(true));
	}

	@Test
	public void testToXML() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "01", "02", "03", "04");
		s.setCompositeElement(3, "03_1", "03_2", "03_3");
		assertEquals(
				"<ISA><ISA01>01</ISA01><ISA02>02</ISA02><ISA03>03_1:03_2:03_3</ISA03><ISA04>04</ISA04></ISA>",
				s.toXML());
	}

	@Test
	public void testToXMLRemoveTrailingEmptyTags() {
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "01", "02", "03", "04", null, null);
		s.setCompositeElement(3, "03_1", "03_2", "03_3");
		assertEquals(
				"<ISA><ISA01>01</ISA01><ISA02>02</ISA02><ISA03>03_1:03_2:03_3</ISA03><ISA04>04</ISA04></ISA>",
				s.toXML(true,false));
	}

}
