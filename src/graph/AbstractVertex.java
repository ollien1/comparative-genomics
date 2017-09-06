package graph;

import sun.security.util.Length;


public abstract class AbstractVertex implements Vertex, Comparable<AbstractVertex> {
	
	protected String sequenceId;
	protected int start;
	protected int end;
	protected double sequenceIdentity;
	protected int alignmentLength;
	protected double eValue;
	protected String bitScore;
	protected boolean direction;
	
	public AbstractVertex(String id, int start, int end, double identity, int length, double eValue, String bitScore){
		
		sequenceId = id;
		this.start = start;
		this.end = end;
		sequenceIdentity = identity;
		alignmentLength = length;
		this.eValue = eValue;
		this.bitScore = bitScore;
		
		if (start < end) direction = true;
		else direction = false;
	}
	
	public String getSequenceId() {
		return sequenceId;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public double getIdentity() {
		return sequenceIdentity;
	}
	
	public int getLength() {
		return alignmentLength;
	}
	
	public double getEValue() {
		return eValue;
	}
	
	public String getBitScore() {
		return bitScore;
	}
	
	public boolean getDirection() {
		return direction;
	}
	
	public int compareTo(AbstractVertex v) {
		return Math.min(start, end) - Math.min(v.getStart(), v.getEnd());
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
		
		if (!(o instanceof Vertex) || o == null) {
			return false;
		}
		
		Vertex v = (Vertex) o;
		
		if (sequenceId.equals(v.getSequenceId()) && start == v.getStart() && end == v.getEnd()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		
		int hash = 17;
		int multiplier = 37;
		
		hash = multiplier * hash + sequenceId.hashCode();
		hash = multiplier * hash + start;
		hash = multiplier * hash + end;
		
		return hash;
	}
	
	public String toString() {
		return start + " - " + end;
	}

	public String displayInfo() {
		
		return "SequenceID: " + sequenceId
				+ "\nStart: " + start
				+ "\nEnd: " + end
				+ "\nIdentity: " + sequenceIdentity
				+ "\nLength: " + alignmentLength
				+ "\neValue: " + eValue
				+ "\nBitScore: " + bitScore;
	}
}
