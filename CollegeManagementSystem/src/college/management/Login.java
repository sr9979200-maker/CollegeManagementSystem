package college.management;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;

    public Login() {

        setTitle("College Management System - Login");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        createLoginUI();
    }

    private void createLoginUI() {

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        // =====================================================
        // LEFT PANEL
        // =====================================================

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(220, 238, 255));

        JLabel cap = new JLabel("🎓");
        cap.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        cap.setHorizontalAlignment(SwingConstants.CENTER);
        cap.setBounds(150, 35, 220, 80);
        leftPanel.add(cap);

        JLabel college1 = new JLabel("College");
        college1.setFont(new Font("Arial", Font.BOLD, 42));
        college1.setForeground(new Color(15, 60, 125));
        college1.setHorizontalAlignment(SwingConstants.CENTER);
        college1.setBounds(50, 115, 420, 55);
        leftPanel.add(college1);

        JLabel college2 = new JLabel("Management System");
        college2.setFont(new Font("Arial", Font.BOLD, 35));
        college2.setForeground(new Color(15, 60, 125));
        college2.setHorizontalAlignment(SwingConstants.CENTER);
        college2.setBounds(20, 170, 480, 55);
        leftPanel.add(college2);

        JLabel tagline = new JLabel("Manage   •   Learn   •   Grow");
        tagline.setFont(new Font("Arial", Font.PLAIN, 22));
        tagline.setForeground(new Color(20, 70, 140));
        tagline.setHorizontalAlignment(SwingConstants.CENTER);
        tagline.setBounds(40, 235, 440, 40);
        leftPanel.add(tagline);

        // College image-like area
        JPanel campusPanel = new JPanel();
        campusPanel.setBounds(20, 300, 480, 270);
        campusPanel.setBackground(new Color(245, 248, 250));
        campusPanel.setLayout(new GridBagLayout());

        JLabel campusText = new JLabel(
                "<html><center>COLLEGE CAMPUS<br><br>" +
                "🏫<br><br>" +
                "Welcome to College<br>" +
                "Management System</center></html>"
        );

        campusText.setFont(new Font("Arial", Font.BOLD, 20));
        campusText.setForeground(new Color(40, 70, 100));

        campusPanel.add(campusText);

        leftPanel.add(campusPanel);

        // Bottom blue area
        JPanel bottomBlue = new JPanel();
        bottomBlue.setBackground(new Color(20, 75, 150));
        bottomBlue.setBounds(0, 570, 520, 100);
        leftPanel.add(bottomBlue);


        // =====================================================
        // RIGHT PANEL
        // =====================================================

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);

        JLabel loginTitle = new JLabel("Login to Your Account");

        loginTitle.setFont(
                new Font("Arial", Font.BOLD, 40)
        );

        loginTitle.setForeground(
                new Color(15, 60, 125)
        );

        loginTitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        loginTitle.setBounds(20, 90, 520, 55);

        rightPanel.add(loginTitle);


        JLabel instruction = new JLabel(
                "<html><center>Please enter your username and password<br>" +
                "to continue</center></html>"
        );

        instruction.setFont(
                new Font("Arial", Font.PLAIN, 22)
        );

        instruction.setForeground(
                new Color(70, 80, 95)
        );

        instruction.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        instruction.setBounds(40, 155, 480, 70);

        rightPanel.add(instruction);


        // =====================================================
        // USERNAME
        // =====================================================

        JLabel userIcon = new JLabel("👤");

        userIcon.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 30)
        );

        userIcon.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        userIcon.setOpaque(true);

        userIcon.setBackground(
                new Color(220, 235, 250)
        );

        userIcon.setBounds(50, 260, 65, 65);

        rightPanel.add(userIcon);


        usernameField = new JTextField();

        usernameField.setFont(
                new Font("Arial", Font.PLAIN, 20)
        );

        usernameField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 200, 215), 2
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 15, 5, 15
                        )
                )
        );

        usernameField.setBounds(125, 260, 400, 65);

        rightPanel.add(usernameField);


        // =====================================================
        // PASSWORD
        // =====================================================

        JLabel passIcon = new JLabel("🔒");

        passIcon.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 30)
        );

        passIcon.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        passIcon.setOpaque(true);

        passIcon.setBackground(
                new Color(220, 235, 250)
        );

        passIcon.setBounds(50, 370, 65, 65);

        rightPanel.add(passIcon);


        passwordField = new JPasswordField();

        passwordField.setFont(
                new Font("Arial", Font.PLAIN, 20)
        );

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 200, 215), 2
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 15, 5, 15
                        )
                )
        );

        passwordField.setBounds(125, 370, 400, 65);

        rightPanel.add(passwordField);


        // =====================================================
        // SHOW PASSWORD
        // =====================================================

        showPassword = new JCheckBox("Show Password");

        showPassword.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        showPassword.setBackground(Color.WHITE);

        showPassword.setBounds(125, 450, 200, 35);

        rightPanel.add(showPassword);


        showPassword.addActionListener(e -> {

            if (showPassword.isSelected()) {

                passwordField.setEchoChar((char) 0);

            } else {

                passwordField.setEchoChar('•');
            }
        });


        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        JButton loginButton = new JButton("➜  Login");

        loginButton.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        loginButton.setForeground(Color.WHITE);

        loginButton.setBackground(
                new Color(35, 105, 190)
        );

        loginButton.setFocusPainted(false);

        loginButton.setBounds(50, 525, 225, 65);

        rightPanel.add(loginButton);


        // =====================================================
        // EXIT BUTTON
        // =====================================================

        JButton exitButton = new JButton("➜  Exit");

        exitButton.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        exitButton.setForeground(Color.WHITE);

        exitButton.setBackground(
                new Color(215, 65, 60)
        );

        exitButton.setFocusPainted(false);

        exitButton.setBounds(300, 525, 225, 65);

        rightPanel.add(exitButton);


        // Login action

        loginButton.addActionListener(e -> login());


        // Exit action

        exitButton.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        // Enter key

        passwordField.addActionListener(e -> login());


        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
    }


    // =========================================================
    // LOGIN METHOD
    // =========================================================

    private void login() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());


        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username!"
            );

            usernameField.requestFocus();

            return;
        }


        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter password!"
            );

            passwordField.requestFocus();

            return;
        }


        String sql =
                "SELECT * FROM users " +
                "WHERE username = ? AND password = ?";


        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Open Dashboard
                new Dashboard().setVisible(true);

                // Close login
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.setText("");
            }

            rs.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Error:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new Login().setVisible(true);

        });
    }
}