package isomorphism;

import graph.Vertex;

import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex> {
	
	@Override
	public int compare(Vertex v1, Vertex v2)
	{
		if (v1.getClass().equals(v2.getClass())) return 0;
		return -1;
	}
}
