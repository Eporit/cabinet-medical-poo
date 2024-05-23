package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cabinet_medical.DatabaseHelper.Appointment;
import cabinet_medical.DatabaseHelper.Doctor;
import cabinet_medical.DatabaseHelper.Patient;


public class Secretaire_appointment_vue {

    JFrame secretaire_appointment_vue_frame;
    private JTextField date_hour;
    private JTable table;
    DefaultTableModel model;
    private int selectedAppointmentId;
    private int selectedDoctorId;
    private int selectedPatientId;
    private JComboBox<Doctor> doctorComboBox;
    private JComboBox<Patient> patientComboBox;
    private JComboBox<String> specialtyComboBox;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Secretaire_appointment_vue window = new Secretaire_appointment_vue();
                    window.secretaire_appointment_vue_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Secretaire_appointment_vue() {
        initialize();
        loadSpecialties();
        loadAppointments();
        loadDoctorsIntoComboBox();
        loadPatientsIntoComboBox();
    }

    private void initialize() {
        secretaire_appointment_vue_frame = new JFrame();
        secretaire_appointment_vue_frame.setBounds(100, 100, 840, 519);
        secretaire_appointment_vue_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secretaire_appointment_vue_frame.getContentPane().setLayout(null);

        JLabel date_h = new JLabel("Date/Hour");
        date_h.setForeground(new Color(128, 128, 128));
        date_h.setFont(new Font("Tahoma", Font.BOLD, 15));
        date_h.setBounds(10, 317, 78, 19);
        secretaire_appointment_vue_frame.getContentPane().add(date_h);

        JLabel medCH = new JLabel("Doctor");
        medCH.setForeground(new Color(128, 128, 128));
        medCH.setFont(new Font("Tahoma", Font.BOLD, 15));
        medCH.setBounds(10, 356, 78, 19);
        secretaire_appointment_vue_frame.getContentPane().add(medCH);

        JLabel patCH = new JLabel("Patient");
        patCH.setForeground(new Color(128, 128, 128));
        patCH.setFont(new Font("Tahoma", Font.BOLD, 15));
        patCH.setBounds(10, 395, 78, 19);
        secretaire_appointment_vue_frame.getContentPane().add(patCH);

        date_hour = new JTextField();
        date_hour.setColumns(10);
        date_hour.setBounds(98, 317, 156, 27);
        secretaire_appointment_vue_frame.getContentPane().add(date_hour);

        doctorComboBox = new JComboBox<>();
        doctorComboBox.setBounds(98, 356, 156, 25);
        secretaire_appointment_vue_frame.getContentPane().add(doctorComboBox);

        patientComboBox = new JComboBox<>();
        patientComboBox.setBounds(98, 395, 156, 25);
        secretaire_appointment_vue_frame.getContentPane().add(patientComboBox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 24, 752, 267);
        secretaire_appointment_vue_frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                selectedAppointmentId = (int) model.getValueAt(i, 0);
                selectedPatientId = (int) model.getValueAt(i, 4);
                selectedDoctorId = (int) model.getValueAt(i, 5);
                date_hour.setText(model.getValueAt(i, 2).toString());
                doctorComboBox.setSelectedItem(new Doctor(selectedDoctorId, model.getValueAt(i, 3).toString()));
                patientComboBox.setSelectedItem(new Patient(selectedPatientId, model.getValueAt(i, 1).toString()));
            }
        });
        model = new DefaultTableModel();
        Object[] column = { "Appointment ID", "Full Name", "Date/Hour", "Doctor", "Patient ID", "Doctor ID" };
        model.setColumnIdentifiers(column);
        table.setModel(model);
        table.removeColumn(table.getColumnModel().getColumn(4));
        table.removeColumn(table.getColumnModel().getColumn(4)); // Doctor ID will shift to the last column after removing Patient ID column
        scrollPane.setViewportView(table);

        JButton addition = new JButton("ADD");
        addition.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Secretaire_home secretaireHome = new Secretaire_home();
                secretaireHome.secretaire_home_frame.setVisible(true);
                secretaire_appointment_vue_frame.dispose();
            }
        });
        addition.setForeground(new Color(248, 248, 255));
        addition.setBackground(new Color(139, 0, 139));
        addition.setFont(new Font("Tahoma", Font.BOLD, 15));
        addition.setBounds(467, 424, 89, 36);
        secretaire_appointment_vue_frame.getContentPane().add(addition);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    try {
                        String dateTimeStr = date_hour.getText();
                        LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);

                        Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
                        Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();

                        if (!DatabaseHelper.isDoctorAvailable(selectedDoctor.getDoctorId(), appointmentTime, selectedAppointmentId)) {
                            JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Doctor is not available at the selected time!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (DatabaseHelper.updateAppointment(selectedAppointmentId, selectedDoctor.getDoctorId(), appointmentTime, selectedPatient.getPatientId())) {
                            model.setValueAt(selectedPatient.getName(), i, 1);
                            model.setValueAt(date_hour.getText(), i, 2);
                            model.setValueAt(selectedDoctor.getName(), i, 3);
                            model.setValueAt(selectedPatient.getPatientId(), i, 4);
                            model.setValueAt(selectedDoctor.getDoctorId(), i, 5);
                            JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Updated successfully");

                            Secretaire_home secretaireHome = new Secretaire_home();
                            secretaireHome.secretaire_home_frame.setVisible(true);
                            secretaire_appointment_vue_frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Failed to update appointment", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Invalid doctor ID", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Invalid date/time format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Please select a row first", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnUpdate.setForeground(new Color(245, 255, 250));
        btnUpdate.setBackground(new Color(47, 79, 79));
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnUpdate.setBounds(570, 425, 89, 36);
        secretaire_appointment_vue_frame.getContentPane().add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    int appointmentId = (int) model.getValueAt(i, 0);
                    if (DatabaseHelper.deleteAppointment(appointmentId)) {
                        model.removeRow(i);
                        JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Failed to delete appointment", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(secretaire_appointment_vue_frame, "Please select a row first", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDelete.setForeground(new Color(248, 248, 255));
        btnDelete.setBackground(new Color(139, 0, 0));
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnDelete.setBounds(675, 425, 89, 36);
        secretaire_appointment_vue_frame.getContentPane().add(btnDelete);

        specialtyComboBox = new JComboBox<>();
        specialtyComboBox.setBounds(98, 290, 156, 25);
        specialtyComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterDoctorsBySpecialty((String) specialtyComboBox.getSelectedItem());
            }
        });
        secretaire_appointment_vue_frame.getContentPane().add(specialtyComboBox);
    }

    private void loadSpecialties() {
        List<String> specialties = DatabaseHelper.getSpecialties();
        for (String specialty : specialties) {
            specialtyComboBox.addItem(specialty);
        }
    }

    private void loadAppointments() {
        List<Appointment> appointments = DatabaseHelper.getAppointments();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Appointment appointment : appointments) {
            model.addRow(new Object[]{
                appointment.getAppointmentId(),
                appointment.getPatientName(),
                appointment.getAppointmentTime().format(dateTimeFormatter),
                appointment.getDoctorName() + " (ID: " + appointment.getDoctorId() + ")",
                appointment.getPatientId(),
                appointment.getDoctorId()
            });
        }
    }

    private void loadDoctorsIntoComboBox() {
        List<Doctor> doctors = DatabaseHelper.getDoctors();
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(doctor);
        }
    }

    private void loadPatientsIntoComboBox() {
        List<Patient> patients = DatabaseHelper.getAllPatients();
        for (Patient patient : patients) {
            patientComboBox.addItem(patient);
        }
    }

    private void filterDoctorsBySpecialty(String specialty) {
        doctorComboBox.removeAllItems(); // Clear the combo box
        List<Doctor> doctors = DatabaseHelper.getDoctorsBySpecialty(specialty);
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(doctor);
        }
    }
}


