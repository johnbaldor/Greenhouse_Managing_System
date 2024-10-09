import java.util.*;

import static java.util.Calendar.YEAR;

public class GreenHouseNursery extends AbsGreenHouse implements Sensible{

    public GreenHouseNursery(){
    }

    public GreenHouseNursery(GregorianCalendar gc){
        super(gc);
    }



    /**
     * Reads an ordered sequence of data from the weather sensors to store in the greenhouse
     * When called multiple times, appends the new readings after the current sensor readings
     *
     * @param values An ordered sequence of [datetime, temperature, humidity, temperature, humidity, ..., datetime, temperature, humidity,....]
     *               - a date and time in yyyymmddHHMMSS format. E.g. 20231106183930 for Nov 11, 2023, 6:39:30pm
     *               - temperature is either degrees Fahrenheit or -999 for an error case
     *               - humidity is either % from 0.0 to 100.0 or -999 for an error case
     *               Assume the sensor data always starts with a valid date
     *               The multiple temperature humidity pairs for a single datetime come from different plant sensors
     *               The values may skip dates and times when the sensors are off (you cannot assume that the date/time intervals will be regular)
     *               You *may* assume that the datetimes will be in ascending order
     */
    @Override
    public void pollSensorData(List<Double> values) {
        double newLatestDate = 0.0;
        boolean after = false;

        System.out.println("start, values:"+ values);

        System.out.println(" ");

        for (double num : values){
            System.out.println("current num"+ num);
            System.out.println("latest date time"+ this.LatestDatetime());
            System.out.println("is current num dt?"+ isDateTime(num));
            System.out.println("is current num d?"+ isDate(num));
                if(isDate(num) & (num * 1000000 >= this.LatestDatetime())) {
                    System.out.println("hit isDate and later" + num);
                    this.Data.add(num * 1000000);
                    after = true;
                    if (num * 1000000 > newLatestDate) {
                        newLatestDate = num * 1000000;
                    }
                }

                if(isDateTime(num) & (num >= this.LatestDatetime())) {
                    System.out.println("hit isDateTime and later" + num);
                    this.Data.add(num);
                    after = true;
                    if(num > newLatestDate){
                        newLatestDate = num;
                    }
                }

                if(num < this.LatestDatetime() & after) {
                    System.out.println("hit after" + num);
                    this.Data.add(num);
                }
            System.out.println(" ");
        }
        System.out.println("new values:" + Data);
        System.out.println("datetime string in pollsensor int:" + newLatestDate);
        if( newLatestDate != 0.0){
        this.setClockTo(newLatestDate);}
        }




    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    @Override
    public TempHumidReading middleReading(){
        this.parse();
        return this.strategy.middleReading();
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
    public TempHumidReading middleReading(double onDate){
        this.parseOndate(onDate);
        return this.strategy.middleReadingOndate(onDate);
    }


}
