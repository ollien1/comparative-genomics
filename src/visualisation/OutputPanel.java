package visualisation;

import isomorphism.MotifType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import graph.SequenceEdge;
import graph.Vertex;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jgrapht.graph.ListenableDirectedGraph;

import com.mxgraph.swing.mxGraphComponent;

public class OutputPanel extends JPanel {

	private GraphVisualiser visualiser;
	
	private JTabbedPane tabbedPane;
	
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> matchList;
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> variationList;
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> insertionList;
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> deletionList;	
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> invInSubList;
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> invInQueryList;
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> dupInSubList;
	private JList<ListenableDirectedGraph<Vertex, SequenceEdge>> dupInQueryList;
	
	private JScrollPane matchScroll;
	private JScrollPane varScroll;
	private JScrollPane insScroll;
	private JScrollPane delScroll;
	private JScrollPane invSubScroll;
	private JScrollPane invQuScroll;
	private JScrollPane dupSubScroll;
	private JScrollPane dupQuScroll;
	
	public OutputPanel(HashMap<MotifType, ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>>> subgraphs, GraphVisualiser visualiser) {
		
		this.visualiser = visualiser;
		
		tabbedPane = new JTabbedPane();
		
//		matchList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		variationList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		insertionList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		deletionList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();		
		invInSubList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		invInQueryList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		dupInSubList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		dupInQueryList = new JList<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		
//		matchScroll = new JScrollPane(matchList);
		varScroll = new JScrollPane(variationList);
		insScroll = new JScrollPane(insertionList);
		delScroll = new JScrollPane(deletionList);
		invSubScroll = new JScrollPane(invInSubList);
		invQuScroll = new JScrollPane(invInQueryList);
		dupSubScroll = new JScrollPane(dupInSubList);
		dupQuScroll = new JScrollPane(dupInQueryList);

//		tabbedPane.addTab("Matches", matchScroll);
		tabbedPane.addTab("Variations", varScroll);
		tabbedPane.addTab("Insertions", insScroll);
		tabbedPane.addTab("Deletions", delScroll);
		tabbedPane.addTab("Inversions in Subject", invSubScroll);
		tabbedPane.addTab("Inversions in Query", invQuScroll);
		tabbedPane.addTab("Duplications in Subject", dupSubScroll);
		tabbedPane.addTab("Duplications in Query", dupQuScroll);
		
//		populateList(matchList, subgraphs.get(MotifType.MATCH), visualiser);
		populateList(variationList, subgraphs.get(MotifType.VARIATION), visualiser);
		populateList(insertionList, subgraphs.get(MotifType.INSERTION), visualiser);
		populateList(deletionList, subgraphs.get(MotifType.DELETION), visualiser);
		populateList(invInSubList, subgraphs.get(MotifType.INV_IN_SUB), visualiser);
		populateList(invInQueryList, subgraphs.get(MotifType.INV_IN_QUERY), visualiser);
		populateList(dupInSubList, subgraphs.get(MotifType.DUP_IN_SUB), visualiser);
		populateList(dupInQueryList, subgraphs.get(MotifType.DUP_IN_QUERY), visualiser);
		
		this.add(tabbedPane);
	}
	
	private void populateList(JList<ListenableDirectedGraph<Vertex, SequenceEdge>> list, ArrayList<ListenableDirectedGraph<Vertex, SequenceEdge>> subgraphs, GraphVisualiser visualiser) {
		
		System.out.println(subgraphs.size());
		
		DefaultListModel<ListenableDirectedGraph<Vertex, SequenceEdge>> listModel = new DefaultListModel<ListenableDirectedGraph<Vertex,SequenceEdge>>();
		
		for (ListenableDirectedGraph<Vertex, SequenceEdge> graph : subgraphs) {
			listModel.addElement(graph);
		}
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				ListenableDirectedGraph<Vertex, SequenceEdge> graph = list.getSelectedValue();
				VisualisationUtils.highlightMotif(graph, visualiser);
			}
		});
		
		list.setModel(listModel);
		list.setFixedCellWidth(800);
	}
}
