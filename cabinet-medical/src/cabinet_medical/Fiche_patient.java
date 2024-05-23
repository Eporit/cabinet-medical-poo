package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class Fiche_patient {

    JFrame fiche_patient_frame;
    private JTextField txtPatientId;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtDateDeNaissance;
    private JTextField txtWeight;
    private JTextField txtHeight;
    private JTextField txtMedicalHistory;
    private JTextField txtSurgicalHistory;
    private int patientId;

    public Fiche_patient(int patientId) {
        this.patientId = patientId;
        initialize();
        loadPatientData();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Fiche_patient window = new Fiche_patient(1); // Example patient ID
                    window.fiche_patient_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        fiche_patient_frame = new JFrame();
        fiche_patient_frame.setBounds(500, 500, 500, 500);
        fiche_patient_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fiche_patient_frame.getContentPane().setLayout(null);

        JLabel lblPatientId = new JLabel("Patient ID");
        lblPatientId.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPatientId.setBounds(10, 11, 64, 14);
        fiche_patient_frame.getContentPane().add(lblPatientId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(84, 9, 86, 20);
        fiche_patient_frame.getContentPane().add(txtPatientId);
        txtPatientId.setColumns(10);
        txtPatientId.setEditable(false);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblName.setBounds(10, 42, 64, 14);
        fiche_patient_frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(84, 40, 86, 20);
        fiche_patient_frame.getContentPane().add(txtName);
        txtName.setColumns(10);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPhone.setBounds(10, 71, 64, 14);
        fiche_patient_frame.getContentPane().add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(84, 70, 86, 20);
        fiche_patient_frame.getContentPane().add(txtPhone);
        txtPhone.setColumns(10);

        JLabel lblDateDeNaissance = new JLabel("Date of Birth");
        lblDateDeNaissance.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDateDeNaissance.setBounds(10, 136, 86, 14);
        fiche_patient_frame.getContentPane().add(lblDateDeNaissance);

        txtDateDeNaissance = new JTextField();
        txtDateDeNaissance.setBounds(106, 135, 103, 20);
        fiche_patient_frame.getContentPane().add(txtDateDeNaissance);
        txtDateDeNaissance.setColumns(10);

        JLabel lblWeight = new JLabel("Weight");
        lblWeight.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblWeight.setBounds(10, 160, 64, 14);
        fiche_patient_frame.getContentPane().add(lblWeight);

        txtWeight = new JTextField();
        txtWeight.setBounds(106, 159, 103, 20);
        fiche_patient_frame.getContentPane().add(txtWeight);
        txtWeight.setColumns(10);

        JLabel lblHeight = new JLabel("Height");
        lblHeight.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHeight.setBounds(10, 184, 64, 14);
        fiche_patient_frame.getContentPane().add(lblHeight);

        txtHeight = new JTextField();
        txtHeight.setBounds(106, 183, 103, 20);
        fiche_patient_frame.getContentPane().add(txtHeight);
        txtHeight.setColumns(10);

        JLabel lblMedicalHistory = new JLabel("Medical History");
        lblMedicalHistory.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMedicalHistory.setBounds(2, 234, 94, 14);
        fiche_patient_frame.getContentPane().add(lblMedicalHistory);

        txtMedicalHistory = new JTextField();
        txtMedicalHistory.setBounds(10, 258, 443, 73);
        fiche_patient_frame.getContentPane().add(txtMedicalHistory);
        txtMedicalHistory.setColumns(10);

        JLabel lblSurgicalHistory = new JLabel("Surgical History");
        lblSurgicalHistory.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSurgicalHistory.setBounds(10, 335, 160, 14);
        fiche_patient_frame.getContentPane().add(lblSurgicalHistory);

        txtSurgicalHistory = new JTextField();
        txtSurgicalHistory.setBounds(10, 359, 443, 63);
        fiche_patient_frame.getContentPane().add(txtSurgicalHistory);
        txtSurgicalHistory.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePatientData();
            }
        });
        btnSave.setBounds(387, 430, 89, 23);
        fiche_patient_frame.getContentPane().add(btnSave);
    }

    private void loadPatientData() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT name, phone, date_de_naissance, weight, height, medical_history, surgical_history FROM patients WHERE patient_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                txtPatientId.setText(String.valueOf(patientId));
                txtName.setText(resultSet.getString("name"));
                txtPhone.setText(resultSet.getString("phone"));
                txtDateDeNaissance.setText(resultSet.getString("date_de_naissance") != null ? resultSet.getString("date_de_naissance") : "");
                txtWeight.setText(resultSet.getString("weight") != null ? resultSet.getString("weight") : "");
                txtHeight.setText(resultSet.getString("height") != null ? resultSet.getString("height") : "");
                txtMedicalHistory.setText(resultSet.getString("medical_history") != null ? resultSet.getString("medical_history") : "");
                txtSurgicalHistory.setText(resultSet.getString("surgical_history") != null ? resultSet.getString("surgical_history") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }

    private void savePatientData() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "UPDATE patients SET name = ?, phone = ?, date_de_naissance = ?, weight = ?, height = ?, medical_history = ?, surgical_history = ? WHERE patient_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, txtName.getText());
            preparedStatement.setString(2, txtPhone.getText());

            // Handle date_de_naissance
            String dateDeNaissance = txtDateDeNaissance.getText();
            if (dateDeNaissance.isEmpty()) {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            } else {
                preparedStatement.setDate(3, Date.valueOf(dateDeNaissance));
            }

            preparedStatement.setString(4, txtWeight.getText().isEmpty() ? null : txtWeight.getText());
            preparedStatement.setString(5, txtHeight.getText().isEmpty() ? null : txtHeight.getText());
            preparedStatement.setString(6, txtMedicalHistory.getText().isEmpty() ? null : txtMedicalHistory.getText());
            preparedStatement.setString(7, txtSurgicalHistory.getText().isEmpty() ? null : txtSurgicalHistory.getText());
            preparedStatement.setInt(8, patientId);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(fiche_patient_frame, "Patient data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(fiche_patient_frame, "Error saving patient data.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dbConnection.close();
        }
    }
}

