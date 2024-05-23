package cabinet_medical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartConsultation {

    JFrame start_consultation_frame;
    private int patientId;
    private int doctorId;

    public StartConsultation(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StartConsultation window = new StartConsultation(1, 1); // Example patient ID and doctor ID
                    window.start_consultation_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        start_consultation_frame = new JFrame();
        start_consultation_frame.setBounds(100, 100, 570, 355);
        start_consultation_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_consultation_frame.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("creation certificat");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Certificat certificat_vue = new Certificat(patientId, doctorId);
                certificat_vue.certificat_frame.setVisible(true);
            }
        });
        btnNewButton.setBackground(new Color(0, 128, 192));
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(127, 101, 241, 46);
        start_consultation_frame.getContentPane().add(btnNewButton);

        JButton btnClickHere = new JButton("creation ordonnance");
        btnClickHere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ordonnance ordonnance_vue = new Ordonnance(patientId, doctorId);
                ordonnance_vue.ordonnance_frame.setVisible(true);
            }
        });
        btnClickHere.setBackground(new Color(0, 128, 192));
        btnClickHere.setForeground(new Color(255, 255, 255));
        btnClickHere.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnClickHere.setBounds(127, 164, 241, 46);
        start_consultation_frame.getContentPane().add(btnClickHere);

        JButton btnNewButton_1 = new JButton("cancel");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Doctor_home doctor_home_vue = new Doctor_home(doctorId); // Pass doctor ID here
                doctor_home_vue.doctor_home_frame.setVisible(true);
                start_consultation_frame.dispose();
            }
        });
        btnNewButton_1.setBounds(127, 267, 85, 21);
        start_consultation_frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("continue");
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Resume resume_vue= new Resume(patientId);
        		resume_vue.resume_frame.setVisible(true);
        		start_consultation_frame.dispose();
        	}
        });
        btnNewButton_2.setBounds(283, 267, 85, 21);
        start_consultation_frame.getContentPane().add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("fiche patient");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Fiche_patient fiche_patient_vue = new Fiche_patient(patientId);
                fiche_patient_vue.fiche_patient_frame.setVisible(true);
            }
        });
        btnNewButton_3.setBounds(370, 21, 159, 21);
        start_consultation_frame.getContentPane().add(btnNewButton_3);

        JButton btnNewButton_3_1 = new JButton("dossier medicale");
        btnNewButton_3_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dossier_medical dossier_medical_vue = new Dossier_medical(patientId);
                dossier_medical_vue.dossier_medical_frame.setVisible(true);
            }
        });
        btnNewButton_3_1.setBounds(370, 52, 159, 21);
        start_consultation_frame.getContentPane().add(btnNewButton_3_1);
    }
}

