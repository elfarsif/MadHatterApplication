package gui;

import scanning.IsbnLookup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;  

public class Inventory {

	private JFrame frame;
	private JTable table;

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	int searchParam = 0;
	int col=0;
	String selectedRow = null;
	String selectedCol = null;
	private JTextField txtScan;
	private static HttpURLConnection con2;
	String selectAll = "select * from madhatter.book order by bookid desc";
	private JTextField txtShelfNumber;
	private JTextField txtShelfValue;
	private JTextField txtNumberOfBooks;
	private JTextField txtSearchBy;
	private JTextField txtSVM;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField txtTitleBF;
	private JTextField txtAuthorBF;
	private JTextField txtYearBF;
	private JTextField txtQtyBF;
	private JTextField txtStorePriceBF;
	private JTextField txtLocationBF;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		launch();
	}
	public static void launch() {
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
		tableLoad(selectAll);
	}
	
	public void searchByShelf() throws SQLException {
		String query = "select * from madhatter.book where location like ?"; 
		tableLoad(query);
		
		String query2 ="SELECT SUM(storeprice) AS 'Shelf Value' FROM madhatter.book WHERE location = ?";
		String location = txtShelfNumber.getText();
		System.out.println(query2);
		pst = con.prepareStatement(query2);
		pst.setString(1,location);
		System.out.println(pst);
		rs = pst.executeQuery();
		String shelfValue = null;
		while (rs.next()) { 
			System.out.println(rs.getString(1));
			shelfValue = rs.getString(1);
		}
		txtShelfValue.setText(shelfValue);
		
	}
	
	public void numberOfBook() throws SQLException {
		String query = "select * from madhatter.book where location like ?"; 
		tableLoad(query);
		
		String query2 ="SELECT SUM(qty) AS 'NumberofBooks' FROM madhatter.book WHERE location = ?";
		String location = txtShelfNumber.getText();
		System.out.println(query2);
		pst = con.prepareStatement(query2);
		pst.setString(1,location);
		System.out.println(pst);
		rs = pst.executeQuery();
		String numberofBooks = null;
		while (rs.next()) { 
			System.out.println(rs.getString(1));
			numberofBooks = rs.getString(1);
		}
		txtNumberOfBooks.setText(numberofBooks);
	}
	
	public void searchByTitle() {
		
		String query = "select * from madhatter.book where title like ?"; 
		tableLoad(query);
		
	}
	
	public void searchByAuthor() {
		
		String query = "select * from madhatter.book where author like ?"; 
		tableLoad(query);
		
	}
	
 	
	public void searchByYear() {
		
		String query = "select * from madhatter.book where year like ?"; 
		tableLoad(query);
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

	
	public void tableLoad(String query) {
			selectedRow=null;
			
			if(query.contains("title")) {
				String title = "%"+txtSearchBy.getText()+"%";
				
				try {pst = con.prepareStatement(query); 
				pst.setString(1,title); 
				System.out.println(pst);
				rs = pst.executeQuery(); 
				table.setModel(DbUtils.resultSetToTableModel(rs));
				}catch (Exception e){ System.out.println("searchByTitle error :" + e); }
				 
			}else if(query.contains("author")){
				String author = "%"+txtSearchBy.getText()+"%";
				
				try {
				  
				pst = con.prepareStatement(query); 
				pst.setString(1,author); 
				System.out.println(pst);
				rs = pst.executeQuery(); 
				table.setModel(DbUtils.resultSetToTableModel(rs));
				  
				  
				}catch (Exception e){ System.out.println("searchByTitle error :" + e); }
			}else if(query.contains("year")){
				String year = ""+txtSearchBy.getText()+"%";
				try {
					  
					pst = con.prepareStatement(query); 
					pst.setString(1,year); 
					System.out.println(pst);
					rs = pst.executeQuery(); 
					table.setModel(DbUtils.resultSetToTableModel(rs));
					  
					  
					}catch (Exception e){ System.out.println("searchByTitle error :" + e); }
			}else if(query.contains("location")){
				String location = "%"+txtShelfNumber.getText()+"%";
				
				try {
				  
				pst = con.prepareStatement(query); 
				pst.setString(1,location); 
				System.out.println(pst);
				rs = pst.executeQuery(); 
				table.setModel(DbUtils.resultSetToTableModel(rs));
				  
				  
				}catch (Exception e){ System.out.println("searchByTitle error :" + e); }
			}else {
				try {
					
					pst = con.prepareStatement(query);
					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				}catch (Exception e){
					System.out.println("tableLoad error :" + e);
				}
			}
			
		}
	
	public void findSVM() {
		String query = "select storeprice,datestored,date_sold from madhatter.book where title like ? limit 1";
		String title = "%"+txtSearchBy.getText()+"%";
		
		try {pst = con.prepareStatement(query); 
		pst.setString(1,title); 
		System.out.println(pst);
		rs = pst.executeQuery(); 
		double price = 0;
		double shelfLife=1;
		double svm=0;
		while (rs.next()) {
			Date datestored=rs.getDate(2);
			Date datesold=rs.getDate(3);
			long difference_In_Time = datesold.getTime() - datestored.getTime();
			shelfLife = (difference_In_Time / (1000 * 60 * 60 * 24))% 365;
			price=rs.getDouble(1);
			svm=price/shelfLife;
			txtSVM.setText(String.format( "%.2f", svm ));
			System.out.println(String.format( "%.2f", svm ));
		}
		}catch (Exception e){ System.out.println("find SVM error :" + e); }
	}
	
	public void deleteButton() {
			
		
		  try {
		  
		  String query = "delete from madhatter.book where bookid= ? "; 
		  pst =  con.prepareStatement(query);
		  pst.setString(1,selectedRow); 
		  System.out.println(pst);
		  pst.executeUpdate(); 
		  JOptionPane.showMessageDialog(null,"Succesfully deleted row:"+selectedRow); 
		  tableLoad(selectAll);
		  
		  //clear text box txtTitle.setText(""); txtAuthor.setText("");
		  
		  //this brings the mouse back to the name text box 
		  
		  
		  }catch (Exception e){ System.out.println("error :" + e); }
		 
	}
	
	// these methods are all for searching api
	public void searchApi() throws MalformedURLException, ProtocolException, IOException, JSONException {
		String scannedIsbn = txtScan.getText();
		System.out.println("search api with : "+scannedIsbn);
		
		String url = "https://api2.isbndb.com/book/"+scannedIsbn;
		 
        try {
 
            URL myurl = new URL(url);
            con2 = (HttpURLConnection) myurl.openConnection();
            con2.setRequestProperty("Content-Type", "application/json");
            con2.setRequestProperty("Authorization", "46339_235f92c72a2b3806631c09ec94464081");
            con2.setRequestMethod("GET");
 
            StringBuilder content;
 
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con2.getInputStream()))) {
 
                String line;
                content = new StringBuilder();
 
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            
            //get attributes from api, an vscode example exists when this needs to be improved
            
            
            try {
            	
				String jsonString = content.toString();
				JSONObject obj = new JSONObject(jsonString);
				String isbn13 = obj.getJSONObject("book").getString("isbn13");
				String title = obj.getJSONObject("book").getString("title");
				String date_published = obj.getJSONObject("book").getString("date_published");
				double msrp = Double.parseDouble(obj.getJSONObject("book").getString("msrp"));
				
				JSONArray arr = obj.getJSONObject("book").getJSONArray("authors");
				ArrayList<String> authors = new ArrayList<String>();
				for (int i = 0; i < arr.length(); i++)
				{
					authors.add(arr.getString(i));
				}
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();
				String datestored = dtf.format(now);
				System.out.println(dtf.format(now));
				System.out.println(jsonString+"\n\n Publisher : "+isbn13+"\n\n title : "+title+"\n\n Date published : "+date_published+"\n\n MSRP : "+msrp+"\n\n Autors : "+authors);
				//if price is zero default to 5 dollars
				if(msrp<2) {
					msrp=5;
					scanToDatabase(scannedIsbn,title,authors.get(0),date_published,1,99,msrp,"store",datestored);
				}else {
					scanToDatabase(scannedIsbn,title,authors.get(0),date_published,1,99,msrp,"store",datestored);
				}
				
				
			} catch (JSONException e) {
				System.out.println("Isbn Api Search value in search doesnt exist, fix with a while try catch loop and an arraylist\n\n");
				e.printStackTrace();
			}
            
            
            
            
 
        } finally {
 
            con2.disconnect();
        }
			
			
	}
	
	
	public void scanToDatabase(String scannedIsbn,String title,String author,String year,int qty,double storeprice,double bookprice,String location,String datestored) {
		System.out.println("add to database");
		
		
		try { 
			String query="insert into book(bookisbn,title,author,year,qty,storeprice,bookprice,location,datestored) values (?,?,?,?,?,?,?,?,?)"; 
			pst = con.prepareStatement(query);
		  
			pst.setString(1,scannedIsbn); 
			pst.setString(2,title);
			pst.setString(3,author); 
			pst.setString(4,year); 
			pst.setInt(5,qty);
			pst.setDouble(6,storeprice); 
			pst.setDouble(7,bookprice); 
			pst.setString(8,location);
			pst.setString(9,datestored);
		  
			System.out.println(pst); pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Succesfully added"); 
			tableLoad(selectAll);
		  
		}catch (Exception e){System.out.println("scanToDatabase error :" + e); }
		
		txtScan.setText("");
		//this brings the mouse back to the name text box
		txtScan.requestFocus();
		 
	}
	
	public void updateBookInformationTable() {

		DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
		
		txtTitleBF.setText(tblModel.getValueAt(table.getSelectedRow(), 2).toString());
		txtAuthorBF.setText(tblModel.getValueAt(table.getSelectedRow(), 3).toString());
		txtYearBF.setText(tblModel.getValueAt(table.getSelectedRow(), 4).toString());
		txtQtyBF.setText(tblModel.getValueAt(table.getSelectedRow(), 5).toString());
		txtStorePriceBF.setText(tblModel.getValueAt(table.getSelectedRow(), 6).toString());
		txtLocationBF.setText(tblModel.getValueAt(table.getSelectedRow(), 8).toString());
	}
	
	public void updateInventory() {
		 try {
			  System.out.println(selectedRow+", "+selectedCol);
			  int row = Integer.parseInt(selectedRow);
			  String query = "update madhatter.book set "+selectedCol+" = ? where bookid= ?"; 
				
				  pst = con.prepareStatement(query); 
				  pst.setString(1,txtLocationBF.getText());
				  pst.setInt(2,row); 
				  String cell= table.getModel().getValueAt(row, col).toString();
				  System.out.println(txtLocationBF.getText());
				  System.out.println(pst); 
				  pst.executeUpdate();
				  JOptionPane.showMessageDialog(null,"Succesfully updated row:"+selectedRow);
				  tableLoad(selectAll);
				 
				  
				 
		}catch (Exception e){ System.out.println("update inventory error :" + e); }
	}
	
	public void selectedColumn() {
		
		col= table.getSelectedColumn();
		selectedCol = table.getColumnName(col);
		System.out.println(table.getColumnName(col));
	}
	
	public void selectedRow() {
		DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
		String tblName = tblModel.getValueAt(table.getSelectedRow(), 0).toString();
		selectedRow = tblName;
		System.out.println(selectedRow);
		updateBookInformationTable();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Inventory Management System");
		frame.setBounds(100, 100, 1525, 785);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mad Hatter Inventory");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel.setBounds(449, 12, 314, 41);
		frame.getContentPane().add(lblNewLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 51, 1491, 687);
		frame.getContentPane().add(tabbedPane);
		
		String[] searchParameter = {"Title","Author","Year"};
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		tabbedPane.addTab("Inventory", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(434, 10, 1052, 292);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRow();
				selectedColumn();
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 12, 404, 63);
		panel.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Register", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lblNewLabel_2 = new JLabel("Scan ISBN");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 29, 118, 13);
		panel_1.add(lblNewLabel_2);
		
		txtScan = new JTextField();
		txtScan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtScan.getText().length()==13) {
					try {
						searchApi();
					} catch (IOException | JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		txtScan.setColumns(10);
		txtScan.setBounds(138, 27, 232, 19);
		panel_1.add(txtScan);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 318, 404, 196);
		panel.add(panel_5);
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lblNewLabel_6 = new JLabel("Enter Shelf # : ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_6.setBounds(10, 67, 118, 13);
		panel_5.add(lblNewLabel_6);
		
		txtShelfNumber = new JTextField();
		txtShelfNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					searchByShelf();
					numberOfBook();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		txtShelfNumber.setColumns(10);
		txtShelfNumber.setBounds(222, 65, 85, 19);
		panel_5.add(txtShelfNumber);
		
		JLabel lblNewLabel_7 = new JLabel("Shelf Value (USD) : ");
		lblNewLabel_7.setBounds(10, 134, 134, 13);
		panel_5.add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		txtShelfValue = new JTextField();
		txtShelfValue.setBorder(null);
		txtShelfValue.setOpaque(false);
		txtShelfValue.setBackground(new Color(0,0,0,0));
		txtShelfValue.setBounds(222, 132, 85, 19);
		panel_5.add(txtShelfValue);
		txtShelfValue.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Number of books :");
		lblNewLabel_8.setBounds(10, 169, 134, 13);
		panel_5.add(lblNewLabel_8);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		txtNumberOfBooks = new JTextField();
		txtNumberOfBooks.setBorder(null);
		txtNumberOfBooks.setOpaque(false);
		txtNumberOfBooks.setBackground(new Color(0,0,0,0));
		txtNumberOfBooks.setBounds(222, 167, 85, 19);
		panel_5.add(txtNumberOfBooks);
		txtNumberOfBooks.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Shelf Information");
		lblNewLabel_1_1.setBounds(114, 10, 149, 27);
		panel_5.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_7.setBounds(10, 85, 404, 99);
		panel.add(panel_7);
		
		JLabel lblNewLabel_9 = new JLabel("Search by ");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_9.setBounds(71, 29, 77, 13);
		panel_7.add(lblNewLabel_9);
		
		txtSearchBy = new JTextField();
		txtSearchBy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(searchParam==0) {
					searchByTitle();
					findSVM();
				}else if(searchParam==1) {
					searchByAuthor();
				}else {
					searchByYear();
				}
			}
		});
		txtSearchBy.setColumns(10);
		txtSearchBy.setBounds(71, 56, 232, 19);
		panel_7.add(txtSearchBy);
		JComboBox comboBoxSearch = new JComboBox(searchParameter);
		comboBoxSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				searchParam = comboBoxSearch.getSelectedIndex();
				System.out.println(searchParam);
				
			}
		});
		comboBoxSearch.setBounds(169, 26, 134, 21);
		panel_7.add(comboBoxSearch);
		
		JLabel lblNewLabel_11 = new JLabel("Shelving Value Metric ($/day) : ");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_11.setBounds(20, 200, 225, 13);
		panel.add(lblNewLabel_11);
		
		txtSVM = new JTextField();
		txtSVM.setBorder(null);
		txtSVM.setOpaque(false);
		txtSVM.setBackground(new Color(0,0,0,0));
		txtSVM.setColumns(10);
		txtSVM.setBounds(299, 194, 69, 19);
		panel.add(txtSVM);
		
		JLabel lblNewLabel_11_1 = new JLabel("Average Shelving Value Metric : ");
		lblNewLabel_11_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_11_1.setBounds(20, 229, 225, 13);
		panel.add(lblNewLabel_11_1);
		
		textField_3 = new JTextField();
		textField_3.setBorder(null);
		textField_3.setOpaque(false);
		textField_3.setBackground(new Color(0,0,0,0));
		textField_3.setColumns(10);
		textField_3.setBounds(299, 223, 69, 19);
		panel.add(textField_3);
		
		JLabel lblNewLabel_11_1_1 = new JLabel("Shelving profit/loss :");
		lblNewLabel_11_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_11_1_1.setBounds(20, 261, 225, 13);
		panel.add(lblNewLabel_11_1_1);
		
		textField_4 = new JTextField();
		textField_4.setBorder(null);
		textField_4.setOpaque(false);
		textField_4.setBackground(new Color(0,0,0,0));
		textField_4.setColumns(10);
		textField_4.setBounds(299, 255, 69, 19);
		panel.add(textField_4);
		
		JButton btnDelete = new JButton("Delect");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButton();
			}
		});
		btnDelete.setBounds(956, 312, 85, 21);
		panel.add(btnDelete);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(444, 312, 403, 338);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Information");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(138, 10, 149, 27);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_12 = new JLabel("Ttile");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_12.setBounds(28, 53, 77, 32);
		panel_2.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Author");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_13.setBounds(28, 95, 77, 32);
		panel_2.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("Year");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_14.setBounds(28, 137, 77, 32);
		panel_2.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("Qty");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_15.setBounds(28, 179, 77, 32);
		panel_2.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("Store Price");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_16.setBounds(28, 221, 77, 32);
		panel_2.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Location");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_17.setBounds(28, 263, 77, 32);
		panel_2.add(lblNewLabel_17);
		
		txtTitleBF = new JTextField();
		txtTitleBF.setBorder(null);
		txtTitleBF.setOpaque(false);
		txtTitleBF.setBackground(new Color(0,0,0,0));
		txtTitleBF.setBounds(115, 61, 278, 19);
		
		panel_2.add(txtTitleBF);
		txtTitleBF.setColumns(10);
		
		txtAuthorBF = new JTextField();
		txtAuthorBF.setBorder(null);
		txtAuthorBF.setOpaque(false);
		txtAuthorBF.setBackground(new Color(0,0,0,0));		
		txtAuthorBF.setColumns(10);
		txtAuthorBF.setBounds(115, 103, 278, 19);
		panel_2.add(txtAuthorBF);
		
		txtYearBF = new JTextField();
		txtYearBF.setBorder(null);
		txtYearBF.setOpaque(false);
		txtYearBF.setBackground(new Color(0,0,0,0));		
		txtYearBF.setColumns(10);
		txtYearBF.setBounds(115, 145, 278, 19);
		panel_2.add(txtYearBF);
		
		txtQtyBF = new JTextField();
		txtQtyBF.setBorder(null);
		txtQtyBF.setOpaque(false);
		txtQtyBF.setBackground(new Color(0,0,0,0));
		txtQtyBF.setColumns(10);
		txtQtyBF.setBounds(115, 187, 278, 19);
		panel_2.add(txtQtyBF);
		
		txtStorePriceBF = new JTextField();
		txtStorePriceBF.setBorder(null);
		txtStorePriceBF.setOpaque(false);
		txtStorePriceBF.setBackground(new Color(0,0,0,0));
		txtStorePriceBF.setColumns(10);
		txtStorePriceBF.setBounds(115, 229, 278, 19);
		panel_2.add(txtStorePriceBF);
		
		txtLocationBF = new JTextField();
		txtLocationBF.setBorder(null);
		txtLocationBF.setOpaque(false);
		txtLocationBF.setBackground(new Color(0,0,0,0));
		txtLocationBF.setColumns(10);
		txtLocationBF.setBounds(115, 271, 278, 19);
		panel_2.add(txtLocationBF);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateInventory();
			}
		});
		btnNewButton.setBounds(861, 312, 85, 21);
		panel.add(btnNewButton);
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Point of Sale", null, panel_6, null);
		panel_6.setLayout(null);
	}
	}
