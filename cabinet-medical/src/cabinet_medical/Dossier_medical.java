package cabinet_medical;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Dossier_medical {

    JFrame dossier_medical_frame;
    private JTextField patientIdField;
    private JTextField visitDateField;
    private JTextField symptomsField;
    private JTextField diagnosisField;
    private JTextField treatmentField;
    private JTextField remarksField;
    private int patientId;
    private JComboBox<String> visitDateComboBox;
    private List<Integer> recordIds;

    public Dossier_medical(int patientId) {
        this.patientId = patientId;
        initialize();
        loadMedicalRecords();
    }

    private void initialize() {
        dossier_medical_frame = new JFrame();
        dossier_medical_frame.setBounds(100, 100, 450, 400);
        dossier_medical_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dossier_medical_frame.getContentPane().setLayout(null);

        JLabel lblVisitDate = new JLabel("Visit Date");
        lblVisitDate.setBounds(10, 10, 80, 20);
        dossier_medical_frame.getContentPane().add(lblVisitDate);

        visitDateComboBox = new JComboBox<>();
        visitDateComboBox.setBounds(100, 10, 150, 20);
        dossier_medical_frame.getContentPane().add(visitDateComboBox);
        visitDateComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = visitDateComboBox.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < recordIds.size()) {
                    loadRecordData(recordIds.get(selectedIndex));
                }
            }
        });

        JLabel lblPatientId = new JLabel("Patient ID");
        lblPatientId.setBounds(10, 40, 80, 20);
        dossier_medical_frame.getContentPane().add(lblPatientId);

        patientIdField = new JTextField();
        patientIdField.setBounds(100, 40, 100, 20);
        dossier_medical_frame.getContentPane().add(patientIdField);
        patientIdField.setColumns(10);
        patientIdField.setEditable(false);

        JLabel lblSymptoms = new JLabel("Symptoms");
        lblSymptoms.setBounds(10, 120, 80, 20);
        dossier_medical_frame.getContentPane().add(lblSymptoms);

        symptomsField = new JTextField();
        symptomsField.setBounds(100, 120, 300, 44);
        dossier_medical_frame.getContentPane().add(symptomsField);
        symptomsField.setColumns(10);

        JLabel lblDiagnosis = new JLabel("Diagnosis");
        lblDiagnosis.setBounds(10, 174, 80, 20);
        dossier_medical_frame.getContentPane().add(lblDiagnosis);

        diagnosisField = new JTextField();
        diagnosisField.setBounds(100, 174, 300, 44);
        dossier_medical_frame.getContentPane().add(diagnosisField);
        diagnosisField.setColumns(10);

        JLabel lblTreatment = new JLabel("Treatment");
        lblTreatment.setBounds(10, 232, 80, 20);
        dossier_medical_frame.getContentPane().add(lblTreatment);

        treatmentField = new JTextField();
        treatmentField.setBounds(100, 233, 300, 44);
        dossier_medical_frame.getContentPane().add(treatmentField);
        treatmentField.setColumns(10);

        JLabel lblRemarks = new JLabel("Remarks");
        lblRemarks.setBounds(10, 287, 80, 20);
        dossier_medical_frame.getContentPane().add(lblRemarks);

        remarksField = new JTextField();
        remarksField.setBounds(100, 287, 300, 45);
        dossier_medical_frame.getContentPane().add(remarksField);
        remarksField.setColumns(10);
    }

    private void loadMedicalRecords() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT record_id, visit_date FROM Medical_Records WHERE patient_id = ?";
        recordIds = new ArrayList<>();
        List<String> visitDates = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                recordIds.add(resultSet.getInt("record_id"));
                visitDates.add(resultSet.getDate("visit_date").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }

        for (String visitDate : visitDates) {
            visitDateComboBox.addItem(visitDate);
        }

        if (!recordIds.isEmpty()) {
            loadRecordData(recordIds.get(0)); // Load the first record by default
        } else {
            JOptionPane.showMessageDialog(dossier_medical_frame, "No medical records found for this patient.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadRecordData(int recordId) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT record_id, patient_id, visit_date, symptoms, diagnosis, treatment, remarque FROM Medical_Records WHERE record_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recordId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patientIdField.setText(String.valueOf(resultSet.getInt("patient_id")));
                visitDateField.setText(resultSet.getDate("visit_date").toString());
                symptomsField.setText(resultSet.getString("symptoms"));
                diagnosisField.setText(resultSet.getString("diagnosis"));
                treatmentField.setText(resultSet.getString("treatment"));
                remarksField.setText(resultSet.getString("remarque"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
}

