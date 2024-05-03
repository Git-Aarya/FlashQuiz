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

    public EditFlashcardsFrame(Folder folder) {
        this.folder = folder;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 10, 10));
        setTitle("Edit Flashcards");

        flashcardComboBox = new JComboBox<>(new Vector<>(folder.getFlashCards()));
        flashcardComboBox.addActionListener(e -> loadFlashcardData());

        questionField = new JTextField();
        answerField = new JTextField();

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateFlashcard());

        add(new JLabel("Select Flashcard:"));
        add(flashcardComboBox);
        add(new JLabel("Question:"));
        add(questionField);
        add(new JLabel("Answer:"));
        add(answerField);
        add(updateButton);

        pack();
        setLocationRelativeTo(null);
        loadFlashcardData();
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
