package college.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentRecords extends JFrame {
    private final DefaultTableModel attendanceModel = createModel(
            "Record ID", "Student ID", "Student Name", "Total Classes", "Present", "Percentage", "Saved At");
    private final DefaultTableModel feesModel = createModel(
            "Record ID", "Student ID", "Student Name", "Total Fees", "Paid Fees", "Remaining", "Payment Date");
    private final DefaultTableModel resultsModel = createModel(
            "Record ID", "Student ID", "Student Name", "Subject", "Marks", "Percentage", "Grade", "Saved At");
    private final JLabel attendanceStatus = new JLabel(" ");
    private final JLabel feesStatus = new JLabel(" ");
    private final JLabel resultsStatus = new JLabel(" ");

    public StudentRecords() {
        this(null);
    }

    public StudentRecords(String selectedTab) {
        setTitle("Saved Student Records");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Attendance", createTab(attendanceModel, attendanceStatus,
                "No attendance records found.", this::loadAttendance));
        tabs.addTab("Fees", createTab(feesModel, feesStatus,
                "No fee records found.", this::loadFees));
        tabs.addTab("Results", createTab(resultsModel, resultsStatus,
                "No result records found.", this::loadResults));
        add(tabs);

        if ("Fees".equals(selectedTab)) {
            tabs.setSelectedIndex(1);
        } else if ("Results".equals(selectedTab)) {
            tabs.setSelectedIndex(2);
        }

        loadAttendance();
        loadFees();
        loadResults();
        setVisible(true);
    }

    private JPanel createTab(DefaultTableModel model, JLabel status, String emptyMessage, Runnable refreshAction) {
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refreshAction.run());

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        toolbar.add(refresh);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(toolbar, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(status, BorderLayout.SOUTH);
        status.putClientProperty("emptyMessage", emptyMessage);
        return panel;
    }

    private void loadAttendance() {
        loadRows(attendanceModel, attendanceStatus,
                "SELECT a.id, a.student_id, s.name, a.total_classes, a.present_classes, " +
                        "a.attendance_percentage, a.created_at " +
                        "FROM attendance a JOIN students s ON s.id = a.student_id " +
                        "ORDER BY a.created_at DESC, a.id DESC");
    }

    private void loadFees() {
        loadRows(feesModel, feesStatus,
                "SELECT f.id, f.student_id, s.name, f.total_fees, f.paid_fees, " +
                        "f.remaining_fees, f.payment_date " +
                        "FROM fees f JOIN students s ON s.id = f.student_id " +
                        "ORDER BY f.payment_date DESC, f.id DESC");
    }

    private void loadResults() {
        loadRows(resultsModel, resultsStatus,
                "SELECT r.id, r.student_id, s.name, r.subject, r.marks, r.percentage, r.grade, r.created_at " +
                        "FROM results r JOIN students s ON s.id = r.student_id " +
                        "ORDER BY r.created_at DESC, r.id DESC");
    }

    private void loadRows(DefaultTableModel model, JLabel status, String sql) {
        model.setRowCount(0);
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            int columnCount = model.getColumnCount();
            while (result.next()) {
                Object[] row = new Object[columnCount];
                for (int column = 0; column < columnCount; column++) {
                    row[column] = result.getObject(column + 1);
                }
                model.addRow(row);
            }
            Object emptyMessage = status.getClientProperty("emptyMessage");
            status.setText(model.getRowCount() == 0
                    ? emptyMessage.toString()
                    : "Records: " + model.getRowCount());
        } catch (Exception e) {
            status.setText("Could not load records.");
            JOptionPane.showMessageDialog(this, "Error loading records: " + e.getMessage());
        }
    }

    private static DefaultTableModel createModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}