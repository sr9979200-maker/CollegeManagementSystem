package college.management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Fees extends JFrame {
    JTextField studentId,total,paid; JLabel remaining;

    public Fees(){
        setTitle("Fees Management");setSize(500,420);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(null);

        JLabel title=new JLabel("FEES MANAGEMENT",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24));title.setBounds(80,25,340,30);add(title);

        studentId=field("Student ID:",70,90);total=field("Total Fees:",70,135);paid=field("Paid Fees:",70,180);

        JButton save=new JButton("Save Payment");save.setBounds(150,230,200,35);add(save);
        remaining=new JLabel("Remaining: 0",SwingConstants.CENTER);
        remaining.setFont(new Font("Arial",Font.BOLD,18));remaining.setBounds(100,285,300,30);add(remaining);

        JButton view=new JButton("View Records");view.setBounds(150,335,200,35);add(view);

        save.addActionListener(e->save());
        view.addActionListener(e->new StudentRecords("Fees"));
        setVisible(true);
    }

    JTextField field(String label,int x,int y){JLabel l=new JLabel(label);l.setBounds(x,y,120,25);add(l);
        JTextField f=new JTextField();f.setBounds(x+130,y,200,28);add(f);return f;}

    void save(){
        try{
            int sid=Integer.parseInt(studentId.getText().trim());
            double t=Double.parseDouble(total.getText().trim()),p=Double.parseDouble(paid.getText().trim());
            if(t<0||p<0||p>t){msg("Invalid fee values.");return;}
            double r=t-p;
            try(Connection c=DBConnection.getConnection()){
                if(!DBConnection.studentExists(c,sid)){
                    msg("Student ID "+sid+" not found. Add the student first or enter an ID from Student Management.");
                    return;
                }
                try(PreparedStatement ps=c.prepareStatement("INSERT INTO fees(student_id,total_fees,paid_fees,remaining_fees) VALUES(?,?,?,?)")){
                ps.setInt(1,sid);ps.setDouble(2,t);ps.setDouble(3,p);ps.setDouble(4,r);ps.executeUpdate();
                }
            }
            remaining.setText(String.format("Remaining: %.2f",r));
            msg("Fee record saved.");
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}