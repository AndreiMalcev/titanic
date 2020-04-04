import tech.tablesaw.api.Table;
import java.io.File;
import java.io.IOException;

public class Saver {


    public static void save(Table data, String path) {
        var file = new File(path);
        try {
            if (file.createNewFile()) {
                data.write().csv(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
