package cabinet_medical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Method to get the list of all doctors
    public static List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        ResultSet resultSet = dbConnection.executeQuery("SELECT doctor_id, name, specialty, email, password, n_tel FROM doctors");

        try {
            while (resultSet.next()) {
                int doctorId = resultSet.getInt("doctor_id");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                String email = resultSet.getString("email");
                String hashedPassword = resultSet.getString("password");
                long phoneNumber = resultSet.getLong("n_tel");
                doctors.add(new Doctor(doctorId, name, specialty, email, hashedPassword, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }

        return doctors;
    }

    // Method to get the list of all unique specialties
    public static List<String> getSpecialties() {
        List<String> specialties = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT specialty FROM doctors";

        try (ResultSet resultSet = dbConnection.executeQuery(query)) {
            while (resultSet.next()) {
                specialties.add(resultSet.getString("specialty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }

        return specialties;
    }

    // Method to get the list of doctors filtered by specialty
    public static List<Doctor> getDoctorsBySpecialty(String specialty) {
        List<Doctor> doctors = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT doctor_id, name, specialty, email, password, n_tel FROM doctors WHERE specialty = ?";

        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, specialty);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int doctorId = resultSet.getInt("doctor_id");
                String name = resultSet.getString("name");
                String specialtyValue = resultSet.getString("specialty");
                String email = resultSet.getString("email");
                String hashedPassword = resultSet.getString("password");
                long phoneNumber = resultSet.getLong("n_tel");
                doctors.add(new Doctor(doctorId, name, specialtyValue, email, hashedPassword, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }

        return doctors;
    }

    // Method to get the list of all patients
    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT patient_id, name, phone FROM patients";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int patientId = resultSet.getInt("patient_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                patients.add(new Patient(patientId, name, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }

        return patients;
    }

    // Method to check if a doctor is available at a specific time
    public static boolean isDoctorAvailable(int doctorId, LocalDateTime appointmentTime) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT COUNT(*) AS count FROM appointments WHERE doctor_id = ? AND appointment_time = TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS')";
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, doctorId);
            statement.setString(2, appointmentTime.format(dateTimeFormatter));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                System.out.println("Doctor is not available at the selected time: " + appointmentTime);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
        return true;
    }

    // Overloaded method to check if a doctor is available excluding a specific appointment
    public static boolean isDoctorAvailable(int doctorId, LocalDateTime appointmentTime, int excludeAppointmentId) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT COUNT(*) AS count FROM appointments WHERE doctor_id = ? AND appointment_time = TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS') AND appointment_id != ?";
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, doctorId);
            statement.setString(2, appointmentTime.format(dateTimeFormatter));
            statement.setInt(3, excludeAppointmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                System.out.println("Doctor is not available at the selected time: " + appointmentTime);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
        return true;
    }

    // Method to add a new patient and create an appointment
    public static boolean addPatientAndAppointment(String patientName, String phoneNumber, int doctorId, LocalDateTime appointmentTime) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();
        String addPatientQuery = "INSERT INTO patients (name, phone) VALUES (?, ?)";
        String getPatientIdQuery = "SELECT patients_seq.CURRVAL FROM dual";
        String addAppointmentQuery = "INSERT INTO appointments (patient_id, doctor_id, appointment_time) VALUES (?, ?, TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'))";

        try {
            connection.setAutoCommit(false); // Begin transaction

            int patientId = -1;

            // Insert patient and get patient ID
            try (PreparedStatement patientStatement = connection.prepareStatement(addPatientQuery)) {
                patientStatement.setString(1, patientName);
                patientStatement.setString(2, phoneNumber);
                patientStatement.executeUpdate();
            }

            try (PreparedStatement getIdStatement = connection.prepareStatement(getPatientIdQuery)) {
                ResultSet rs = getIdStatement.executeQuery();
                if (rs.next()) {
                    patientId = rs.getInt(1);
                }
            }

            if (patientId == -1) {
                throw new SQLException("Failed to retrieve patient ID.");
            }

            // Insert appointment
            try (PreparedStatement appointmentStatement = connection.prepareStatement(addAppointmentQuery)) {
                appointmentStatement.setInt(1, patientId);
                appointmentStatement.setInt(2, doctorId);
                appointmentStatement.setString(3, appointmentTime.format(dateTimeFormatter));
                appointmentStatement.executeUpdate();
            }

            connection.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.close();
        }
    }

    // Method to get the list of all appointments
    public static List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "SELECT a.appointment_id, p.name AS patient_name, d.name AS doctor_name, a.appointment_time, a.doctor_id, a.patient_id " +
                       "FROM appointments a " +
                       "JOIN patients p ON a.patient_id = p.patient_id " +
                       "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                       "ORDER BY a.appointment_time ASC";
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                String patientName = resultSet.getString("patient_name");
                String doctorName = resultSet.getString("doctor_name");
                LocalDateTime appointmentTime = resultSet.getTimestamp("appointment_time").toLocalDateTime();
                int doctorId = resultSet.getInt("doctor_id");
                int patientId = resultSet.getInt("patient_id");
                appointments.add(new Appointment(appointmentId, patientName, doctorName, appointmentTime, doctorId, patientId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close();
        }
        return appointments;
    }

    // Method to update an appointment
    public static boolean updateAppointment(int appointmentId, int doctorId, LocalDateTime appointmentTime, int patientId) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "UPDATE appointments SET doctor_id = ?, appointment_time = ?, patient_id = ? WHERE appointment_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, doctorId);
            statement.setObject(2, appointmentTime);
            statement.setInt(3, patientId);
            statement.setInt(4, appointmentId);
            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.close();
        }
    }

    // Method to delete an appointment
    public static boolean deleteAppointment(int appointmentId) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String query = "DELETE FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.close();
        }
    }


    public static class Doctor {
        private int doctor_Id;
        private String name;
        private String specialty;
        private String email;
        private String Password;
        private long n_Tel;

        private List<Patient> patients = new ArrayList<>();
        private List<Appointment> appointments = new ArrayList<>();
        //consctucteur
        public Doctor(int doctorId, String name, String specialty, String email, String Password, long nTel) {
            this.doctor_Id = doctorId;
            this.name = name;
            this.specialty = specialty;
            this.email = email;
            this.Password = Password;
            this.n_Tel = nTel;
        }
        public Doctor(int doctorId, String name) {
            this.doctor_Id = doctorId;
            this.name = name;
        }

        // Getters
        public int getDoctorId() { return doctor_Id; }
        public String getName() { return name; }
        public String getSpecialty() { return specialty; }
        public String getEmail() { return email; }
        public String getHashedPassword() { return Password; }
        public long getNTel() { return n_Tel; }
        public List<Patient> getPatients() { return patients; }
        public List<Appointment> getAppointments() { return appointments; }

        // Setters
        public void setDoctorId(int doctorId) { this.doctor_Id = doctorId; }
        public void setName(String name) { this.name = name; }
        public void setSpecialty(String specialty) { this.specialty = specialty; }
        public void setEmail(String email) { this.email = email; }
        public void setHashedPassword(String hashedPassword) { this.Password = hashedPassword; }
        public void setNTel(long nTel) { this.n_Tel = nTel; }

        //other methods
        @Override
        public String toString() {
            return doctor_Id + " - " + name; 
        }

    }
    public static class Appointment {
        private int appointmentId;
        private String patientName;
        private String doctorName;
        private LocalDateTime appointmentTime;
        private int doctorId;
        private int patientId;

        public Appointment(int appointmentId, String patientName, String doctorName, LocalDateTime appointmentTime, int doctorId, int patientId) {
            this.appointmentId = appointmentId;
            this.patientName = patientName;
            this.doctorName = doctorName;
            this.appointmentTime = appointmentTime;
            this.doctorId = doctorId;
            this.patientId = patientId;
        }

        public int getAppointmentId() { return appointmentId; }
        public String getPatientName() { return patientName; }
        public String getDoctorName() { return doctorName; }
        public LocalDateTime getAppointmentTime() { return appointmentTime; }
        public int getDoctorId() { return doctorId; }
        public int getPatientId() { return patientId; }
    }

    public static class Patient {
        private int patientId;
        private String name;
        private String phone;
        private Date dateDeNaissance;
        private double weight;
        private double height;
        private String medicalHistory;
        private String surgicalHistory;

        private List<Doctor> doctors = new ArrayList<>();
        private List<Appointment> appointments = new ArrayList<>();
        private List<MedicalRecord> medicalRecords = new ArrayList<>();

        public Patient(int patientId, String name, String phone, Date dateDeNaissance,
                       double weight, double height, String medicalHistory, String surgicalHistory) {
            this.patientId = patientId;
            this.name = name;
            this.phone = phone;
            this.dateDeNaissance = dateDeNaissance;
            this.weight = weight;
            this.height = height;
            this.medicalHistory = medicalHistory;
            this.surgicalHistory = surgicalHistory;
        }
        public Patient(int patientId, String name) {
            this.patientId = patientId;
            this.name = name;
        }
        public Patient(int id, String name, String phoneNumber) {
            this.patientId = id;
            this.name = name;
            this.phone = phoneNumber;
        }

        // Getters
        public int getPatientId() { return patientId; }
        public String getName() { return name; }
        public String getPhone() { return phone; }
        public Date getDateDeNaissance() { return dateDeNaissance; }
        public double getWeight() { return weight; }
        public double getHeight() { return height; }
        public String getMedicalHistory() { return medicalHistory; }
        public String getSurgicalHistory() { return surgicalHistory; }
        public List<Doctor> getDoctors() { return doctors; }
        public List<Appointment> getAppointments() { return appointments; }
        public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }

        // Setters
        public void setPatientId(int patientId) { this.patientId = patientId; }
        public void setName(String name) { this.name = name; }
        public void setPhone(String phone) { this.phone = phone; }
        public void setDateDeNaissance(Date dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
        public void setWeight(double weight) { this.weight = weight; }
        public void setHeight(double height) { this.height = height; }
        public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
        public void setSurgicalHistory(String surgicalHistory) { this.surgicalHistory = surgicalHistory; }
        
        
        @Override
        public String toString() {
            return patientId + " - " + name; 
        }
    }
    
    public static class Secretaire {
        private int secretaireId;
        private String name;
        private String email;
        private String hashedPassword;
        private long nTel;

        public Secretaire(int secretaireId, String name, String email, String hashedPassword, long nTel) {
            this.secretaireId = secretaireId;
            this.name = name;
            this.email = email;
            this.hashedPassword = hashedPassword;
            this.nTel = nTel;
        }

        // Getters
        public int getSecretaireId() { return secretaireId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getHashedPassword() { return hashedPassword; }
        public long getNTel() { return nTel; }

        // Setters
        public void setSecretaireId(int secretaireId) { this.secretaireId = secretaireId; }
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
        public void setNTel(long nTel) { this.nTel = nTel; }

    }
public static class MedicalRecord {
    private int recordId;
    private int patientId;
    private Date visitDate;
    private String symptoms;
    private String diagnosis;
    private String treatment;
    private String remarque;

    public MedicalRecord(int recordId, int patientId, Date visitDate, String symptoms,
                         String diagnosis, String treatment, String remarque) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.visitDate = visitDate;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.remarque = remarque;
    }

    // Getters
    public int getRecordId() { return recordId; }
    public int getPatientId() { return patientId; }
    public Date getVisitDate() { return visitDate; }
    public String getSymptoms() { return symptoms; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getRemarque() { return remarque; }

    // Setters
    public void setRecordId(int recordId) { this.recordId = recordId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public void setRemarque(String remarque) { this.remarque = remarque; }

}
}

