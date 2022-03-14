package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

public class Main extends JFrame implements KeyListener{
	
	UserManagement Um=UserManagement.getInstance();
	FileManagement Fm=FileManagement.getInstance();
	
	int currentLine=2;
	public boolean success=true;
	
	static int userSize, lastUserSize;
	
	String generalText="MyCloud Application: guest@MyCloudApp";
	JLabel topLabel;
	
	JPanel panel;
	JScrollPane scrollPane;
	JTextField currentTextF;
	
	Vector<String> lastCmd;
	int cmdIndex=1;
	
	static Parser parse;
	
	void moveScroll()
	{
		if(currentLine>=this.getHeight()/20-1) 
			 scrollPane.getVerticalScrollBar().setValue( scrollPane.getVerticalScrollBar().getMaximum());
	}
	
	void createNewInput()
	{
		topLabel.setText("MyCloud Application: "+ Um.LoggedUser.Username+"@MyCloudApp");
		
		currentLine++;
		JTextField text=new JTextField();
		
		text.setPreferredSize( new Dimension(panel.getWidth(), 20 ) );
		text.setMaximumSize( new Dimension(panel.getWidth()-5, 20 ) );
		text.setFont( new Font("Calibri", Font.PLAIN, 15) );
		text.setForeground(Color.white);
		text.setCaretColor(Color.white);
		text.setBackground(new Color(50,120,200));
		text.setAlignmentX( Component.LEFT_ALIGNMENT );
		
		text.setOpaque(false);
		text.setBorder(null);
		
		text.setText(">");
		text.setCaretPosition(1);
		text.getCaret().setVisible(true);
		
		text.addKeyListener(this);
		panel.add(text);
		
		if(currentTextF!=null)
		{
			lastCmd.add(currentTextF.getText().substring(1));
			if( lastCmd.size()==11 ) lastCmd.remove(0);
			cmdIndex=lastCmd.size();
			
			if( success ) currentTextF.setForeground(Color.green);
			else currentTextF.setForeground(Color.orange);
			
			currentTextF.setEditable(false);
		}
		this.currentTextF=text;
		
		this.pack();
		text.requestFocusInWindow();
		text.getCaret();
		this.setVisible(true);
		
		moveScroll();
		this.show();
	}

	public void addLabel(String str)
	{
		currentLine++;
		JLabel label=new JLabel(str);
		label.setFont( new Font("Calibri", Font.PLAIN, 15) );
		label.setForeground(Color.white);
		
		label.setAlignmentX( Component.LEFT_ALIGNMENT );
		panel.add(label);
	}
	
	public void addDialog(String str) {
		JDialog dialog=new JDialog(this,"Echo message",true);
        dialog.setSize(400,70);
        dialog.setLayout(new FlowLayout());
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setResizable(false);
        
        JLabel label=new JLabel(str);
        label.setAlignmentY(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        
        dialog.add(label);
        dialog.show();
	}
	
	public void addList(Vector<String> vect)
	{
		JList<String> list=new JList<String>(vect);
		list.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(list);
	}
	
	public void addTable(Vector< Vector<String> > rows, Vector<String> columnNames)
	{
		int dimx=rows.get(0).size();
		int dimy=rows.size();
		
		JTable table = new JTable( rows, columnNames );

		JScrollPane scrollPane = new JScrollPane( table );
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		scrollPane.setPreferredSize(new Dimension((int)(this.getWidth()/4)*dimx,
				Math.min(20+25*dimy, 150)));
		scrollPane.setMaximumSize(new Dimension((int)(this.getWidth()/4)*dimx,
				Math.min(20+25*dimy, 150)));
		
		table.setEnabled(false);
		panel.add( scrollPane, BorderLayout.NORTH );
	}
	
	Main(String str)
	{
		super(str);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setMinimumSize(new Dimension( (int)(screenSize.getWidth()-screenSize.getWidth()/2.5),
				(int)(screenSize.getHeight()-screenSize.getHeight()/3) ) ) ;
		
		this.setMaximumSize(new Dimension( (int)(screenSize.getWidth()-screenSize.getWidth()/2.5),
				(int)(screenSize.getHeight()-screenSize.getHeight()/3) ) ) ;
		
		this.setLocation(screenSize.width/2-this.getSize().width/2,
						 screenSize.height/2-this.getSize().height/2);
		
		this.setResizable(false);
		this.setLayout(null);
		
		currentTextF=null;
		this.lastCmd=new Vector<String>();
		
		
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBounds(20, 0 , this.getWidth()-30, this.getHeight()-20 );
		panel.setMaximumSize( new Dimension(this.getWidth()-5, this.getHeight() ) );
		panel.setBackground(new Color(50,120,200));
		
		currentLine++;
		topLabel=new JLabel("MyCloud Application: guest@MyCloudApp");
		topLabel.setFont( new Font("Calibri", Font.PLAIN, 15) );
		topLabel.setForeground(Color.RED);
		topLabel.setBounds(0, 0, this.getWidth(), 20);
		
		topLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
		this.add(topLabel);
		
		addLabel(" ");
		createNewInput();
		
		scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setAlignmentY(BOTTOM_ALIGNMENT);
        
        scrollPane.setBounds(0, 20 , this.getWidth()-5, this.getHeight()-45);
        this.getContentPane().add(scrollPane);
		
		this.show();
		this.pack();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if( e.getKeyCode()==KeyEvent.VK_ENTER){
			this.success=true;
			cmd(currentTextF.getText());
			createNewInput();
		}
	
		if( e.getKeyCode()==KeyEvent.VK_UP) 
		{
			if(cmdIndex>0) cmdIndex--;
			currentTextF.setText(">"+lastCmd.get(cmdIndex));
		}
		
		if( e.getKeyCode()==KeyEvent.VK_DOWN) 
		{
			if(cmdIndex<lastCmd.size()-1)
			{
				cmdIndex++;
				currentTextF.setText(">"+lastCmd.get(cmdIndex));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	static void cmd(String str)
	{
		parse=new Parser(str.substring(1));
		parse.parseCommand();
	}
	
	public static void main(String[] args) {
		
		
		  // Look and feel din arhiva,dar arata mai urat si am lasat default
		 /*	try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			} catch (Exception e) {
			e.printStackTrace();
			}
		*/
		
		
		UIManager.getDefaults().put("ScrollPane.ancestorInputMap",  
		        new UIDefaults.LazyInputMap(new Object[] {}));
		
		Main window=new Main("MyCloudApp");
		FileManagement.getInstance().terminal=window;
	}

}
