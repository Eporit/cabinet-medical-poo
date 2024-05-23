package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class login_page {

    JFrame login_frame;
    private JTextField email;
    private Connection connection;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login_page window = new login_page();
                    window.login_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public login_page() {
        initialize();
        // Establish and test the connection
        establishConnection();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        login_frame = new JFrame();
        login_frame.setBounds(100, 100, 840, 519);
        login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Configure the connection to the Oracle DB
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "cabinet_medical", "yudl6esti3abew1lthu6");

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        login_frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("MedicalCabinet ");
        lblNewLabel.setBounds(306, 13, 269, 42);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBackground(new Color(0, 128, 192));
        lblNewLabel.setForeground(new Color(0, 128, 192));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        login_frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Connexion");
        lblNewLabel_1.setBounds(53, 71, 109, 17);
        lblNewLabel_1.setForeground(new Color(0, 128, 192));
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        login_frame.getContentPane().add(lblNewLabel_1);

        email = new JTextField();
        email.setToolTipText("");
        email.setBounds(129, 153, 263, 25);
        login_frame.getContentPane().add(email);
        email.setColumns(10);

        JLabel mail = new JLabel("Email");
        mail.setBounds(42, 156, 84, 14);
        mail.setForeground(new Color(128, 128, 128));
        mail.setFont(new Font("Tahoma", Font.BOLD, 15));
        login_frame.getContentPane().add(mail);

        JLabel mot_passe = new JLabel("Password ");
        mot_passe.setBounds(42, 205, 96, 14);
        mot_passe.setForeground(new Color(128, 128, 128));
        mot_passe.setFont(new Font("Tahoma", Font.BOLD, 15));
        login_frame.getContentPane().add(mot_passe);


        JLabel image1 = new JLabel("");
        image1.setBounds(468, 66, 340, 368);
        login_frame.getContentPane().add(image1);

        JButton New_account = new JButton("log in");
        New_account.setIcon(new ImageIcon(login_page.class.getResource("/images/login-.png")));
        New_account.setForeground(new Color(255, 255, 255));
        New_account.setBounds(74, 295, 263, 32);
        New_account.setBackground(new Color(0, 128, 192));
        New_account.setFont(new Font("Tahoma", Font.BOLD, 15));
        New_account.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userEmail = email.getText();
                String userPassword = new String(passwordField.getPassword());

                int doctorId = verifyLoginDoctor(userEmail, userPassword);
                if (doctorId != -1) {
                    JOptionPane.showMessageDialog(login_frame, "Login successful as Doctor!");
                    Doctor_home Doctor_frame = new Doctor_home(doctorId); 
                    Doctor_frame.doctor_home_frame.setVisible(true);
                    login_frame.dispose();
                } else if (verifyLoginSecretaire(userEmail, userPassword)) {
                    JOptionPane.showMessageDialog(login_frame, "Login successful as Secretaire!");
                    Secretaire_home secretaire_frame = new Secretaire_home();
                    secretaire_frame.secretaire_home_frame.setVisible(true);
                    login_frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(login_frame, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        login_frame.getContentPane().add(New_account);

        JButton FG_pswrd = new JButton("sign up");
        FG_pswrd.setIcon(new ImageIcon(login_page.class.getResource("/images/add-user.png")));
        FG_pswrd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sign_up_page App2 = new Sign_up_page();
                App2.sign_up_frame.setVisible(true);
                login_frame.dispose();
            }
        });
        FG_pswrd.setForeground(new Color(255, 255, 255));
        FG_pswrd.setFont(new Font("Tahoma", Font.BOLD, 14));
        FG_pswrd.setBackground(new Color(0, 128, 192));
        FG_pswrd.setBounds(74, 349, 264, 32);
        login_frame.getContentPane().add(FG_pswrd);

        passwordField = new JPasswordField();
        passwordField.setBounds(129, 202, 263, 25);
        login_frame.getContentPane().add(passwordField);

    }

    private void establishConnection() {
        System.out.println("Attempting to establish a database connection...");
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Oracle JDBC Driver Registered!");

            // Configure connection to Oracle DB
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "cabinet_medical", "yudl6esti3abew1lthu6");
            if (connection != null) {
                System.out.println("Database connection established successfully.");
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to establish database connection.");
            e.printStackTrace();
        }
    }

    private int verifyLoginDoctor(String email, String password) {
        String query = "SELECT doctor_id FROM doctors WHERE email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Doctor found in database with email: " + email);
                return resultSet.getInt("doctor_id");
            } else {
                System.out.println("Doctor not found in database with email: " + email);
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean verifyLoginSecretaire(String email, String password) {
        String query = "SELECT * FROM secretaire WHERE email = ? AND password = ?";
        return verifyLogin(query, email, password);
    }

    private boolean verifyLogin(String query, String email, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("User found in database with email: " + email);
                return true;
            } else {
                System.out.println("User not found in database with email: " + email);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
