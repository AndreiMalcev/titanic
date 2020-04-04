import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.io.ClassPathResource;
import java.io.IOException;


public class LoaderModel implements Loader {


    @Override
    public MultiLayerNetwork load(String path) {
        String fullPath;
        MultiLayerNetwork model = null;
        try {
            fullPath = new ClassPathResource(path).getFile().getPath();
            model = KerasModelImport.
                    importKerasSequentialModelAndWeights(fullPath);
        } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        }
        return model;
    }
}
