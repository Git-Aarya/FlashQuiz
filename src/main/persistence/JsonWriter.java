package persistence;

import org.json.JSONObject;
import model.Folder;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Map;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Writable object to file
    public void write(Map<String, Folder> foldersMap) {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Folder> entry : foldersMap.entrySet()) {
            json.put(entry.getKey(), entry.getValue().toJson());
        }
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}


