import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Query extends JApplet implements ActionListener
{
	JLabel lbpname;
	public JTextField txp1name,result;
	JButton bttotwin,bttotloose;
	JPanel P1,P2;
	
	public void init()
	{
		lbpname = new JLabel("Enter Player name");
		txp1name = new JTextField(20);
		
		bttotwin = new JButton("Win");
		bttotloose= new JButton("Loose");
		result=new JTextField(20);
	//	btresult.setFont("");
		
		P1=new JPanel();
		P1.setLayout(new GridLayout(1,2));
		P1.add(lbpname);
		P1.add(txp1name);
		
		P2=new JPanel();
		P2.setLayout(new GridLayout(1,1));
		P2.add(result);

		P1.add(bttotwin);
		P1.add(bttotloose);
		
		
		setLayout(new GridLayout(2,1));
		add(P1);
		add(P2);
		
		bttotwin.addActionListener(this);
		bttotloose.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src=ae.getSource();
		if(src==bttotwin)
		{
			
			
			String pnm1=txp1name.getText();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ticktacdb","root", "");
				
				PreparedStatement pstmt=con.prepareStatement("Select count(*) from gameTB where winner=?");
				pstmt.setString(1, pnm1);
				ResultSet rs=pstmt.executeQuery();
				int c=0;
				rs.next();
				c=rs.getInt(1);
				result.setText(c+" wins");
				
				
				con.close();
				} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
			else
			{
				String pnm=txp1name.getText();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ticktacdb","root", "");
					
					PreparedStatement pstmt=con.prepareStatement("Select count(*) from gameTB where winner=?");
					pstmt.setString(1, pnm);
					ResultSet rs=pstmt.executeQuery();
					int w=0;
					rs.next();
					w=rs.getInt(1);
					
					pstmt=con.prepareStatement("Select count(*) from gameTB where pname1=?");
					pstmt.setString(1, pnm);
					rs=pstmt.executeQuery();
					int t=0;
					rs.next();
					t=rs.getInt(1);
					
					pstmt=con.prepareStatement("Select count(*) from gameTB where pname2=?");
					pstmt.setString(1, pnm);
					rs=pstmt.executeQuery();
					rs.next();
					t+=rs.getInt(1);
					result.setText(t-w+" loss");
					
					
					con.close();
					} 
				catch (ClassNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			
		}
	}
}
