package college.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentManagement extends JFrame {
    JTextField id, name, email, phone, course, branch, semester, search;
    JTable table;
    DefaultTableModel model;

    public StudentManagement() {
        setTitle("Student Management");
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("STUDENT MANAGEMENT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(350, 15, 350, 35);
        add(title);

        id = field("ID (Auto):", 35, 80, false);
        id.setToolTipText("Database-generated ID. Select a student row to load it for update or delete.");
        name = field("Name:", 35, 120, true);
        email = field("Email:", 35, 160, true);
        phone = field("Phone:", 35, 200, true);
        course = field("Course:", 35, 240, true);
        branch = field("Branch:", 35, 280, true);
        semester = field("Semester:", 35, 320, true);

        JButton add = new JButton("Add");
        add.setBounds(35, 370, 100, 35);
        add(add);
        JButton update = new JButton("Update");
        update.setBounds(145, 370, 100, 35);
        add(update);
        JButton delete = new JButton("Delete");
        delete.setBounds(35, 415, 100, 35);
        add(delete);
        JButton clear = new JButton("Clear");
        clear.setBounds(145, 415, 100, 35);
        add(clear);

        search = new JTextField();
        search.setBounds(370, 70, 300, 30);
        add(search);
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(680, 70, 100, 30);
        add(searchBtn);
        JButton allBtn = new JButton("Show All");
        allBtn.setBounds(790, 70, 100, 30);
        add(allBtn);

        model = new DefaultTableModel(
            new String[]{"ID","Name","Email","Phone","Course","Branch","Semester"}, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(270, 115, 740, 430);
        add(sp);

        add.addActionListener(e -> addStudent());
        update.addActionListener(e -> updateStudent());
        delete.addActionListener(e -> deleteStudent());
        clear.addActionListener(e -> clear());
        searchBtn.addActionListener(e -> searchStudent());
        allBtn.addActionListener(e -> loadStudents());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    id.setText(v(r,0)); name.setText(v(r,1)); email.setText(v(r,2));
                    phone.setText(v(r,3)); course.setText(v(r,4)); branch.setText(v(r,5));
                    semester.setText(v(r,6));
                }
            }
        });

        loadStudents();
        setVisible(true);
    }

    JTextField field(String label, int x, int y, boolean editable) {
        JLabel l = new JLabel(label);
        l.setBounds(x, y, 80, 25);
        add(l);
        JTextField f = new JTextField();
        f.setBounds(x + 85, y, 150, 27);
        f.setEditable(editable);
        add(f);
        return f;
    }

    String v(int r, int c) { return table.getValueAt(r,c).toString(); }

    void addStudent() {
        String sql = "INSERT INTO students(name,email,phone,course,branch,semester) VALUES(?,?,?,?,?,?)";
        try (Connection con=DBConnection.getConnection(); PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1,name.getText()); ps.setString(2,email.getText()); ps.setString(3,phone.getText());
            ps.setString(4,course.getText()); ps.setString(5,branch.getText());
            ps.setInt(6,Integer.parseInt(semester.getText()));
            ps.executeUpdate(); msg("Student added."); loadStudents(); clear();
        } catch(Exception e){ msg("Error: "+e.getMessage()); }
    }

    void loadStudents() {
        model.setRowCount(0);
        try (Connection con=DBConnection.getConnection();
             Statement st=con.createStatement();
             ResultSet rs=st.executeQuery("SELECT * FROM students ORDER BY id DESC")) {
            while(rs.next()) model.addRow(new Object[]{
                rs.getInt("id"),rs.getString("name"),rs.getString("email"),
                rs.getString("phone"),rs.getString("course"),rs.getString("branch"),
                rs.getInt("semester")});
        } catch(Exception e){ msg("Error: "+e.getMessage()); }
    }

    void updateStudent() {
        String sql="UPDATE students SET name=?,email=?,phone=?,course=?,branch=?,semester=? WHERE id=?";
        try(Connection con=DBConnection.getConnection(); PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,name.getText()); ps.setString(2,email.getText()); ps.setString(3,phone.getText());
            ps.setString(4,course.getText()); ps.setString(5,branch.getText());
            ps.setInt(6,Integer.parseInt(semester.getText())); ps.setInt(7,Integer.parseInt(id.getText()));
            ps.executeUpdate(); msg("Student updated."); loadStudents();
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }

    void deleteStudent() {
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("DELETE FROM students WHERE id=?")){
            ps.setInt(1,Integer.parseInt(id.getText())); ps.executeUpdate();
            msg("Student deleted."); loadStudents(); clear();
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }

    void searchStudent() {
        model.setRowCount(0);
        String sql="SELECT * FROM students WHERE name LIKE ? OR course LIKE ? OR branch LIKE ?";
        try(Connection con=DBConnection.getConnection(); PreparedStatement ps=con.prepareStatement(sql)){
            String q="%"+search.getText()+"%"; ps.setString(1,q); ps.setString(2,q); ps.setString(3,q);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) model.addRow(new Object[]{
                rs.getInt("id"),rs.getString("name"),rs.getString("email"),
                rs.getString("phone"),rs.getString("course"),rs.getString("branch"),rs.getInt("semester")});
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }

    void clear(){id.setText("");name.setText("");email.setText("");phone.setText("");course.setText("");branch.setText("");semester.setText("");}
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}