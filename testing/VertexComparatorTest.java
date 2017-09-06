import static org.junit.Assert.*;
import isomorphism.DirectionalVertexComparator;
import isomorphism.VertexComparator;
import graph.QueryVertex;
import graph.SubjectVertex;

import javax.security.auth.Subject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class VertexComparatorTest
{
	SubjectVertex forwardSub;
	SubjectVertex reverseSub;
	
	QueryVertex forwardQuery;
	QueryVertex reverseQuery;
	
	VertexComparator vc;
	DirectionalVertexComparator dvc;
	
	@Before
	public void setUp() throws Exception
	{
		forwardSub = new SubjectVertex("forwardSub", 0, 1, 0.0, 1, 0.0, "");
		reverseSub = new SubjectVertex("reverseSub", 1, 0, 0.0, 1, 0.0, "");
		
		forwardQuery = new QueryVertex("forwardQuery", 0, 1, 0.0, 1, 0.0, "");
		reverseQuery = new QueryVertex("reverseQuery", 1, 0, 0.0, 1, 0.0, "");

		vc = new VertexComparator();
		dvc = new DirectionalVertexComparator();
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		assertEquals(0, vc.compare(forwardSub, reverseSub));
		assertEquals(0, vc.compare(forwardQuery, reverseQuery));
		
		assertNotEquals(0, vc.compare(forwardSub, forwardQuery));
		assertNotEquals(0, vc.compare(forwardSub, reverseQuery));
		assertNotEquals(0, vc.compare(reverseSub, forwardQuery));
		assertNotEquals(0, vc.compare(reverseSub, reverseQuery));
		
		assertNotEquals(0, dvc.compare(forwardSub, reverseSub));
		assertNotEquals(0, dvc.compare(forwardQuery, reverseQuery));

		assertNotEquals(0, dvc.compare(forwardSub, forwardQuery));
		assertNotEquals(0, dvc.compare(forwardSub, reverseQuery));
		assertNotEquals(0, dvc.compare(reverseSub, forwardQuery));
		assertNotEquals(0, dvc.compare(reverseSub, reverseQuery));
	}

}
