package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

// Entry point for the FlashQuiz application.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FlashQuizAppGUI app = new FlashQuizAppGUI();
                    app.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

