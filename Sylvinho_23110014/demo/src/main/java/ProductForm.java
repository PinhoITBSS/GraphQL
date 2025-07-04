import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductForm extends JFrame {
    private JTextField tfName = new JTextField();
    private JTextField tfPrice = new JTextField();
    private JTextField tfCategory = new JTextField();
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductForm() {
        setTitle("GraphQL Product Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new java.awt.Color(245, 245, 250));

        // Judul
        JLabel titleLabel = new JLabel("GraphQL Product CRUD", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        titleLabel.setForeground(new java.awt.Color(44, 62, 80));
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Form dan tombol
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new java.awt.Color(245, 245, 250));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createTitledBorder("Product Data"),
            javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(tfName);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(tfPrice);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(tfCategory);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBackground(new java.awt.Color(245, 245, 250));
        JButton btnAdd = new JButton("Add");
        JButton btnFetch = new JButton("Show All");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        java.awt.Font btnFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        btnAdd.setFont(btnFont);
        btnFetch.setFont(btnFont);
        btnEdit.setFont(btnFont);
        btnDelete.setFont(btnFont);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnFetch);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.WEST);

        // Tabel
        String[] columnNames = {"ID", "Name", "Price", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        table.setSelectionBackground(new java.awt.Color(220, 240, 255));
        table.setSelectionForeground(new java.awt.Color(40, 40, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Product List"));
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Listeners
        btnAdd.addActionListener(e -> tambahProduk());
        btnFetch.addActionListener(e -> ambilSemuaProduk());
        btnEdit.addActionListener(e -> editProduk());
        btnDelete.addActionListener(e -> hapusProduk());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tfName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    tfPrice.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    tfCategory.setText(tableModel.getValueAt(selectedRow, 3).toString());
                } else {
                    clearInputFields();
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        ambilSemuaProduk();
    }

    private void tambahProduk() {
        String name = tfName.getText();
        String price = tfPrice.getText();
        String category = tfCategory.getText();
        if (name.isEmpty() || price.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
        int id = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{id, name, price, category});
        clearInputFields();
    }

    private void ambilSemuaProduk() {
        // Kosongkan tabel (dummy, karena tidak ada backend)
        tableModel.setRowCount(0);
    }

    private void editProduk() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!");
            return;
        }
        String name = tfName.getText();
        String price = tfPrice.getText();
        String category = tfCategory.getText();
        if (name.isEmpty() || price.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
        tableModel.setValueAt(name, selectedRow, 1);
        tableModel.setValueAt(price, selectedRow, 2);
        tableModel.setValueAt(category, selectedRow, 3);
        clearInputFields();
    }

    private void hapusProduk() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            return;
        }
        tableModel.removeRow(selectedRow);
        clearInputFields();
    }

    private void clearInputFields() {
        tfName.setText("");
        tfPrice.setText("");
        tfCategory.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductForm::new);
    }
}