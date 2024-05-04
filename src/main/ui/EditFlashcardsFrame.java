package ui;

import model.FlashCard;
import model.Folder;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class EditFlashcardsFrame extends JFrame {
    private Folder folder;
    private JComboBox<FlashCard> flashcardComboBox;
    private JTextField questionField;
    private JTextField answerField;
    private JButton updateButton;

    public EditFlashcardsFrame(Folder folder) {
        this.folder = folder;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Edit Flashcards");
        getContentPane().setBackground(new Color(18, 18, 18)); // Dark background
        setLayout(new BorderLayout(10, 10)); // Border layout for better spacing
        setSize(400, 200); // Setting size of the window
        setLocationRelativeTo(null);

        // Panel for form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(getContentPane().getBackground());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo box for selecting flashcards
        flashcardComboBox = new JComboBox<>(new Vector<>(folder.getFlashCards()));
        flashcardComboBox.addActionListener(e -> loadFlashcardData());
        flashcardComboBox.setBackground(new Color(30, 30, 30));
        flashcardComboBox.setForeground(Color.WHITE);

        questionField = new JTextField();
        questionField.setBackground(new Color(30, 30, 30));
        questionField.setForeground(Color.WHITE);
        questionField.setCaretColor(Color.WHITE);

        answerField = new JTextField();
        answerField.setBackground(new Color(30, 30, 30));
        answerField.setForeground(Color.WHITE);
        answerField.setCaretColor(Color.WHITE);

        formPanel.add(new JLabel("Select Flashcard:"));
        formPanel.add(flashcardComboBox);
        formPanel.add(new JLabel("Question:"));
        formPanel.add(questionField);
        formPanel.add(new JLabel("Answer:"));
        formPanel.add(answerField);

        // Update button
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateFlashcard());
        updateButton.setBackground(new Color(50, 60, 70));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Adding components to frame
        add(formPanel, BorderLayout.CENTER);
        add(updateButton, BorderLayout.SOUTH);
        styleComponents();
    }

    private void styleComponents() {
        // Setting styles for labels
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                ((JPanel) comp).setOpaque(false);
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JLabel) {
                        JLabel label = (JLabel) innerComp;
                        label.setForeground(new Color(230, 230, 230));
                    }
                }
            }
        }
    }

    private void loadFlashcardData() {
        FlashCard flashCard = (FlashCard) flashcardComboBox.getSelectedItem();
        if (flashCard != null) {
            questionField.setText(flashCard.getQuestion());
            answerField.setText(flashCard.getAnswer());
        }
    }

    private void updateFlashcard() {
        FlashCard flashCard = (FlashCard) flashcardComboBox.getSelectedItem();
        if (flashCard != null) {
            flashCard.setQuestion(questionField.getText());
            flashCard.setAnswer(answerField.getText());
            JOptionPane.showMessageDialog(this, "Flashcard updated successfully.");
            flashcardComboBox.repaint();
        }
    }
}

