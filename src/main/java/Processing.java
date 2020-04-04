import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import java.util.HashMap;
import java.util.Map;


public class Processing {

    private static final String[] deleteColumns = {"PassengerId", "Name", "Ticket", "Cabin"};


    private static void getDummies(Table data) {
        Map<String, Integer> sexToInt = new HashMap<>();
        sexToInt.put("male", 0);
        sexToInt.put("female", 1);
        replace("Sex", data, sexToInt);
        Map<String, Integer> embarkedToInt = new HashMap<>();
        embarkedToInt.put("S", 0);
        embarkedToInt.put("C", 1);
        embarkedToInt.put("Q", 2);
        replace("Embarked", data, embarkedToInt);
    }


    private static void replace(String name, Table data, Map<String, Integer> map) {
        Column<Integer> newColumn = IntColumn.create(name);
        for (var line : data.stringColumn(name).asList()) {
            newColumn.append(map.get(line));
        }
        data.replaceColumn(name, newColumn);
    }


    private static void nullToMedian(String name, Table data) {
        var median = data.doubleColumn(name).median();
        var column = data.doubleColumn(name).asList();
        column.replaceAll(x -> {
            if (x == null) {
                return median;
            }
            return x;
        });
        data.replaceColumn(name, DoubleColumn.create(name, column.toArray(new Double[0])));
    }


    private static double[] getRow(Row row) {
        double[] res = new double[row.columnCount()];
        for (int i = 0; i < res.length; ++i) {
            var value = row.getObject(i);
            if (value instanceof Integer) {
                res[i] = ((Integer) value).doubleValue();
            } else {
                res[i] = row.getDouble(i);
            }
        }
        return res;
    }


    public static INDArray preprocessed(Table data) {
        data.removeColumns(deleteColumns);
        getDummies(data);
        nullToMedian("Age", data);
        nullToMedian("Fare", data);
        double[][] mas = new double[data.rowCount()][data.columnCount()];
        for (int i = 0; i < data.rowCount(); i++) {
            mas[i] = getRow(data.row(i));
        }
        return Nd4j.create(mas);
    }


    public static Table processPredict(IntColumn columnId, INDArray target) {
        Column<Integer> columnTarget = IntColumn.create("Survived");
        for (int i = 0; i < target.length(); i++) {
            columnTarget.append((int) Math.round(target.getDouble(i)));
        }
        return Table.create(columnId, columnTarget);
    }
}