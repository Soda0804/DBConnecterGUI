import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {

    private JButton addButton, deleteButton, updateButton, queryButton,connectButton;
    private JTextField userField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;
    private static  String USER =null;
    private static  String PASS = null;
    private static  String DB_URL = "jdbc:mysql://localhost:3306/Students";

    public MainFrame() {
        setTitle("MySQL数据库管理系统");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        connectButton =new JButton("连接数据库");
        addButton = new JButton("添加记录");
        deleteButton = new JButton("删除记录");
        updateButton = new JButton("修改记录");
        queryButton = new JButton("查找记录");
        userField =new JTextField(20);
        passwordField = new JPasswordField(20);
        showPasswordCheckbox = new JCheckBox("显示密码");

        connectButton.addActionListener(this);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        queryButton.addActionListener(this);
        showPasswordCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){passwordField.setEchoChar((char)0);}
                else {passwordField.setEchoChar('*');}
            }
        });

        // 创建一个新的 JPanel，并使用 FlowLayout 布局管理器将按钮排列在一行或一列
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(connectButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(queryButton);
        JPanel inputPanel =new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("用户名："));
        inputPanel.add(userField);
        inputPanel.add(new JLabel("密码："));
        inputPanel.add(passwordField);
        inputPanel.add(showPasswordCheckbox);


        // 将按钮面板添加到主窗体的 CENTER 区域
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 根据按钮的不同，创建新的窗口并显示对应的面板
        if (e.getSource() == addButton) {
            AddPanel addPanel = new AddPanel();
            createNewFrame(addPanel);
        } else if (e.getSource() == deleteButton) {
            DeletePanel deletePanel = new DeletePanel();
            createNewFrame(deletePanel);
        } else if (e.getSource() == updateButton) {
            UpdatePanel updatePanel = new UpdatePanel();
            createNewFrame(updatePanel);
        } else if (e.getSource() == queryButton) {
            QueryPanel queryPanel = new QueryPanel();
            createNewFrame(queryPanel);
        }else if (e.getSource()==connectButton){
            USER = userField.getText(); // 获取user字段的值
            PASS = passwordField.getText(); // 获取 password 字段的值
            Connection conn = MainFrame.getConnection();
            // 显示连接成功或失败的提示
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "数据库连接成功", "连接状态", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "数据库连接失败", "连接状态", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void createNewFrame(JPanel panel) {
        // 创建一个新的窗口，并将指定面板添加到其中
        JFrame frame = new JFrame();
        frame.setTitle(panel.getClass().getSimpleName());
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}