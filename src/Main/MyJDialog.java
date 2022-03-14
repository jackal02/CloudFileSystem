package Main;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class MyJDialog extends JFrame{
	
	JDialog dialog;

    public MyJDialog(String str)
    {
    	setTitle("Echo message");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JDialog.setDefaultLookAndFeelDecorated(true);
        
        dialog=new JDialog(this,str,true);
        dialog.setSize(400,400);
        dialog.setLayout(new FlowLayout());
        
     
        dialog.setVisible(true);
    }
}
