import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.lang.*;


public class Select extends JFrame implements ActionListener
{
	String userName;
	Connection conn1;
	String tableName,orderName,data[]={"ASC","DESC"},columnName="",columnNameSelected="";
	JPanel jp1,jp2;
	JLabel title;
	JComboBox<String> order;
	JTextArea result;
	Statement st;
	ResultSet rs;
	int total;
	
	public Select(String s, Connection conn1, String userName,String tableName)
	{
		super(s);
		this.conn1=conn1;
		this.userName=userName;
		this.tableName=tableName;
		
		title= new JLabel("Table = "+tableName , JLabel.CENTER);
		
		order = new JComboBox<>(data);
		order.setSelectedIndex(0);
		orderName = (String)order.getSelectedItem();
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
            
				JComboBox cb = (JComboBox)e.getSource();
				orderName = (String)cb.getSelectedItem();
				showData();
			}
		});
		
		result =new JTextArea(5, 29);
		result.setFont(new Font("Courier", Font.BOLD,20));
		result.setEditable(false);
		result.setBackground(Color.white);
		//result.setLineWrap(true);
		//result.setWrapStyleWord(true);
		
		System.out.println("run");
		
		getColumnName();
		String[] splited = columnName.split("\\++");
		
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,30));
		jp2 = new JPanel(new GridLayout(2,1,10,10));
		
		for(int i=0;i<splited.length;i++)
		{
			addColumn(splited[i],jp1);
		}
			
		jp2.add(jp1);
		jp2.add(order);
		
		add(title,BorderLayout.NORTH);
		add(jp2,BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(result);

		JPanel jp3 = new JPanel();
		jp3.add(scroll);
		
		add(jp3,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(360,430);
		//pack();
		setLocation(450, 150);
		setVisible(true);
		setResizable(false);
	}
	
	public void getColumnName()
	{
		try
		{
			System.out.println("find column name");
				 st=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				DatabaseMetaData metadata = conn1.getMetaData();
				rs = metadata.getColumns(null, userName.toUpperCase(), tableName, null);
				total = 0;
				
				while (rs.next()) {
					columnName += rs.getString("COLUMN_NAME")+"+";
					System.out.println(columnName);
					total++;
				}
				System.out.println(total);
		}catch(Exception e)
		{
			System.out.println(e);
		}
				
	}
	
	public void addColumn(String col,JPanel jp)
	{
		JCheckBox jc = new JCheckBox(col);
		jc.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {             
            
				if(jc.isSelected())
				{
					columnNameSelected +=jc.getText()+"+";
					System.out.println(columnNameSelected);
					showData();
				}
				else
				{
					String[] splited = columnNameSelected.split("\\++");
					columnNameSelected="";
					for(int i=0;i<splited.length;i++)
					{
						//System.out.println(splited[i]+"kkk"+jc.getText());
						if(splited[i].equals(jc.getText()))
						{
							//System.out.println(splited[i]+"hghggdhg"+jc.getText());
							showData();
						}
						else
						{
							columnNameSelected +=splited[i]+"+";
							showData();
						}
						
					}
		
				}				
			}           
		});
	  jp.add(jc);
	}
	
	
	public void showData()
	{
		if(columnNameSelected.equals(""))
		{
			System.out.println("No Colum selected");
			result.setText("");
		}
		else
		{
			String splited[] =columnNameSelected.split("\\++");
			String sql="",s1="",sql1="";
			for(int i=0;i<splited.length;i++)
			{
				sql += splited[i]+",";
				s1 +=	splited[i]+"\t";
				sql1 += splited[i]+" "+orderName+" ,";
			}
			String equ = sql.substring(0, Math.min(sql.length(), sql.length()-1));
			String equ1 = sql1.substring(0, Math.min(sql1.length(), sql1.length()-1));
			result.setText(s1);
			s1+="\n";
			for(int i=0;i<splited.length;i++)
			{
				s1+="----\t";
			}
			s1+="\n";
			try{
			
				if(splited.length == 1)
				{
					rs = st.executeQuery("select "+equ+" from "+tableName+ " ORDER BY "+ equ +" "+orderName);
					System.out.println("select "+equ+" from "+tableName+ " ORDER BY "+ equ +" "+orderName);
				}
				else
				{
					rs = st.executeQuery("select "+equ+" from "+tableName+ " ORDER BY "+ equ1);
					System.out.println("select "+equ+" from "+tableName+ " ORDER BY "+ equ1);
				}
				
			//	System.out.println("select "+equ+" from "+tableName+ " ORDER BY "+ equ +" "+orderName);
				
				while(rs.next())
				{
					for(int i=0;i<splited.length;i++)
					{
						s1 += rs.getString(i+1)+"\t";
					}
					s1+="\n";
				}
				result.setText(s1+"\n");
			}
			catch(Exception ee2)
			{
				System.out.println(ee2+"show table");
			}
			
		}
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		
		
	}
}