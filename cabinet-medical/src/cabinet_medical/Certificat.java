package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class Certificat {

    JFrame certificat_frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_4;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private int patientId;
    private int doctorId;

    public Certificat(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        initialize();
        loadPatientDoctorInfo();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Certificat window = new Certificat(1, 1); // Example patient ID and doctor ID
                    window.certificat_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        certificat_frame = new JFrame();
        certificat_frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
        certificat_frame.setBounds(100, 100, 697, 543);
        certificat_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        certificat_frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Certificat médical ");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(248, 44, 227, 20);
        certificat_frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Name ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(22, 113, 74, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("specialite");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(22, 144, 82, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel(" Patient :");
        lblNewLabel_1_2.setForeground(new Color(128, 128, 128));
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_2.setBounds(22, 175, 82, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_4 = new JLabel("Doctor :");
        lblNewLabel_1_4.setForeground(new Color(128, 128, 128));
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_4.setBounds(22, 82, 106, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_4);

        JLabel lblNewLabel_1_2_1 = new JLabel("Name ");
        lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_2_1.setBounds(22, 206, 82, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_2_1);

        JLabel lblNewLabel_1_2_1_1 = new JLabel("Age");
        lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_2_1_1.setBounds(22, 236, 89, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_2_1_1);

        JLabel lblNewLabel_2 = new JLabel("Signature :");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setBounds(546, 399, 106, 26);
        certificat_frame.getContentPane().add(lblNewLabel_2);

        textField = new JTextField();
        textField.setBounds(99, 115, 264, 20);
        certificat_frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(99, 144, 264, 20);
        certificat_frame.getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(99, 206, 264, 20);
        certificat_frame.getContentPane().add(textField_2);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(99, 236, 264, 20);
        certificat_frame.getContentPane().add(textField_4);

        JButton btnNewButton = new JButton("Cancel ");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartConsultation consultation = new StartConsultation(patientId, doctorId);
                consultation.start_consultation_frame.setVisible(true);
                certificat_frame.dispose();
            }
        });
        btnNewButton.setBackground(new Color(64, 128, 128));
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setBounds(14, 455, 89, 41);
        certificat_frame.getContentPane().add(btnNewButton);

        JLabel lblNewLabel_1_2_2 = new JLabel("Details :");
        lblNewLabel_1_2_2.setForeground(Color.GRAY);
        lblNewLabel_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_2_2.setBounds(14, 266, 82, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_2_2);

        JLabel lblNewLabel_1_3_1 = new JLabel(" Consultation Date");
        lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_3_1.setBounds(396, 82, 172, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_3_1);

        JLabel lblNewLabel_1_3_1_1 = new JLabel("Diagnostic");
        lblNewLabel_1_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_3_1_1.setBounds(14, 296, 98, 20);
        certificat_frame.getContentPane().add(lblNewLabel_1_3_1_1);

        JLabel lblNewLabel_1_3_1_2 = new JLabel("Length of incapacity ");
        lblNewLabel_1_3_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_3_1_2.setBounds(6, 354, 166, 41);
        certificat_frame.getContentPane().add(lblNewLabel_1_3_1_2);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(406, 116, 201, 20);
        certificat_frame.getContentPane().add(textField_6);

        textField_7 = new JTextField();
        textField_7.setColumns(10);
        textField_7.setBounds(155, 291, 452, 56);
        certificat_frame.getContentPane().add(textField_7);

        textField_8 = new JTextField();
        textField_8.setColumns(10);
        textField_8.setBounds(159, 357, 448, 32);
        certificat_frame.getContentPane().add(textField_8);

        // Set consultation date to current date
        textField_6.setText(LocalDate.now().toString());
        
        JButton btnImprimer = new JButton("imprimer");
        btnImprimer.setForeground(Color.WHITE);
        btnImprimer.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnImprimer.setBackground(new Color(64, 128, 128));
        btnImprimer.setBounds(208, 455, 172, 41);
        certificat_frame.getContentPane().add(btnImprimer);
    }

    private void loadPatientDoctorInfo() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String patientQuery = "SELECT name, date_de_naissance FROM patients WHERE patient_id = ?";
        String doctorQuery = "SELECT name, specialty FROM doctors WHERE doctor_id = ?";
        
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement patientStmt = connection.prepareStatement(patientQuery);
             PreparedStatement doctorStmt = connection.prepareStatement(doctorQuery)) {

            // Load patient information
            patientStmt.setInt(1, patientId);
            ResultSet patientRs = patientStmt.executeQuery();
            if (patientRs.next()) {
                textField_2.setText(patientRs.getString("name"));

                java.sql.Date dob = patientRs.getDate("date_de_naissance");
                if (dob != null) {
                    LocalDate dateOfBirth = dob.toLocalDate();
                    int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
                    textField_4.setText(String.valueOf(age));
                } else {
                    textField_4.setText("");
                }
            }

            // Load doctor information
            doctorStmt.setInt(1, doctorId);
            ResultSet doctorRs = doctorStmt.executeQuery();
            if (doctorRs.next()) {
                textField.setText(doctorRs.getString("name"));
                textField_1.setText(doctorRs.getString("specialty")); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
}
