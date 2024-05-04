package ui;

import model.FlashCard;
import model.Folder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

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

    public ReviewFlashcardsFrame(Folder folder) {
        this.folder = folder;
        this.flashcards = folder.getFlashCards();
        setUndecorated(true);
        initializeComponents();
        displayNextCard();
        setRoundedFrame();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Review Flashcards");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel titleBar = createTitleBar();
        getContentPane().add(titleBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(18, 18, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 30, 5, 30); // Reduced bottom padding for elements

        questionLabel = new JLabel("Click 'Next' to start reviewing", SwingConstants.CENTER);
        questionLabel.setForeground(new Color(230, 230, 230));
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(questionLabel, gbc);

        answerArea = new JTextArea(8, 20);
        answerArea.setWrapStyleWord(true);
        answerArea.setLineWrap(true);
        answerArea.setEditable(false);
        answerArea.setForeground(new Color(230, 230, 230));
        answerArea.setBackground(new Color(45, 45, 45));
        answerArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane answerScrollPane = new JScrollPane(answerArea);
        answerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        answerScrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        styleRoundedBorder(answerScrollPane, new Color(45, 45, 45), 15);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(answerScrollPane, gbc);

        timerLabel = new JLabel("Time: --:--", SwingConstants.CENTER);
        timerLabel.setForeground(new Color(230, 230, 230));
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 30, 0, 30); // Reduced space above the timer label
        mainPanel.add(timerLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        showAnswerButton = createRoundedButton("Show Answer");
        nextButton = createRoundedButton("Next");
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(nextButton);
        gbc.insets = new Insets(5, 30, 10, 30); // Reduced space above buttons
        mainPanel.add(buttonPanel, gbc);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        showAnswerButton.addActionListener(e -> showAnswer());
        nextButton.addActionListener(e -> displayNextCard());
    }

    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(30, 30, 30)); // Dark background color for title bar
        titleBar.setLayout(new BorderLayout());
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel titleLabel = new JLabel("    ", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE); // White text for the title
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> dispose());
        closeButton.setFocusPainted(false); // Removes focus border
        closeButton.setBorderPainted(false); // Removes the border around the button
        closeButton.setContentAreaFilled(false); // Removes background fill
        closeButton.setForeground(Color.WHITE); // Set the text color to white
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setOpaque(false); // Make sure the button is completely transparent
        titleBar.add(closeButton, BorderLayout.EAST);

        // Add mouse dragging functionality
        MouseAdapter ma = new MouseAdapter() {
            private Point initialClick;

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        };
        titleBar.addMouseListener(ma);
        titleBar.addMouseMotionListener(ma);

        return titleBar;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 70, 70));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        styleRoundedBorder(button, new Color(70, 70, 70), 10);
        return button;
    }

    private void styleRoundedBorder(JComponent component, Color backgroundColor, int radius) {
        component.setOpaque(false);
        component.setBorder(new RoundedBorder(radius));
        component.setBackground(backgroundColor);
    }

    private void setRoundedFrame() {
        // Shape the window to have rounded corners
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
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
            long elapsedTime = (endTime - startTime) / 1000;
            String timeTaken = String.format("%d min, %d sec", elapsedTime / 60, elapsedTime % 60);

            answerArea.setText(card.getAnswer());
            timerLabel.setText("Time taken: " + timeTaken);
        }
    }
}
