import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainWindow extends JFrame implements ActionListener
{
	Connection conn ;
	JButton migration, queryBuilder, createNewTable , dropTable;
	JPanel jp1;
	String s,userName;
	public MainWindow(Connection conn,String s,String userName)
	{
		super(s);
		this.conn=conn;
		this.s=s;
		this.userName=userName;
		migration = new JButton("Import From Other Database");
		migration.setPreferredSize(new Dimension(200, 200)); 
		migration.addActionListener(this);
		
		queryBuilder = new JButton("Query Builder");
		queryBuilder.setPreferredSize(new Dimension(200, 200)); 
		queryBuilder.addActionListener(this);
		
		createNewTable = new JButton("create New Table");
		createNewTable.setPreferredSize(new Dimension(200, 200)); 
		createNewTable.addActionListener(this);
		
		dropTable = new JButton("Drop Table");
		dropTable.setPreferredSize(new Dimension(200, 200)); 
		dropTable.addActionListener(this);
		
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,30));
		jp1.add(migration);
		jp1.add(queryBuilder);
		jp1.add(createNewTable);
		jp1.add(dropTable);
		
		
		add(jp1,BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,510);
		//pack();
		setLocation(450, 150);
		setVisible(true);
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getSource() == migration)
		{
			new LoginMigration(s,conn);
		}
		else if(ae.getSource() == queryBuilder)
		{
			new QueryBuilder(s,conn,userName);
		}
		else if(ae.getSource() == createNewTable)
		{
			new CreateTable(s,conn);
		}
		else if(ae.getSource() == dropTable)
		{
			new DropTable(s,conn,userName);
		}
	}
}