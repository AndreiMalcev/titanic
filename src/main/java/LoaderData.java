import org.nd4j.linalg.io.ClassPathResource;
import tech.tablesaw.api.Table;
import java.io.IOException;


public class LoaderData implements Loader {


    @Override
    public Table load(String path) {
        Table data = null;
        try {
            var fullPath = new ClassPathResource(path).getFile().getPath();
            data = Table.read().csv(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
