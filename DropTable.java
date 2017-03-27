import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.lang.*;

class DropTable extends JFrame implements ActionListener
{
	
	Connection conn1;
	JComboBox<String> table;
	String s,tableName,tableNameDetail="",userName;
	JButton drop;
	boolean flag1 =false;
	
	DropTable(String s,Connection conn1,String userName)
	{
		super(s);
		this.s = s;
		this.conn1 = conn1;
		this.userName=userName;
		getTables(conn1);
		String[] data = tableNameDetail.split("\\++");
		table = new JComboBox<>(data);
		table.setSelectedIndex(0);
		tableName = (String)table.getSelectedItem();
		table.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
            
				JComboBox cb = (JComboBox)e.getSource();
				tableName = (String)cb.getSelectedItem();
			}
		});

		drop = new JButton("Drop Table");
		drop.addActionListener(this);
		
		add(table,BorderLayout.NORTH);
		add(drop,BorderLayout.SOUTH);
		//add(addMoreColumn,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300,300);
		//pack();
		setResizable(false);
		setLocation(450, 150);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == drop)
		{
			 
			int response = JOptionPane.showConfirmDialog(null, "\nTable :- "+tableName+" Is going to Deleted ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.NO_OPTION) {
								System.out.println("No button clicked");
									flag1=false;
							} else if (response == JOptionPane.YES_OPTION) {
									System.out.println("Yes button clicked");
									flag1=true;
							} else if (response == JOptionPane.CLOSED_OPTION) {
								System.out.println("JOptionPane closed");
								flag1=false;
							}
							if(flag1)
							{
								try{
										Statement stmt = conn1.createStatement();
      
										String sql = "DROP TABLE "+tableName;
										System.out.println(tableName);
										stmt.executeUpdate(sql);
										this.dispose();
								}catch(Exception e)
								{
									JOptionPane.showMessageDialog(this,"Some error Occured "+e);
									this.dispose();
								}
							}
		}
	}
	
	
	public void getTables(Connection c)
	{
			
		try{
		
			DatabaseMetaData dbmd = c.getMetaData();
			String[] types = {"TABLE"};
			ResultSet tables = dbmd.getTables(null, userName.toUpperCase(), null, types);
		
			while (tables.next()) 
			{
				tableNameDetail += tables.getString(3)+"+";				
			}
		}catch(Exception e){
					 JOptionPane.showMessageDialog(this,"Can't Connect To Database \n Try again."+e.toString(),"Error In Connecting",JOptionPane.ERROR_MESSAGE);
		}
	}
}