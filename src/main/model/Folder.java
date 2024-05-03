package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a folder for organizing flashcards.
// Constructs a Folder with the given name and an empty list of flashcards.

public class Folder implements Writable {
    private final String name;
    private final List<FlashCard> flashCards;

    // MODIFIES: This
    // EFFECTS: Initializes the Folder with the provided name and an empty list of flashcards.
    public Folder(String name) {
        this.name = name;
        this.flashCards = new ArrayList<>();
    }

    // REQUIRES: flashCard is not null
    // MODIFIES: This
    // EFFECTS: Adds the provided flashcard to the list of flashcards in the folder.
    public void addFlashCard(FlashCard flashCard) {
        flashCards.add(flashCard);
    }

    // EFFECTS: Retrieves the list of flashcards in the folder.
    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    // EFFECTS: Retrieves the name of the folder.
    public String getName() {
        return name;
    }

    // REQUIRES: 0 <= index < flashCards.size()
    // MODIFIES: this
    // EFFECTS: Updates the question and answer of the specified flashcard.
    public void editFlashCard(int index, String newQuestion, String newAnswer) {
        if (index >= 0 && index < flashCards.size()) {
            FlashCard flashCard = flashCards.get(index);
            flashCard.setQuestion(newQuestion);
            flashCard.setAnswer(newAnswer);
        } else {
            System.out.println("Invalid index. Flashcard not edited.");
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("flashCards", flashCardsToJson());
        return json; // Return the JSONObject instead of JSONArray
    }

    private JSONArray flashCardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (FlashCard flashCard : flashCards) {
            jsonArray.put(flashCard.toJson());
        }
        return jsonArray;
    }
}


