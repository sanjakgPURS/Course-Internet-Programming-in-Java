package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import dto.Contact;

@ManagedBean
@SessionScoped
public class ContactDB {
	
	//private ArrayList<Contact> contacts = new ArrayList<Contact>();
	private Contact currentContact = new Contact();
	private String searchPattern = "";
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/ip14adresar?characterEncoding=utf8";
	private String username = "root";
	private String password = "test";
	private Connection connection = null;
	private PreparedStatement ps = null;
	private String SELECT_CONTACTS = "SELECT * FROM contacts";
	private String INSERT_CONTACT = "INSERT INTO contacts (id, firstName, lastName, address, mobileNumber, fixedNumber, email, description, dateOfBirth) VALUES (?,?,?,?,?,?,?,?,?)";
	private String DELETE_CONTACT = "DELETE FROM contacts WHERE id=(?)";
	
	public String getSearchPattern() {
		return searchPattern;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}
	
	public ContactDB() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		connection = DriverManager.getConnection(url,username,password);
	}
	
	public Contact getCurrentContact() {
		return currentContact;
	}

	public void setCurrentContact(Contact currentContact) {
		this.currentContact = currentContact;
	}
	//
	// getContacts from DATABASE
	//
	public ArrayList<Contact> getContacts() throws SQLException {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		ps = connection.prepareStatement(SELECT_CONTACTS);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Contact temp = new Contact();
			temp.setId(UUID.fromString(rs.getString("id")));
			temp.setFirstName(rs.getString("firstName"));
			temp.setLastName(rs.getString("lastName"));
			temp.setAddress(rs.getString("address"));
			temp.setMobileNumber(rs.getString("mobileNumber"));
			temp.setFixedNumber(rs.getString("fixedNumber"));
			temp.setEmail(rs.getString("email"));
			temp.setDescription(rs.getString("description"));
			java.sql.Date sqlDate = rs.getDate("dateOfBirth");
			temp.setDateOfBirth(sqlDate);
			contacts.add(temp);
		}
		return contacts;
	}
	//
	// Funkcije
	//
	public String addContact() throws SQLException {
		UUID userUUID = UUID.randomUUID();
		currentContact.setId(userUUID);
		//contacts.add(currentContact);
		ps = connection.prepareStatement(INSERT_CONTACT);
		ps.setString(1, currentContact.getId().toString());
		ps.setString(2, currentContact.getFirstName());
		ps.setString(3, currentContact.getLastName());
		ps.setString(4, currentContact.getAddress());
		ps.setString(5, currentContact.getMobileNumber());
		ps.setString(6, currentContact.getFixedNumber());
		ps.setString(7, currentContact.getEmail());
		ps.setString(8, currentContact.getDescription());
		java.sql.Date sqlDate = new java.sql.Date(currentContact.getDateOfBirth().getTime());
		ps.setDate(9, sqlDate);
		ps.execute();
		currentContact = new Contact();
		return "index.xhtml";
	}
	public String deleteContact(UUID id) throws SQLException {
		System.out.println("Brisanje kontakta UUID = " + id);
		ps = connection.prepareStatement(DELETE_CONTACT);
		ps.setString(1, id.toString());
		ps.execute();
		return "index.xhtml";
	}

}
