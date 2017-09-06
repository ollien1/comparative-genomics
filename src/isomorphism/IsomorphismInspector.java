package isomorphism;

import graph.AbstractVertex;
import graph.QueryVertex;
import graph.SequenceEdge;
import graph.SubjectVertex;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.GraphMapping;
import org.jgrapht.Graphs;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.ListenableDirectedGraph;

public class IsomorphismInspector {

	private ListenableDirectedGraph<Vertex, SequenceEdge> graph;
	private VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge> inspector;

	private ListenableDirectedGraph<Vertex, SequenceEdge> match;
	private ListenableDirectedGraph<Vertex, SequenceEdge> variation;
	private ListenableDirectedGraph<Vertex, SequenceEdge> insertion;
	private ListenableDirectedGraph<Vertex, SequenceEdge> deletion;
	private ListenableDirectedGraph<Vertex, SequenceEdge> invInSubject;
	private ListenableDirectedGraph<Vertex, SequenceEdge> invInQuery;
	private ListenableDirectedGraph<Vertex, SequenceEdge> dupInSubject;
	private ListenableDirectedGraph<Vertex, SequenceEdge> adjDupInSubject;
	private ListenableDirectedGraph<Vertex, SequenceEdge> dupInQuery;
	private ListenableDirectedGraph<Vertex, SequenceEdge> adjDupInQuery;

	private VertexComparator vComparator;
	private DirectionalVertexComparator dvComparator;
	private EdgeComparator eComparator;

	public IsomorphismInspector(ListenableDirectedGraph<Vertex, SequenceEdge> graph) {

		this.graph = graph;

		match = Subgraphs.match();
		variation = Subgraphs.variation();
		insertion = Subgraphs.insertion();
		deletion = Subgraphs.deletion();
		invInSubject = Subgraphs.inversionInSubject();
		invInQuery = Subgraphs.inversionInQuery();
		dupInSubject = Subgraphs.duplicationInSubject();
		adjDupInSubject = Subgraphs.adjacentDuplicationInSubject();
		dupInQuery = Subgraphs.duplicationInQuery();
		adjDupInQuery = Subgraphs.adjacentDuplicationInQuery();

		vComparator = new VertexComparator();
		dvComparator = new DirectionalVertexComparator();
		eComparator = new EdgeComparator();
	}

