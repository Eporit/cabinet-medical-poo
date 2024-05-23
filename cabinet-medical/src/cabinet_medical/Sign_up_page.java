package cabinet_medical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Sign_up_page {

	         JFrame sign_up_frame;
	         JTextField full_name;
	         JTextField numero_tel;
	         JTextField email_field;
	         JTextField password_feild;
	         JTextField specialiteField;
	     	 Connection connection;

	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sign_up_page window = new Sign_up_page();
					window.sign_up_frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sign_up_page() {
		initialize();
		establishConnection();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		sign_up_frame = new JFrame();
		sign_up_frame.setBounds(100, 100, 840, 519);
		sign_up_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sign_up_frame.getContentPane().setLayout(null);
		
		JLabel f_name = new JLabel("Full Name");
		f_name.setForeground(new Color(128, 128, 128));
		f_name.setFont(new Font("Tahoma", Font.BOLD, 15));
		f_name.setBounds(10, 31, 119, 19);
		sign_up_frame.getContentPane().add(f_name);
		
		full_name = new JTextField();
		full_name.setBounds(10, 51, 373, 28);
		sign_up_frame.getContentPane().add(full_name);
		full_name.setColumns(10);
		
		JLabel profession = new JLabel("Profession");
		profession.setForeground(new Color(128, 128, 128));
		profession.setFont(new Font("Tahoma", Font.BOLD, 15));
		profession.setBounds(10, 96, 179, 19);
		sign_up_frame.getContentPane().add(profession);
		
		JComboBox<String> roleComboBox = new JComboBox<>();
        roleComboBox.setBounds(20, 125, 200, 25);
        roleComboBox.addItem("Doctor");
        roleComboBox.addItem("Secretaire");
        sign_up_frame.getContentPane().add(roleComboBox);
        
        JLabel lblSpecialite = new JLabel("Specialite");
        lblSpecialite.setForeground(new Color(128, 128, 128));
        lblSpecialite.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblSpecialite.setBounds(246, 93, 100, 25);
        sign_up_frame.getContentPane().add(lblSpecialite);

        specialiteField = new JTextField();
        specialiteField.setBounds(246, 125, 200, 25);
        specialiteField.setEnabled(false); // Initially disabled
        sign_up_frame.getContentPane().add(specialiteField);
        specialiteField.setColumns(10);

        // Add ActionListener to roleComboBox to enable/disable specialiteField
        roleComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                if ("Doctor".equals(selectedRole)) {
                    specialiteField.setEnabled(true);
                } else {
                    specialiteField.setEnabled(false);
                }
            }
        });
		
		JLabel Add = new JLabel("numero tel");
		Add.setForeground(new Color(128, 128, 128));
		Add.setFont(new Font("Tahoma", Font.BOLD, 15));
		Add.setBounds(10, 170, 104, 19);
		sign_up_frame.getContentPane().add(Add);
		
		numero_tel = new JTextField();
		numero_tel.setBounds(10, 190, 761, 28);
		sign_up_frame.getContentPane().add(numero_tel);
		numero_tel.setColumns(10);
		
		JLabel mail = new JLabel("Email");
		mail.setForeground(new Color(128, 128, 128));
		mail.setFont(new Font("Tahoma", Font.BOLD, 15));
		mail.setBounds(10, 242, 119, 14);
		sign_up_frame.getContentPane().add(mail);
		
		email_field = new JTextField();
		email_field.setBounds(10, 267, 373, 28);
		sign_up_frame.getContentPane().add(email_field);
		email_field.setColumns(10);
		
		JLabel mot_de_passe = new JLabel("Password ");
		mot_de_passe.setFont(new Font("Tahoma", Font.BOLD, 15));
		mot_de_passe.setForeground(new Color(128, 128, 128));
		mot_de_passe.setBounds(398, 242, 104, 14);
		sign_up_frame.getContentPane().add(mot_de_passe);
		
		password_feild = new JTextField();
		password_feild.setBounds(398, 267, 373, 28);
		sign_up_frame.getContentPane().add(password_feild);
		password_feild.setColumns(10);
		
		JButton Bt_connexion = new JButton("Log in");
		Bt_connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bt_connexion.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
							login_page App2 = new login_page();
							App2.login_frame.setVisible(true);
							sign_up_frame.dispose();
					  }
					});

			}
		});
		Bt_connexion.setFont(new Font("Tahoma", Font.BOLD, 15));
		Bt_connexion.setBackground(new Color(0, 128, 192));
		Bt_connexion.setForeground(new Color(255, 255, 255));
		Bt_connexion.setBounds(10, 388, 89, 28);
		sign_up_frame.getContentPane().add(Bt_connexion);
		
		JButton sign_up_button = new JButton("sign up");
		sign_up_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { String fullName = full_name.getText();
            String phone = numero_tel.getText();
            String email = email_field.getText();
            String password = password_feild.getText();
            String role = (String) roleComboBox.getSelectedItem();
            String specialite = specialiteField.getText();

            if ("Doctor".equals(role)) {
                insertDoctor(fullName, phone, email, password, specialite);
            } else if ("Secretaire".equals(role)) {
                insertSecretaire(fullName, phone, email, password);
            }
        }
    });
    sign_up_button.setForeground(Color.WHITE);
    sign_up_button.setFont(new Font("Tahoma", Font.BOLD, 15));
    sign_up_button.setBackground(new Color(0, 128, 192));
    sign_up_button.setBounds(10, 350, 104, 28);
    sign_up_frame.getContentPane().add(sign_up_button);
}

private void establishConnection() {
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "cabinet_medical", "yudl6esti3abew1lthu6");
        System.out.println("Database connection established successfully.");
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}

private void insertDoctor(String fullName, String phone, String email, String password, String specialite) {
    try {
        String query = "INSERT INTO doctors (name, n_tel, email, password, specialty) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fullName);
        preparedStatement.setString(2, phone);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        preparedStatement.setString(5, specialite);
        preparedStatement.executeUpdate();
        JOptionPane.showMessageDialog(sign_up_frame, "Doctor registered successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(sign_up_frame, "Error registering doctor.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void insertSecretaire(String fullName, String phone, String email, String password) {
    try {
        String query = "INSERT INTO secretaire (name, n_tel, email, password) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fullName);
        preparedStatement.setString(2, phone);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        preparedStatement.executeUpdate();
        JOptionPane.showMessageDialog(sign_up_frame, "Secretaire registered successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(sign_up_frame, "Error registering secretaire.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}