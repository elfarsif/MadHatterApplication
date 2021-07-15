package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField txtUser;
	private JTextField txtPassword;
	private JLabel Login;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		Connect();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 481, 213);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUser.setBounds(54, 67, 88, 23);
		frame.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(54, 100, 88, 23);
		frame.getContentPane().add(lblPassword);
		
		txtUser = new JTextField();
		txtUser.setBounds(152, 70, 206, 19);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(152, 100, 206, 19);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtUser.getText();
				String password = txtPassword.getText();
				
				validateLogin(user,password);
			}
		});
		btnLogin.setBounds(199, 129, 85, 21);
		frame.getContentPane().add(btnLogin);
		
		Login = new JLabel("Login");
		Login.setFont(new Font("Tahoma", Font.BOLD, 17));
		Login.setBounds(210, 10, 88, 23);
		frame.getContentPane().add(Login);
	}
	
	public void Connect(){
		
		String driver = "com.mysql.jdbc.Driver";
		
		String url = "jdbc:mysql://127.0.0.1:3306/madhatter"; 
		String uid = "root"; 
		String pw = "rapace";
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, uid, pw);
		} catch(Exception e) {
			
		}
		
	}

	public void validateLogin(String user,String password) {
		System.out.println("Validate user : "+user+"\nValidate pass : "+password);
		String query = "select * from madhatter.user where username = ? and userpassword = ?";
		try {
			
			pst = con.prepareStatement(query);
			pst.setString(1, user);
			pst.setString(2, password);
			rs = pst.executeQuery();
			System.out.println(pst);
			
			int count=0;
			while (rs.next()) {
				count=+ 1;
			}
			if(count==1) {
				frame.setVisible(false);
				Inventory inventory = new Inventory();
				inventory.launch();
			}else if(count>1){
				JOptionPane.showMessageDialog(null, "duplicate username and passoword");
				txtUser.setText("");
				txtPassword.setText("");
				txtUser.requestFocus();
			}else {
				JOptionPane.showMessageDialog(null, "user or password does not exists");
				txtUser.setText("");
				txtPassword.setText("");
				txtUser.requestFocus();
			}
		}catch (Exception e){
			System.out.println("validate Login error :" + e);
		}
	}
}
