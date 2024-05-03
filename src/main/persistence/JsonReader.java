package persistence;

import model.FlashCard;
import model.Folder;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads folder from file and returns it;
    // throws IOException if an error occurs reading from file
    public Map<String, Folder> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFolders(jsonObject);
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Writable object from JSON object and returns it
    private Map<String, Folder> parseFolders(JSONObject jsonObject) {
        Map<String, Folder> folders = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            JSONObject folderJson = jsonObject.getJSONObject(key);
            Folder folder = parseFolder(folderJson);
            folders.put(key, folder);
        }
        return folders;
    }

    // MODIFIES: this
    // EFFECTS: parses Folder from JSON object and returns it
    private Folder parseFolder(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Folder folder = new Folder(name);
        JSONArray flashCardsJsonArray = jsonObject.getJSONArray("flashCards"); // This should be a JSONArray
        addFlashCards(folder, flashCardsJsonArray);
        return folder;
    }

    // MODIFIES: folder
    // EFFECTS: parses FlashCards from JSON object and adds them to folder
    private void addFlashCards(Folder folder, JSONArray jsonArray) {
        for (Object obj : jsonArray) {
            JSONObject flashCardJson = (JSONObject) obj;
            String question = flashCardJson.getString("question");
            String answer = flashCardJson.getString("answer");
            folder.addFlashCard(new FlashCard(question, answer));
        }
    }


}
