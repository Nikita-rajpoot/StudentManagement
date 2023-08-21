import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;


public class Login extends JFrame {
    JLabel label;
    JLabel imgLabel;
    JLabel emailLabel , passWordJLabel;
    JTextField emailField , passwordField;
    JButton sign_up , close , addConnection;
    boolean status=false;
    int count=0;
    JDBC jdbc = new JDBC();

    public Login() {
        this.setTitle("Login Page");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3, 2));

        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(89, 255, 0));

        this.setBounds(300, 100, 900, 600);
        this.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(69, 162, 158));
        panel.setBounds(0, 0, 300, 600);
        panel.setLayout(new BorderLayout());

        imgLabel = new JLabel(new ImageIcon("C:/Users/vandana/Desktop/CODSOFT/image/User1.jpg"));
        imgLabel.setBounds(50, 90, 200, 200);
         
        label = new JLabel("<html><h1>Admin Sign-up</h1></html>");
        label.setForeground(new Color(11, 12, 16));
        label.setBounds(70, 300, 200, 30);

        JLabel emailLabel = new JLabel("<html><h2>Email:</h2></html>");
        emailLabel.setBounds(420, 80, 250, 30);
        emailLabel.setForeground(new Color(102, 252, 241));

        emailField = new JTextField(10);
        emailField.setBounds(420, 130, 300, 30);

        JLabel passwordLabel = new JLabel("<html><h2>Password:</h2></html>");
        passwordLabel.setBounds(420, 190, 250, 30);
        passwordLabel.setForeground(new Color(102, 252, 241));

        passwordField = new JPasswordField(10);
        passwordField.setBounds(420, 240, 300, 30);

        sign_up = new JButton("Sign up");
        sign_up.setBounds(420, 420, 100, 30);
        // let's set the location and size of buttons & color
        sign_up.setBorder(BorderFactory.createEtchedBorder());
        sign_up.setBorder(new LineBorder(new Color(102, 252, 241)));
        sign_up.setBackground(new Color(11, 12, 16));
        sign_up.setForeground(new Color(102, 252, 241));
        sign_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = emailField.getText();
                String password = new String(passwordField.getText());

                // Simulate authentication logic
                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(Login.this, "Login successful!");
                    // Open main student management system window
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Login failed. Invalid credentials.");
                }
            }
        });
        close = new JButton("cancel");
        close.setBounds(620, 420, 100, 30);
        close.setBorder(BorderFactory.createEtchedBorder());
        close.setBorder(new LineBorder(new Color(102, 252, 241)));
        close.setBackground(new Color(11, 12, 16));
        close.setForeground(new Color(102, 252, 241));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application when closeButton is clicked
            }
        });

        addConnection = new JButton("Connection");// to create database connectivity (database, tables, insertion)
        addConnection.setBounds(730, 10, 150, 30);
        // let's set the location and size of buttons & color
        addConnection.setBorder(BorderFactory.createEtchedBorder());
        addConnection.setBorder(new LineBorder(new Color(110, 250, 245)));
        addConnection.setBackground(new Color(13, 15, 18));
        addConnection.setForeground(new Color(112, 232, 241));
        addConnection.setFocusable(false);


        add(addConnection);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(sign_up);
        add(close);
        add(label);
        add(imgLabel);
        add(panel);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String com = e.getActionCommand();
        System.out.println(com);
        if(com.equals("Connection")){
            new LoginDb().Db_connection_Frame();
        }else if (com.equals("Sign up")) {
            String emailLogin = emailField.getText() ;
            String passwordLogin = passwordField.getText();
            System.out.println(emailLogin);
            System.out.println(passwordLogin);
            try{
                jdbc .setConnection();
                System.out.println("Connected successfully!");
                Statement stmt;
                stmt = jdbc.connection.createStatement();
                PreparedStatement ps = jdbc.connection.prepareStatement("select * from faculty where email = ? and password = ?");
                ps.setString(1,emailLogin);
                ps.setString(2, passwordLogin);
                ResultSet rs1 = ps.executeQuery();
                System.out.println(rs1);
                status = rs1.next();
                System.out.println(status);
                ImageIcon  icon = new ImageIcon("C:/Users/vandana/Desktop/CODSOFT/image/Warning.png");
                if (status == true) {
                    count = JOptionPane.showOptionDialog(null,"Successfully signed up","Connection",
                            JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE,icon,null,0);
                    if (count == 0){
                        new Student();
                        dispose();
                    } 
                 } else {
                    JOptionPane.showOptionDialog(null, "Invalid Email & password","Connection", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE, icon, null,0);
                 }
            } catch (Exception exception) {
                exception.getStackTrace();
            }
        } else{
            System.exit(0);
        }
    }
}