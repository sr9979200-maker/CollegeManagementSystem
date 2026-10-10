package college.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Course extends JFrame {
    JTextField id,name,duration,fees; JTable table; DefaultTableModel model;

    public Course(){
        setTitle("Course Management");setSize(800,550);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(null);

        JLabel title=new JLabel("COURSE MANAGEMENT",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24));title.setBounds(250,15,300,35);add(title);

        id=field("ID:",30,80,false);name=field("Course:",30,120,true);
        duration=field("Duration:",30,160,true);fees=field("Fees:",30,200,true);

        JButton add=new JButton("Add");add.setBounds(30,250,100,35);add(add);
        JButton update=new JButton("Update");update.setBounds(140,250,100,35);add(update);
        JButton delete=new JButton("Delete");delete.setBounds(30,295,100,35);add(delete);
        JButton clear=new JButton("Clear");clear.setBounds(140,295,100,35);add(clear);

        model=new DefaultTableModel(new String[]{"ID","Course","Duration","Fees"},0);
        table=new JTable(model);JScrollPane sp=new JScrollPane(table);sp.setBounds(270,80,490,330);add(sp);

        add.addActionListener(e->addCourse());update.addActionListener(e->updateCourse());
        delete.addActionListener(e->deleteCourse());clear.addActionListener(e->clear());

        table.addMouseListener(new java.awt.event.MouseAdapter(){public void mouseClicked(java.awt.event.MouseEvent e){
            int r=table.getSelectedRow();if(r>=0){id.setText(v(r,0));name.setText(v(r,1));duration.setText(v(r,2));fees.setText(v(r,3));}
        }});
        load();setVisible(true);
    }

    JTextField field(String label,int x,int y,boolean edit){JLabel l=new JLabel(label);l.setBounds(x,y,80,25);add(l);
        JTextField f=new JTextField();f.setBounds(x+85,y,150,27);f.setEditable(edit);add(f);return f;}
    String v(int r,int c){return table.getValueAt(r,c).toString();}
    void addCourse(){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO courses(name,duration,fees) VALUES(?,?,?)")){
        p.setString(1,name.getText());p.setString(2,duration.getText());p.setDouble(3,Double.parseDouble(fees.getText()));p.executeUpdate();msg("Course added.");load();clear();
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void load(){model.setRowCount(0);try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM courses ORDER BY id DESC")){
        while(r.next())model.addRow(new Object[]{r.getInt("id"),r.getString("name"),r.getString("duration"),r.getDouble("fees")});
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void updateCourse(){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE courses SET name=?,duration=?,fees=? WHERE id=?")){
        p.setString(1,name.getText());p.setString(2,duration.getText());p.setDouble(3,Double.parseDouble(fees.getText()));p.setInt(4,Integer.parseInt(id.getText()));p.executeUpdate();msg("Course updated.");load();
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void deleteCourse(){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM courses WHERE id=?")){
        p.setInt(1,Integer.parseInt(id.getText()));p.executeUpdate();msg("Course deleted.");load();clear();
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void clear(){id.setText("");name.setText("");duration.setText("");fees.setText("");}
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}