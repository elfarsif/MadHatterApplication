package gui;

import scanning.FindBookInfo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Inventory {

	private JFrame frame;
	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtYear;
	private JTextField txtQty;
	private JTextField txtStorePrice;
	private JTextField txtBookPrice;
	private JTextField txtLocation;
	private JTable table;
	private JTextField txtSearchISBN;

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
					Inventory window = new Inventory();
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
	public Inventory() {
		initialize();
		Connect();
		tableLoad();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1170, 462);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mad Hatter Inventory");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel.setBounds(449, 10, 314, 41);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 63, 404, 244);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ISBN");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(20, 38, 106, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Title");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1.setBounds(20, 63, 106, 13);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Author");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1.setBounds(20, 86, 106, 13);
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Year");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1_1.setBounds(20, 109, 106, 13);
		panel.add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Qty");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1_1_1.setBounds(20, 132, 106, 13);
		panel.add(lblNewLabel_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Store Price");
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1_1_1_1.setBounds(20, 155, 106, 13);
		panel.add(lblNewLabel_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("Book Price");
		lblNewLabel_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1_1_1_1_1.setBounds(20, 178, 106, 13);
		panel.add(lblNewLabel_1_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1_1_1 = new JLabel("Location");
		lblNewLabel_1_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1_1_1_1_1_1.setBounds(20, 201, 106, 13);
		panel.add(lblNewLabel_1_1_1_1_1_1_1_1_1);
		
		txtISBN = new JTextField();
		txtISBN.setBounds(136, 36, 232, 19);
		panel.add(txtISBN);
		txtISBN.setColumns(10);
		
		txtTitle = new JTextField();
		txtTitle.setColumns(10);
		txtTitle.setBounds(136, 61, 232, 19);
		panel.add(txtTitle);
		
		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(136, 84, 232, 19);
		panel.add(txtAuthor);
		
		txtYear = new JTextField();
		txtYear.setColumns(10);
		txtYear.setBounds(136, 107, 232, 19);
		panel.add(txtYear);
		
		txtQty = new JTextField();
		txtQty.setColumns(10);
		txtQty.setBounds(136, 130, 232, 19);
		panel.add(txtQty);
		
		txtStorePrice = new JTextField();
		txtStorePrice.setColumns(10);
		txtStorePrice.setBounds(136, 153, 232, 19);
		panel.add(txtStorePrice);
		
		txtBookPrice = new JTextField();
		txtBookPrice.setColumns(10);
		txtBookPrice.setBounds(136, 176, 232, 19);
		panel.add(txtBookPrice);
		
		txtLocation = new JTextField();
		txtLocation.setColumns(10);
		txtLocation.setBounds(136, 199, 232, 19);
		panel.add(txtLocation);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToDatabase();
			}
		});
		btnNewButton.setBounds(40, 317, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(178, 317, 85, 21);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Bulk Save");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnClear.setBounds(311, 317, 85, 21);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(449, 63, 697, 279);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 352, 404, 63);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("ISBN");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 29, 118, 13);
		panel_1.add(lblNewLabel_2);
		
		txtSearchISBN = new JTextField();
		txtSearchISBN.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				searchIsbn();
				
			}
		});
		txtSearchISBN.setColumns(10);
		txtSearchISBN.setBounds(138, 27, 232, 19);
		panel_1.add(txtSearchISBN);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateButton();
			}
		});
		btnUpdate.setBounds(697, 352, 85, 21);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnClear_1_1 = new JButton("Delete");
		btnClear_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("deletebutton");
				deleteButton();
			}
		});
		btnClear_1_1.setBounds(816, 352, 85, 21);
		frame.getContentPane().add(btnClear_1_1);
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

		public void saveToDatabase() {
			
			try {
				
				String query = "insert into book(bookisbn,title,author,year,qty,storeprice,bookprice,location) values (?,?,?,?,?,?,?,?)";
				pst = con.prepareStatement(query);
				
				pst.setString(1,txtISBN.getText());
				pst.setString(2,txtTitle.getText());
				pst.setString(3,txtAuthor.getText());
				pst.setInt(4,Integer.parseInt(txtYear.getText()));
				pst.setInt(5,Integer.parseInt(txtQty.getText()));
				pst.setDouble(6,Double.parseDouble(txtStorePrice.getText()));
				pst.setDouble(7,Double.parseDouble(txtBookPrice.getText()));
				pst.setString(8,txtLocation.getText());
				
				
				
				System.out.println(pst);
				pst.executeUpdate(); 
				JOptionPane.showMessageDialog(null, "Succesfully added");
				tableLoad();
				
				//clear text box
				txtISBN.setText("");
				txtTitle.setText("");
				txtAuthor.setText("");
				txtYear.setText("");
				txtQty.setText("");
				txtStorePrice.setText("");
				txtBookPrice.setText("");
				txtLocation.setText("");
				
				//this brings the mouse back to the name text box
				txtISBN.requestFocus();
				
				
			}catch (Exception e){
				System.out.println("error :" + e);
			}
		}

		public void tableLoad() {

			
			try {
				
				String query = "select * from madhatter.book";
				pst = con.prepareStatement(query);
				
				rs = pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				
			}catch (Exception e){
				System.out.println("error :" + e);
			}
		}

		public void searchIsbn() {
			
			try {
				
				String isbn = txtSearchISBN.getText();
				String sql = "select title,author,year,qty,storeprice,bookprice,location FROM madhatter.book where bookisbn = ?";
				pst = con.prepareStatement(sql);
				pst.setString(1, isbn);
				rs = pst.executeQuery();
				
				if(rs.next()==true) {
					
					txtTitle.setText(rs.getString(1));
					txtAuthor.setText(rs.getString(2));
					txtYear.setText(rs.getString(3));
					txtQty.setText(rs.getString(4));
					txtStorePrice.setText(rs.getString(5));
					txtBookPrice.setText(rs.getString(6));
					txtLocation.setText(rs.getString(7));
					
				}else {
					txtTitle.setText("");
					txtAuthor.setText("");
					txtYear.setText("");
					txtQty.setText("");
					txtStorePrice.setText("");
					txtBookPrice.setText("");
					txtLocation.setText("");
				}
				
			}catch(Exception e) {
				System.out.println("error :"+e);
			}
			
		}

		public void updateButton() {
			
			try {
				
				String query = "update madhatter.book set title= ?, author= ?, year= ?, qty = ?, storeprice= ?, bookprice= ?, location= ? where bookisbn= ? ";
				pst = con.prepareStatement(query);
				
				pst.setString(1,txtTitle.getText());
				pst.setString(2,txtAuthor.getText());
				pst.setInt(3, Integer.parseInt(txtYear.getText()));
				pst.setInt(4,Integer.parseInt(txtQty.getText()));
				pst.setDouble(5,Double.parseDouble(txtStorePrice.getText()));
				pst.setDouble(6,Double.parseDouble(txtBookPrice.getText()));
				pst.setString(7,txtLocation.getText());
				pst.setString(8,txtISBN.getText());
				
				System.out.println(pst);
				pst.executeUpdate(); 
				JOptionPane.showMessageDialog(null, "Succesfully updated");
				tableLoad();
				
				//clear text box
				txtISBN.setText("");
				txtTitle.setText("");
				txtAuthor.setText("");
				txtYear.setText("");
				txtQty.setText("");
				txtStorePrice.setText("");
				txtBookPrice.setText("");
				txtLocation.setText("");
				
				//this brings the mouse back to the name text box
				txtISBN.requestFocus();
				
				
			}catch (Exception e){
				System.out.println("error :" + e);
			}
		}
		
		public void deleteButton() {
			
			try {
				
				String query = "delete from madhatter.book where bookisbn= ? ";
				pst = con.prepareStatement(query);
				System.out.println(txtSearchISBN.getText());
				pst.setString(1,txtSearchISBN.getText());
				System.out.println(pst);
				pst.executeUpdate(); 
				JOptionPane.showMessageDialog(null, "Succesfully deleted");
				tableLoad();
				
				//clear text box
				txtTitle.setText("");
				txtAuthor.setText("");
				txtYear.setText("");
				txtQty.setText("");
				txtStorePrice.setText("");
				txtBookPrice.setText("");
				txtLocation.setText("");
				
				//this brings the mouse back to the name text box
				txtISBN.requestFocus();
				
				
			}catch (Exception e){
				System.out.println("error :" + e);
			}
		}
		
		public void bulkSaveISBN() {
			
		}






}
