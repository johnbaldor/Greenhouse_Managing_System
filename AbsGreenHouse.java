import java.util.*;

import static java.util.Calendar.YEAR;

/**
 * An abstract superclass to provide template methods for performance specific subclasses.
 */
public abstract class AbsGreenHouse implements QualityControlable {
    public ParsedDataStrategy strategy = new StoreDataLinkedList();
    private GregorianCalendar calendar;
    protected LinkedList<Double> Data;

    int keepTrackStrat = 0;

    protected AbsGreenHouse() {
        Data = new LinkedList<>();
        calendar = new GregorianCalendar();
    }
    public AbsGreenHouse(GregorianCalendar calendar) {
        Data = new LinkedList<>();
        this.calendar = (GregorianCalendar) calendar.clone();
    }





    void setStrategy(ParsedDataStrategy otherStrategy){
        this.Data.clear();
        this.strategy = otherStrategy;

        keepTrackStrat = keepTrackStrat + 1;

    }



    public void parse() {
        LinkedList<Double> andDataTemp = new LinkedList<>();
        LinkedList<Double> humidities = new LinkedList<>();
        LinkedList<Double> temperatures = new LinkedList<>();
        for (double num: Data){
            if (! isDateTime(num)){
                andDataTemp.add(num);}
            }

        int counter =1;
        for (double num: andDataTemp){
            if (counter % 2 == 0){
                humidities.add(num);}
            else {temperatures.add(num);}
            counter = counter + 1;}


        System.out.println(strategy);

        if (keepTrackStrat % 2 == 1){
            ArrayList<Double> tempA = new ArrayList<>(temperatures);
            ArrayList<Double> humdA = new ArrayList<>(humidities);
            strategy.consumeData(tempA,humdA);
        }

        else {strategy.consumeData(temperatures, humidities);}

    }



    public void parseOndate(double onDate) {
        LinkedList<Double> temperaturesOnDate = new LinkedList<>();
        LinkedList<Double> humiditiesOnDate = new LinkedList<>();

        int counter = 1;
        boolean rightDate = false;
        for (double num : Data) {
            if ((! sameDate(onDate, toDate(num))) & isDate(num)){
                rightDate = false;
            }
            if (sameDate(onDate, toDate(num))) {
                rightDate =  true;
            }
            else if (counter % 2 == 0 & rightDate){
                humiditiesOnDate.add(num);
                counter = counter+ 1;
            }
            else if (counter % 2 == 1 & rightDate){
                temperaturesOnDate.add(num);
                counter = counter+ 1;
            }
            else{System.out.println(3);}
        }


        if (keepTrackStrat % 2 == 1){
            ArrayList<Double> tempA = new ArrayList<>(temperaturesOnDate);
            ArrayList<Double> humdA = new ArrayList<>(humiditiesOnDate);
            strategy.consumeData(tempA,humdA);
        }

        else {strategy.consumeData(temperaturesOnDate, humiditiesOnDate);}


    }



    /**
     * A helper method to convert a gregroian calendar to a HW3 style datetime double
     * @return a HW3 style datetime double
     */
    public double LatestDatetime(){


        double year = calendar.get(YEAR);
        double month = calendar.get(Calendar.MONTH) + 1;
        double day = calendar.get(Calendar.DAY_OF_MONTH);
        double hour = calendar.get(Calendar.HOUR_OF_DAY);
        double minute = calendar.get(Calendar.MINUTE);
        double second = calendar.get(Calendar.SECOND);
        return second +
                (minute * 100.0) + //shifted 2 decimal places
                (hour * 100.0 * 100.0) + //shifted 4 decimal places
                (day * 100.0 * 100.0 * 100.0) + //shifted 6 decimal places
                (month * 100.0 * 100.0 * 100.0 * 100.0) + //shifted 8 decimal places
                (year * 100.0 * 100.0 * 100.0 * 100.0 * 100.0); //shifted 10 decimal places

    }

    /**
     * Given a datetime as a double, make a java.util.GregorianCalendar object with the
     * appropriate year, month, day of the month, hour of the day, minute, and second.
     *
     * @param datetime a double in the format YYYYMMDDhhmmss.0
     * for example 20231112133045 for the date time Nov 12th 2023 at 1:30:45pm
     */
    public void setClockTo(double datetime) {
        String datetimeStr = String.format("%.0f", datetime);
        System.out.println("datetime string in set clock method:" + datetimeStr);

        int year = Integer.parseInt(datetimeStr.substring(0, 4));
        // Subtract 1 from month because GregorianCalendar months are 0-based
        int month = Integer.parseInt(datetimeStr.substring(4, 6)) - 1;
        int day = Integer.parseInt(datetimeStr.substring(6, 8));
        int hour = Integer.parseInt(datetimeStr.substring(8, 10));
        int minute = Integer.parseInt(datetimeStr.substring(10, 12));
        int second = Integer.parseInt(datetimeStr.substring(12, 14));
        this.calendar = new GregorianCalendar(year, month, day, hour, minute, second);
        System.out.println(this.calendar.get(YEAR));
        System.out.println(this.calendar.get(Calendar.MONTH));
    }

    /**
     * computes the current percentage of non-datetime sensor values that are -999.0s
     *
     * @return a percent value between 0.0 and 100.0 inclusive
     */
    public double percentError() {
        int errorCount = 0;
        int totalCount = 0;
       for (double num: Data){
           if (num == -999.0 ){
               System.out.println(num);
               errorCount += 1;
               totalCount += 1;
           }
           else if (! isDateTime(num)){
               System.out.println(num);
               totalCount +=1;
           }}
       System.out.println(totalCount);
       System.out.println(errorCount);
       return (double) errorCount /totalCount * 100;
    }



    // GIVEN CODE
    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
    public boolean isDate(double sensorDatum){

        return 19700101000000.0 > sensorDatum & sensorDatum> 19700101.0;
    }

    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970 00:00:00 represented as a double
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
    public boolean isDateTime(double sensorDatum){
        return sensorDatum > 19700101000000.0;
    }

    /**
     * Converts the double date time format to just the date part by dividing and rounding
     * @param dateTime YYYYMMDDhhmmss.0
     * @return YYYYMMDD.0
     */
    public double toDate(double dateTime){
        return Math.floor(dateTime / 1000000.0); // convert YYYYMMDDhhmmss -> YYYYMMDD
    }

    /**
     * compares two YYYYMMDD.0 for equality
     * @param date1 one YYYYMMDD.0
     * @param date2 another YYYYMMDD.0
     * @return true if they are within some error tolerance (0.001) of each other
     */
    public boolean sameDate(double date1, double date2){
        return Math.abs(date1 - date2) < 0.0000001;
    }





}
