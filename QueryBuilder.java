import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.lang.*;



public class QueryBuilder extends JFrame implements ActionListener
{
	Connection conn1 ;
	JButton select, insert, update , delete;
	JPanel jp1,jp2;
	String s,userName,tableNameDetail="",tableName;
	JComboBox<String> table;
	JLabel st;
	
	public QueryBuilder(String s,Connection conn1,String userName)
	{
		super(s);
		this.conn1=conn1;
		this.s=s;
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
		st=new JLabel("Select Table", JLabel.CENTER); 
		
		select = new JButton("Select");
		select.setPreferredSize(new Dimension(150, 150)); 
		select.addActionListener(this);
		
		insert = new JButton("Insert");
		insert.setPreferredSize(new Dimension(150, 150)); 
		insert.addActionListener(this);
		
		update = new JButton("Update");
		update.setPreferredSize(new Dimension(150, 150)); 
		update.addActionListener(this);
		
		delete = new JButton("Delete");
		delete.setPreferredSize(new Dimension(150, 150)); 
		delete.addActionListener(this);
		
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,30));
		
		//jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,20));
		jp2=new JPanel();
		jp2.setLayout(new GridLayout(1,2,0,10));
		jp1.add(select);
		jp1.add(insert);
		jp1.add(update);
		jp1.add(delete);
		
		jp2.add(st);
		jp2.add(table);
		
		add(jp1,BorderLayout.CENTER);
		add(jp2,BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(360,430);
		//pack();
		setLocation(450, 150);
		setVisible(true);
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getSource() == select)
		{
			System.out.println(tableName);
			new Select(s,conn1,userName,tableName);
		}
		else if(ae.getSource() == insert)
		{
			System.out.println(tableName);
			new Insert(s,conn1,userName,tableName);
			//QueryBulder(s,conn,userName);
		}
		else if(ae.getSource() == update)
		{
			System.out.println(tableName);
			new Update(s,conn1,userName,tableName);
			//new CreateTable(s,conn);
		}
		else if(ae.getSource() == delete)
		{
			System.out.println(tableName);
			new Delete(s,conn1,userName,tableName);
			//new DropTable(s,conn,userName);
		}
		this.dispose();
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