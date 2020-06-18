package com.golf.HandicapTrackerGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import com.golf.handicapTracker.HandicapTrackerDatabase;

import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;

public class HandicapTrackerGUI {

	private JFrame frame;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private HandicapTrackerDatabase database;
	private JPanel loginPanel;
	private JPanel mainPanel;
	private JLabel lblHandicapindex;
	private JLabel user;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HandicapTrackerGUI window = new HandicapTrackerGUI();
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
	public HandicapTrackerGUI() {
		database = new HandicapTrackerDatabase();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 540, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 528, 428);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 81, 504, 311);
		mainPanel.add(tabbedPane);
		
		JPanel addScore = new JPanel();
		tabbedPane.addTab("Add Score", null, addScore, null);
		addScore.setLayout(null);
		
		String [] courses = this.getCourses();
		JComboBox courseSelector = new JComboBox(courses);
		courseSelector.setBounds(30, 22, 144, 24);
		addScore.add(courseSelector);
		
		
		JLabel lblHi = new JLabel("Hi");
		lblHi.setBounds(61, 12, 70, 15);
		mainPanel.add(lblHi);
		
		JLabel lblYourHandicapIndex = new JLabel("Your handicap index is:");
		lblYourHandicapIndex.setBounds(61, 39, 194, 15);
		mainPanel.add(lblYourHandicapIndex);
		
		lblHandicapindex = new JLabel("handicapIndex");
		lblHandicapindex.setBounds(231, 40, 94, 15);
		mainPanel.add(lblHandicapindex);
		
		user = new JLabel("user");
		user.setBounds(92, 8, 70, 15);
		mainPanel.add(user);
		mainPanel.setVisible(false);
		
		loginPanel = new JPanel();
		loginPanel.setBounds(0, 0, 528, 399);
		frame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(211, 70, 114, 19);
		loginPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(211, 115, 114, 19);
		loginPanel.add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel lblHandicapTracker = new JLabel("Handicap Tracker");
		lblHandicapTracker.setHorizontalAlignment(SwingConstants.CENTER);
		lblHandicapTracker.setFont(new Font("Ubuntu", Font.BOLD, 20));
		lblHandicapTracker.setBounds(131, 27, 221, 31);
		loginPanel.add(lblHandicapTracker);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(111, 63, 88, 22);
		loginPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(112, 114, 83, 15);
		loginPanel.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			
			}
		});
		btnLogin.setBounds(194, 165, 117, 25);
		loginPanel.add(btnLogin);
	}
	
	private String[] getCourses() {
		ArrayList<Map<String, String>> courses = database.getCourses();
		
		String[]  returnArray = new String[courses.size()];
		
		int i = 0;
		for (Map<String,String> course: courses) {
			returnArray[i] = course.get("courseName") + " (" + course.get("courseCity") + ", " + course.get("courseState") + ")";
			i++;
		}
		
		return returnArray;
		
	}

	public void login() {
		System.out.println("Login button pressed");
		boolean loginSuccessful = false;
		try {
			loginSuccessful = database.login(this.usernameTextField.getText().trim(), String.valueOf(this.passwordTextField.getPassword()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(loginSuccessful) {
			renderMainPanel(this.usernameTextField.getText().trim());
		}
		else {
			this.usernameTextField.setText("");
			this.passwordTextField.setText("");
		}
	}

	private void renderMainPanel(String userName) {
		this.loginPanel.setVisible(false);
		this.mainPanel.setVisible(true);
		this.user.setText(userName);
		try {
			this.lblHandicapindex.setText(String.format("%.1f",database.getUserHandicapIndex(userName)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
