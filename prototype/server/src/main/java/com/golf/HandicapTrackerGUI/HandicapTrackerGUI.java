package com.golf.HandicapTrackerGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.golf.handicapTracker.Course;
import com.golf.handicapTracker.HandicapTrackerDatabase;
import com.golf.handicapTracker.Tee;

import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;

public class HandicapTrackerGUI {

	private JFrame frame;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private HandicapTrackerDatabase database;
	private JPanel loginPanel;
	private JPanel mainPanel;
	private JLabel lblHandicapindex;
	private JLabel user;
	private ArrayList<Course> courses;
	private Tee[] tees;
	private JTextField newScoreTextField;
	private JComboBox teeSelector;
	private JComboBox courseSelector;
	private JDateChooser dateChooser;
	private JTable table;
	private JTextField textField;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
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
		tees = new Tee[6];
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
		
		
		courseSelector = new JComboBox(this.getCourses());
		courseSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				courseSelected(courseSelector.getSelectedItem());
			}

		});
		courseSelector.setBounds(97, 22, 390, 24);
		addScore.add(courseSelector);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(26, 22, 70, 15);
		addScore.add(lblCourse);
		
		JLabel lblNewLabel = new JLabel("Tees");
		lblNewLabel.setBounds(25, 96, 70, 15);
		addScore.add(lblNewLabel);
		
		
		
		teeSelector = new JComboBox(this.tees);
		teeSelector.setBounds(97, 89, 390, 24);
		addScore.add(teeSelector);
		
		
		newScoreTextField = new JTextField();
		newScoreTextField.setBounds(163, 186, 150, 35);
		addScore.add(newScoreTextField);
		newScoreTextField.setColumns(10);
		
		JLabel lblAdjustedScore = new JLabel("Adjusted Score");
		lblAdjustedScore.setBounds(12, 204, 148, 15);
		addScore.add(lblAdjustedScore);
		
		JButton btnAddScore = new JButton("Add Score");
		btnAddScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addRound();
			}
		});
		btnAddScore.setBounds(185, 247, 117, 25);
		addScore.add(btnAddScore);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(123, 135, 196, 34);
		addScore.add(dateChooser);
		
		JPanel scoreHistory = new JPanel();
		tabbedPane.addTab("Past Scores", null, scoreHistory, null);
		scoreHistory.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 12, 475, 260);
		scoreHistory.add(table);
		
		JPanel addCourse = new JPanel();
		tabbedPane.addTab("Add Course", null, addCourse, null);
		addCourse.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(114, 30, 114, 19);
		addCourse.add(textField);
		textField.setColumns(10);
		
		
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
		usernameTextField.setBounds(211, 70, 182, 31);
		loginPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(211, 115, 182, 31);
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
		
		Course[] courses = this.getCourses();
	}
	
	protected void addRound() {
		System.out.println("Add Round");
		Course course = (Course) this.courseSelector.getSelectedItem();
		Tee tee = (Tee) this.teeSelector.getSelectedItem();
		
		try {
			database.insertRound(this.user.getText(), course.getName()	, 
					course.getCity(), course.getState(), tee.getColor(), this.getSelectedDate(), Integer.parseInt(this.newScoreTextField.getText()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.updateHandicap();
		this.clearTextFields();
		
		
	}
	
	private void clearTextFields() {
		// TODO Auto-generated method stub
		this.dateChooser.setDate(null);
		this.newScoreTextField.setText("");
		
		
	}

	private void updateHandicap() {
		try {
			this.lblHandicapindex.setText(String.format("%.1f",database.getUserHandicapIndex(this.user.getText())));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void courseSelected(Object selectedItem) {
		// TODO Auto-generated method stub
		Course course = null;
		if(!(selectedItem instanceof Course)) {
			System.out.println("NOT Instance of Course");
			return;
			
		}
		course = (Course) selectedItem;
		ArrayList<Tee> courseTees;
		try {
			courseTees = database.getTees(course.getName(), course.getCity(), course.getState());
			System.out.println(courseTees);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return;
		}
		
		this.teeSelector.removeAllItems();
		for (Tee tee: courseTees) {
			this.teeSelector.addItem(tee);
			
		}
			
		
	}

	private Timestamp getSelectedDate() {
		Date date = this.dateChooser.getDate();
		return new Timestamp(date.getTime());
		
	}
	
	private Course[] getCourses() {
		try {
			this.courses = database.getCourses();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Course[] courseArray = new Course[courses.size()];
		
		int i = 0;
		for (Course course: courses) {
			courseArray[i] = course;
			i++;
		}
		
		
		return courseArray;
		
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
