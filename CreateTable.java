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

class CreateTable extends JFrame implements ActionListener
{
	JTextField tableName,columnName,size;
	JTextArea result;
	JButton create,addMoreColumn;
	JLabel tN,cN,dT,sZ; 
	JPanel jp1,jp2,jp3;
	String data[] ={"int","varchar"};
	String dataTypeName="",s,tableNameS="",columnNameS="",sizeS="";
	String sql="",command="";
	Connection conn1;
	JComboBox<String> dataType;
	
	public CreateTable(String s,Connection conn1)
	{
		super(s);
		this.s = s;
		this.conn1 = conn1;
		dataType = new JComboBox<>(data);
		dataType.setSelectedIndex(0);
		dataTypeName = (String)dataType.getSelectedItem();
		dataType.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {     
            
			JComboBox cb = (JComboBox)e.getSource();
			dataTypeName = (String)cb.getSelectedItem();
			if(dataTypeName!="int")
			{
				size.setEditable(true);

			}
			else{
				size.setEditable(false);
			}
         }
      }); 
		
		
		tN=new JLabel("Table Name", JLabel.CENTER); 
		cN=new JLabel("Column Name", JLabel.CENTER); 
		dT=new JLabel("Data Type", JLabel.CENTER); 
		sZ=new JLabel("Size", JLabel.CENTER); 
		
		
		create = new JButton("Create Table");
		create.addActionListener(this);
		
		addMoreColumn = new JButton("Add Column");
		addMoreColumn.addActionListener(this);
		
		tableName =new  JTextField(10);
		tableName.setFont(new Font("Courier", Font.BOLD,20));
		
		columnName =new JTextField(10);
		columnName.setFont(new Font("Courier", Font.BOLD,20));
		
		size =new JTextField(10);
		size.setFont(new Font("Courier", Font.BOLD,20));
		size.setEditable(false);
		
		result =new JTextArea(5, 10);
		result.setFont(new Font("Courier", Font.BOLD,20));
		result.setEditable(false);
		result.setBackground(Color.white);
		result.setLineWrap(true);
		result.setWrapStyleWord(true);

		
		jp1 = new JPanel();
		jp1.setLayout(new GridLayout(4,2,10,10));
		
		jp2 = new JPanel();
		//jp2.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		jp2.setLayout(new BorderLayout());
		
		jp3 = new JPanel();
		jp3.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		
		jp1.add(tN);
		jp1.add(tableName);
		jp1.add(cN);
		jp1.add(columnName);
		jp1.add(dT);
		jp1.add(dataType);
		jp1.add(sZ);
		jp1.add(size);
		
		jp3.add(create);
		jp3.add(addMoreColumn);
		jp2.add(jp3,BorderLayout.NORTH);
		jp2.add(result,BorderLayout.SOUTH);
		
		add(jp1,BorderLayout.CENTER);
		add(jp2,BorderLayout.SOUTH);
		//add(addMoreColumn,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,500);
		//pack();
		setResizable(false);
		setLocation(450, 150);
		setVisible(true);
	}
		
		
		
	public void actionPerformed(ActionEvent ae)
	{
		tableNameS = tableName.getText();
		columnNameS = columnName.getText();
		
		
		if(dataTypeName!="int")
		{
			sizeS = size.getText();
		}
		
		
		if(ae.getSource() == create)
		{
		
				if(tableNameS!=""||columnNameS!="")
				{
					try
					{
							System.out.println(command);
							Statement st=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
							st.executeUpdate(command);
							JOptionPane.showMessageDialog(this, "Success! Table Created.");
							this.dispose();
					}
					catch(Exception e2)
					{
						JOptionPane.showMessageDialog(null,"Error In Creating Table  \n ."+e2.toString()+"\n","Error ",JOptionPane.OK_CANCEL_OPTION);
					}
				
				}			
			 
		}
		if(ae.getSource() == addMoreColumn)
		{
			System.out.println(tableNameS+columnNameS+sizeS);
			if(dataTypeName!="int")
			{
				sizeS = size.getText();
				
				if(tableNameS.equals("")||columnNameS.equals("")||sizeS.equals(""))
				{
					JOptionPane.showMessageDialog(this,"Fill All Detail.");
				}
				else
				{
					intilise();
					sql +=columnNameS+" "+dataTypeName+"("+sizeS+")"+" ";
					sql +=",";
					System.out.println(sql);
					String equ = sql.substring(0, Math.min(sql.length(), sql.length()-1));
					command = "create table "+tableNameS+ "  ("+equ+")";
					result.setText(command+";");
				}
			}
			else
			{
				if(tableNameS.equals("")||columnNameS.equals(""))
				{
					JOptionPane.showMessageDialog(this,"Fill All Detail.");
				}
				else
				{
					intilise();
					sql += columnNameS+" "+dataTypeName+" ";
					sql +=",";
					System.out.println(sql);
					String equ = sql.substring(0, Math.min(sql.length(), sql.length()-1));
					command = "create table "+tableNameS+ "  ("+equ+")";
					result.setText(command+";");
				}
			}
			
			if(dataTypeName!="int")
			{
				dataType.setSelectedIndex(0);
			}
			 
		}
	}
	public void intilise()
	{
		tableName.setEditable(false);
		columnName.setText("");
		size.setText("");		
		size.setEditable(false);
		
	}
}