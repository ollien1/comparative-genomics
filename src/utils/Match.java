package utils;

public class Match {
	
	private String queryId;
	private String subjectId;
	private double sequenceIdentity;
	private int alignmentLength;
	private int queryStart;
	private int queryEnd;
	private int subjectStart;
	private int subjectEnd;
	private double eValue;
	private String bitScore;
	
	public Match(String[] blastLine) {
		
		queryId = blastLine[0];
		subjectId = blastLine[1];
		sequenceIdentity = Double.parseDouble(blastLine[2]);
		alignmentLength = Integer.parseInt(blastLine[3]);
		queryStart = Integer.parseInt(blastLine[6]);
		queryEnd = Integer.parseInt(blastLine[7]);
		subjectStart = Integer.parseInt(blastLine[8]);
		subjectEnd = Integer.parseInt(blastLine[9]);
		eValue = Double.parseDouble(blastLine[10]);
		bitScore = blastLine[11];
	}
	
	public String getQueryId() {
		return queryId;
	}
	
	public String getSubjectId() {
		return subjectId;
	}
	
	public double getSequenceIdentity() {
		return sequenceIdentity;
	}
	
	public int getLength() {
		return alignmentLength;
	}
	
	public int getQueryStart() {
		return queryStart;
	}
	
	public int getQueryEnd() {
		return queryEnd;
	}
	
	public int getSubjectStart() {
		return subjectStart;
	}
	
	public int getSubjectEnd() {
		return subjectEnd;
	}
	
	public double getEValue() {
		return eValue;
	}
	
	public String getBitScore() {
		return bitScore;
	}
}
