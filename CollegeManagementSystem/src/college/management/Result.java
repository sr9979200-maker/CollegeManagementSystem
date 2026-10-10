package college.management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Result extends JFrame {
    JTextField studentId,subject,marks; JLabel percentage,grade;

    public Result(){
        setTitle("Result / Marks");setSize(500,450);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(null);

        JLabel title=new JLabel("RESULT / MARKS MANAGEMENT",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,21));title.setBounds(60,25,370,30);add(title);

        studentId=field("Student ID:",70,90);subject=field("Subject:",70,135);marks=field("Marks:",70,180);

        JButton calc=new JButton("Calculate & Save");calc.setBounds(140,230,220,35);add(calc);

        percentage=new JLabel("Percentage: 0%",SwingConstants.CENTER);
        percentage.setFont(new Font("Arial",Font.BOLD,18));percentage.setBounds(100,285,300,30);add(percentage);
        grade=new JLabel("Grade: -",SwingConstants.CENTER);
        grade.setFont(new Font("Arial",Font.BOLD,18));grade.setBounds(100,325,300,30);add(grade);

        JButton view=new JButton("View Records");view.setBounds(150,370,200,35);add(view);

        calc.addActionListener(e->calculate());
        view.addActionListener(e->new StudentRecords("Results"));
        setVisible(true);
    }

    JTextField field(String label,int x,int y){JLabel l=new JLabel(label);l.setBounds(x,y,120,25);add(l);
        JTextField f=new JTextField();f.setBounds(x+130,y,200,28);add(f);return f;}

    void calculate(){
        try{
            int sid=Integer.parseInt(studentId.getText().trim());String sub=subject.getText().trim();
            double m=Double.parseDouble(marks.getText().trim());
            if(sub.isEmpty()){msg("Enter a subject.");return;}
            if(m<0||m>100){msg("Marks must be between 0 and 100.");return;}
            String g=m>=90?"A+":m>=80?"A":m>=70?"B":m>=60?"C":m>=50?"D":"F";

            String sql="INSERT INTO results(student_id,subject,marks,percentage,grade) VALUES(?,?,?,?,?)";
            try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
                if(!DBConnection.studentExists(c,sid)){
                    msg("Student ID "+sid+" not found. Add the student first or enter an ID from Student Management.");
                    return;
                }
                ps.setInt(1,sid);ps.setString(2,sub);ps.setDouble(3,m);ps.setDouble(4,m);ps.setString(5,g);ps.executeUpdate();
            }
            percentage.setText(String.format("Percentage: %.2f%%",m));grade.setText("Grade: "+g);
            msg("Result saved.");
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}