package college.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Subject extends JFrame {
    JTextField id,name,code,courseId; JTable table; DefaultTableModel model;

    public Subject(){
        setTitle("Subject Management");setSize(850,550);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(null);

        JLabel title=new JLabel("SUBJECT MANAGEMENT",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24));title.setBounds(270,15,300,35);add(title);

        id=field("ID:",30,80,false);name=field("Subject:",30,120,true);
        code=field("Code:",30,160,true);courseId=field("Course ID:",30,200,true);

        JButton add=new JButton("Add");add.setBounds(30,250,100,35);add(add);
        JButton update=new JButton("Update");update.setBounds(140,250,100,35);add(update);
        JButton delete=new JButton("Delete");delete.setBounds(30,295,100,35);add(delete);
        JButton clear=new JButton("Clear");clear.setBounds(140,295,100,35);add(clear);

        model=new DefaultTableModel(new String[]{"ID","Subject","Code","Course ID"},0);
        table=new JTable(model);JScrollPane sp=new JScrollPane(table);sp.setBounds(270,80,540,330);add(sp);

        add.addActionListener(e->addSubject());update.addActionListener(e->updateSubject());
        delete.addActionListener(e->deleteSubject());clear.addActionListener(e->clear());
        table.addMouseListener(new java.awt.event.MouseAdapter(){public void mouseClicked(java.awt.event.MouseEvent e){
            int r=table.getSelectedRow();if(r>=0){id.setText(v(r,0));name.setText(v(r,1));code.setText(v(r,2));courseId.setText(v(r,3));}
        }});
        load();setVisible(true);
    }

    JTextField field(String label,int x,int y,boolean edit){JLabel l=new JLabel(label);l.setBounds(x,y,80,25);add(l);
        JTextField f=new JTextField();f.setBounds(x+85,y,150,27);f.setEditable(edit);add(f);return f;}
    String v(int r,int c){return table.getValueAt(r,c).toString();}
    void addSubject(){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO subjects(name,code,course_id) VALUES(?,?,?)")){
        p.setString(1,name.getText());p.setString(2,code.getText());p.setInt(3,Integer.parseInt(courseId.getText()));p.executeUpdate();msg("Subject added.");load();clear();
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void load(){model.setRowCount(0);try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM subjects ORDER BY id DESC")){
        while(r.next())model.addRow(new Object[]{r.getInt("id"),r.getString("name"),r.getString("code"),r.getInt("course_id")});
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void updateSubject(){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE subjects SET name=?,code=?,course_id=? WHERE id=?")){
        p.setString(1,name.getText());p.setString(2,code.getText());p.setInt(3,Integer.parseInt(courseId.getText()));p.setInt(4,Integer.parseInt(id.getText()));p.executeUpdate();msg("Subject updated.");load();
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void deleteSubject(){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM subjects WHERE id=?")){
        p.setInt(1,Integer.parseInt(id.getText()));p.executeUpdate();msg("Subject deleted.");load();clear();
    }catch(Exception e){msg("Error: "+e.getMessage());}}
    void clear(){id.setText("");name.setText("");code.setText("");courseId.setText("");}
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}