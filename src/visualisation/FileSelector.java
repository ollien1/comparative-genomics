package visualisation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelector extends JDialog {

	private JTextField filePath;
	private JButton browseButton;
	
	private JCheckBox minLengthCheckBox;
	private JTextField minLengthTF;
	
	private JButton okButton;
	private JButton cancelButton;
	
	public FileSelector(JFrame owner) {
		
		super(owner, "Select alignment data", true);
		
		filePath = new JTextField();
		browseButton = new JButton("Browse");
		
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("blast", "blast", "txt"));
				int returnVal = fc.showOpenDialog(FileSelector.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePath.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		minLengthCheckBox = new JCheckBox("Set minimum match length");
		minLengthTF = new JTextField();
		
		minLengthTF.setEnabled(false);
		
		minLengthCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					minLengthTF.setEnabled(true);
				} else {
					minLengthTF.setEnabled(false);
				}
			}
		});
		
		okButton = new JButton("OK");
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String file = filePath.getText();
				int minLength = 0;
				
				if (minLengthCheckBox.isSelected()) {
					minLength = Integer.parseInt(minLengthTF.getText());
				}
				
				Main.init(file, minLength);
				dispose();
			}
		});
		
		cancelButton = new JButton("Cancel");
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		add(filePath);
		add(browseButton);
		
		add(minLengthCheckBox);
		add(minLengthTF);
		
		add(okButton);
		add(cancelButton);
		
		setLayout(new GridLayout(3, 2, 20, 20));
		pack();
	}
}
