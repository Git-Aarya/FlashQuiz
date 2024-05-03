package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FolderTest {
    private Folder testFolder;

    @BeforeEach
    void setUp() {
        // Create a new Folder
        testFolder = new Folder("CPSC210");
    }

    @Test
    void testGetName() {
        assertEquals("CPSC210", testFolder.getName());
    }

    @Test
    void testAddFlashCard() {
        FlashCard flashCard = new FlashCard("What is my CPSC210 project on?", "FlashCards");
        testFolder.addFlashCard(flashCard);
        assertTrue(testFolder.getFlashCards().contains(flashCard));
    }

    @Test
    void testAddMultipleFlashCardsAndGetNames() {
        FlashCard flashCard1 = new FlashCard("Question 1", "Answer 1");
        FlashCard flashCard2 = new FlashCard("Question 2", "Answer 2");
        FlashCard flashCard3 = new FlashCard("Question 3", "Answer 3");

        testFolder.addFlashCard(flashCard1);
        testFolder.addFlashCard(flashCard2);
        testFolder.addFlashCard(flashCard3);

        assertEquals(3, testFolder.getFlashCards().size());
        assertTrue(testFolder.getFlashCards().contains(flashCard1));
        assertTrue(testFolder.getFlashCards().contains(flashCard2));
        assertTrue(testFolder.getFlashCards().contains(flashCard3));
    }

    @Test
    void testEditFlashCard() {
        FlashCard flashCard = new FlashCard("What is my CPSC210 project on?", "FlashCards");
        testFolder.addFlashCard(flashCard);

        // Edit the flashcard
        testFolder.editFlashCard(0, "New Question", "New Answer");

        assertEquals("New Question", testFolder.getFlashCards().get(0).getQuestion());
        assertEquals("New Answer", testFolder.getFlashCards().get(0).getAnswer());
    }

    @Test
    void testEditFlashCardInvalidIndex() {
        FlashCard flashCard = new FlashCard("What is my CPSC210 project on?", "FlashCards");
        testFolder.addFlashCard(flashCard);

        testFolder.editFlashCard(1, "Invalid Question", "Invalid Answer");

        assertEquals("What is my CPSC210 project on?", testFolder.getFlashCards().get(0).getQuestion());
        assertEquals("FlashCards", testFolder.getFlashCards().get(0).getAnswer());
    }

    @Test
    void testEditFlashCardNullFolder() {
        // Attempt to edit with a null folder
        Folder nullFolder = null;
        assertThrows(NullPointerException.class, () ->
                nullFolder.editFlashCard(0, "Question", "Answer"));
    }

    @Test
    void testEditFlashCardWithNegativeIndex() {
        FlashCard initialFlashCard = new FlashCard("Original Question?", "Original Answer");
        testFolder.addFlashCard(initialFlashCard);
        testFolder.editFlashCard(-1, "Edited Question", "Edited Answer");

        assertEquals("Original Question?", testFolder.getFlashCards().get(0).getQuestion());
        assertEquals("Original Answer", testFolder.getFlashCards().get(0).getAnswer());
    }

    @Test
    void testEditFlashCardBeyondListSize() {
        FlashCard initialFlashCard = new FlashCard("Original Question?", "Original Answer");
        testFolder.addFlashCard(initialFlashCard);
        testFolder.editFlashCard(2, "Edited Question", "Edited Answer");

        assertEquals("Original Question?", testFolder.getFlashCards().get(0).getQuestion());
        assertEquals("Original Answer", testFolder.getFlashCards().get(0).getAnswer());
    }

    @Test
    void testGetFlashCards() {
        FlashCard flashCard1 = new FlashCard("Who is my CPSC210 professor?", "Felix Grund");
        FlashCard flashCard2 = new FlashCard("Who is my CPSC210 lab TA", "Michelle");

        testFolder.addFlashCard(flashCard1);
        testFolder.addFlashCard(flashCard2);

        assertEquals(2, testFolder.getFlashCards().size());
    }

    @Test
    void testGetMultipleFlashCards() {
        FlashCard flashCard1 = new FlashCard("Question 1", "Answer 1");
        FlashCard flashCard2 = new FlashCard("Question 2", "Answer 2");
        FlashCard flashCard3 = new FlashCard("Question 3", "Answer 3");

        testFolder.addFlashCard(flashCard1);
        testFolder.addFlashCard(flashCard2);
        testFolder.addFlashCard(flashCard3);

        assertEquals(3, testFolder.getFlashCards().size());
        assertTrue(testFolder.getFlashCards().contains(flashCard1));
        assertTrue(testFolder.getFlashCards().contains(flashCard2));
        assertTrue(testFolder.getFlashCards().contains(flashCard3));
    }

    @Test
    void testGetFlashCardsEmptyFolder() {
        assertTrue(testFolder.getFlashCards().isEmpty());
    }
}
