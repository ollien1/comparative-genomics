package visualisation;

import graph.AbstractVertex;
import graph.GraphBuilder;
import graph.QueryVertex;
import graph.SequenceEdge;
import graph.SubjectVertex;
import graph.Vertex;
import isomorphism.IsomorphismInspector;
import isomorphism.MotifType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jgrapht.graph.ListenableDirectedGraph;

import sun.font.CreatedFontTracker;
import utils.BlastParser;
import utils.Match;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphSelectionModel;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		FileSelector fileSelector = new FileSelector(null);
		fileSelector.setLocationRelativeTo(null);
		fileSelector.setVisible(true);
	}
	
	protected static void init(String file, int minLength) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1280, 720));

		JPanel panel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		long time = System.currentTimeMillis();
		
		ArrayList<Match> blastData = null;
		try
		{
			blastData = BlastParser.parse(file, minLength);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		GraphBuilder builder = new GraphBuilder(blastData);
		System.out.println(System.currentTimeMillis() - time);
		time = System.currentTimeMillis();
		
		GraphVisualiser visualiser = new GraphVisualiser(new mxGraph(), builder);
		System.out.println(System.currentTimeMillis() - time);

		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(300, 200));
		textArea.setBorder(new LineBorder(Color.BLACK));
		textArea.setEditable(false);

		visualiser.getGraphX().getSelectionModel().addListener(mxEvent.CHANGE, new mxIEventListener()
		{
			@Override
			public void invoke(Object sender, mxEventObject event) {
				if (sender instanceof mxGraphSelectionModel) {
					Object cell = ((mxGraphSelectionModel)sender).getCell();
					textArea.setText(visualiser.getCellToVertexMap().get(cell).displayInfo());
				}
			}
		});

		IsomorphismInspector inspector = new IsomorphismInspector(builder.getGraph());

		OutputPanel outputPanel = new OutputPanel(inspector.inspectAll(), visualiser);

		c.gridx = 0;
		c.insets = new Insets(10, 10, 10, 10);
		bottomPanel.add(textArea);
		
		c.gridx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		bottomPanel.add(outputPanel);

		panel.add(visualiser, BorderLayout.NORTH);
		panel.add(bottomPanel, BorderLayout.SOUTH);

		frame.setJMenuBar(createMenuBar(visualiser));

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	private static JMenuBar createMenuBar(GraphVisualiser visualiser) {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem newAlignmentItem = new JMenuItem("New alignment");
		
		newAlignmentItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileSelector fileSelector = new FileSelector(null);
				fileSelector.setLocationRelativeTo(null);
				fileSelector.setVisible(true);
			}
		});
		
		fileMenu.add(newAlignmentItem);

		JMenu viewMenu = new JMenu("View");
		JMenuItem jumpToPosInSub = new JMenuItem("Jump to sequence position in subject");

		jumpToPosInSub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int pos = Integer.parseInt(JOptionPane.showInputDialog("Enter sequence position", ""));
				VisualisationUtils.scrollToPos(pos, SubjectVertex.class, visualiser);
			}
		});

		JMenuItem jumpToPosInQuery = new JMenuItem("Jump to sequence position in query");

		jumpToPosInQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int pos = Integer.parseInt(JOptionPane.showInputDialog("Enter sequence position", ""));
				VisualisationUtils.scrollToPos(pos, QueryVertex.class, visualiser);
			}
		});

		viewMenu.add(jumpToPosInSub);
		viewMenu.add(jumpToPosInQuery);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		return menuBar;
	}
}
