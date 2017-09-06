package isomorphism;

import graph.QueryVertex;
import graph.SequenceEdge;
import graph.SubjectVertex;
import graph.Vertex;

import org.jgrapht.graph.ListenableDirectedGraph;

public class Subgraphs {

	public static ListenableDirectedGraph<Vertex, SequenceEdge> match() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> match = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject = new SubjectVertex("Subject", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query = new QueryVertex("Query", 1, 2, 0.0, 0, 0.0, "0.0");
		
		match.addVertex(subject);
		match.addVertex(query);
		
		SequenceEdge edge = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		match.addEdge(subject, query, edge);
		
		return match;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> variation() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> variation = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex sStart = new SubjectVertex("sStart", 1, 2, 0.0, 0, 0.0, "0.0");
		SubjectVertex sEnd = new SubjectVertex("sEnd", 3, 4, 0.0, 0, 0.0, "0.0");
		QueryVertex qStart = new QueryVertex("qStart", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex qEnd = new QueryVertex("qEnd", 3, 4, 0.0, 0, 0.0, "0.0");

		variation.addVertex(sStart);
		variation.addVertex(sEnd);
		variation.addVertex(qStart);
		variation.addVertex(qEnd);
		
		SequenceEdge startMatch = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge endMatch = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge subjectGap = new SequenceEdge(SequenceEdge.EdgeType.GAP);
		SequenceEdge queryGap = new SequenceEdge(SequenceEdge.EdgeType.GAP);

		variation.addEdge(sStart, qStart, startMatch);
		variation.addEdge(sEnd, qEnd, endMatch);
		variation.addEdge(sStart, sEnd, subjectGap);
		variation.addEdge(qStart, qEnd, queryGap);
		
		return variation;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> insertion() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> insertion = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex sStart = new SubjectVertex("sStart", 1, 2, 0.0, 0, 0.0, "0.0");
		SubjectVertex sEnd = new SubjectVertex("sEnd", 3, 4, 0.0, 0, 0.0, "0.0");
		QueryVertex qStart = new QueryVertex("qStart", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex qEnd = new QueryVertex("qEnd", 3, 4, 0.0, 0, 0.0, "0.0");

		insertion.addVertex(sStart);
		insertion.addVertex(sEnd);
		insertion.addVertex(qStart);
		insertion.addVertex(qEnd);
		
		SequenceEdge startMatch = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge endMatch = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge subjectGap = new SequenceEdge(SequenceEdge.EdgeType.GAP);
		SequenceEdge queryGap = new SequenceEdge(SequenceEdge.EdgeType.ADJACENT);

		insertion.addEdge(sStart, qStart, startMatch);
		insertion.addEdge(sEnd, qEnd, endMatch);
		insertion.addEdge(sStart, sEnd, subjectGap);
		insertion.addEdge(qStart, qEnd, queryGap);
		
		return insertion;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> deletion() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> deletion = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex sStart = new SubjectVertex("sStart", 1, 2, 0.0, 0, 0.0, "0.0");
		SubjectVertex sEnd = new SubjectVertex("sEnd", 3, 4, 0.0, 0, 0.0, "0.0");
		QueryVertex qStart = new QueryVertex("qStart", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex qEnd = new QueryVertex("qEnd", 3, 4, 0.0, 0, 0.0, "0.0");

		deletion.addVertex(sStart);
		deletion.addVertex(sEnd);
		deletion.addVertex(qStart);
		deletion.addVertex(qEnd);
		
		SequenceEdge startMatch = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge endMatch = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge subjectGap = new SequenceEdge(SequenceEdge.EdgeType.ADJACENT);
		SequenceEdge queryGap = new SequenceEdge(SequenceEdge.EdgeType.GAP);

		deletion.addEdge(sStart, qStart, startMatch);
		deletion.addEdge(sEnd, qEnd, endMatch);
		deletion.addEdge(sStart, sEnd, subjectGap);
		deletion.addEdge(qStart, qEnd, queryGap);
		
		return deletion;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> inversionInSubject() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> inversionInSubject =
				new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject = new SubjectVertex("Subject", 2, 1, 0.0, 0, 0.0, "0.0");
		QueryVertex query = new QueryVertex("Query", 1, 2, 0.0, 0, 0.0, "0.0");
		
		inversionInSubject.addVertex(subject);
		inversionInSubject.addVertex(query);
		
		SequenceEdge edge = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		inversionInSubject.addEdge(subject, query, edge);
		
		return inversionInSubject;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> inversionInQuery() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> inversionInQuery = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject = new SubjectVertex("Subject", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query = new QueryVertex("Query", 2, 1, 0.0, 0, 0.0, "0.0");
		
		inversionInQuery.addVertex(subject);
		inversionInQuery.addVertex(query);
		
		SequenceEdge edge = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		inversionInQuery.addEdge(subject, query, edge);
		
		return inversionInQuery;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> duplicationInQuery() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> duplicationInQuery = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject = new SubjectVertex("Subject", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query1 = new QueryVertex("q1", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query2 = new QueryVertex("q2", 3, 4, 0.0, 0, 0.0, "0.0");
		
		duplicationInQuery.addVertex(subject);
		duplicationInQuery.addVertex(query1);
		duplicationInQuery.addVertex(query2);
		
		SequenceEdge edge1 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge edge2 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);

		duplicationInQuery.addEdge(subject, query1, edge1);
		duplicationInQuery.addEdge(subject, query2, edge2);
		
		return duplicationInQuery;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> adjacentDuplicationInQuery() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> adjacentDuplicationInQuery = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject = new SubjectVertex("Subject", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query1 = new QueryVertex("q1", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query2 = new QueryVertex("q2", 3, 4, 0.0, 0, 0.0, "0.0");
		
		adjacentDuplicationInQuery.addVertex(subject);
		adjacentDuplicationInQuery.addVertex(query1);
		adjacentDuplicationInQuery.addVertex(query2);
		
		SequenceEdge edge1 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge edge2 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge def = new SequenceEdge(SequenceEdge.EdgeType.DEFAULT);

		adjacentDuplicationInQuery.addEdge(subject, query1, edge1);
		adjacentDuplicationInQuery.addEdge(subject, query2, edge2);
		adjacentDuplicationInQuery.addEdge(query1, query2, def);
		
		return adjacentDuplicationInQuery;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> duplicationInSubject() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> duplicationInSubject = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject1 = new SubjectVertex("s1", 1, 2, 0.0, 0, 0.0, "0.0");
		SubjectVertex subject2 = new SubjectVertex("s2", 3, 4, 0.0, 0, 0.0, "0.0");
		QueryVertex query = new QueryVertex("Query", 1, 2, 0.0, 0, 0.0, "0.0");
		
		duplicationInSubject.addVertex(subject1);
		duplicationInSubject.addVertex(subject2);
		duplicationInSubject.addVertex(query);
		
		SequenceEdge edge1 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge edge2 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);

		duplicationInSubject.addEdge(subject1, query, edge1);
		duplicationInSubject.addEdge(subject2, query, edge2);
		
		return duplicationInSubject;
	}
	
	public static ListenableDirectedGraph<Vertex, SequenceEdge> adjacentDuplicationInSubject() {
		
		ListenableDirectedGraph<Vertex, SequenceEdge> adjacentDuplicationInSubject = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
		
		SubjectVertex subject1 = new SubjectVertex("s1", 1, 2, 0.0, 0, 0.0, "0.0");
		SubjectVertex subject2 = new SubjectVertex("s2", 1, 2, 0.0, 0, 0.0, "0.0");
		QueryVertex query = new QueryVertex("Query", 1, 2, 0.0, 0, 0.0, "0.0");
		
		adjacentDuplicationInSubject.addVertex(subject1);
		adjacentDuplicationInSubject.addVertex(subject2);
		adjacentDuplicationInSubject.addVertex(query);
		
		SequenceEdge edge1 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge edge2 = new SequenceEdge(SequenceEdge.EdgeType.MATCH);
		SequenceEdge def = new SequenceEdge(SequenceEdge.EdgeType.DEFAULT);

		adjacentDuplicationInSubject.addEdge(subject1, query, edge1);
		adjacentDuplicationInSubject.addEdge(subject2, query, edge2);
		adjacentDuplicationInSubject.addEdge(subject1, subject2, def);
		
		return adjacentDuplicationInSubject;
	}
}
