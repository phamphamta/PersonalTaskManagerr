package repository;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class TaskRepository {
    private static final String DB_FILE_PATH = "tasks_database.json";

    static {
        File file = new File(DB_FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            } catch (IOException e) {
                System.err.println("Không thể tạo file database: " + e.getMessage());
            }
        }
    }

    public static JSONArray load() {
        try (FileReader reader = new FileReader(DB_FILE_PATH)) {
            return (JSONArray) new JSONParser().parse(reader);
        } catch (IOException | ParseException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
            return new JSONArray();
        }
    }

    public static void save(JSONArray data) {
        try (FileWriter writer = new FileWriter(DB_FILE_PATH)) {
            writer.write(data.toJSONString());
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}

