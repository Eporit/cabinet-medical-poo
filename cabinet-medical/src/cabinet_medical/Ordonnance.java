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

public class Ordonnance {

    JFrame ordonnance_frame;
    JTextField textField;
    JTextField textField_1;
    JTextField textField_2;
    JTextField textField_5;
    JTextField textField_6;
    private int patientId;
    private int doctorId;

    public Ordonnance(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        initialize();
        loadPatientDoctorInfo();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ordonnance window = new Ordonnance(1, 1); // Example patient ID and doctor ID
                    window.ordonnance_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        ordonnance_frame = new JFrame();
        ordonnance_frame.getContentPane().setForeground(new Color(0, 0, 64));
        ordonnance_frame.setBounds(100, 100, 786, 537);
        ordonnance_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ordonnance_frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Ordonnance Medicale");
        lblNewLabel.setForeground(new Color(0, 0, 64));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(243, 32, 214, 22);
        ordonnance_frame.getContentPane().add(lblNewLabel);

        JLabel lblPatienteName = new JLabel("Patient Name");
        lblPatienteName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPatienteName.setBounds(10, 128, 103, 22);
        ordonnance_frame.getContentPane().add(lblPatienteName);

        JLabel lblDate = new JLabel("Date");
        lblDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDate.setBounds(457, 81, 62, 14);
        ordonnance_frame.getContentPane().add(lblDate);

        JLabel lblAge = new JLabel("Age");
        lblAge.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAge.setBounds(10, 183, 56, 19);
        ordonnance_frame.getContentPane().add(lblAge);

        textField = new JTextField();
        textField.setBounds(122, 127, 295, 20);
        ordonnance_frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(534, 81, 177, 20);
        ordonnance_frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(45, 184, 146, 20);
        ordonnance_frame.getContentPane().add(textField_2);

        JLabel lblNewLabel_1 = new JLabel("Doctor Name");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(10, 81, 103, 14);
        ordonnance_frame.getContentPane().add(lblNewLabel_1);

        textField_5 = new JTextField();
        textField_5.setBounds(128, 80, 289, 20);
        ordonnance_frame.getContentPane().add(textField_5);
        textField_5.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Médicaments / dosages / duree : ");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setBounds(10, 229, 270, 22);
        ordonnance_frame.getContentPane().add(lblNewLabel_2);

        textField_6 = new JTextField();
        textField_6.setBounds(10, 262, 578, 225);
        ordonnance_frame.getContentPane().add(textField_6);
        textField_6.setColumns(10);

        JLabel lblSignature = new JLabel("Signature :");
        lblSignature.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSignature.setBounds(630, 231, 81, 19);
        ordonnance_frame.getContentPane().add(lblSignature);

        JButton btnNewButton = new JButton("Cancel ");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartConsultation consultation = new StartConsultation(patientId, doctorId);
                consultation.start_consultation_frame.setVisible(true);
                ordonnance_frame.dispose();
            }
        });
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setBackground(new Color(64, 128, 128));
        btnNewButton.setBounds(659, 10, 89, 41);
        ordonnance_frame.getContentPane().add(btnNewButton);

        // Set consultation date to current date
        textField_1.setText(LocalDate.now().toString());
        
        JButton btnImprimer = new JButton("imprimer");
        btnImprimer.setForeground(Color.WHITE);
        btnImprimer.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnImprimer.setBackground(new Color(64, 128, 128));
        btnImprimer.setBounds(613, 446, 135, 41);
        ordonnance_frame.getContentPane().add(btnImprimer);
    }

    private void loadPatientDoctorInfo() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String patientQuery = "SELECT name, date_de_naissance FROM patients WHERE patient_id = ?";
        String doctorQuery = "SELECT name FROM doctors WHERE doctor_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement patientStmt = connection.prepareStatement(patientQuery);
             PreparedStatement doctorStmt = connection.prepareStatement(doctorQuery)) {

            patientStmt.setInt(1, patientId);
            ResultSet patientRs = patientStmt.executeQuery();
            if (patientRs.next()) {
                textField.setText(patientRs.getString("name"));

                java.sql.Date dob = patientRs.getDate("date_de_naissance");
                if (dob != null) {
                    LocalDate dateOfBirth = dob.toLocalDate();
                    int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
                    textField_2.setText(String.valueOf(age));
                } else {
                    textField_2.setText("");
                }
            }

            doctorStmt.setInt(1, doctorId);
            ResultSet doctorRs = doctorStmt.executeQuery();
            if (doctorRs.next()) {
                textField_5.setText(doctorRs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
}
