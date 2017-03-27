import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.lang.*;



public class Delete extends JFrame implements ActionListener
{
	String userName,command="",columnName="",selectedColumnName="",sql="",type="",resultData="";
	Connection conn1;
	String tableName;
	JLabel title,where,input;
	JButton delete,addQuery;
	JTextArea result;
	JComboBox<String> column;
	JTextField jtf;
	Statement st ;
	ResultSet rs;
	int counter=0;
	String[] dataType;
	
	public Delete(String s, Connection conn1, String userName,String tableName)
	{
		super(s);
		this.conn1=conn1;
		this.userName=userName;
		this.tableName=tableName;
		setLayout(new BorderLayout(10,10));
		title= new JLabel("Table = "+tableName , JLabel.CENTER);
		
		where= new JLabel("Where = " , JLabel.CENTER);
		
		input= new JLabel("Input = " , JLabel.CENTER);
		
		delete = new JButton("Delete Data");
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
		
		command = "delete from "+tableName;
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
		
		
		
		
		JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,3,10));
		jp1.add(where);
		jp1.add(column);
		jp1.add(input);
		jp1.add(jtf);
		
		JPanel jp2 = new JPanel(new GridLayout(1,2,0,0));
		
		JPanel jp3 = new JPanel(new GridLayout(2,1,0,0));
		jp3.add(title);
		
		
		//JPanel jp4 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,30));
		
		jp3.add(jp1);
		//jp3.add(jp4);
		
		jp2.add(addQuery);
		jp2.add(delete);
		
		add(jp3,BorderLayout.NORTH);
		add(result,BorderLayout.CENTER);
		add(jp2,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400,400);
		//pack();
		setLocation(450, 150);
		setVisible(true);
		setResizable(false);
	}

	public void actionPerformed(ActionEvent ae)
	{
		
		
		if(ae.getSource() == delete)
		{
			if(resultData.equals(""))
			{
				try
				{
					
				
					int response = JOptionPane.showConfirmDialog(null, "\nEmpty Input Do You Want To delete Data Whole Table", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.NO_OPTION) {
								System.out.println("No button clicked");
									this.dispose();
							} else if (response == JOptionPane.YES_OPTION) {
									System.out.println("Yes button clicked");
									st.executeUpdate(command);
							} else if (response == JOptionPane.CLOSED_OPTION) {
								System.out.println("JOptionPane closed");
								this.dispose();
							}
				}catch(Exception e123)
				{
					JOptionPane.showMessageDialog(this,"Error Table Data not Deleted Succesfuly "+e123);
				}
			}
			else
			{
				try{
					String[] splited =selectedColumnName.split("\\[+");
					if(dataType[counter].equals("INT")||dataType[counter].equals("NUMBER"))
					{
							sql=command+" "+"where "+ splited[0].trim() +" = " ;
							//result.setText(sql + Integer.parseInt(jtf.getText())+" ;");
							st.executeUpdate(sql+ Integer.parseInt(resultData)+" ");
					}
					else
					{
							sql=command+" "+"where "+ splited[0].trim() +" = '" ;
							//result.setText(sql + jtf.getText()+"' ;");
							st.executeUpdate(sql + resultData+"' ");
					}
						JOptionPane.showMessageDialog(this,"Data Deleted Succesfuly");
						this.dispose();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(this,"Error Data not Deleted Succesfuly "+e);
				}
		
			}
		}
		
		if(ae.getSource() == addQuery)
		{
			if(jtf.getText().equals(""))
			{
					JOptionPane.showMessageDialog(this,"Empty Input");
			}
			else
			{
				resultData=jtf.getText();
				String[] splited =selectedColumnName.split("\\[+");
				if(dataType[counter].equals("INT")||dataType[counter].equals("NUMBER"))
				{
						sql=command+" "+"where "+ splited[0].trim() +" = " ;
						result.setText(sql + Integer.parseInt(resultData)+" ;");
				}
				else
				{
						sql=command+" "+"where "+ splited[0].trim() +" = '" ;
						result.setText(sql + resultData+"' ;");
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