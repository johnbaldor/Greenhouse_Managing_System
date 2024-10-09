import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ParsedDataStrategy {
    public void consumeData(List<Double> temperatures, List<Double> humidities);
    public void consumeDataOnDate(List<Double> temperaturesOnDate, List<Double> humiditiesOndate);

    public void sortParsedData();
    public void clean(List<Double> values);

    public TempHumidReading middleReading();
    public TempHumidReading middleReadingOndate(double onDate);




}
