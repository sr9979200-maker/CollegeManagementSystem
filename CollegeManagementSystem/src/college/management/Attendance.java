package college.management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Attendance extends JFrame {
    JTextField studentId,total,present;
    JLabel percentage;

    public Attendance(){
        setTitle("Attendance Management");setSize(500,400);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(null);

        JLabel title=new JLabel("ATTENDANCE MANAGEMENT",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,22));title.setBounds(70,25,350,30);add(title);

        studentId=field("Student ID:",70,90);total=field("Total Classes:",70,135);present=field("Present:",70,180);

        JButton save=new JButton("Save Attendance");save.setBounds(45,230,190,35);add(save);
        JButton view=new JButton("View Records");view.setBounds(255,230,190,35);add(view);
        percentage=new JLabel("Attendance: 0%",SwingConstants.CENTER);
        percentage.setFont(new Font("Arial",Font.BOLD,18));percentage.setBounds(100,285,300,30);add(percentage);

        save.addActionListener(e->save());
        view.addActionListener(e->new StudentRecords("Attendance"));
        setVisible(true);
    }

    JTextField field(String label,int x,int y){JLabel l=new JLabel(label);l.setBounds(x,y,120,25);add(l);
        JTextField f=new JTextField();f.setBounds(x+130,y,200,28);add(f);return f;}

    void save(){
        try{
            int sid=Integer.parseInt(studentId.getText().trim());
            int t=Integer.parseInt(total.getText().trim());
            int p=Integer.parseInt(present.getText().trim());
            if(t<=0||p<0||p>t){msg("Invalid attendance values.");return;}
            double pct=p*100.0/t;
            String sql="INSERT INTO attendance(student_id,total_classes,present_classes,attendance_percentage) VALUES(?,?,?,?)";
            try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
                if(!DBConnection.studentExists(c,sid)){
                    msg("Student ID "+sid+" not found. Add the student first or enter an ID from Student Management.");
                    return;
                }
                ps.setInt(1,sid);ps.setInt(2,t);ps.setInt(3,p);ps.setDouble(4,pct);ps.executeUpdate();
            }
            percentage.setText(String.format("Attendance: %.2f%%",pct));
            msg("Attendance saved.");
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}