package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cabinet_medical.DatabaseHelper.Doctor;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Secretaire_new_appointment {

    JFrame secretaire_new_app_frame;
    JTextField namefield;
    JTextField n_tel_field;
    JTextField hourfield;
    JTextField textField_2;
    JTextField yearfield;
    JTextField monthfield;
    JTextField dayfield;
    JTable table;
    JComboBox<String> specialtyComboBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Secretaire_new_appointment window = new Secretaire_new_appointment();
                    window.secretaire_new_app_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Secretaire_new_appointment() {
        initialize();
        loadSpecialties();
        loadDoctors();
    }

    private void initialize() {
        secretaire_new_app_frame = new JFrame();
        secretaire_new_app_frame.setBounds(100, 100, 840, 519);
        secretaire_new_app_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secretaire_new_app_frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Full name");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setForeground(new Color(128, 128, 128));
        lblNewLabel.setBounds(39, 30, 263, 25);
        secretaire_new_app_frame.getContentPane().add(lblNewLabel);

        namefield = new JTextField();
        namefield.setBounds(39, 61, 405, 31);
        secretaire_new_app_frame.getContentPane().add(namefield);
        namefield.setColumns(10);

        JLabel lblPhoneNumber = new JLabel("Phone number ");
        lblPhoneNumber.setForeground(Color.GRAY);
        lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblPhoneNumber.setBounds(39, 103, 263, 25);
        secretaire_new_app_frame.getContentPane().add(lblPhoneNumber);

        JLabel lblSelectedDoctor = new JLabel("Selected doctor ");
        lblSelectedDoctor.setForeground(Color.GRAY);
        lblSelectedDoctor.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblSelectedDoctor.setBounds(39, 192, 263, 25);
        secretaire_new_app_frame.getContentPane().add(lblSelectedDoctor);

        n_tel_field = new JTextField();
        n_tel_field.setColumns(10);
        n_tel_field.setBounds(39, 139, 405, 31);
        secretaire_new_app_frame.getContentPane().add(n_tel_field);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(39, 228, 405, 31);
        secretaire_new_app_frame.getContentPane().add(textField_2);

        JLabel lblAppointmentDate = new JLabel("Appointment Date");
        lblAppointmentDate.setForeground(Color.GRAY);
        lblAppointmentDate.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblAppointmentDate.setBounds(39, 284, 263, 25);
        secretaire_new_app_frame.getContentPane().add(lblAppointmentDate);

        yearfield = new JTextField();
        yearfield.setColumns(10);
        yearfield.setBounds(64, 320, 45, 31);
        secretaire_new_app_frame.getContentPane().add(yearfield);

        JLabel lblNewLabel_1 = new JLabel("year");
        lblNewLabel_1.setBounds(20, 329, 34, 13);
        secretaire_new_app_frame.getContentPane().add(lblNewLabel_1);

        monthfield = new JTextField();
        monthfield.setColumns(10);
        monthfield.setBounds(185, 320, 45, 31);
        secretaire_new_app_frame.getContentPane().add(monthfield);

        JLabel lblNewLabel_2 = new JLabel("month");
        lblNewLabel_2.setBounds(133, 329, 56, 13);
        secretaire_new_app_frame.getContentPane().add(lblNewLabel_2);

        dayfield = new JTextField();
        dayfield.setColumns(10);
        dayfield.setBounds(278, 321, 45, 31);
        secretaire_new_app_frame.getContentPane().add(dayfield);

        JLabel lblNewLabel_2_1 = new JLabel("day");
        lblNewLabel_2_1.setBounds(251, 330, 45, 13);
        secretaire_new_app_frame.getContentPane().add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_2 = new JLabel("hour");
        lblNewLabel_2_2.setBounds(351, 329, 45, 13);
        secretaire_new_app_frame.getContentPane().add(lblNewLabel_2_2);

        hourfield = new JTextField();
        hourfield.setForeground(Color.BLACK);
        hourfield.setColumns(10);
        hourfield.setBounds(391, 321, 45, 31);
        secretaire_new_app_frame.getContentPane().add(hourfield);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(454, 62, 360, 290);
        secretaire_new_app_frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(new Color(192, 192, 192));
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Doctor Name", "Specialty", "Dr ID"}
        ) {
            boolean[] columnEditables = new boolean[] {
                false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(248);
        table.getColumnModel().getColumn(1).setPreferredWidth(302);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    String selectedDoctorName = table.getValueAt(table.getSelectedRow(), 0).toString();
                    textField_2.setText(selectedDoctorName);
                }
            }
        });

        specialtyComboBox = new JComboBox<>();
        specialtyComboBox.setBounds(454, 30, 200, 25);
        specialtyComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterDoctorsBySpecialty((String) specialtyComboBox.getSelectedItem());
            }
        });
        secretaire_new_app_frame.getContentPane().add(specialtyComboBox);

        JButton btnNewButton = new JButton("Add");
        btnNewButton.setBounds(190, 410, 85, 21);
        secretaire_new_app_frame.getContentPane().add(btnNewButton);
        
        JButton btnCancle = new JButton("Cancel");
        btnCancle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		secretaire_new_app_frame.dispose();  // Close the current frame
                openSecretaireHomeFrame();  // Open the secretaire_home_frame
        	}
        });
        btnCancle.setBounds(729, 451, 85, 21);
        secretaire_new_app_frame.getContentPane().add(btnCancle);
        btnNewButton.addActionListener(e -> addAppointment());
    }

    private void loadSpecialties() {
        List<String> specialties = DatabaseHelper.getSpecialties();
        for (String specialty : specialties) {
            specialtyComboBox.addItem(specialty);
        }
    }

    private void loadDoctors() {
        List<Doctor> doctors = DatabaseHelper.getDoctors();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSpecialty(), doctor.getDoctorId()});
        }
    }

    private void filterDoctorsBySpecialty(String specialty) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table
        List<Doctor> doctors = DatabaseHelper.getDoctorsBySpecialty(specialty);
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSpecialty(), doctor.getDoctorId()});
        }
    }

    private void addAppointment() {
        try {
            String yearText = yearfield.getText();
            String monthText = monthfield.getText();
            String dayText = dayfield.getText();
            String hourText = hourfield.getText();

            // Check if any of the date fields are empty
            if (yearText.isEmpty() || monthText.isEmpty() || dayText.isEmpty() || hourText.isEmpty()) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Please enter valid numbers for date and time.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nameText = namefield.getText();
            String n_tel_Text = n_tel_field.getText();

            // Check if name or phone fields are empty
            if (nameText.isEmpty() || n_tel_Text.isEmpty()) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Name and phone number cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int year = Integer.parseInt(yearText);
            int month = Integer.parseInt(monthText);
            int day = Integer.parseInt(dayText);
            int hour = Integer.parseInt(hourText);

            // Ensure valid ranges for hour
            if (hour < 8 || hour > 17) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Hour must be between 8 and 17.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate the date using java.time.LocalDate
            LocalDate date;
            try {
                date = LocalDate.of(year, month, day);
            } catch (DateTimeParseException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Invalid date! Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime appointmentTime = date.atTime(hour, 0);

            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "No doctor selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int doctorId = Integer.parseInt(table.getValueAt(selectedRow, 2).toString());

            if (!DatabaseHelper.isDoctorAvailable(doctorId, appointmentTime)) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Doctor is not available at the selected time!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseHelper.addPatientAndAppointment(nameText, n_tel_Text, doctorId, appointmentTime)) {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Appointment added successfully!");
                secretaire_new_app_frame.dispose();  // Close the current frame
                openSecretaireHomeFrame();  // Open the secretaire_home_frame
            } else {
                JOptionPane.showMessageDialog(secretaire_new_app_frame, "Failed to add appointment!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(secretaire_new_app_frame, "Invalid input! Please enter valid numbers for date and time.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openSecretaireHomeFrame() {
        Secretaire_home secretaireHome = new Secretaire_home();
        secretaireHome.secretaire_home_frame.setVisible(true);
    }
}
