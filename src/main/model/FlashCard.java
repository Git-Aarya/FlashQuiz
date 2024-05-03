package model;

// Represents a flippable flashcard with a question and an answer.
// Constructs a FlashCard with the given question and answer.

import org.json.JSONObject;
import persistence.Writable;

public class FlashCard implements Writable {
    private String question;
    private String answer;

    // EFFECTS: Initializes the FlashCard with the provided question and answer.
    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // EFFECTS: Retrieves the question on the flashcard.
    public String getQuestion() {
        return question;
    }

    // EFFECTS: Retrieves the answer on the flashcard.
    public String getAnswer() {
        return answer;
    }

    // EFFECTS: Sets the question on the flashcard.
    public void setQuestion(String question) {
        this.question = question;
    }

    // EFFECTS: Sets the answer on the flashcard.
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        return json;
    }
}



