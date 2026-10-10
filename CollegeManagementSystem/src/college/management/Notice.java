package college.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Notice extends JFrame {
    JTextField titleField;
    JTextArea messageArea;
    JTable table;
    DefaultTableModel model;

    public Notice(){
        setTitle("Notice Board");setSize(850,600);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(null);

        JLabel title=new JLabel("COLLEGE NOTICE BOARD",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24));title.setBounds(250,15,350,35);add(title);

        JLabel tl=new JLabel("Title:");tl.setBounds(35,80,70,25);add(tl);
        titleField=new JTextField();titleField.setBounds(100,80,230,28);add(titleField);

        JLabel ml=new JLabel("Message:");ml.setBounds(35,120,70,25);add(ml);
        messageArea=new JTextArea();messageArea.setLineWrap(true);messageArea.setWrapStyleWord(true);
        JScrollPane mp=new JScrollPane(messageArea);mp.setBounds(100,120,230,120);add(mp);

        JButton add=new JButton("Add Notice");add.setBounds(100,260,110,35);add(add);
        JButton delete=new JButton("Delete");delete.setBounds(220,260,110,35);add(delete);

        model=new DefaultTableModel(new String[]{"ID","Title","Message","Date"},0);
        table=new JTable(model);JScrollPane sp=new JScrollPane(table);sp.setBounds(360,80,450,380);add(sp);

        add.addActionListener(e->addNotice());delete.addActionListener(e->deleteNotice());
        load();setVisible(true);
    }

    void addNotice(){
        String sql="INSERT INTO notices(title,message) VALUES(?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,titleField.getText());p.setString(2,messageArea.getText());p.executeUpdate();
            msg("Notice added.");load();titleField.setText("");messageArea.setText("");
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void load(){
        model.setRowCount(0);
        try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();
            ResultSet r=s.executeQuery("SELECT * FROM notices ORDER BY id DESC")){
            while(r.next())model.addRow(new Object[]{r.getInt("id"),r.getString("title"),r.getString("message"),r.getTimestamp("created_at")});
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void deleteNotice(){
        int row=table.getSelectedRow();if(row<0){msg("Select a notice.");return;}
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM notices WHERE id=?")){
            p.setInt(1,Integer.parseInt(table.getValueAt(row,0).toString()));p.executeUpdate();msg("Notice deleted.");load();
        }catch(Exception e){msg("Error: "+e.getMessage());}
    }
    void msg(String s){JOptionPane.showMessageDialog(this,s);}
}