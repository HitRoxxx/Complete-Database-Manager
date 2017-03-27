import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

class Display extends Thread
{
;
	JPanel jp;
	String tableName ;
	JCheckBox jc;
	
	Display(String tableName , JPanel jp)
	{
		this.tableName=tableName;
		this.jp=jp;
	}
	public void run()
	{
		
		jc = new JCheckBox(tableName);
		jc.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {             
            
				if(jc.isSelected())
				{
					ShowTableMigration.tableNameSelected +=jc.getText()+"+";
					System.out.println(ShowTableMigration.tableNameSelected);
				}
				else
				{
					String[] splited = ShowTableMigration.tableNameSelected.split("\\++");
					ShowTableMigration.tableNameSelected="";
					for(int i=0;i<splited.length;i++)
					{
						//System.out.println(splited[i]+"kkk"+jc.getText());
						if(splited[i].equals(jc.getText()))
						{
							//System.out.println(splited[i]+"hghggdhg"+jc.getText());
						}
						else
						{
							ShowTableMigration.tableNameSelected +=splited[i]+"+";
						}
						
					}
		
				}				
			}           
		});
	  jp.add(jc);
					
	}
			
}