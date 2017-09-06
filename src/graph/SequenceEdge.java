package graph;

import org.jgrapht.graph.DefaultEdge;

public class SequenceEdge extends DefaultEdge {
	
	private EdgeType type;
	
	public SequenceEdge(EdgeType type) {
		super();
		this.type = type;
	}
	
	public enum EdgeType {
		GAP, NO_GAP, OVERLAP, MATCH, DEFAULT, ADJACENT;
	}
	
	public EdgeType getType() {
		return type;
	}
	
	public String toString() {
		return type.toString();
	}
}
