package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor_home {

    JFrame doctor_home_frame;
    private JTable table;
    private DefaultTableModel model;
    private int selectedPatientId;
    private int doctorId;

    public Doctor_home(int doctorId) {
        this.doctorId = doctorId;
        initialize();
        loadAppointments();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Doctor_home window = new Doctor_home(1); // Example doctor ID
                    window.doctor_home_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        doctor_home_frame = new JFrame();
        doctor_home_frame.setBounds(100, 100, 812, 492);
        doctor_home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doctor_home_frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 66, 780, 300);
        doctor_home_frame.getContentPane().add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel();
        Object[] columnNames = {"Appointment ID", "Patient Name", "Date/Time", "Patient ID"};
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
        scrollPane.setViewportView(table);
        table.removeColumn(table.getColumnModel().getColumn(3)); // Hide the Patient ID column

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(255, 69, 0));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBounds(10, 376, 128, 46);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int appointmentId = (int) model.getValueAt(selectedRow, 0);
                    int response = JOptionPane.showConfirmDialog(doctor_home_frame, "Are you sure you want to delete this appointment?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        deleteAppointment(appointmentId);
                        model.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(doctor_home_frame, "Please select an appointment to delete.");
                }
            }
        });
        doctor_home_frame.getContentPane().add(btnDelete);

        JButton btnStartConsultation = new JButton("Start Consultation");
        btnStartConsultation.setBackground(new Color(0, 128, 192));
        btnStartConsultation.setForeground(new Color(255, 255, 255));
        btnStartConsultation.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnStartConsultation.setBounds(589, 376, 199, 46);
        btnStartConsultation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedPatientId = (int) model.getValueAt(selectedRow, 3); // Get the hidden Patient ID
                    StartConsultation consultationvue = new StartConsultation(selectedPatientId, doctorId);
                    consultationvue.start_consultation_frame.setVisible(true);
                    doctor_home_frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(doctor_home_frame, "Please select an appointment to start the consultation.");
                }
            }
        });
        doctor_home_frame.getContentPane().add(btnStartConsultation);
    }

    /**
     * Load doctorappointments from the database.
     */
    private void loadAppointments() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT a.appointment_id, p.name AS patient_name, a.appointment_time, p.patient_id " +
                       "FROM appointments a " +
                       "JOIN patients p ON a.patient_id = p.patient_id " +
                       "WHERE a.doctor_id = ? " +
                       "ORDER BY a.appointment_time ASC";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                String patientName = resultSet.getString("patient_name");
                String appointmentTime = resultSet.getString("appointment_time");
                int patientId = resultSet.getInt("patient_id");
                model.addRow(new Object[]{appointmentId, patientName, appointmentTime, patientId});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }

    /**
     * Delete appointment from the database.
     */
    private void deleteAppointment(int appointmentId) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "DELETE FROM appointments WHERE appointment_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
}
