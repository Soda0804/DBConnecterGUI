import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class AddPanel extends JPanel implements ActionListener {
    private JTextField idField, nameField, ageField;
    private JButton addButton;

    public AddPanel() {
        setLayout(new GridLayout(4, 2)); // 设置面板布局

        // 创建标签和文本框
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        addButton = new JButton("Add"); // 创建按钮

        // 将标签、文本框和按钮添加到面板中
        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(addButton);

        addButton.addActionListener(this); // 添加按钮的事件监听器
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) { // 如果事件源是按钮
            int id = Integer.parseInt(idField.getText()); // 获取 ID 字段的值
            String name = nameField.getText(); // 获取 Name 字段的值
            int age = Integer.parseInt(ageField.getText()); // 获取 Age 字段的值

            try (Connection conn = MainFrame.getConnection()) { // 获取数据库连接
                String sql = "INSERT INTO users (id, name, age) VALUES (?, ?, ?)"; // SQL 语句
                PreparedStatement statement = conn.prepareStatement(sql); // 创建 PreparedStatement
                statement.setInt(1, id); // 设置 ID 参数的值
                statement.setString(2, name); // 设置 Name 参数的值
                statement.setInt(3, age); // 设置 Age 参数的值
                int rowsInserted = statement.executeUpdate(); // 执行更新
                if (rowsInserted > 0) { // 如果有行被插入
                    JOptionPane.showMessageDialog(this, "Record added successfully!"); // 显示成功消息框
                    idField.setText(""); // 清空 ID 字段
                    nameField.setText(""); // 清空 Name 字段
                    ageField.setText(""); // 清空 Age 字段
                }
            } catch (SQLException ex) { // 捕获 SQL 异常
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage()); // 显示错误消息框
            }
        }
    }
}
