import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileSortGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel center = new JPanel();
	private JButton btnSort = new JButton("Sort");
	private JButton btnLocate = new JButton("Locate");
	private JTextField txtSource = new JTextField();
	
	private String[] formats = new String[] {"Sort by Name", "Sort by Date Modified",
            "Sort by Size", "Sort by Type"};
	private JComboBox<String> comboFormat = new JComboBox<>(formats);	

	public FileSortGui(){
		renderComponents();
	}
	
	public void renderComponents() {
		
		btnSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				File f = new File(txtSource.getText());
		        
		        if(f.exists()){
		            if(f.isDirectory()){
		       
		               String s = txtSource.getText().toString();
		               File dir = new File(s);
	            	   File[] files = dir.listFiles();
	            	   
		               if(comboFormat.getSelectedItem() == "Sort by Name") {
		            	   byName(s,dir,files);
		               }
		               else if(comboFormat.getSelectedItem() == "Sort by Type") {
	
		            	   byType(s,dir,files);
		               }
		               else if(comboFormat.getSelectedItem() == "Sort by Size") {
		            	   bySize(s,dir,files);
		               }
		               else if(comboFormat.getSelectedItem() == "Sort by Date Modified") {
		            	   byDate(s,dir,files);
		               }
		            }
		            else{
			        	JOptionPane.showMessageDialog(null, "Is File");
			        }
		        }
		        else{
		            JOptionPane.showMessageDialog(null, "Please input address properly!");
		        }
			}
			
		});
		
		btnLocate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	
				String choosertitle = null;
				JFileChooser chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(choosertitle);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
   
				if (chooser.showOpenDialog(center) == JFileChooser.APPROVE_OPTION) { 
					txtSource.setText(""+chooser.getCurrentDirectory());
					txtSource.setText(""+chooser.getSelectedFile());
				}
				else {
					JOptionPane.showMessageDialog(null, "Nothing is selected");
				}
			}
			
		});
	
		btnLocate.setBounds(340,60,100,40);
		center.add(btnLocate);
		btnSort.setBounds(220,60,100,40);
		center.add(btnSort);
		txtSource.setBounds(40,10,410,40);
		center.add(txtSource);
		comboFormat.setBounds(50,60,150,40);
		center.add(comboFormat);
		
		center.setLayout(null);
		this.setPreferredSize(new Dimension(500,140));
		this.setMaximumSize(new Dimension(500,140));
		this.setMinimumSize(new Dimension(500,140));
		this.setVisible(true);
		this.setTitle(".....Sorted results are in the console :'D");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(center,BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void byName(String s, File dir, File[] files) {

		Arrays.sort(files, (f1, f2) -> f1.compareTo(f2));

 	   	for (File file : files) {
 		   if (file.isDirectory()) {
 			   System.out.println("DIR \t" + file.getName());
 		   } 
 		   else {
 			   System.out.println("FILE\t" + file.getName());
 		   }
 	   	}
 	   	JOptionPane.showMessageDialog(null, "Sorting by Name Complete!");
	}
	
	public void byType(String s, File dir, File[] files) {
		Arrays.sort(files, (f1, f2) -> {
 		   if (f1.isDirectory() && !f2.isDirectory()) {
 	            return -1;
 		   } 
 		   else if (!f1.isDirectory() && f2.isDirectory()) {
 	            return 1;
 		   } 
 		   else {
 	            return f1.compareTo(f2);
 		   }
 	   });

 	   for (File file : files) {
 		   if (!file.isHidden()) {
 			   if (file.isDirectory()) {
 				   System.out.println("DIR \t" + file.getName());
 			   } 
 			   else {
 				   System.out.println("FILE\t" + file.getName());
 			   }
 		   }
 	   }
 	   JOptionPane.showMessageDialog(null, "Sorting by Type Complete!");
	}
	
	public void bySize(String s, File dir, File[] files) {

		Arrays.sort(files, (f1, f2) -> {
			return new Long(f1.length()).compareTo(new Long(f2.length()));
		});

		for (File file : files) {
			if (!file.isHidden()) {
				if (!file.isDirectory()) {
	               System.out.println("FILE\t" + " " + file.length() + " bytes\t\t" + file.getName());
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Sorting by Size Complete!");
	}
	
	public void byDate(String s, File dir, File[] files) {
		
		Arrays.sort(files, (f1, f2) -> {
	         return new Date(f1.lastModified()).compareTo(new Date(f2.lastModified()));
		});

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		for (File file : files) {
			if (!file.isHidden()) {
	            if (file.isDirectory()) {
	               System.out.println("DIR \t" + " " 
	                     + df.format(new Date(file.lastModified())) + "\t" + file.getName());
	            } 
	            else {
	               System.out.println("FILE\t" + " " 
	                     + df.format(new Date(file.lastModified())) + "\t" + file.getName());
	            }
			}
		}
		JOptionPane.showMessageDialog(null, "Sorting by Date Modified Complete!");
	}
	
	public static void main(String[] args) {
		new FileSortGui();
	}

}
