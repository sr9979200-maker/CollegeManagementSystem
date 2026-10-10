package college.management;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("College Management System - Dashboard");

                setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);


        JLabel title = new JLabel(
                "COLLEGE MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );
        title.setForeground(new Color(15, 60, 125));


        JLabel welcome = new JLabel(
                "Welcome to Dashboard",
                SwingConstants.CENTER
        );

        welcome.setFont(
                new Font("Arial", Font.PLAIN, 22)
        );

                JPanel heading = new JPanel(new GridLayout(2, 1, 0, 10));
                heading.setBackground(Color.WHITE);
                heading.add(title);
                heading.add(welcome);

                JPanel modules = new JPanel(new GridLayout(3, 3, 16, 16));
                modules.setBackground(Color.WHITE);
                modules.add(moduleButton("Student Management", () -> new StudentManagement()));
                modules.add(moduleButton("Faculty Management", () -> new FacultyManagement()));
                modules.add(moduleButton("Course Management", () -> new Course()));
                modules.add(moduleButton("Subject Management", () -> new Subject()));
                modules.add(moduleButton("Attendance", () -> new Attendance()));
                modules.add(moduleButton("Results", () -> new Result()));
                modules.add(moduleButton("Fees", () -> new Fees()));
                modules.add(moduleButton("Notices", () -> new Notice()));
                modules.add(moduleButton("View Saved Records", () -> new StudentRecords()));

                JButton logout = new JButton("Logout");
                logout.setFont(new Font("Arial", Font.BOLD, 16));
                logout.addActionListener(e -> {
                        dispose();
                        new Login().setVisible(true);
                });

                JPanel content = new JPanel(new BorderLayout(16, 20));
                content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
                content.setBackground(Color.WHITE);
                content.add(heading, BorderLayout.NORTH);
                content.add(modules, BorderLayout.CENTER);
                content.add(logout, BorderLayout.SOUTH);

                add(content);
        }

        private JButton moduleButton(String label, Runnable action) {
                JButton button = new JButton(label);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(35, 105, 190));
                button.setFocusPainted(false);
                button.addActionListener(e -> action.run());
                return button;
        }

}