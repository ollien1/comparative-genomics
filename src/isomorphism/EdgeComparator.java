package isomorphism;

import graph.SequenceEdge;

import java.util.Comparator;

public class EdgeComparator implements Comparator<SequenceEdge> {

	@Override
	public int compare(SequenceEdge e1, SequenceEdge e2)
	{
		// If one edge is default and the other is not match
		if (((e1.getType() == SequenceEdge.EdgeType.DEFAULT) && (e2.getType() != SequenceEdge.EdgeType.MATCH)) 
				|| ((e2.getType() == SequenceEdge.EdgeType.DEFAULT) && (e1.getType() != SequenceEdge.EdgeType.MATCH))) {
			return 0;
		}
		
		// If one edge is adjacent and the other is no gap or overlap
		if (((e1.getType() == SequenceEdge.EdgeType.ADJACENT) && ((e2.getType() == SequenceEdge.EdgeType.NO_GAP) || (e2.getType() == SequenceEdge.EdgeType.OVERLAP)))
				|| ((e2.getType() == SequenceEdge.EdgeType.ADJACENT) && ((e1.getType() == SequenceEdge.EdgeType.NO_GAP) || (e1.getType() == SequenceEdge.EdgeType.OVERLAP)))) {
			return 0;
		}
		
		return e1.getType().compareTo(e2.getType());
	}
}
