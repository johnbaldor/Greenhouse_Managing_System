import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StoreDataArrayList implements ParsedDataStrategy  {

    protected ArrayList<Double> Data;

    protected ArrayList<Double> temperatures = new ArrayList<>();
    protected ArrayList<Double> humidities = new ArrayList<>();
    protected ArrayList<Double> temperaturesOnDate= new ArrayList<>();
    protected ArrayList<Double> humiditiesOnDate= new ArrayList<>();





    @Override
    public void consumeData(List<Double> temperatures, List<Double> humidities) {
        if (! humidities.isEmpty() & temperatures.isEmpty()){
        this.humidities.clear();
        this.temperatures.clear();}
        this.humidities = (ArrayList<Double>) humidities;
        this.temperatures = (ArrayList<Double>) temperatures;

    }

    @Override
    public void consumeDataOnDate(List<Double> temperaturesOnDate, List<Double> humiditiesOnDate) {
        if (! humiditiesOnDate.isEmpty() & temperaturesOnDate.isEmpty()){
        this.humiditiesOnDate.clear();
        this.temperaturesOnDate.clear();}
        this.humiditiesOnDate = (ArrayList<Double>) humiditiesOnDate;
        this.temperaturesOnDate = (ArrayList<Double>) temperaturesOnDate;

    }

    @Override
    public void sortParsedData() {
        Collections.sort(temperatures);
        Collections.sort(humidities);
        Collections.sort(humiditiesOnDate);
        Collections.sort(temperaturesOnDate);
        clean(temperatures);
        clean(humidities);
        clean(humiditiesOnDate);
        clean(temperaturesOnDate);
    }

    @Override
    public void clean(List<Double> values) {
        for (int i = 0; i < values.size(); i++) {
            double currentValue = values.get(i);
            if (currentValue == -999.0) {
                values.remove(i);
                i--;}}}



    @Override
    public TempHumidReading middleReading(){

        System.out.println(temperatures);
        System.out.println(humidities);

        if (temperatures.isEmpty() & humidities.isEmpty()){
            return new SuperTempHumidReading(-999.0,-999.0);}
        else if (temperatures.isEmpty()){
            return new SuperTempHumidReading(-999.0,humidities.get(humidities.size() / 2));
        }
        else if (humidities.isEmpty()){
            return new SuperTempHumidReading(temperatures.get(temperatures.size() / 2),-999.0);
        }
        else {
            return new SuperTempHumidReading(temperatures.get(temperatures.size() / 2),humidities.get(humidities.size() / 2));}
    }

    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    @Override
    public TempHumidReading middleReadingOndate(double onDate){
        System.out.println(humiditiesOnDate);

        if(temperaturesOnDate.isEmpty() & humiditiesOnDate.isEmpty()){
            return new TempHumidReading(-999,-999);
        }
        return new TempHumidReading(temperaturesOnDate.get(temperaturesOnDate.size() / 2),humiditiesOnDate.get(humiditiesOnDate.size() / 2));


    }


}
