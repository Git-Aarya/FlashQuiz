package ui;

import model.Folder;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DeleteFolderFrame extends JFrame {
    private Map<String, Folder> folders;
    private JComboBox<String> folderComboBox;
    private JButton deleteButton;

    public DeleteFolderFrame(Map<String, Folder> folders) {
        this.folders = folders;
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Delete Folder");
        setLayout(new BorderLayout());
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        folderComboBox = new JComboBox<>(folders.keySet().toArray(new String[0]));
        deleteButton = new JButton("Delete Folder");
        deleteButton.addActionListener(e -> deleteFolder());

        add(folderComboBox, BorderLayout.CENTER);
        add(deleteButton, BorderLayout.PAGE_END);

        pack();
    }

    private void deleteFolder() {
        String selectedFolderName = (String) folderComboBox.getSelectedItem();
        if (selectedFolderName != null) {
            int dialogResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the folder \"" + selectedFolderName + "\"?",
                    "Confirm Folder Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (dialogResult == JOptionPane.YES_OPTION) {
                folders.remove(selectedFolderName);
                folderComboBox.removeItem(selectedFolderName);
                JOptionPane.showMessageDialog(this, "Folder \"" + selectedFolderName + "\" deleted successfully.");

                if (folders.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All folders have been deleted.");
                    dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No folder is selected.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


