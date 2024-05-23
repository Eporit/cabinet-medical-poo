package cabinet_medical;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Resume {

    JFrame resume_frame;
    private JTextField symptomsField;
    private JTextField diagnosisField;
    private JTextField remarquefield;
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private JTextField Traitmentfield;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Resume window = new Resume(1); // Example doctor, patient, and appointment IDs
                    window.resume_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Resume(int patientId) {
        this.patientId = patientId; // Store the patient ID
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        resume_frame = new JFrame();
        resume_frame.setBounds(100, 100, 683, 553);
        resume_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resume_frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Résumé de la consultation");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(197, 29, 330, 30);
        resume_frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Symptômes :");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(10, 116, 106, 20);
        resume_frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Diagnostique :");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(10, 172, 144, 20);
        resume_frame.getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Traitement :");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_2.setBounds(10, 251, 106, 20);
        resume_frame.getContentPane().add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("Remarque : ");
        lblNewLabel_1_3.setForeground(new Color(64, 0, 64));
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1_3.setBounds(10, 342, 97, 20);
        resume_frame.getContentPane().add(lblNewLabel_1_3);

        JButton btnAddToMedicalFile = new JButton("Add to medical file");
        btnAddToMedicalFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToDossierMedical();
                deleteAppointment();
                Doctor_home doctor_home_vue = new Doctor_home(doctorId);
                doctor_home_vue.doctor_home_frame.setVisible(true);
                resume_frame.dispose();
            }
        });
        btnAddToMedicalFile.setBackground(new Color(64, 0, 64));
        btnAddToMedicalFile.setForeground(new Color(255, 255, 255));
        btnAddToMedicalFile.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnAddToMedicalFile.setBounds(183, 434, 216, 69);
        resume_frame.getContentPane().add(btnAddToMedicalFile);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartConsultation startConsultation = new StartConsultation(doctorId, patientId);
                startConsultation.start_consultation_frame.setVisible(true);
                resume_frame.dispose();
            }
        });
        btnBack.setBounds(10, 10, 85, 21);
        resume_frame.getContentPane().add(btnBack);

        symptomsField = new JTextField();
        symptomsField.setBounds(126, 135, 463, 35);
        resume_frame.getContentPane().add(symptomsField);
        symptomsField.setColumns(10);

        diagnosisField = new JTextField();
        diagnosisField.setBounds(126, 208, 463, 35);
        resume_frame.getContentPane().add(diagnosisField);
        diagnosisField.setColumns(10);

        Traitmentfield = new JTextField();
        Traitmentfield.setColumns(10);
        Traitmentfield.setBounds(126, 289, 463, 35);
        resume_frame.getContentPane().add(Traitmentfield);

        remarquefield = new JTextField();
        remarquefield.setBounds(122, 371, 467, 35);
        resume_frame.getContentPane().add(remarquefield);
        remarquefield.setColumns(10);
    }

    private void addToDossierMedical() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "INSERT INTO Medical_Records (appointment_id, patient_id, visit_date, symptoms, diagnosis, treatment, remarque) "
                     + "SELECT appointment_id, patient_id, appointment_time, ?, ?, ?, ? FROM Appointments WHERE appointment_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, symptomsField.getText());
            preparedStatement.setString(2, diagnosisField.getText());
            preparedStatement.setString(3, Traitmentfield.getText());
            preparedStatement.setString(4, remarquefield.getText());
            preparedStatement.setInt(5, appointmentId);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(resume_frame, "Medical record added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(resume_frame, "Failed to add medical record.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnection.close();
        }
    }

    private void deleteAppointment() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "DELETE FROM Appointments WHERE appointment_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(resume_frame, "Failed to delete appointment.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnection.close();
        }
    }
}

