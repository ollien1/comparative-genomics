package isomorphism;

import graph.Vertex;

import java.util.Comparator;

public class DirectionalVertexComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2)
	{
		if (v1.getClass().equals(v2.getClass())) {
			if (v1.getDirection() == v2.getDirection()) {
				return 0;
			}
		}
		return -1;
	}
}
