package college.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FacultyManagement extends JFrame {
    JTextField id,name,email,phone,department,subject;
    JTable table; DefaultTableModel model;

    public FacultyManagement(){
        setTitle("Faculty Management"); setSize(1100,650); setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); setLayout(null);

        JLabel title=new JLabel("FACULTY MANAGEMENT",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24)); title.setBounds(330,15,350,35); add(title);

        id=field("ID:",35,80,false); name=field("Name:",35,120,true);
        email=field("Email:",35,160,true); phone=field("Phone:",35,200,true);
        department=field("Department:",35,240,true); subject=field("Subject:",35,280,true);

        JButton add=new JButton("Add"); add.setBounds(35,330,100,35); add(add);
        JButton update=new JButton("Update"); update.setBounds(145,330,100,35); add(update);
        JButton delete=new JButton("Delete"); delete.setBounds(35,375,100,35); add(delete);
        JButton clear=new JButton("Clear"); clear.setBounds(145,375,100,35); add(clear);

        model=new DefaultTableModel(new String[]{"ID","Name","Email","Phone","Department","Subject"},0);
        table=new JTable(model);
        JScrollPane sp=new JScrollPane(table); sp.setBounds(340,80,720,460); add(sp);

        add.addActionListener(e->addFaculty()); update.addActionListener(e->updateFaculty());
        delete.addActionListener(e->deleteFaculty()); clear.addActionListener(e->clear());

        table.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){
                int r=table.getSelectedRow(); if(r>=0){
                    id.setText(v(r,0)); name.setText(v(r,1)); email.setText(v(r,2));
                    phone.setText(v(r,3)); department.setText(v(r,4)); subject.setText(v(r,5));
                }
            }
        });
        load(); setVisible(true);
    }

    JTextField field(String label,int x,int y,boolean edit){
        JLabel l=new JLabel(label); l.setBounds(x,y,90,25); add(l);
        JTextField f=new JTextField(); f.setBounds(x+95,y,150,27); f.setEditable(edit); add(f); return f;
    }
    String v(int r,int c){return table.getValueAt(r,c).toString();}

    void addFaculty(){
        String sql="INSERT INTO faculty(name,email,phone,department,subject) VALUES(?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,name.getText());p.setString(2,email.getText());p.setString(3,phone.getText());
            p.setString(4,department.getText());p.setString(5,subject.getText());p.executeUpdate();
            msg("Faculty added.");load();clear();
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void load(){
        model.setRowCount(0);
        try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();
            ResultSet r=s.executeQuery("SELECT * FROM faculty ORDER BY id DESC")){
            while(r.next())model.addRow(new Object[]{r.getInt("id"),r.getString("name"),r.getString("email"),
                r.getString("phone"),r.getString("department"),r.getString("subject")});
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void updateFaculty(){
        String sql="UPDATE faculty SET name=?,email=?,phone=?,department=?,subject=? WHERE id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,name.getText());p.setString(2,email.getText());p.setString(3,phone.getText());
            p.setString(4,department.getText());p.setString(5,subject.getText());p.setInt(6,Integer.parseInt(id.getText()));
            p.executeUpdate();msg("Faculty updated.");load();
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void deleteFaculty(){
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM faculty WHERE id=?")){
            p.setInt(1,Integer.parseInt(id.getText()));p.executeUpdate();msg("Faculty deleted.");load();clear();
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void clear(){id.setText("");name.setText("");email.setText("");phone.setText("");department.setText("");subject.setText("");}
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}