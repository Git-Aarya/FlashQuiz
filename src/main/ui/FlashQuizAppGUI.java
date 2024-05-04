package ui;

import model.FlashCard;
import model.Folder;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Refactored to use Java Swing for GUI instead of console, renamed to FlashQuizAppGUI.
public class FlashQuizAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/flashcards.json";
    private final Map<String, Folder> folders;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JPanel mainContent;

    // Constructor renamed to match class name
    public FlashQuizAppGUI() throws FileNotFoundException {
        folders = new HashMap<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));

        setupUI();
        updateFoldersDisplay();
        loadFolders();
    }

    private void setupUI() {
        getContentPane().setLayout(new BorderLayout());

        JPanel topBar = createTopBar();
        getContentPane().add(topBar, BorderLayout.NORTH);

        JPanel sidebar = setupSidebar();
        mainContent = setupMainContent();

        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(32, 34, 37)); // Or any color you prefer for the bar
        topBar.setPreferredSize(new Dimension(0, 30)); // Set the height of the top bar

        // Left side logo
        JLabel logoLabel = new JLabel(new ImageIcon("path/to/your/logo.png")); // Replace with your logo path
        topBar.add(logoLabel, BorderLayout.WEST);

        // Middle title
        JLabel titleLabel = new JLabel("FlashQuiz", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set the font size and style
        topBar.add(titleLabel, BorderLayout.CENTER);

        // Right side control buttons
        JPanel controlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlButtons.setOpaque(false); // Make the panel transparent

        JButton minimizeButton = new JButton(new ImageIcon("data\\button_icons\\minimize-sign.png")); // Add your minimize icon
        JButton closeButton = new JButton(new ImageIcon("data\\button_icons\\x-button.png")); // Add your close icon

        // Set buttons transparent, without borders, and add action listeners
        int controlButtonSize = 16; // Adjust this size as needed
        setupControlButton(minimizeButton, "data\\button_icons\\minimize-sign.png", controlButtonSize, () -> setState(Frame.ICONIFIED));
        setupControlButton(closeButton, "data\\button_icons\\x-button.png", 16, () -> {
            saveFolders();
            System.exit(0);
        });

        controlButtons.add(minimizeButton);
        controlButtons.add(closeButton);

        topBar.add(controlButtons, BorderLayout.EAST);

        return topBar;
    }

    private JPanel setupSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        Color sidebarColor = new Color(32, 34, 37);
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(300, getHeight()));

        // Add action buttons to the sidebar
        addButton(sidebar, "Create Folder", this::createFolder);
        addButton(sidebar, "Add FlashCard", this::addFlashCardToFolder);
        addButton(sidebar, "Review Flashcards", this::reviewFlashcards);
        addButton(sidebar, "Edit Flashcards", this::editFlashcards);
        addButton(sidebar, "Delete Folder", this::deleteFolder);
        addButton(sidebar, "Save", e -> saveFolders());
        addButton(sidebar, "Load", e -> loadFolders());

        sidebar.add(Box.createVerticalGlue());

        JButton exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(e -> {
            saveFolders();
            System.exit(0);
        });
        sidebar.add(exitButton);

        return sidebar;
    }

    private JPanel setupMainContent() {
        JPanel mainContent = new JPanel();
        mainContent.setBackground(new Color(44, 47, 51));
        mainContent.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        return mainContent;
    }

    private void updateFoldersDisplay() {
        mainContent.removeAll();

        folders.forEach((name, folder) -> {
            JPanel folderPanel = new RoundedPanel();
            folderPanel.setPreferredSize(new Dimension(300, 190));
            folderPanel.setBackground(new Color(50, 60, 70));
            folderPanel.setLayout(new BorderLayout());

            JLabel folderLabel = new JLabel(name, SwingConstants.CENTER);
            folderLabel.setForeground(Color.WHITE);
            folderPanel.add(folderLabel, BorderLayout.CENTER);

            folderPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ReviewFlashcardsFrame reviewFrame = new ReviewFlashcardsFrame(folder);
                    reviewFrame.setVisible(true);
                }
            });

            mainContent.add(folderPanel);
        });

        mainContent.revalidate();
        mainContent.repaint();
    }

    private void setupControlButton(JButton button, String imagePath, int size, Runnable action) {
        // Resize the icon image
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(resizedImg));

        // Make the button transparent and without borders
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.addActionListener(e -> action.run());
    }

    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        styleButton(button);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void styleButton(JButton button) {
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(new Color(230, 230, 230));
        button.setBackground(new Color(50, 60, 70));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(new RoundedBorder(10));
        button.setOpaque(true);
        button.setContentAreaFilled(false);

        // Add mouse listener for the hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(false);
                button.setBackground(new Color(50, 60, 70));
            }
        });
    }


    private void reviewFlashcards(ActionEvent e) {
        String folderName = (String) JOptionPane.showInputDialog(
                this,
                "Select a folder to review flashcards:",
                "Review Flashcards",
                JOptionPane.QUESTION_MESSAGE,
                null,
                folders.keySet().toArray(),
                null);

        if (folderName != null && folders.containsKey(folderName)) {
            Folder folder = folders.get(folderName);
            ReviewFlashcardsFrame reviewFrame = new ReviewFlashcardsFrame(folder);
            reviewFrame.setVisible(true);
        }
    }

    // Handler for editing flashcards
    private void editFlashcards(ActionEvent e) {
        String folderName = (String) JOptionPane.showInputDialog(
                this,
                "Select a folder to edit flashcards:",
                "Edit Flashcards",
                JOptionPane.QUESTION_MESSAGE,
                null,
                folders.keySet().toArray(),
                null);

        if (folderName != null && folders.containsKey(folderName)) {
            Folder folder = folders.get(folderName);
            EditFlashcardsFrame editFrame = new EditFlashcardsFrame(folder);
            editFrame.setVisible(true);
        }
    }

    // Handler for deleting a folder
    private void deleteFolder(ActionEvent e) {
        String folderName = (String) JOptionPane.showInputDialog(
                this,
                "Select a folder to delete:",
                "Delete Folder",
                JOptionPane.QUESTION_MESSAGE,
                null,
                folders.keySet().toArray(),
                null);

        if (folderName != null && folders.containsKey(folderName)) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the folder '" + folderName + "'?",
                    "Delete Folder",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                folders.remove(folderName);
                JOptionPane.showMessageDialog(this, "Folder '" + folderName + "' deleted successfully.");
                updateFoldersDisplay();
            }
        }
    }

    // Handler for creating a new folder
    private void createFolder(ActionEvent e) {
        String folderName = JOptionPane.showInputDialog(this, "Enter a name for the new folder:");
        if (folderName != null && !folderName.trim().isEmpty()) {
            folders.put(folderName, new Folder(folderName));
            JOptionPane.showMessageDialog(this, "Folder created: " + folderName);
            updateFoldersDisplay();
        }
    }

    // Handler for adding a flashcard to a folder
    private void addFlashCardToFolder(ActionEvent e) {
        String folderName = (String) JOptionPane.showInputDialog(this,
                "Enter the folder name where you want to add the flashcard:",
                "Select Folder",
                JOptionPane.PLAIN_MESSAGE,
                null,
                folders.keySet().toArray(),
                null
        );
        if (folderName != null && folders.containsKey(folderName)) {
            Folder folder = folders.get(folderName);
            String question = JOptionPane.showInputDialog(this, "Enter a question for the flashcard:");
            if (question != null && !question.trim().isEmpty()) {
                String answer = JOptionPane.showInputDialog(this, "Enter the answer for the flashcard:");
                if (answer != null && !answer.trim().isEmpty()) {
                    folder.addFlashCard(new FlashCard(question, answer));
                    JOptionPane.showMessageDialog(this, "Flashcard added to folder " + folderName);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Folder does not exist. Please create it first.");
        }
    }

    // Method to save folders to JSON
    private void saveFolders() {
        try {
            jsonWriter.open();
            jsonWriter.write(folders);
            jsonWriter.close();

            // Set the look and feel for JOptionPane
            UIManager.put("Panel.background", new Color(50, 50, 50));
            UIManager.put("OptionPane.background", new Color(50, 50, 50));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(30, 30, 30));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(45, 45, 45));  // Focus color
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

            JOptionPane.showMessageDialog(this, "Folders saved to file.", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            UIManager.put("Panel.background", new Color(50, 50, 50));
            UIManager.put("OptionPane.background", new Color(50, 50, 50));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(30, 30, 30));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(45, 45, 45));  // Focus color
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE, "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to load folders from JSON
    private void loadFolders() {
        try {
            folders.clear();
            folders.putAll(jsonReader.read());
            updateFoldersDisplay();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }
}

