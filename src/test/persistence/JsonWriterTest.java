package persistence;

import model.Folder;
import model.FlashCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriterEmptyFolders() throws IOException {
        Path file = tempDir.resolve("emptyFolders.json");
        JsonWriter writer = new JsonWriter(file.toString());
        writer.open();
        writer.write(new HashMap<>()); // Write an empty map
        writer.close();

        JsonReader reader = new JsonReader(file.toString());
        Map<String, Folder> folders = reader.read();
        assertTrue(folders.isEmpty());
    }

    @Test
    void testWriterGeneralFolders() throws IOException {
        Map<String, Folder> foldersMap = new HashMap<>();
        Folder cpsc210 = new Folder("CPSC210");
        cpsc210.addFlashCard(new FlashCard("What is Java?", "A programming language."));
        cpsc210.addFlashCard(new FlashCard("What is JUnit?", "A testing framework."));
        foldersMap.put("CPSC210", cpsc210);

        Path file = tempDir.resolve("generalFolders.json");
        JsonWriter writer = new JsonWriter(file.toString());
        writer.open();
        writer.write(foldersMap);
        writer.close();

        JsonReader reader = new JsonReader(file.toString());
        Map<String, Folder> folders = reader.read();
        assertEquals(1, folders.size());
        assertTrue(folders.containsKey("CPSC210"));
        Folder folder = folders.get("CPSC210");
        assertEquals("CPSC210", folder.getName());
        assertEquals(2, folder.getFlashCards().size());
    }
}
