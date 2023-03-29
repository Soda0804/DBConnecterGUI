import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class DeletePanel extends JPanel implements ActionListener {
    private JTextField idField;
    private JButton deleteButton;

    public DeletePanel() {
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        deleteButton = new JButton("Delete");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(idLabel)
                .addComponent(idField)
                .addComponent(deleteButton));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(idLabel)
                .addComponent(idField)
                .addComponent(deleteButton));

        deleteButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int id = Integer.parseInt(idField.getText());

            try (Connection conn = MainFrame.getConnection()) {
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Record deleted successfully!");
                    idField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage());
            }
        }
    }
}

