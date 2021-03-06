import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener
{
	JComboBox database;
	JTextField databaseInstance,userName;
	JPasswordField userPassword;
	JButton login;
	JLabel d,dI,uN,uP; 
	JPanel jp1;
	static String databaseName;
	String data[] ={"ORACLE","MYSQL"};
	String s;
	public Login(String s)
	{
		super(s);
		this.s = s;
		JComboBox<String> database = new JComboBox<>(data);
		database.setSelectedIndex(0);
		databaseName = (String)database.getSelectedItem();
		database.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {     
            
			JComboBox cb = (JComboBox)e.getSource();
			databaseName = (String)cb.getSelectedItem();
         }
      }); 
		
		d=new JLabel("Database", JLabel.CENTER); 
		dI=new JLabel("Database Instance", JLabel.CENTER); 
		uN=new JLabel("User Name", JLabel.CENTER); 
		uP=new JLabel("User Password", JLabel.CENTER); 
		
		
		login = new JButton("Login");
		login.addActionListener(this);
		
		databaseInstance =new  JTextField(10);
		databaseInstance.setFont(new Font("Courier", Font.BOLD,20));
		userName =new JTextField(10);
		userName.setFont(new Font("Courier", Font.BOLD,20));
		userPassword =new JPasswordField(10);
		userPassword.setFont(new Font("Courier", Font.BOLD,20));
		
		jp1 = new JPanel();
		jp1.setLayout(new GridLayout(4,2,10,10));
		
		jp1.add(d);
		jp1.add(database);
		jp1.add(dI);
		jp1.add(databaseInstance);
		jp1.add(uN);
		jp1.add(userName);
		jp1.add(uP);
		jp1.add(userPassword);
		
		add(jp1,BorderLayout.CENTER);
		add(login,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,300);
		//pack();
		setLocation(450, 150);
		setVisible(true);
	}
		
		
		
	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getSource() == login)
		{
			
			String s1 = databaseInstance.getText().trim();
			String s2 = userName.getText().trim();
			String s3 = new String(userPassword.getPassword());
			if(databaseName.equals("ORACLE"))
			{
				try
				{
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:"+s1,s2,s3);
					JOptionPane.showMessageDialog(this, "Success! Connection Established.");
					new MainWindow(con,s,s2);
					this.dispose();
				}catch(Exception e){
			
					 JOptionPane.showMessageDialog(this,"Can't Connect To Database \n Try again."+e.toString(),"Error In Connecting",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else
			{
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+s1,s2,s3);
					JOptionPane.showMessageDialog(this, "Success! Connection Established.");
					new MainWindow(con,s,s2);
					this.dispose();
				}catch(Exception e){
					 JOptionPane.showMessageDialog(this,"Can't Connect To Database \n Try again."+e.toString(),"Error In Connecting",JOptionPane.ERROR_MESSAGE);
				}
			}
			 
		}
	}
}