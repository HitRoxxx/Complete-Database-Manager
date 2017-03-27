import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.lang.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class Insert extends JFrame implements ActionListener
{
	String userName;
	Connection conn1;
	String tableName,orderName,data[]={"ASC","DESC"},columnName="",columnNameSelected="",sql="",type="",cName="";
	String[] resultSplited;
	JPanel jp1,jp2;
	JLabel title;
	Statement st;
	ResultSet rs;
	JButton insert;
	boolean flag = true;
	int counter[],cData[];
	int conter2=0,counter3;
	String[] splited;
	
	public Insert(String s, Connection conn1, String userName,String tableName)
	{
		super(s);
		this.conn1=conn1;
		this.userName=userName;
		this.tableName=tableName;
		JOptionPane.showMessageDialog(this,"Please First Insert Data Serial wise one by one Then Edit data\n If not Done Wrong data Will Input");
		
		title= new JLabel("Table = "+tableName , JLabel.CENTER);
		
		insert = new JButton("Insert Data");
		insert.addActionListener(this);
		
	//	result =new JTextArea(5, 10);
		//result.setFont(new Font("Courier", Font.BOLD,20));
		//result.setEditable(false);
	//	result.setBackground(Color.white);
		//result.setLineWrap(true);
		//result.setWrapStyleWord(true);
		
		System.out.println("run");
		
		getColumnName();
		splited = columnName.split("\\++");
		String[] typeSplited = type.split("\\++");
		cData = new int[splited.length];
		
		resultSplited = new String[splited.length];
		counter = new int[splited.length];
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,30));
		jp2 = new JPanel(new GridLayout(splited.length,1,10,10));
		
		for(int i=0;i<splited.length;i++)
		{
			addColumn(typeSplited[i],splited[i],jp2);
			counter[i]=i;
			resultSplited[i]="";
			
			cName +="?,";
			if(typeSplited[i].equals("INT"))
			{
				cData[i]=1;
			}
			else
			{
				cData[i]=2;
			}
		}
			
		
		add(title,BorderLayout.NORTH);
		//add(jp1,BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(jp2);
		//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel jp3 = new JPanel(new GridLayout());
		jp3.add(scroll);
		
		add(jp3,BorderLayout.CENTER);
		add(insert,BorderLayout.SOUTH);
		
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
				
				while (rs.next()) {
					columnName += rs.getString("COLUMN_NAME")+"+";
					type += rs.getString("TYPE_NAME")+"+";
					System.out.println(type);
				}
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
				
	}
	
	public void addColumn(String typ,String col,JPanel jp)
	{
		JLabel jc = new JLabel(col+"[ "+typ +" ]");
		JTextField jtf =new JTextField(10);
		jtf.setFont(new Font("Courier", Font.BOLD,20));
		jtf.addFocusListener(new FocusListener() {
			String str ="";
			public void focusGained(FocusEvent e) {
				str = jtf.getText();
				for(int i=0;i<splited.length;i++)
				{
					if(resultSplited[i].equals(str))
					{
						counter3 =i;
					}
				}
			}

			public void focusLost(FocusEvent e) {
				
				if(flag)
				{
					resultSplited[counter[conter2++]] = jtf.getText();
				}
				else
				{
						resultSplited[counter3] = jtf.getText();
				}
				if(conter2 == (splited.length-1))
				{
					flag = false;
				}
				
			}	
			
		});
		
		JPanel jp10=  new JPanel(new GridLayout(1,2,10,10));
		jp10.add(jc);
		jp10.add(jtf);
		
		
		jp.add(jp10);
	}
	
	

	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getSource() == insert)
		{
			try{
				String val = cName.substring(0, Math.min(cName.length(), cName.length()-1));
				PreparedStatement ps = conn1.prepareStatement("insert into "+tableName+" values("+val+")");
				for(int i=0;i<splited.length;i++)
				{
						System.out.print(resultSplited[i]+" ");
						if(cData[i]==1)
						{
							ps.setInt(i+1,Integer.parseInt(resultSplited[i]));
						}
						else
						{
							ps.setString(i+1,resultSplited[i]);
						}
				}
			
				ps.executeUpdate();
				ps.close();
				
				JOptionPane.showMessageDialog(this,"Data inserted Succesfuly");
			}catch(Exception ee3)
			{
				System.out.println(ee3+"insert error");
			}
			
		}
		this.dispose();
		
		
	}
}