	public ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> inspect(MotifType motif) {

		switch(motif) {

		case MATCH:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, match, vComparator, eComparator, false);
			break;
		case VARIATION:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, variation, vComparator, eComparator, false);
			break;
		case INSERTION:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, insertion, vComparator, eComparator, false);
			break;
		case DELETION:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, deletion, vComparator, eComparator, false);
			break;
		case INV_IN_SUB:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, invInSubject, dvComparator, eComparator, false);
			break;
		case INV_IN_QUERY:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, invInQuery, dvComparator, eComparator, false);
			break;
		case DUP_IN_SUB:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, dupInSubject, vComparator, eComparator, false);
			ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> dupInSub = mappingsToArray(inspector.getMappings());
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, adjDupInSubject, vComparator, eComparator, false);
			dupInSub.addAll(mappingsToArray(inspector.getMappings()));
			return combineDuplications(dupInSub, QueryVertex.class);
		case DUP_IN_QUERY:
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, dupInQuery, vComparator, eComparator, false);
			ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> dupInQuery = mappingsToArray(inspector.getMappings());
			inspector = new VF2SubgraphIsomorphismInspector<Vertex, SequenceEdge>(graph, adjDupInQuery, vComparator, eComparator, false);
			dupInQuery.addAll(mappingsToArray(inspector.getMappings()));
			return combineDuplications(dupInQuery, SubjectVertex.class);
		}

		return mappingsToArray(inspector.getMappings());
	}

	private ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> mappingsToArray(Iterator<GraphMapping<Vertex, SequenceEdge>> iterator) {

		ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> mappings = new ArrayList<ListenableDirectedGraph<Vertex,SequenceEdge>>();

		if (inspector.isomorphismExists()) {
			for (Iterator<GraphMapping<Vertex, SequenceEdge>> iter = iterator; iter.hasNext();) {
				ListenableDirectedGraph<Vertex, SequenceEdge> subgraph = mappingToSubgraph(iter.next());
				mappings.add(subgraph);
			}
		}

		return mappings;
	}

	private ListenableDirectedGraph<Vertex, SequenceEdge> mappingToSubgraph(GraphMapping<Vertex, SequenceEdge> mapping) {

		ListenableDirectedGraph<Vertex, SequenceEdge> subgraph = 
				new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);

		Set<Vertex> vertices = graph.vertexSet();

		for (Vertex v : vertices) {
			Vertex vertex = mapping.getVertexCorrespondence(v, true);
			if (vertex != null) {
				subgraph.addVertex(v);
			}
		}

		//Search for and add edges
		for (Vertex v : subgraph.vertexSet()) {
			for (Vertex v2 : subgraph.vertexSet()) {	
				if (graph.containsEdge(v, v2)) {
					subgraph.addEdge(v, v2, graph.getEdge(v, v2));
				}
			}
		}

		return subgraph;
	}

	private ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> combineDuplications(ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> graphs, Class<? extends AbstractVertex> type) {

		ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> combinedGraphs = new ArrayList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		HashSet<Vertex> duplicatedVertices = new HashSet<Vertex>();
		ListenableDirectedGraph<Vertex, SequenceEdge> combinedGraph = null;

		for (ListenableDirectedGraph<Vertex, SequenceEdge> graph : graphs) {

			for (Vertex v : graph.vertexSet()) {
				
				if (type.isInstance(v) && !duplicatedVertices.contains(v)) {
					duplicatedVertices.add(v);
					combinedGraph = new ListenableDirectedGraph<Vertex, SequenceEdge>(SequenceEdge.class);
					Graphs.addGraph(combinedGraph, graph);
					
					for (ListenableDirectedGraph<Vertex, SequenceEdge> graph2 : graphs) {
						if (graph2.vertexSet().contains(v)) {
							Graphs.addGraph(combinedGraph, graph2);
						}
					}
					combinedGraphs.add(combinedGraph);
				}
			}			
		}

		return combinedGraphs;
	}

	public HashMap<MotifType, ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>>> inspectAll() {

		HashMap<MotifType, ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>>> subgraphMap = 
				new HashMap<MotifType, ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>>>();

		long time = System.currentTimeMillis();
		System.out.println("Starting search ");

//		subgraphMap.put(MotifType.MATCH, inspect(MotifType.MATCH));
//		System.out.println("Match search complete " + (System.currentTimeMillis() - time));
//		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.VARIATION, inspect(MotifType.VARIATION));
		System.out.println("Variation search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.INSERTION, inspect(MotifType.INSERTION));
		System.out.println("Insertion search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.DELETION, inspect(MotifType.DELETION));
		System.out.println("Deletion search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.INV_IN_SUB, inspect(MotifType.INV_IN_SUB));
		System.out.println("Inv in sub search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.INV_IN_QUERY, inspect(MotifType.INV_IN_QUERY));
		System.out.println("Inv in query search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.DUP_IN_SUB, inspect(MotifType.DUP_IN_SUB));
		System.out.println("Dup in sub search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

//		subgraphMap.put(MotifType.ADJ_DUP_IN_SUB, inspect(MotifType.ADJ_DUP_IN_SUB));
//		System.out.println("Adj dup in sub search complete " + (System.currentTimeMillis() - time));
//		time = System.currentTimeMillis();

		subgraphMap.put(MotifType.DUP_IN_QUERY, inspect(MotifType.DUP_IN_QUERY));
		System.out.println("Dup in query search complete " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

//		subgraphMap.put(MotifType.ADJ_DUP_IN_QUERY, inspect(MotifType.ADJ_DUP_IN_QUERY));
//		System.out.println("Adj dup in query search complete " + (System.currentTimeMillis() - time));
//		time = System.currentTimeMillis();

		return subgraphMap;
	}
}
