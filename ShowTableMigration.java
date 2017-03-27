import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.lang.*;



public class ShowTableMigration extends JFrame implements ActionListener
{
	Connection conn1,conn2 ;
	JPanel jp1;
	String s,table,s2;
	JButton transfer;
	static String tableNameSelected="";

	
	public ShowTableMigration(Connection conn1,Connection conn2,String s ,String table ,String s2)
	{
		super(s);
		this.conn1=conn1;
		this.conn2=conn2;
		this.s=s;
		this.table=table;
		this.s2=s2;
	
		
		System.out.println(table);
		String[] splited = table.split("\\++");
		
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,30));
		
		transfer = new JButton("Transfer");
		transfer.addActionListener(this);
		
		for(int i=0;i<splited.length;i++)
		{
			ShowTableMigration.tableNameSelected="";
			Display drive = new Display(splited[i],jp1);
			drive.start();
			try
			{
				drive.join();
			}
			catch(Exception eee){}
		}
			
		add(jp1,BorderLayout.CENTER);
		add(transfer,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,500);
		setLocation(450, 150);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == transfer)
		{
			if(tableNameSelected.equals(""))
			{
				JOptionPane.showMessageDialog(this,"NO Table Selected.","NO Data",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				String[] splited = tableNameSelected.split("\\++");
				for(int i=0;i<splited.length;i++)
				{
					CreateTable(splited[i],conn1,conn2,s2);
					
				}
				this.dispose();
			}
		}
	}
	
	public void CreateTable(String tableName , Connection conn1,Connection conn2 ,String s2)
	{
		boolean flag1 = true;
		if(LoginMigration.databaseName.equals("ORACLE"))
		{
			Statement st = null;
			ResultSet rs = null;
			try
			{
				String sql ="",cName;
				int cData[],counter=0;
				st=conn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				DatabaseMetaData metadata = conn1.getMetaData();
				rs = metadata.getColumns(null, s2.toUpperCase(), tableName, null);
				rs.last(); 
				int total = rs.getRow();
				rs.beforeFirst();
				System.out.println(total);
				
				cData = new int[total];
				cName="";
				for(int i= 0;i<total;i++)
				{
					cName +="?,";
				}
				int flag =0;
				while (rs.next()) {
					String name = rs.getString("COLUMN_NAME");
					String type = rs.getString("TYPE_NAME");
					int size = rs.getInt("COLUMN_SIZE");
		
					System.out.println("Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
					if(type.equals("INT"))
					{
						sql += name+" "+"INT"+" ";
					}
					else
					{
						sql +=name+" "+type+"("+size+")"+" ";
					}
					sql +=",";
				}
				
				String equ = sql.substring(0, Math.min(sql.length(), sql.length()-1));
				System.out.println(equ);
				try{
					
					
						Statement st1 = conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						try{
								
								st1.executeUpdate("create table "+tableName+"  ("+equ+")");
								
								
						}catch(Exception e2)
						{
							System.out.println(e2+"table not created");
							//JOptionPane.showMessageDialog(o,"Error In Creating Table  \n ."+e2.toString()+"\n Data Will Be Added","Error ",JOptionPane.OK_CANCEL_OPTION);
							//JDialog.setDefaultLookAndFeelDecorated(true);
							int response = JOptionPane.showConfirmDialog(null, "\n ."+e2.toString()+"\n Want To concatenate data in Found Table", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
						}
					if(flag1)
					{
						rs = st.executeQuery("select * from "+tableName);
			

						while(rs.next())
						{
							String result2="";
							String val = cName.substring(0, Math.min(cName.length(), cName.length()-1));
						
							int [] result1= new int[counter];
							
							for(int i =0,j=0 ;i<total ;i++)
							{
								if(cData[i] == 1)
								{
									result1[j]=rs.getInt(i+1);
									j++;
								}
								else{
									result2 +=rs.getString(i+1)+"+";
								}
							}
							PreparedStatement ps = conn1.prepareStatement("insert into "+tableName+" values("+val+")");
							
							String[] splited = result2.split("\\++");
							
							for(int i =0,j=0,k=0 ;i<total ;i++)
							{
								if(cData[i] == 1)
								{
									ps.setInt(i+1,result1[j]);
								//	System.out.println("\n"+result1[j]+"gshhshshhshhs");
									j++;
								}
								else{
									ps.setString(i+1,splited[k]);
								//	System.out.println("\n"+splited[k]+"shjshjsjhhjshjshjshj");	
									k++;
								}
							}
							ps.executeUpdate();
							ps.close();							
							
						}
					}
				}
				catch(Exception ee1)
				{
					System.out.println(ee1);
					 //JOptionPane.showMessageDialog(this,"Error In Creating Table  \n ."+ee1.toString(),"Error ",JOptionPane.ERROR_MESSAGE);
				}
			
			} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("fdrtgedrtg");
			}			
				
		}
		else
		{
			Statement st = null;
			ResultSet rs = null;
			try
			{
				String sql ="",cName;
				int cData[],counter=0;
				System.out.println(tableName);
				st = conn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
rs = st.executeQuery("SELECT COLUMN_NAME,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+tableName+"' ORDER BY ORDINAL_POSITION");
				System.out.println("After insertion of Record");
				rs.last(); 
				int total = rs.getRow();
				rs.beforeFirst();
				System.out.println(total);
				
				cData = new int[total];
				cName="";
				for(int i= 0;i<total;i++)
				{
					cName +="?,";
				}
				int flag =0;
				while(rs.next())
				{
					
					System.out.println("COLUMN_NAME   "+rs.getString(1));
					System.out.println("DATA_TYPE   "+rs.getString(2));
					System.out.println("CHARACTER_MAXIMUM_LENGTH    "+rs.getString(3));
					
					String name = rs.getString(1);
					String type = rs.getString(2);
					int size=0;
					if(rs.getString(3)=="null")
					{
						
					}
					else
					{
						 size= rs.getInt(3);
					}
					
					
					if(type.equals("int"))
					{
						sql += name+" "+"number"+" ";
						cData[flag] =1;
						counter++;
					}
					else
					{
						sql +=name+" "+type+"("+size+")"+" ";
						cData[flag] =2;
						
					}
					flag++;
					sql +=",";
				}
				String equ = sql.substring(0, Math.min(sql.length(), sql.length()-1));
				System.out.println(equ);
				try{
					
					
						Statement st1 = conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						try{
								//System.out.println("create table "+tableName+"  ("+sql+")");
								//String aaa = "create table "+tableName+" ( "+sql+" )";
								//System.out.println("\n\n\n"+aaa);
								st1.executeUpdate("create table "+tableName+"  ("+equ+")");
								
								
						}catch(Exception e2)
						{
							System.out.println(e2+"table not created");
							//JOptionPane.showMessageDialog(o,"Error In Creating Table  \n ."+e2.toString()+"\n Data Will Be Added","Error ",JOptionPane.OK_CANCEL_OPTION);
							//JDialog.setDefaultLookAndFeelDecorated(true);
							int response = JOptionPane.showConfirmDialog(null, "\n ."+e2.toString()+"\n Want To concatenate data in Found Table", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
						}
					if(flag1)
					{
						rs = st.executeQuery("select * from "+tableName);
			

						while(rs.next())
						{
							String result2="";
							String val = cName.substring(0, Math.min(cName.length(), cName.length()-1));
						
							int [] result1= new int[counter];
							
							for(int i =0,j=0 ;i<total ;i++)
							{
								if(cData[i] == 1)
								{
									result1[j]=rs.getInt(i+1);
									j++;
								}
								else{
									result2 +=rs.getString(i+1)+"+";
								}
							}
							PreparedStatement ps = conn1.prepareStatement("insert into "+tableName+" values("+val+")");
							
							String[] splited = result2.split("\\++");
							
							for(int i =0,j=0,k=0 ;i<total ;i++)
							{
								if(cData[i] == 1)
								{
									ps.setInt(i+1,result1[j]);
								//	System.out.println("\n"+result1[j]+"gshhshshhshhs");
									j++;
								}
								else{
									ps.setString(i+1,splited[k]);
								//	System.out.println("\n"+splited[k]+"shjshjsjhhjshjshjshj");	
									k++;
								}
							}
							ps.executeUpdate();
							ps.close();							
							
						}
					}
				}
				catch(Exception ee1)
				{
					System.out.println(ee1);
					 //JOptionPane.showMessageDialog(this,"Error In Creating Table  \n ."+ee1.toString(),"Error ",JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception e){System.out.println(e);
			e.printStackTrace();}
	
		}

		
		
	}
	
}