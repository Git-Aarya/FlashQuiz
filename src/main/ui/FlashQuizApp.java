package ui;

import model.FlashCard;
import model.Folder;
import model.StudyTimer;

import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// A Java-based desktop application for creating, organizing, and reviewing flashcards.
// Initializes the FlashQuiz application, handles user input, and manages flashcard operations.

public class FlashQuizApp {
    private static final String JSON_STORE = "./data/flashcards.json";
    private final Map<String, Folder> folders;
    private final StudyTimer timer;
    private final Scanner scanner;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // MODIFIES: folders, timer, scanner
    // EFFECTS: Creates instances of folders, timer, and scanner, and loads existing folders.
    public FlashQuizApp() throws FileNotFoundException {
        folders = new HashMap<>();
        timer = new StudyTimer();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        // loadFolders(); -Autoload feature, removed for now since its against the guideline
    }

    // MODIFIES: This, folders, timer, scanner
    // EFFECTS: Manages user input, performs actions based on user choices, and saves folders on exit.
    public void start() {
        int action;
        do {
            printOptions();
            action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 1: createFolder();
                    break;
                case 2: addFlashCardToFolder();
                    break;
                case 3: reviewFlashCards();
                    break;
                case 4: deleteFolder();
                    break;
                case 5: editFlashCard();
                    break;
                case 6: saveFolders();
                    break;
                case 7: loadFolders();
                    break;
            }
        } while (action != 0);
    }

    // EFFECTS: Prints options for the user
    public void printOptions() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Create a folder");
        System.out.println("[2] Add a flashcard to a folder");
        System.out.println("[3] Review flashcards");
        System.out.println("[4] Delete a folder");
        System.out.println("[5] Edit a FlashCard");
        System.out.println("[6] Save folders to file");
        System.out.println("[7] Load folders from file");
        System.out.println("[0] Exit");
    }

    // MODIFIES: folders
    // EFFECTS: Prompts the user for a folder name, creates the folder, and adds it to the application.
    private void createFolder() {
        System.out.println("Enter a name for the new folder:");
        String folderName = scanner.nextLine();
        folders.put(folderName, new Folder(folderName));
        System.out.println("Folder created: " + folderName);
    }

    // MODIFIES: folders
    // EFFECTS: Prompts the user for a folder name, deletes the selected folder if it exists.
    private void deleteFolder() {
        listAllFolders();
        System.out.println("Enter the folder name you want to delete:");
        String folderName = scanner.nextLine();

        if (folders.containsKey(folderName)) {
            folders.remove(folderName);
            System.out.println("Folder deleted: " + folderName);
        } else {
            System.out.println("Folder does not exist.");
        }
    }

    // EFFECTS: Prints the names of all available folders or a message if no folders are available.
    private void listAllFolders() {
        if (folders.isEmpty()) {
            System.out.println("No folders available.");
        } else {
            System.out.println("Available folders:");
            for (String folderName : folders.keySet()) {
                System.out.println("- " + folderName);
            }
        }
    }

    // REQUIRES: folder != null
    // EFFECTS: Prints the list of flashcards in the console.
    private void listAllFlashCards(Folder folder) {
        List<FlashCard> flashCards = folder.getFlashCards();

        if (flashCards.isEmpty()) {
            System.out.println("No flashcards in the folder.");
        } else {
            System.out.println("Flashcards in folder '" + folder.getName() + "':");
            for (int i = 0; i < flashCards.size(); i++) {
                FlashCard flashCard = flashCards.get(i);
                System.out.println("[" + i + "] Question: " + flashCard.getQuestion());
            }
        }
    }

    // MODIFIES: folders
    // EFFECTS: Prompts the user for a folder name, adds a flashcard to the selected folder.
    private void addFlashCardToFolder() {
        listAllFolders();
        System.out.println("Enter the folder name where you want to add the flashcard:");
        String folderName = scanner.nextLine();
        Folder folder = folders.get(folderName);
        if (folder == null) {
            System.out.println("Folder does not exist. Please create it first.");
            return;
        }
        System.out.println("Enter a question for the flashcard:");
        String question = scanner.nextLine();
        System.out.println("Enter the answer for the flashcard:");
        String answer = scanner.nextLine();
        folder.addFlashCard(new FlashCard(question, answer));
        System.out.println("Flashcard added to folder " + folderName);
    }


    // EFFECTS: Prompts the user for a folder name, reviews flashcards with a timer, and displays results.
    private void reviewFlashCards() {
        listAllFolders();
        System.out.println("Enter the folder name you want to review:");
        String folderName = scanner.nextLine();
        Folder folder = folders.get(folderName);
        if (folder == null) {
            System.out.println("Folder does not exist.");
            return;
        }

        for (FlashCard card : folder.getFlashCards()) {
            long startTime = System.currentTimeMillis();
            System.out.println("Question: " + card.getQuestion());
            System.out.println("Press enter to show the answer...");
            scanner.nextLine();
            long endTime = System.currentTimeMillis();
            System.out.println("Answer: " + card.getAnswer());
            System.out.println("Time taken: " + timer.getElapsedTime(startTime,endTime));
            System.out.println();
        }
    }


    // REQUIRES: folder != null && 0 <= flashCardIndex < folder.getFlashCards().size()
    // MODIFIES: Flashcard content in the specified folder.
    // EFFECTS: Prompts the user for new question and answer, then updates the flashcard.

    private void editFlashCard() {
        listAllFolders();
        System.out.println("Enter the folder name where the flashcard is located:");
        String folderName = scanner.nextLine();
        Folder folder = folders.get(folderName);

        if (folder == null) {
            System.out.println("Folder does not exist.");
            return;
        }

        listAllFlashCards(folder);
        System.out.println("Enter the index of the flashcard you want to edit:");
        int flashCardIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (flashCardIndex < 0 || flashCardIndex >= folder.getFlashCards().size()) {
            System.out.println("Invalid index. Flashcard not edited.");
            return;
        }

        System.out.println("Enter the new question for the flashcard:");
        String newQuestion = scanner.nextLine();
        System.out.println("Enter the new answer for the flashcard:");
        String newAnswer = scanner.nextLine();

        folder.editFlashCard(flashCardIndex, newQuestion, newAnswer);
        System.out.println("Flashcard edited successfully.");
    }

    // MODIFIES: this
    // EFFECTS: Saves the folders to the data file
    private void saveFolders() {
        try {
            jsonWriter.open();
            jsonWriter.write(folders); // Pass the entire folders map to the write method
            jsonWriter.close();
            System.out.println("Folders saved to file.");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: Loads the folders from the data file
    private void loadFolders() {
        try {
            folders.putAll(jsonReader.read());
            System.out.println("Folders loaded from file.");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}




