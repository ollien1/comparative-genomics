package visualisation;

import graph.GraphBuilder;
import graph.QueryVertex;
import graph.SequenceEdge;
import graph.SequenceEdge.EdgeType;
import graph.SubjectVertex;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.graph.ListenableDirectedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class GraphVisualiser extends mxGraphComponent {

	private GraphBuilder builder;
	private ListenableDirectedGraph<Vertex, SequenceEdge> graphT;
	private mxGraph graphX;
	private mxGraphModel model;

	private LinkedHashMap<Vertex, mxCell> vertexToCellMap = new LinkedHashMap<Vertex, mxCell>();
	private LinkedHashMap<SequenceEdge, mxCell> edgeToCellMap = new LinkedHashMap<SequenceEdge, mxCell>();
	private LinkedHashMap<mxCell, Vertex> cellToVertexMap = new LinkedHashMap<mxCell, Vertex>();
	private LinkedHashMap<mxCell, SequenceEdge> cellToEdgeMap = new LinkedHashMap<mxCell, SequenceEdge>();

	protected static final String SUBJECT_VERTEX_STYLE = "SUBJECT_VERTEX_STYLE";
	protected static final String QUERY_VERTEX_STYLE = "QUERY_VERTEX_STYLE";
	protected static final String MOTIF_HIGHLIGHT_STYLE = "MOTIF_HIGHLIGHT_STYLE";
	protected static final String GAP_EDGE_STYLE = "GAP_EDGE_STYLE";
	protected static final String EDGE_STYLE = "EDGE_STYLE";
	protected static final String OVERLAP_EDGE_STYLE = "OVERLAP_EDGE_STYLE";
	protected static final String EDGE_HIGHLIGHT_STYLE = "EDGE_HIGHLIGHT_STYLE";
	protected static final String GAP_EDGE_HIGHLIGHT_STYLE = "GAP_EDGE_HIGHLIGHT_STYLE";
	
	protected ListenableDirectedGraph<Vertex, SequenceEdge> highlightedGraph = null;

	public GraphVisualiser(mxGraph graph, GraphBuilder builder) {

		super(graph);

		this.builder = builder;
		graphT = builder.getGraph();
		model = new mxGraphModel();
		graphX = graph;

		graphX.setModel(model);

		buildModel();
		buildStyleSheet();
		graphVisualisation();
	}

	private void buildModel() {

		model.beginUpdate();

		try {
			for (Vertex vertex : builder.getQueryVertices()) {
				addVertex(vertex);
			}
			for (Vertex vertex : builder.getSubjectVertices()) {
				addVertex(vertex);
			}
			for (SequenceEdge edge : graphT.edgeSet()) {
				addEdge(edge);
			}
		} finally {
			model.endUpdate();
		}
	}

	private void addVertex(Vertex v) {

		model.beginUpdate();

		try {
			mxCell cell = new mxCell(v);

			cell.setVertex(true);
			cell.setId(null);
			graphX.addCell(cell);

			vertexToCellMap.put(v, cell);
			cellToVertexMap.put(cell, v);
		} finally {
			model.endUpdate();
		}
	}

	private void addEdge(SequenceEdge e) {

		model.beginUpdate();

		try {
			mxCell cell = new mxCell(e);

			cell.setEdge(true);
			cell.setId(null);
			cell.setValue(e.getType().toString());
			cell.setGeometry(new mxGeometry());
			cell.getGeometry().setRelative(true);

			Vertex source = graphT.getEdgeSource(e);
			Vertex target = graphT.getEdgeTarget(e);
			graphX.addEdge(cell, null, vertexToCellMap.get(source), vertexToCellMap.get(target), null);

			edgeToCellMap.put(e, cell);
			cellToEdgeMap.put(cell, e);
		} finally {
			model.endUpdate();
		}
	}

	public void buildStyleSheet() {

		mxStylesheet stylesheet = graphX.getStylesheet();

		Map<String, Object> subjectNodeStyle = new HashMap<String, Object>();
		subjectNodeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		subjectNodeStyle.put(mxConstants.STYLE_FILLCOLOR, "#66CC00");
		subjectNodeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");

		stylesheet.putCellStyle(SUBJECT_VERTEX_STYLE, subjectNodeStyle);

		Map<String, Object> queryNodeStyle = new HashMap<String, Object>();
		queryNodeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		queryNodeStyle.put(mxConstants.STYLE_FILLCOLOR, "#9999FF");
		queryNodeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");

		stylesheet.putCellStyle(QUERY_VERTEX_STYLE, queryNodeStyle);

		Map<String, Object> motifHighlightStyle = new HashMap<String, Object>();
		motifHighlightStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		motifHighlightStyle.put(mxConstants.STYLE_FILLCOLOR, "#D14545");
		motifHighlightStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");

		stylesheet.putCellStyle(MOTIF_HIGHLIGHT_STYLE, motifHighlightStyle);

		Map<String, Object> gapEdgeStyle = new HashMap<String, Object>();
		gapEdgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#FFFFFF");
		gapEdgeStyle.put(mxConstants.STYLE_DASHED, "1");
		gapEdgeStyle.put(mxConstants.STYLE_SPACING_BOTTOM, "-15");

		stylesheet.putCellStyle(GAP_EDGE_STYLE, gapEdgeStyle);

		Map<String, Object> edgeStyle = new HashMap<String, Object>();
		edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#FFFFFF");
		edgeStyle.put(mxConstants.STYLE_SPACING_BOTTOM, "-15");

		stylesheet.putCellStyle(EDGE_STYLE, edgeStyle);

		Map<String, Object> overlapEdgeStyle = new HashMap<String, Object>();
		overlapEdgeStyle.put(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC);
		overlapEdgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#FFFFFF");
		overlapEdgeStyle.put(mxConstants.STYLE_SPACING_BOTTOM, "-15");

		stylesheet.putCellStyle(OVERLAP_EDGE_STYLE, overlapEdgeStyle);

		Map<String, Object> gapEdgeHighlightStyle = new HashMap<String, Object>();
		gapEdgeHighlightStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#FFFFFF");
		gapEdgeHighlightStyle.put(mxConstants.STYLE_DASHED, "1");
		gapEdgeHighlightStyle.put(mxConstants.STYLE_STROKECOLOR, "#D14545");
		gapEdgeHighlightStyle.put(mxConstants.STYLE_SPACING_BOTTOM, "-15");

		stylesheet.putCellStyle(GAP_EDGE_HIGHLIGHT_STYLE, gapEdgeHighlightStyle);

		Map<String, Object> edgeHighlightStyle = new HashMap<String, Object>();
		edgeHighlightStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#FFFFFF");
		edgeHighlightStyle.put(mxConstants.STYLE_STROKECOLOR, "#D14545");
		edgeHighlightStyle.put(mxConstants.STYLE_SPACING_BOTTOM, "-15");

		stylesheet.putCellStyle(EDGE_HIGHLIGHT_STYLE, edgeHighlightStyle);

		graphX.setStylesheet(stylesheet);
	}

	public mxGraph getGraphX() {
		return graphX;
	}

	public void graphVisualisation() {

		graphX.getModel().beginUpdate(); 

		int x = 20, y = 20;
		final int width = 100, height = 70;

		// Position and style subject vertices
		for (mxCell cell : vertexToCellMap.values()) {     		        	
			if (cell.getValue() instanceof SubjectVertex) {

				graphX.getModel().setStyle(cell, SUBJECT_VERTEX_STYLE);
				graphX.getModel().setGeometry(cell, new mxGeometry(x, y, width, height));
				x += 180;
			}
		}

		x = 20;
		y = 300;

		// Position and style query vertices
		for (mxCell cell : vertexToCellMap.values()) {
			if (cell.getValue() instanceof QueryVertex) {

				graphX.getModel().setStyle(cell, QUERY_VERTEX_STYLE);
				graphX.getModel().setGeometry(cell, new mxGeometry(x, y, width, height));
				x += 180;
			}						
		}

		// Set edge styles
		for (mxCell edgeCell : edgeToCellMap.values()) {

			switch (edgeCell.getValue().toString()) {
			
			case "GAP":
				graphX.getModel().setStyle(edgeCell, GAP_EDGE_STYLE); 
				break;
			case "NO_GAP":
				graphX.getModel().setStyle(edgeCell, EDGE_STYLE);  
				break;
			case "OVERLAP":
				graphX.getModel().setStyle(edgeCell, OVERLAP_EDGE_STYLE);  
				break;
			case "MATCH":
				graphX.getModel().setStyle(edgeCell, EDGE_STYLE);
				break;
			}
		}

		graphX.getModel().endUpdate();

		graphX.setCellsMovable(false);
		graphX.setCellsDeletable(false);
		graphX.setCellsEditable(false);
		graphX.setEdgeLabelsMovable(false);
		graphX.setDisconnectOnMove(false);
		graphX.setCellsDisconnectable(false);
		graphX.setCellsLocked(true);
	}

	public LinkedHashMap<Vertex, mxCell> getVertexToCellMap() {
		return vertexToCellMap;
	}

	public LinkedHashMap<mxCell, Vertex> getCellToVertexMap() {
		return cellToVertexMap;
	}

	public LinkedHashMap<SequenceEdge, mxCell> getEdgeToCellMap() {
		return edgeToCellMap;
	}

	public LinkedHashMap<mxCell, SequenceEdge> getCellToEdgeMap() {
		return cellToEdgeMap;
	}
}
