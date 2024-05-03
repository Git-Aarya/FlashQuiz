package persistence;

import model.Folder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @TempDir
    Path tempDir;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader(tempDir.resolve("nonExistentFile.json").toString());
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderEmptyFolder() throws IOException {
        Path file = tempDir.resolve("emptyFolder.json");
        Files.writeString(file, "{}");

        JsonReader reader = new JsonReader(file.toString());
        Map<String, Folder> folders = reader.read();
        assertTrue(folders.isEmpty());
    }

    @Test
    void testReaderGeneralFolder() throws IOException {
        Path file = tempDir.resolve("generalFolder.json");
        String content = "{" +
                "\"CPSC210\": {" +
                "\"flashCards\": [" +
                "{\"question\": \"What is Java?\", \"answer\": \"A programming language.\"}," +
                "{\"question\": \"What is JUnit?\", \"answer\": \"A testing framework.\"}" +
                "]," +
                "\"name\": \"CPSC210\"" +
                "}" +
                "}";
        Files.writeString(file, content);

        JsonReader reader = new JsonReader(file.toString());
        Map<String, Folder> folders = reader.read();
        assertEquals(1, folders.size());
        assertTrue(folders.containsKey("CPSC210"));
        Folder folder = folders.get("CPSC210");
        assertEquals("CPSC210", folder.getName());
        assertEquals(2, folder.getFlashCards().size());
        checkFlashCard("What is Java?", "A programming language.", folder.getFlashCards().get(0));
        checkFlashCard("What is JUnit?", "A testing framework.", folder.getFlashCards().get(1));
    }
}
