package persistence;

import model.FlashCard;
import model.Folder;
import model.StudyTimer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFlashCard(String question, String answer, FlashCard flashCard) {
        assertEquals(question, flashCard.getQuestion());
        assertEquals(answer, flashCard.getAnswer());
    }
}
