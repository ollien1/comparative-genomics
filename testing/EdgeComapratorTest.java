import static org.junit.Assert.*;
import isomorphism.EdgeComparator;
import graph.SequenceEdge;
import graph.SequenceEdge.EdgeType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EdgeComapratorTest
{
	SequenceEdge matchEdge;
	SequenceEdge defaultEdge;
	SequenceEdge gapEdge;
	SequenceEdge noGapEdge;
	SequenceEdge overlapEdge;
	SequenceEdge adjacentEdge;
	
	EdgeComparator comparator;
	
	@Before
	public void setUp() throws Exception
	{
		matchEdge = new SequenceEdge(EdgeType.MATCH);
		defaultEdge = new SequenceEdge(EdgeType.DEFAULT);
		gapEdge = new SequenceEdge(EdgeType.GAP);
		noGapEdge = new SequenceEdge(EdgeType.NO_GAP);
		overlapEdge = new SequenceEdge(EdgeType.OVERLAP);
		adjacentEdge = new SequenceEdge(EdgeType.ADJACENT);
		
		comparator = new EdgeComparator();
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		assertNotEquals(0, comparator.compare(matchEdge, defaultEdge));
		assertNotEquals(0, comparator.compare(matchEdge, gapEdge));
		assertNotEquals(0, comparator.compare(matchEdge, noGapEdge));
		assertNotEquals(0, comparator.compare(matchEdge, overlapEdge));
		assertNotEquals(0, comparator.compare(matchEdge, adjacentEdge));
		
		assertEquals(0, comparator.compare(defaultEdge, gapEdge));
		assertEquals(0, comparator.compare(defaultEdge, noGapEdge));
		assertEquals(0, comparator.compare(defaultEdge, overlapEdge));
		assertEquals(0, comparator.compare(defaultEdge, adjacentEdge));

		assertNotEquals(0, comparator.compare(gapEdge, noGapEdge));
		assertNotEquals(0, comparator.compare(gapEdge, overlapEdge));
		assertNotEquals(0, comparator.compare(gapEdge, adjacentEdge));

		assertNotEquals(0, comparator.compare(noGapEdge, overlapEdge));
		assertEquals(0, comparator.compare(noGapEdge, adjacentEdge));

		assertEquals(0, comparator.compare(overlapEdge, adjacentEdge));
	}

}
