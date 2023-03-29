import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QueryPanel extends JPanel implements ActionListener {
    private JTextField idField;
    private JButton searchButton;
    private JTable resultTable;

    public QueryPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        searchButton = new JButton("Search");
        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(searchButton);

        resultTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name", "Age"}, 0));
        JScrollPane scrollPane = new JScrollPane(resultTable);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            int id = Integer.parseInt(idField.getText());

            try (Connection conn = MainFrame.getConnection()) {
                String sql = "SELECT * FROM users WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
                model.setRowCount(0);

                while (rs.next()) {
                    int rsId = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    Object[] row = {rsId, name, age};
                    model.addRow(row);
                }

                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Record not found!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error querying record: " + ex.getMessage());
            }
        }
    }
}
