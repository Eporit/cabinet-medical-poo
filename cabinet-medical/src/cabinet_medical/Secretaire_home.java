package cabinet_medical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Secretaire_home {

    JFrame secretaire_home_frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Secretaire_home window = new Secretaire_home();
                    window.secretaire_home_frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Secretaire_home() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        secretaire_home_frame = new JFrame();
        secretaire_home_frame.setBounds(100, 100, 500, 370);
        secretaire_home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secretaire_home_frame.getContentPane().setLayout(null);

        JButton rendez_vous = new JButton("Appointment ");
        rendez_vous.setForeground(Color.WHITE);
        rendez_vous.setBackground(new Color(0, 128, 192));
        rendez_vous.setFont(new Font("Tahoma", Font.BOLD, 15));
        rendez_vous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Secretaire_appointment_vue appointment_vue = new Secretaire_appointment_vue();
                appointment_vue.secretaire_appointment_vue_frame.setVisible(true);
                secretaire_home_frame.dispose();
            }
        });
        rendez_vous.setBounds(152, 61, 148, 38);
        secretaire_home_frame.getContentPane().add(rendez_vous);

        JButton nouveau = new JButton("New");
        nouveau.setForeground(new Color(255, 255, 255));
        nouveau.setBackground(new Color(0, 128, 192));
        nouveau.setFont(new Font("Tahoma", Font.BOLD, 15));
        nouveau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Secretaire_new_appointment new_appointment = new Secretaire_new_appointment();
                new_appointment.secretaire_new_app_frame.setVisible(true);
                secretaire_home_frame.dispose();
            }
        });
        nouveau.setBounds(27, 200, 107, 27);
        secretaire_home_frame.getContentPane().add(nouveau);

        JButton view = new JButton("Old");
        view.setForeground(new Color(255, 255, 255));
        view.setBackground(new Color(0, 128, 192));
        view.setFont(new Font("Tahoma", Font.BOLD, 15));
        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Secretaire_old_appointment_vue old_appointment_vue = new Secretaire_old_appointment_vue();
                old_appointment_vue.secretaire_old_appointment_frame.setVisible(true);
                secretaire_home_frame.dispose();
            }
        });
        view.setBounds(331, 200, 107, 27);
        secretaire_home_frame.getContentPane().add(view);

        JLabel Ajout = new JLabel("Add");
        Ajout.setFont(new Font("Tahoma", Font.BOLD, 15));
        Ajout.setBounds(209, 144, 54, 38);
        secretaire_home_frame.getContentPane().add(Ajout);
    }
}
