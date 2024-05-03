package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlashCardTest {
    private FlashCard testFlashCard;

    @BeforeEach
    void setUp() {
        // Create a new FlashCard with a question and answer
        testFlashCard = new FlashCard("What is my CPSC210 project on?", "FlashCards");
    }

    @Test
    void testGetQuestion() {
        assertEquals("What is my CPSC210 project on?", testFlashCard.getQuestion());
    }

    @Test
    void testGetAnswer() {
        assertEquals("FlashCards", testFlashCard.getAnswer());
    }

    @Test
    void testSetQuestion() {
        FlashCard flashCard = new FlashCard("Question", "Answer");
        flashCard.setQuestion("New Question");
        assertEquals("New Question", flashCard.getQuestion());
    }

    @Test
    void testSetAnswer() {
        FlashCard flashCard = new FlashCard("Question", "Answer");
        flashCard.setAnswer("New Answer");
        assertEquals("New Answer", flashCard.getAnswer());
    }
}