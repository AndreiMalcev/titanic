public class Main {


    public static void main(String[] args) {
        var data = new LoaderData().load("data/test.csv");
        var idColumn = data.intColumn("PassengerId");
        var features = Processing.preprocessed(data);
        var model = new LoaderModel().load("model/titanic.h5");
        var prediction = model.output(features);
        var dataToKaggle = Processing.processPredict(idColumn, prediction);
        Saver.save(dataToKaggle, "src//main//resources//predict//submission.csv");
    }
}