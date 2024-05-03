package ui;

import model.FlashCard;
import model.Folder;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.StudyTimer;

public class ReviewFlashcardsFrame extends JFrame {
    private Folder folder;
    private JLabel questionLabel;
    private JTextArea answerArea;
    private JLabel timerLabel;
    private JButton showAnswerButton;
    private JButton nextButton;
    private List<FlashCard> flashcards;
    private int currentCardIndex = 0;
    private long startTime;
    private final StudyTimer timer;

    public ReviewFlashcardsFrame(Folder folder) {
        this.folder = folder;
        this.flashcards = folder.getFlashCards();
        this.timer = new StudyTimer();
        initializeComponents();
        displayNextCard();
    }


    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Review Flashcards");
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        questionLabel = new JLabel("Click 'Next' to start reviewing", SwingConstants.CENTER);
        questionLabel.setForeground(new Color(230, 230, 230));
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        answerArea = new JTextArea(3, 20);
        answerArea.setWrapStyleWord(true);
        answerArea.setLineWrap(true);
        answerArea.setEditable(false);
        answerArea.setForeground(new Color(230, 230, 230));
        answerArea.setBackground(new Color(45, 45, 45));
        answerArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane answerScrollPane = new JScrollPane(answerArea);

        timerLabel = new JLabel("Time: --:--", SwingConstants.CENTER);
        timerLabel.setForeground(new Color(230, 230, 230));

        showAnswerButton = createButton("Show Answer");
        nextButton = createButton("Next");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        add(questionLabel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.6;
        gbc.insets = new Insets(10, 50, 10, 50);
        add(answerScrollPane, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(timerLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(nextButton);
        buttonPanel.setOpaque(false);

        showAnswerButton.addActionListener(e -> showAnswer());
        nextButton.addActionListener(e -> displayNextCard());

        gbc.gridy = 3;
        gbc.weighty = 0.1;
        add(buttonPanel, gbc);

        pack();
        setSize(500, 350);
        setLocationRelativeTo(null);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(new Color(30, 30, 30));
        button.setBackground(new Color(230, 230, 230));
        return button;
    }

    private void displayNextCard() {
        if (currentCardIndex < flashcards.size()) {
            FlashCard card = flashcards.get(currentCardIndex);
            startTime = System.currentTimeMillis();
            questionLabel.setText("Question: " + card.getQuestion());
            answerArea.setText("");
            timerLabel.setText("Time taken: --:--");
            currentCardIndex++;
        } else {
            JOptionPane.showMessageDialog(this, "You have reviewed all flashcards.");
            dispose();
        }
    }

    private void showAnswer() {
        if (currentCardIndex <= flashcards.size() && currentCardIndex > 0) {
            FlashCard card = flashcards.get(currentCardIndex - 1);
            long endTime = System.currentTimeMillis();
            String elapsedTime = timer.getElapsedTime(startTime, endTime);

            answerArea.setText(card.getAnswer());
            timerLabel.setText("Time taken: " + elapsedTime);
        }
    }
}