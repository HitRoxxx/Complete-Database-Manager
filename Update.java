import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.lang.*;



public class Update extends JFrame implements ActionListener
{
String userName,command="",columnName="",selectedColumnName="",sql="",type="",resultData="",selectedColumnName1="",resultData1="";
	Connection conn1;
	String tableName;
	JLabel title,set,input1,where,input;
	JButton delete,addQuery;
	JTextArea result;
	JComboBox<String> column,column1;
	JTextField jtf,jtf1;
	Statement st ;
	ResultSet rs;
	int counter=0,counter1=0;
	String[] dataType;
	
	public Update(String s, Connection conn1, String userName,String tableName)
	{
		super(s);
		this.conn1=conn1;
		this.userName=userName;
		this.tableName=tableName;
		setLayout(new BorderLayout(10,10));
		title= new JLabel("Table = "+tableName , JLabel.CENTER);
		
		where= new JLabel("Where = " , JLabel.CENTER);
		
		input= new JLabel("Input = " , JLabel.CENTER);
		
		set= new JLabel("Set = " , JLabel.CENTER);
		
		input1= new JLabel("Input = " , JLabel.CENTER);
		
		
		delete = new JButton("Update Data");
		delete.addActionListener(this);
		
		addQuery = new JButton("Add Query");
		addQuery.addActionListener(this);
		
		result =new JTextArea(5, 30);
		result.setFont(new Font("Courier", Font.BOLD,20));
		result.setEditable(false);
		result.setBackground(Color.white);
		result.setLineWrap(true);
		result.setWrapStyleWord(true);
		
		jtf =new JTextField(10);
		jtf.setFont(new Font("Courier", Font.BOLD,20));
		jtf.setText("");
		
		jtf1 =new JTextField(10);
		jtf1.setFont(new Font("Courier", Font.BOLD,20));
		jtf1.setText("");
		
		command = "UPDATE "+tableName;
		result.setText(command+";");
		
		getColumnName();
		
		String[] data = columnName.split("\\++");
		dataType = type.split("\\++");
		String[] dataCombo = new String[data.length];
		for(int i = 0 ; i<data.length;i++)
		{
			dataCombo[i]=data[i] +"[ "+dataType[i]+" ]";
		}
		column = new JComboBox<>(dataCombo);
		column.setSelectedIndex(0);
		selectedColumnName = (String)column.getSelectedItem();
		column.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
            
				JComboBox cb = (JComboBox)e.getSource();
				selectedColumnName = (String)cb.getSelectedItem();
				counter = cb.getSelectedIndex();
				jtf.setText("");
			}
		});
		
		column1 = new JComboBox<>(dataCombo);
		column1.setSelectedIndex(0);
		selectedColumnName1 = (String)column1.getSelectedItem();
		column1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
            
				JComboBox cb = (JComboBox)e.getSource();
				selectedColumnName1 = (String)cb.getSelectedItem();
				counter1 = cb.getSelectedIndex();
				jtf1.setText("");
			}
		});
		
		
		JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,3,10));
		
		jp1.add(set);
		jp1.add(column1);
		jp1.add(input1);
		jp1.add(jtf1);
		
		JPanel jp5 = new JPanel(new FlowLayout(FlowLayout.LEFT,3,10));
		
		jp5.add(where);
		jp5.add(column);
		jp5.add(input);
		jp5.add(jtf);
		
		
		
		JPanel jp2 = new JPanel(new GridLayout(1,2,0,0));
		
		JPanel jp3 = new JPanel(new GridLayout(3,1,0,0));
		jp3.add(title);
		
		
		//JPanel jp4 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,30));
		
		jp3.add(jp1);
		jp3.add(jp5);
		
		jp2.add(addQuery);
		jp2.add(delete);
		
		add(jp3,BorderLayout.NORTH);
		add(result,BorderLayout.CENTER);
		add(jp2,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450,450);
		//pack();
		setLocation(450, 150);
		setVisible(true);
		setResizable(false);
	}

	public void actionPerformed(ActionEvent ae)
	{
		
		
		if(ae.getSource() == delete)
		{
			if(resultData.equals("")||resultData1.equals(""))
			{
								
					JOptionPane.showMessageDialog(this,"Add Query To Update");
					
			}
			else
			{
				try{
					String[] splited =selectedColumnName.split("\\[+");
					String[] splited1 =selectedColumnName1.split("\\[+");
					if(dataType[counter1].equals("INT")||dataType[counter1].equals("NUMBER"))
					{
						if(dataType[counter].equals("INT")||dataType[counter].equals("NUMBER"))
						{
							sql=command+" "+"set "+ splited1[0].trim() +" = " ;
							//result.setText(sql+ Integer.parseInt(resultData1) + " where "+ splited[0].trim() +" = "+Integer.parseInt(resultData)+" ;");
							
							st.executeUpdate(sql+ Integer.parseInt(resultData1) + " where "+ splited[0].trim() +" = "+Integer.parseInt(resultData));
						}
						else
						{
							sql=command+" "+"set "+ splited1[0].trim() +" = " ;
							result.setText(sql+ Integer.parseInt(resultData1) + " where "+ splited[0].trim() +" = '"+resultData+"' ;");
							st.executeUpdate(sql+ Integer.parseInt(resultData1) + " where "+ splited[0].trim() +" = '"+resultData+"'");
						}
						
					}
					else
					{
							if(dataType[counter].equals("INT")||dataType[counter].equals("NUMBER"))
							{
								sql=command+" "+"set "+ splited1[0].trim() +" = '" ;
								//result.setText(sql+ resultData1 + "' where "+ splited[0].trim() +" = "+Integer.parseInt(resultData)+" ;");
								st.executeUpdate(sql+ resultData1 + "' where "+ splited[0].trim() +" = "+Integer.parseInt(resultData));
							}
							else
							{
								sql=command+" "+"set "+ splited1[0].trim() +" = '" ;
							//	result.setText(sql+ resultData1 + "' where "+ splited[0].trim() +" = '"+resultData+"' ;");
								st.executeUpdate(sql+ resultData1 + "' where "+ splited[0].trim() +" = '"+resultData+"'");
							}
					}
					JOptionPane.showMessageDialog(this," Data  Updated Succesfuly ");
					this.dispose();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(this,"Error Data not Updated Succesfuly "+e);
				}
		
			}
		}
		
		if(ae.getSource() == addQuery)
		{
			if(jtf.getText().equals("")||jtf1.getText().equals(""))
			{
					JOptionPane.showMessageDialog(this,"Empty Input");
			}
			else
			{
				resultData=jtf.getText();
				resultData1=jtf1.getText();
				resultData1=jtf1.getText();
				String[] splited =selectedColumnName.split("\\[+");
				String[] splited1 =selectedColumnName1.split("\\[+");
				if(dataType[counter1].equals("INT")||dataType[counter1].equals("NUMBER"))
				{
					if(dataType[counter].equals("INT")||dataType[counter].equals("NUMBER"))
					{
						sql=command+" "+"set "+ splited1[0].trim() +" = " ;
						result.setText(sql+ Integer.parseInt(resultData1) + " where "+ splited[0].trim() +" = "+Integer.parseInt(resultData)+" ;");
					}
					else
					{
							sql=command+" "+"set "+ splited1[0].trim() +" = " ;
						result.setText(sql+ Integer.parseInt(resultData1) + " where "+ splited[0].trim() +" = '"+resultData+"' ;");
					}
						
				}
				else
				{
						if(dataType[counter].equals("INT")||dataType[counter].equals("NUMBER"))
						{
							sql=command+" "+"set "+ splited1[0].trim() +" = '" ;
							result.setText(sql+ resultData1 + "' where "+ splited[0].trim() +" = "+Integer.parseInt(resultData)+" ;");
						}
						else
						{
							sql=command+" "+"set "+ splited1[0].trim() +" = '" ;
							result.setText(sql+ resultData1 + "' where "+ splited[0].trim() +" = '"+resultData+"' ;");
						}
				}
			}
		
		}
		
	}
	
	public void getColumnName()
	{
		try
		{
			System.out.println("find column name");
				st=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				DatabaseMetaData metadata = conn1.getMetaData();
				rs = metadata.getColumns(null, userName.toUpperCase(), tableName, null);
				
				while (rs.next()) {
					columnName += rs.getString("COLUMN_NAME")+"+";
					type += rs.getString("TYPE_NAME")+"+";
				//	System.out.println(type);
				}
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
				
	}
}