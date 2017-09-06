package graph;

import graph.SequenceEdge.EdgeType;

import java.util.ArrayList;
import java.util.Collections;
import org.jgrapht.graph.ListenableDirectedGraph;

import utils.Match;

public class GraphBuilder {
	
	private ListenableDirectedGraph<Vertex, SequenceEdge> graph;
	private ArrayList<Match> blastData;
	private ArrayList<SubjectVertex> subjectVertices;
	private ArrayList<QueryVertex> queryVertices;

	public GraphBuilder(ArrayList<Match> blastData) {
		
		graph = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		this.blastData = blastData;
		subjectVertices  = new ArrayList<SubjectVertex>();
		queryVertices = new ArrayList<QueryVertex>();
				
		populateGraph();
		
		Collections.sort(subjectVertices);
		Collections.sort(queryVertices);
		
		addHorizontalEdges();
	}
	
	private void populateGraph() {
		
		for (Match match : blastData) {
			
			SubjectVertex sv = 
					new SubjectVertex(match.getSubjectId(), match.getSubjectStart(), match.getSubjectEnd(), match.getSequenceIdentity(), match.getLength(), match.getEValue(), match.getBitScore());			
			QueryVertex qv = 
					new QueryVertex(match.getQueryId(), match.getQueryStart(), match.getQueryEnd(), match.getSequenceIdentity(), match.getLength(), match.getEValue(), match.getBitScore());
			
			if (!subjectVertices.contains(sv) && !queryVertices.contains(qv)) {
				
				subjectVertices.add(sv);
				queryVertices.add(qv);
				
				graph.addVertex(sv);
				graph.addVertex(qv);
				
				graph.addEdge(sv, qv, new SequenceEdge(SequenceEdge.EdgeType.MATCH));
			}
			else if (subjectVertices.contains(sv) && !queryVertices.contains(qv)) {
				
				queryVertices.add(qv);
				graph.addVertex(qv);
				
				SubjectVertex existingSubject = subjectVertices.get(subjectVertices.indexOf(sv));
				
				graph.addEdge(existingSubject, qv, new SequenceEdge(SequenceEdge.EdgeType.MATCH));
			}
			else {
				
				subjectVertices.add(sv);
				graph.addVertex(sv);
				
				QueryVertex existingQuery = queryVertices.get(queryVertices.indexOf(qv));
				
				graph.addEdge(sv, existingQuery, new SequenceEdge(SequenceEdge.EdgeType.MATCH));
			}
		}
	}
	
	private void addHorizontalEdges() {
		
		Vertex currentSubject;
		Vertex currentQuery;
		
		Vertex nextSubject = null;
		Vertex nextQuery = null;
		
		for (int i = 0; i < subjectVertices.size(); i++) {
			
			currentSubject = subjectVertices.get(i);
			
			if (i + 1 != subjectVertices.size()) {
				nextSubject = subjectVertices.get(i + 1);
				graph.addEdge(currentSubject, nextSubject, new SequenceEdge(gapCheck(currentSubject, nextSubject)));
			}			
		}
		
		for (int i = 0; i < queryVertices.size(); i++) {
			
			currentQuery = queryVertices.get(i);
			
			if (i + 1 != queryVertices.size()) {
				nextQuery = queryVertices.get(i + 1);
				graph.addEdge(currentQuery, nextQuery, new SequenceEdge(gapCheck(currentQuery, nextQuery)));
			}			
		}
	}
	
	private EdgeType gapCheck(Vertex current, Vertex next) {
		
		int currentStart = current.getStart();
		int currentEnd = current.getEnd();
		
		int nextStart = next.getStart();
		int nextEnd = next.getEnd();
		
		if (((currentEnd + 1) == nextStart) || ((currentEnd + 1) == nextEnd) ||
				((currentStart + 1) == nextStart) || ((currentStart + 1) == nextEnd)) {
			return SequenceEdge.EdgeType.NO_GAP;
		}
		else if (((currentEnd + 1) < nextStart) && ((currentStart + 1) < nextStart) && 
				((currentEnd + 1) < nextEnd) && ((currentStart + 1) < nextEnd)) {
			return SequenceEdge.EdgeType.GAP;
		}
		else return SequenceEdge.EdgeType.OVERLAP;
	}
	
	public ListenableDirectedGraph<Vertex, SequenceEdge> getGraph() {
		return graph;
	}
	
	public ArrayList<SubjectVertex> getSubjectVertices() {
		return subjectVertices;
	}
	
	public ArrayList<QueryVertex> getQueryVertices() {
		return queryVertices;
	}
}
