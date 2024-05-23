package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cabinet_medical.DatabaseHelper.Doctor;
import cabinet_medical.DatabaseHelper.Patient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JComboBox;

public class Secretaire_old_appointment_vue {

    JFrame secretaire_old_appointment_frame;
    JTable table;
    JTable table_1;
    DefaultTableModel model;
    DefaultTableModel model1;
    JTextField doctorIdField;
    JTextField patientIdField;
    JTextField doctorNameField;
    JTextField doctorSpecialtyField;
    JTextField patientNameField;
    JTextField patientPhoneField;
    JTextField dayField;
    JTextField monthField;
    JTextField yearField;
    JTextField hourField;
    JComboBox<String> specialtyComboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Secretaire_old_appointment_vue window = new Secretaire_old_appointment_vue();
                    window.secretaire_old_appointment_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Secretaire_old_appointment_vue() {
        initialize();
        loadSpecialties();
        loadDoctors();
        loadPatients();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        secretaire_old_appointment_frame = new JFrame();
        secretaire_old_appointment_frame.setBounds(100, 100, 890, 587);
        secretaire_old_appointment_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secretaire_old_appointment_frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 68, 388, 243);
        secretaire_old_appointment_frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        model = new DefaultTableModel();
        Object[] column = {"ID", "Name", "Specialty"};
        model.setColumnIdentifiers(column);
        table.setModel(model);
        scrollPane.setViewportView(table);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                doctorIdField.setText(model.getValueAt(table.getSelectedRow(), 0).toString());
                doctorNameField.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
                doctorSpecialtyField.setText(model.getValueAt(table.getSelectedRow(), 2).toString());
            }
        });

        JLabel lblChooseADoctor = new JLabel("Choose a doctor");
        lblChooseADoctor.setBounds(10, 28, 139, 29);
        lblChooseADoctor.setFont(new Font("Tahoma", Font.BOLD, 14));
        secretaire_old_appointment_frame.getContentPane().add(lblChooseADoctor);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(466, 68, 398, 243);
        secretaire_old_appointment_frame.getContentPane().add(scrollPane_1);

        table_1 = new JTable();
        scrollPane_1.setViewportView(table_1);
        model1 = new DefaultTableModel();
        Object[] column1 = {"ID", "Name", "Phone Number"};
        model1.setColumnIdentifiers(column1);
        table_1.setModel(model1);
        scrollPane_1.setViewportView(table_1);

        table_1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table_1.getSelectedRow() != -1) {
                patientIdField.setText(model1.getValueAt(table_1.getSelectedRow(), 0).toString());
                patientNameField.setText(model1.getValueAt(table_1.getSelectedRow(), 1).toString());
                patientPhoneField.setText(model1.getValueAt(table_1.getSelectedRow(), 2).toString());
            }
        });

        JLabel lblChooseAPatient = new JLabel("Choose a patient");
        lblChooseAPatient.setBounds(466, 28, 139, 29);
        lblChooseAPatient.setFont(new Font("Tahoma", Font.BOLD, 14));
        secretaire_old_appointment_frame.getContentPane().add(lblChooseAPatient);

        doctorIdField = new JTextField();
        doctorIdField.setBounds(73, 353, 168, 23);
        secretaire_old_appointment_frame.getContentPane().add(doctorIdField);
        doctorIdField.setColumns(10);

        JLabel lblDoctorID = new JLabel("Doctor ID:");
        lblDoctorID.setForeground(new Color(128, 128, 128));
        lblDoctorID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDoctorID.setBounds(101, 339, 103, 14);
        secretaire_old_appointment_frame.getContentPane().add(lblDoctorID);

        patientIdField = new JTextField();
        patientIdField.setColumns(10);
        patientIdField.setBounds(576, 353, 168, 23);
        secretaire_old_appointment_frame.getContentPane().add(patientIdField);

        JLabel lblPatientID = new JLabel("Patient ID:");
        lblPatientID.setForeground(Color.GRAY);
        lblPatientID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPatientID.setBounds(608, 339, 103, 14);
        secretaire_old_appointment_frame.getContentPane().add(lblPatientID);

        doctorNameField = new JTextField();
        doctorNameField.setColumns(10);
        doctorNameField.setBounds(68, 410, 173, 20);
        secretaire_old_appointment_frame.getContentPane().add(doctorNameField);

        doctorSpecialtyField = new JTextField();
        doctorSpecialtyField.setColumns(10);
        doctorSpecialtyField.setBounds(73, 467, 173, 20);
        secretaire_old_appointment_frame.getContentPane().add(doctorSpecialtyField);

        patientNameField = new JTextField();
        patientNameField.setColumns(10);
        patientNameField.setBounds(571, 410, 173, 20);
        secretaire_old_appointment_frame.getContentPane().add(patientNameField);

        patientPhoneField = new JTextField();
        patientPhoneField.setColumns(10);
        patientPhoneField.setBounds(571, 467, 173, 20);
        secretaire_old_appointment_frame.getContentPane().add(patientPhoneField);

        JLabel lblDoctorName = new JLabel("Doctor Name");
        lblDoctorName.setForeground(Color.GRAY);
        lblDoctorName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDoctorName.setBounds(106, 397, 98, 14);
        secretaire_old_appointment_frame.getContentPane().add(lblDoctorName);

        JLabel lblDoctorSpecialty = new JLabel("Specialty");
        lblDoctorSpecialty.setForeground(Color.GRAY);
        lblDoctorSpecialty.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDoctorSpecialty.setBounds(101, 452, 98, 17);
        secretaire_old_appointment_frame.getContentPane().add(lblDoctorSpecialty);

        JLabel lblPatientName = new JLabel("Patient Name");
        lblPatientName.setForeground(Color.GRAY);
        lblPatientName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPatientName.setBounds(600, 396, 111, 17);
        secretaire_old_appointment_frame.getContentPane().add(lblPatientName);

        JLabel lblPatientPhone = new JLabel("Phone Number");
        lblPatientPhone.setForeground(Color.GRAY);
        lblPatientPhone.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPatientPhone.setBounds(584, 452, 139, 17);
        secretaire_old_appointment_frame.getContentPane().add(lblPatientPhone);

        JLabel lblDay = new JLabel("Day:");
        lblDay.setBounds(290, 352, 50, 25);
        secretaire_old_appointment_frame.getContentPane().add(lblDay);

        dayField = new JTextField();
        dayField.setBounds(323, 352, 30, 25);
        secretaire_old_appointment_frame.getContentPane().add(dayField);

        JLabel lblMonth = new JLabel("Month:");
        lblMonth.setBounds(435, 352, 50, 25);
        secretaire_old_appointment_frame.getContentPane().add(lblMonth);

        monthField = new JTextField();
        monthField.setBounds(480, 352, 30, 25);
        secretaire_old_appointment_frame.getContentPane().add(monthField);

        JLabel lblYear = new JLabel("Year:");
        lblYear.setBounds(290, 394, 50, 25);
        secretaire_old_appointment_frame.getContentPane().add(lblYear);

        yearField = new JTextField();
        yearField.setBounds(333, 394, 50, 25);
        secretaire_old_appointment_frame.getContentPane().add(yearField);

        JLabel lblHour = new JLabel("Hour:");
        lblHour.setBounds(435, 394, 50, 25);
        secretaire_old_appointment_frame.getContentPane().add(lblHour);

        hourField = new JTextField();
        hourField.setBounds(480, 394, 30, 25);
        secretaire_old_appointment_frame.getContentPane().add(hourField);

        JButton btnNewButton = new JButton("Create Appointment");
        btnNewButton.setBounds(333, 466, 163, 21);
        secretaire_old_appointment_frame.getContentPane().add(btnNewButton);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to create an appointment
                createAppointment();
            }
        });

        specialtyComboBox = new JComboBox<>();
        specialtyComboBox.setBounds(248, 35, 150, 23);
        specialtyComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterDoctorsBySpecialty((String) specialtyComboBox.getSelectedItem());
            }
        });
        secretaire_old_appointment_frame.getContentPane().add(specialtyComboBox);
        
        JButton btnCancle = new JButton("cancle");
        btnCancle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Secretaire_home secretaireHome = new Secretaire_home();
                secretaireHome.secretaire_home_frame.setVisible(true);
                secretaire_old_appointment_frame.dispose();
        	}
        });
        btnCancle.setBounds(732, 508, 119, 21);
        secretaire_old_appointment_frame.getContentPane().add(btnCancle);
    }

    private void loadSpecialties() {
        // Populate the specialtyComboBox with unique specialties from the doctor list
        List<String> specialties = DatabaseHelper.getSpecialties();
        for (String specialty : specialties) {
            specialtyComboBox.addItem(specialty);
        }
    }

    private void loadDoctors() {
        List<DatabaseHelper.Doctor> doctors = DatabaseHelper.getDoctors();
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{doctor.getDoctorId(), doctor.getName(), doctor.getSpecialty()});
        }
    }

    private void loadPatients() {
        List<DatabaseHelper.Patient> patients = DatabaseHelper.getAllPatients();
        for (Patient patient : patients) {
            model1.addRow(new Object[]{patient.getPatientId(), patient.getName(), patient.getPhone()});
        }
    }

    private void filterDoctorsBySpecialty(String specialty) {
        model.setRowCount(0); // Clear the table
        List<DatabaseHelper.Doctor> doctors = DatabaseHelper.getDoctorsBySpecialty(specialty);
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{doctor.getDoctorId(), doctor.getName(), doctor.getSpecialty()});
        }
    }

    private void createAppointment() {
        try {
            int doctorId = Integer.parseInt(doctorIdField.getText());
            int patientId = Integer.parseInt(patientIdField.getText());
            String patientName = patientNameField.getText();
            String patientPhone = patientPhoneField.getText();
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            int hour = Integer.parseInt(hourField.getText());
            
            LocalDateTime appointmentTime = LocalDateTime.of(year, month, day, hour, 0);
            if (hour < 8 || hour > 17) {
                JOptionPane.showMessageDialog(secretaire_old_appointment_frame, "Hour must be between 8 and 17.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseHelper.isDoctorAvailable(doctorId, appointmentTime)) {
                boolean success = DatabaseHelper.addPatientAndAppointment(patientName, patientPhone, doctorId, appointmentTime);
                if (success) {
                    JOptionPane.showMessageDialog(secretaire_old_appointment_frame, "Appointment created successfully.");
                } else {
                    JOptionPane.showMessageDialog(secretaire_old_appointment_frame, "Failed to create appointment.");
                }
            } else {
                JOptionPane.showMessageDialog(secretaire_old_appointment_frame, "The doctor is not available at the selected time.");
            }
        } catch (NumberFormatException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(secretaire_old_appointment_frame, "Please enter valid date and time.");
        }
    }
}

