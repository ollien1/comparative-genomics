package visualisation;

import graph.AbstractVertex;
import graph.SequenceEdge;
import graph.SubjectVertex;
import graph.Vertex;
import graph.SequenceEdge.EdgeType;

import java.util.ArrayList;

import org.jgrapht.graph.ListenableDirectedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class VisualisationUtils {

	protected static void highlightMotif(ListenableDirectedGraph<Vertex, SequenceEdge> graph, GraphVisualiser visualiser) {

		mxGraph graphX = visualiser.getGraphX();
		
		// If another subgraph has been highlighted, remove the highlight
		if (visualiser.highlightedGraph == null) {
			visualiser.highlightedGraph = graph;
		} else {
			ListenableDirectedGraph<Vertex, SequenceEdge> highlightedGraph = visualiser.highlightedGraph;
			
			graphX.getModel().beginUpdate();
			
			for (Vertex v : highlightedGraph.vertexSet()) {
				mxCell cell = visualiser.getVertexToCellMap().get(v);
				
				if (v instanceof SubjectVertex) {
					graphX.getModel().setStyle(cell, GraphVisualiser.SUBJECT_VERTEX_STYLE);
				} else {
					graphX.getModel().setStyle(cell, GraphVisualiser.QUERY_VERTEX_STYLE);
				}
			}
			
			for (SequenceEdge e : highlightedGraph.edgeSet()) {
				mxCell cell = visualiser.getEdgeToCellMap().get(e);
				
				switch(e.getType()) {
					case GAP: graphX.getModel().setStyle(cell, GraphVisualiser.GAP_EDGE_STYLE);
						break;
					case NO_GAP:
					case MATCH: 
						graphX.getModel().setStyle(cell, GraphVisualiser.EDGE_STYLE);
						break;
					case OVERLAP: graphX.getModel().setStyle(cell, GraphVisualiser.OVERLAP_EDGE_STYLE);
						break;
				}
			}
			
			graphX.getModel().endUpdate();
			
			visualiser.highlightedGraph = graph;
		}
		
		// Highlight selected graph
		graphX.getModel().beginUpdate();

		for (Vertex v : graph.vertexSet()) {
			mxCell cell = visualiser.getVertexToCellMap().get(v);
			graphX.getModel().setStyle(cell, GraphVisualiser.MOTIF_HIGHLIGHT_STYLE);
		}
		
		for (SequenceEdge e : graph.edgeSet()) {
			mxCell cell = visualiser.getEdgeToCellMap().get(e);
			
			if (e.getType() == EdgeType.GAP) {
				graphX.getModel().setStyle(cell, GraphVisualiser.GAP_EDGE_HIGHLIGHT_STYLE);
			} else {
				graphX.getModel().setStyle(cell, GraphVisualiser.EDGE_HIGHLIGHT_STYLE);
			}
		}

		graphX.getModel().endUpdate();

		// Scroll screen to first subject vertex in subgraph
		Vertex first = null;

		for (Vertex v: graph.vertexSet()) {
			if (first == null) {
				first = v;
			}
			else if (v instanceof SubjectVertex && Math.min(v.getStart(), v.getEnd()) > Math.min(first.getStart(), first.getEnd())) {
				first = v;
			}
		}

		visualiser.scrollCellToVisible(visualiser.getVertexToCellMap().get(first), true);
	}
	
	protected static void scrollToPos(int pos, Class<? extends AbstractVertex> type, GraphVisualiser visualiser) {
		
		Vertex closest = null;

		for (Vertex v : visualiser.getCellToVertexMap().values()) {

			if (type.isInstance(v)) {

				if (closest == null) {
					closest = v;
				} else if ((Math.min(v.getStart(), v.getEnd()) - pos) < (pos - Math.max(closest.getStart(), closest.getEnd()))) {
					closest = v;
				} else {
					break;
				}
			}
		}
		mxCell cell = visualiser.getVertexToCellMap().get(closest);
		visualiser.scrollCellToVisible(cell, true);
	}
}
