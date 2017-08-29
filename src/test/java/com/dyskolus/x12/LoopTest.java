package com.dyskolus.x12;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.dyskolus.x12.Context;
import com.dyskolus.x12.Loop;
import com.dyskolus.x12.Segment;

public class LoopTest {

	@Test
	public void testLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		assertNotNull(loop);		
	}

	@Test
	public void testAddChildString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		Loop child = loop.addChild("GS");
		assertNotNull(child);		
	}

	@Test
	public void testAddChildIntLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		Loop gs = new Loop(new Context('~', '*', ':'), "GS");
		Loop st = new Loop(new Context('~', '*', ':'), "ST");
		loop.addChild(0, gs);
		loop.addChild(1, st);
		assertEquals("ST", loop.getLoop(1).getName());		
	}

	@Test
	public void testAddSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		Segment s = loop.addSegment();
		assertNotNull(s);
	}

	@Test
	public void testAddEmptySegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		Segment s = loop.addSegment(0);
		assertNotNull(s);
	}

	@Test
	public void testAddSegmentString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		assertEquals("ST", loop.getSegment(0).getElement(0));
	}

	@Test
	public void testAddSegmentSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		Segment segment = new Segment(new Context('~', '*', ':'));
		segment.addElements("ST*835*000000001");
		loop.addSegment(segment);
		assertEquals("ST", loop.getSegment(0).getElement(0));
	}

	@Test
	public void testAddSegmentInt() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("TRN*1*0000000000*1999999999");
		loop.addSegment("DTM*111*20090915");
		Segment segment = new Segment(new Context('~', '*', ':'));
		segment.addElements("ST*835*000000001");
		loop.addSegment(0, segment);
		assertEquals("ST", loop.getSegment(0).getElement(0));
	}

	@Test
	public void testAddSegmentIntString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("TRN*1*0000000000*1999999999");
		loop.addSegment("DTM*111*20090915");
		loop.addSegment(0, "ST*835*000000001");
		assertEquals("ST", loop.getSegment(0).getElement(0));
	}

	@Test
	public void testAddSegmentIntSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		Segment segment = new Segment(new Context('~', '*', ':'));
		segment.addElements("ST*835*000000001");
		loop.addSegment(2, "TRN*1*0000000000*1999999999");
		assertEquals("TRN", loop.getSegment(2).getElement(0));
	}

	@Test
	public void testAddChildIntString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		loop.addChild("GS");
		loop.addChild(1, "ST");
		assertEquals("ST", loop.getLoop(1).getName());		
	}

	@Test
	public void testSetSegment(){
		Context context = new Context('~', '*', ':');
		Segment testSeg = new Segment(context);
		Loop loop = new Loop(context, "ISA");
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		loop.setSegment(2, testSeg);
		Segment actual = loop.getSegment(2);
		assertEquals(testSeg, actual);
	}

	@Test
	public void testHasLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		assertEquals(true, loop.hasLoop("ST"));
	}

	@Test
	public void testHasLoopRecursive() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		loop.addChild("GS");
		Loop stloop = loop.addChild("ST");
		stloop.addChild("FAKE");
		assertEquals(true, loop.hasLoop("FAKE"));
	}

	@Test
	public void testFindLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		List<Loop> loops = loop.findLoop("2000");
		assertEquals(new Integer(1), new Integer(loops.size()));
	}

	@Test
	public void testFindMulitpleLoops() {
		Context context = new Context('~', '*', ':');
		Loop loop = new Loop(context, "ISA");
		Loop subLoop = new Loop(context, "2000");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		subLoop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		loop.addChild(4,subLoop);
		List<Loop> loops = loop.findLoop("2000");
		assertEquals(3, loops.size());
	}

	@Test
	public void testFindSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		Loop child1 = loop.addChild("2000");
		child1.addSegment("LX*1");
		Loop child2 = loop.addChild("2000");
		child2.addSegment("LX*2");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		List<Segment> segments = loop.findSegment("LX");
		assertEquals(new Integer(2), new Integer(segments.size()));
	}

	@Test
	public void testGetContext() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		assertEquals("[~,*,:]", loop.getContext().toString());
	}

	@Test
	public void testGetLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "X12");
		loop.addChild("ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		assertEquals("1000A", loop.getLoop(3).getName());
	}

	@Test
	public void testGetLoops() {
		Loop loop = new Loop(new Context('~', '*', ':'), "X12");
		loop.addChild("ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		List<Loop> loops = loop.getLoops();
		assertEquals(10, loops.size());
	}
	@Test
	public void testGetSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");		
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		assertEquals("DTM", loop.getSegment(2).getElement(0));
	}

	@Test
	public void testGetDefaultSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		assertEquals("ST", loop.getSegment().getElement(0));
	}

	@Test
	public void testGetSegments() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		List<Segment> segments = loop.getSegments();
		assertEquals(3, segments.size());
	}

	@Test
	public void testGetName() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		assertEquals("ST", loop.getName());
	}

	@Test
	public void testIterator() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		assertNotNull(loop.iterator());
	}

	@Test
	public void testRemoveLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "X12");
		loop.addChild("ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("SE");
		loop.addChild("GE");
		loop.addChild("IEA");

		Loop l1 = loop.removeLoop(3);
		assertEquals("1000A", l1.getName());
		
		Loop l2 = loop.removeLoop(0);
		assertEquals("ISA", l2.getName());
	}

	@Test
	public void testRemoveSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("TRN*1*0000000000*1999999999");
		loop.addSegment("DTM*111*20090915");
		loop.addSegment(0, "ST*835*000000001");

		Segment s = loop.removeSegment(2);
		assertEquals("TRN*1*0000000000*1999999999", s.toString());
		assertEquals(3, loop.size());
	}
	
	@Test
	public void testChildList() {
		Loop loop = new Loop(new Context('~', '*', ':'), "X12");
		loop.addChild("ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("SE");
		loop.addChild("GE");
		loop.addChild("IEA");
		List<Loop> childList = loop.childList();
		assertEquals(new Integer(11), new Integer(childList.size()));
	}

	@Test
	public void testSize() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		assertEquals(new Integer(3), new Integer(loop.size()));		
	}

	@Test
	public void testSetContext() {
		Loop loop = new Loop(new Context('a', 'b', 'c'), "ST");
		Context context = new Context('~', '*', ':');
		loop.setContext(context);
		assertEquals("[~,*,:]", loop.getContext().toString());
	}

	@Test
	public void testSetChildIntString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "X12");
		loop.addChild("ISA");
		loop.addChild("GS");
		loop.addChild("XX");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		loop.setChild(2, "ST"); // test
		assertEquals("ST", loop.getLoop(2).getName());
	}

	@Test
	public void testSetChildIntLoop() {
		Loop loop = new Loop(new Context('~', '*', ':'), "X12");
		loop.addChild("ISA");
		loop.addChild("GS");		
		loop.addChild("XX");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		loop.setChild(2, new Loop(new Context('~', '*', ':'), "ST"));
		assertEquals("ST", loop.getLoop(2).getName());
	}

	@Test
	public void testSetEmptySegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("NOT*THE*RIGHT*SEGMENT");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("TRN*1*0000000000*1999999999");
		loop.addSegment("DTM*111*20090915");
		loop.setSegment(0);
		assertEquals(0, loop.getSegment(0).size());
	}

	@Test
	public void testSetSegmentInt() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("NOT*THE*RIGHT*SEGMENT");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("TRN*1*0000000000*1999999999");
		loop.addSegment("DTM*111*20090915");
		Segment segment = new Segment(new Context('~', '*', ':'));
		segment.addElements("ST*835*000000001");
		loop.setSegment(0, segment);
		assertEquals("ST", loop.getSegment(0).getElement(0));
	}

	@Test
	public void testSetSegmentIntString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("NOT*THE*RIGHT*SEGMENT");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("TRN*1*0000000000*1999999999");
		loop.addSegment("DTM*111*20090915");
		Segment segment = new Segment(new Context('~', '*', ':'));
		segment.addElements("ST*835*000000001");
		loop.setSegment(0, segment);
		assertEquals("ST", loop.getSegment(0).getElement(0));
	}

	@Test
	public void testSetSegmentIntSegment() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		loop.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop.addSegment("DTM*111*20090915");
		loop.addSegment("NOT*THE*RIGHT*SEGMENT");
		loop.setSegment(2, "TRN*1*0000000000*1999999999");
		assertEquals("TRN", loop.getSegment(2).getElement(0));
	}

	@Test
	public void testSetName() {
		Loop loop = new Loop(new Context('~', '*', ':'), "AB");
		loop.setName("ST");
		assertEquals("ST", loop.getName());		
	}

	@Test
	public void testToString() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		assertEquals("ST*835*000000001~", loop.toString());
	}

	@Test
	public void testToStringRemoveTrailingEmptyElements() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		Segment s = loop.addSegment("ST*835*000000001");
		s.addElement("");
		s.addElement("");
		assertEquals("ST*835*000000001~", loop.toString(true));
	}

	@Test
	public void testToStringRemoveTrailingEmptyElementsTwo() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		Segment s = loop.addSegment("ST*835*000000001***ST05");
		s.addElement(null);
		s.addElement(null);
		assertEquals("ST*835*000000001***ST05~", loop.toString(true));
	}

	@Test
	public void testToStringRemoveTrailingEmptyElementsThree() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		Segment s1 = loop.addSegment("ST1*ST101*ST102***ST105");
		s1.addElement(null);
		s1.addElement(null);
		Segment s2 = loop.addSegment("ST2*ST201*ST202***ST205");
		s2.addElement("");
		s2.addElement("");	
		assertEquals("ST1*ST101*ST102***ST105~ST2*ST201*ST202***ST205~", loop.toString(true));
	}

	@Test
	public void testToXML() {
		Loop loop = new Loop(new Context('~', '*', ':'), "ST");
		loop.addSegment("ST*835*000000001");
		assertEquals(
				"<LOOP NAME=\"ST\"><ST><ST01><![CDATA[835]]></ST01><ST02><![CDATA[000000001]]></ST02></ST></LOOP>",
				loop.toXML());
	}

	@Test
	public void testGetDepth() {
		int expectedDepth = 0;
		int expectedChildDepth = 1;
		Loop loop = new Loop(new Context('~', '*', ':'), "ISA");
		loop.addChild("GS");
		loop.addChild("ST");
		loop.addChild("1000A");
		loop.addChild("1000B");
		loop.addChild("2000");
		loop.addChild("2100");
		loop.addChild("2110");
		loop.addChild("GE");
		loop.addChild("IEA");
		Loop child = loop.getLoop(0);
		int actualChildDepth = child.getDepth();
		int actualDepth = loop.getDepth();
		assertEquals(expectedDepth, actualDepth);
		assertEquals(expectedChildDepth, actualChildDepth);
	}

}
