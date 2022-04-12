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

public class ResistrationPage extends JApplet implements ActionListener
{
	JLabel lbpname1,lbpname2;
	public JTextField txp1name,txp2name;
	JButton btplay,btclose;
	JPanel P1,P2;
	
	public void init()
	{
		lbpname1 = new JLabel("Enter Player 1 name");
		txp1name = new JTextField(20);
		
		lbpname2= new JLabel("Enter Player 2 name");
		txp2name = new JTextField(20);
		
		
		
		btplay = new JButton("Play");
		btclose= new JButton("close");
		
		P1=new JPanel();
		P1.setLayout(new GridLayout(1,2));
		P1.add(lbpname1);
		P1.add(txp1name);
		P1.add(lbpname2);
		P1.add(txp2name);
		
		P2=new JPanel();
		P2.setLayout(new GridLayout(1,2));
		P2.add(btplay);
		P2.add(btclose);
		
		setLayout(new GridLayout(2,1));
		add(P1);
		add(P2);
		
		btplay.addActionListener(this);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root", "");
			
			Statement stmt=con.createStatement();
			stmt.executeUpdate("create database if not exists ticktacDB");
		
			stmt.execute("use ticktacDB");
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

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src=ae.getSource();
		if(src==btplay)
		{
			
			
			String pnm1=txp1name.getText();
			String pnm2=txp2name.getText();
			P1=new TickTacToe(pnm1,pnm2);
			add(P1);
			validate();
			java.util.Date dt=new Date();
			java.sql.Date dtsql=new java.sql.Date(dt.getYear(),dt.getMonth(),dt.getDate());
			
			try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ticktacDB","root", "");
			
			Statement stmt=con.createStatement();
			stmt.executeUpdate("create database if not exists ticktacDB");
			stmt.executeUpdate("create table if not exists GameTB"+"(pname1 varchar(50),pname2 varchar(50),winner varchar(50),gamedate date)");
			
			PreparedStatement pstmt=con.prepareStatement("Insert into gameTB(pname1,pname2,gamedate) values(?,?,?)");
			
			pstmt.setString(1, pnm1);
			pstmt.setString(2, pnm2);
			pstmt.setDate(3, dtsql);
			pstmt.executeUpdate();
			
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
