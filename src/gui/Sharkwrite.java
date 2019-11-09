package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
public class Sharkwrite extends JFrame {
    
	private String indexie;
	private JPanel contentPane;
	private File selFile;
	private String path;
	private boolean tryitrel=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sharkwrite frame = new Sharkwrite();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Sharkwrite() {
		setTitle("Sharkwrite");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][20][grow]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0 1 2,grow");
		panel.setLayout(new MigLayout("", "[grow][grow][60]", "[grow]"));
		
		JButton btnSave = new JButton("Save");
		panel.add(btnSave, "cell 0 0,grow");
		
		JButton btnOpen = new JButton("Open");
		
		panel.add(btnOpen, "cell 1 0,grow");
		
		JButton btnLicense = new JButton("LICENSE");
		btnLicense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "GOTO https://tinyurl.com/SharkLicense to see the License");
			}
		});
		panel.add(btnLicense, "cell 2 0,grow");
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 2 1 4,grow");
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc= new JFileChooser();
				int status=fc.showOpenDialog(null);
				if(status==JFileChooser.APPROVE_OPTION) {
					selFile =fc.getSelectedFile();
                    //textPane
                    if(selFile.exists()) {
                    	String filest;
                    	path=selFile.getPath();
                    	tryitrel=true;
                    	indexie="";
                    	try(BufferedReader in= new BufferedReader(new FileReader(selFile))){
                    		while((filest=in.readLine())!=null) {
                    			indexie+=filest+"\n";
                    		}
                    		setTitle("Sharkwrite - "+selFile.getName());
                    		textPane.setText(indexie);
                    	}catch(IOException ex) {
                    		ex.printStackTrace();
                    	}
                    }else {
                    	JOptionPane.showMessageDialog(null, "We can't find your file");
                    }
				}
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indexie=textPane.getText();
				if(tryitrel) {
					try(BufferedWriter out = new BufferedWriter(new FileWriter(selFile))){
						out.write(indexie);
						out.newLine();
						}catch(IOException ex) {
						ex.printStackTrace();}
				}else {
					JFileChooser fc= new JFileChooser();
					int status=fc.showOpenDialog(null);
					if(status==JFileChooser.APPROVE_OPTION) {
						selFile =fc.getSelectedFile();
						path=selFile.getPath();
						if(!selFile.exists()) {
							path=selFile.getPath();
	                    	tryitrel=true;
	                    	try(BufferedWriter out = new BufferedWriter(new FileWriter(selFile))){
	    						out.write(indexie);
	    						out.newLine();
	    						}catch(IOException ex) {
	    						ex.printStackTrace();
	    						}
						}else{
							path+=".shark-no-overwrite";
							selFile=new File(path);
							tryitrel=true;
							JOptionPane.showMessageDialog(null, "Your file exist. We overwrite the old file not, your file is now saved under "+path);
	                    	try(BufferedWriter out = new BufferedWriter(new FileWriter(selFile))){
	    						out.write(indexie);
	    						out.newLine();
	    						}catch(IOException ex) {
	    						ex.printStackTrace();}
						}
						setTitle("Sharkwrite - "+selFile.getName());
					}
				}
			}});
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, panel, btnSave, btnOpen, scrollPane}));
	}
}
