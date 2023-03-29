import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class UpdatePanel extends JPanel implements ActionListener {
    private JTextField idField, nameField, ageField;
    private JButton updateButton;

    public UpdatePanel() {
        setLayout(new GridLayout(4, 2));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        updateButton = new JButton("Update");

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(updateButton);

        updateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());


            try (Connection conn = MainFrame.getConnection()) {
                String sql = "UPDATE users SET name = ?, age = ? WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.setInt(3, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Record updated successfully!");
                    idField.setText("");
                    nameField.setText("");
                    ageField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating record: " + ex.getMessage());
            }
        }
    }
}
