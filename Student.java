import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Student extends JFrame {
    JLabel imgLabel, headingLabel;
    JButton addButton,editButton;
    JPanel studentDataPanel;
    JDBC jdbc;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    JTable studentTable;
    JScrollPane scrollPane;
    JTextField searField;
    int rowAffected;
    JButton refresh;
    Container contentPane;
    UpdateStudent upstd;

    public Student() {
        this.setTitle("Student Management");
        setSize(300, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane=getContentPane();
        contentPane.setBackground(new Color(13,19,21));

        this.setBounds(0, 0, 1525, 820);
        this.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(69, 162, 158));
        panel.setBounds(0, 0, 300, 600);
        panel.setLayout(new BorderLayout());

        imgLabel = new JLabel(new ImageIcon("C:/Users/vandana/Desktop/CODSOFT/image/User2.png"));
        imgLabel.setBounds(50, 90, 200, 200);

        addButton = new JButton("Add Students");
        addButton.setBounds(420, 420, 100, 30);
        addButton.setBorder(BorderFactory.createEtchedBorder());
        addButton.setBorder(new LineBorder(new Color(102, 252, 241)));
        addButton.setBackground(new Color(11, 12, 16));
        addButton.setForeground(new Color(102, 252, 241));
        addButton.setFocusable(false);

        editButton = new JButton("Edit Students");
        editButton.setBounds(420, 420, 100, 30);
        editButton.setBorder(BorderFactory.createEtchedBorder());
        editButton.setBorder(new LineBorder(new Color(102, 252, 241)));
        editButton.setBackground(new Color(11, 12, 16));
        editButton.setForeground(new Color(102, 252, 241));
        editButton.setFocusable(false);

        studentDataPanel = new JPanel();
        studentDataPanel.setBackground(Color.white);
        studentDataPanel.setBounds(400, 150, 1000, 300);
        studentDataPanel.setLayout(new BorderLayout());

        refresh = new JButton();
        refresh.setIcon(new ImageIcon("C:/Users/vandana/Desktop/CODSOFT/image/refresh.jpeg"));
        refresh.setToolTipText("Refresh");
        refresh.setBackground(Color.BLACK);
        refresh.setFocusable(false);
        refresh.setBorder(new LineBorder(Color.BLACK));
        refresh.setBounds(1330,90,50,50);

        JLabel imagLabel = new JLabel(new ImageIcon("C:/Users/vandana/Desktop/CODSOFT/image/student.jpeg"));
        imagLabel.setIconTextGap(-10);
        imagLabel.setBounds(1100,400,500,500);
        initializeTable();
        showTable();
        contentPane.add(imgLabel);
        contentPane.add(refresh);
        contentPane.add(studentDataPanel);
        contentPane.add(editButton);
        contentPane.add(addButton);
        contentPane.add(headingLabel);
        contentPane.add(panel);
        contentPane.add(imagLabel);
        this.setVisible(true);
    }

    private void initializeTable() {
        String tableColums[] = {"Student-ID","First Name","Last Name","DOB","Email","Contact","Subject","Year"};
        String tableData[][] = new String[0][tableColums.length];
        DefaultTableModel model = new DefaultTableModel(tableData, tableColums);
        studentTable = new JTable(model);
        studentTable.setRowHeight(50);
        studentTable.setFont(new Font("monospaced",Font.BOLD,10));

        scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(0,0,1000,300);

        studentDataPanel.add(scrollPane);
    }
    public void showTable() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.setRowCount(0);

        try{
            jdbc = new JDBC();
            jdbc.setConnection();
            System.out.println("Connection success");

            preparedStatement = jdbc.connection.prepareStatement("SELECT * from students;");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Object[] rowData = {
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                };
                model.addRow(rowData);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
     public  void actionPerformed(ActionEvent e) {
       String com = e.getActionCommand();
       System.out.println(com);
       if(com.equals("Add Students")) {
        dispose();
        new AddStudent();
       }else if (com.equals("Edit Students")) {
        dispose();
        upstd = new UpdateStudent();
       } else {
            studentDataPanel.setBounds(400, 150,1000,300);
            showTable();
       }
    }
}
