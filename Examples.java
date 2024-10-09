import org.junit.Test;

import java.util.*;

import static java.util.Calendar.YEAR;
import static org.junit.Assert.*;

public class Examples {
    public Examples(){}
    @Test
    public void testPrintWhatever(){
        GregorianCalendar gc = new GregorianCalendar(2020, Calendar.MARCH,13);
        GreenHouseNursery ghn = new GreenHouseNursery(gc);
        System.out.println(ghn.LatestDatetime());
    }
    @Test
    public void testSetStrat(){
        GregorianCalendar gc = new GregorianCalendar(2020, Calendar.MARCH,13);
        GreenHouseNursery ghn = new GreenHouseNursery(gc);
        gc.add(YEAR,2);
        List<Double> data = List.of(20191010203040.0 , 210.0 , 1209.0, 321.0, 123.0,20211010203040.0, 100.0, 100.0);
        ghn.pollSensorData(data);
        ghn.setStrategy(new StoreDataArrayList());
        ghn.pollSensorData(data);
        assertEquals(ghn.middleReading(20211010203040.0).toString(), new SuperTempHumidReading(100,100));

    }

    @Test
    public void testgregCalendarmiddle(){
        GregorianCalendar gc = new GregorianCalendar(2020, Calendar.MARCH,13);
        GreenHouseNursery ghn = new GreenHouseNursery(gc);


        List<Double> data = List.of(20191010203040.0 , 210.0 , 1209.0, 321.0, 123.0,20211010203040.0, 100.0, 100.0);
        ghn.pollSensorData(data);
        assertEquals(ghn.middleReading().toString(), new SuperTempHumidReading(100,100));
    }
    @Test
    public void testgregCalendarmiddleProduce(){
        GregorianCalendar gc = new GregorianCalendar(2020, Calendar.MARCH,13);
        GreenHouseProduce ghn = new GreenHouseProduce(gc);


        List<Double> data = List.of(20191010203040.0 , 210.0 , 1209.0, 321.0, 123.0,20211010203040.0, 100.0, 100.0);
        ghn.pollSensorData(data);
        assertEquals(ghn.middleReading(), new SuperTempHumidReading(100,100));
    }
    @Test
    public void testgregCalendarmiddlebug(){
        GregorianCalendar gc = new GregorianCalendar(2020, Calendar.MARCH,13);
        GreenHouseNursery ghn = new GreenHouseNursery(gc);
        gc.add(YEAR,2);
        List<Double> data = List.of(20191010203040.0 , 210.0 , 1209.0, 321.0, 123.0,20211010203040.0, 100.0, 100.0);
        ghn.pollSensorData(data);
        assertEquals(ghn.middleReading().toString(), new SuperTempHumidReading(100,100));
    }

    @Test
    public void testPrecentError(){
        GregorianCalendar gc = new GregorianCalendar(2020, Calendar.MARCH,14);
        GreenHouseNursery ghn = new GreenHouseNursery(gc);
        List<Double> data = List.of(20191010203040.0 , 210.0 , 1209.0, 321.0, 123.0,20211010203040.0, 100.0, 100.0,-999.0,-999.0);
        ghn.pollSensorData(data);
        assertEquals(ghn.percentError(), 50.0, 0.1);
    }
/*
    @Test
    public void testParseProduce(){
        List<Double> sensorData =
                List.of(20231106010101.0, 45.5, 34.0, 46.6, 40.0, 20231130020202.0, 22.2, 20.0, 35.5, 30.0, -999.0, 31.0, 32.2, -999.0);
        GreenHouseProduce greenHouse1 = new GreenHouseProduce();
        greenHouse1.pollSensorData(sensorData);
        greenHouse1.parse();
        assertEquals(List.of(34.0, 40.0, 20.0, 30.0, 31.0, -999.0),greenHouse1.humidities);
    }


    @Test
    public void testConstructorGHP(){
        List<Double> sensorData =
                List.of(20231106010101.0, 45.5, 34.0, 46.6, 40.0, 20231130020202.0, 22.2, 20.0, 35.5, 30.0, -999.0, 31.0, 32.2, -999.0);
        GreenHouseNursery greenHouse1 = new GreenHouseNursery();
        greenHouse1.pollSensorData(sensorData);
        GreenHouseProduce greenHouse2 = new GreenHouseProduce();
        greenHouse2.pollSensorData(sensorData);
        assertEquals(new GreenHouseProduce().middleReading(),new GreenHouseProduce().middleReading());
    }
    @Test
    public void testError(){
        GreenHouseNursery test = new GreenHouseNursery();
        assertEquals(new SuperTempHumidReading(),test.middleReading(20231106.0));
    }




    @Test
    public void testConstructorNoArg(){
        assertEquals("{Err;Err}", new SuperTempHumidReading().toString());
    }
    @Test
    public void testConstructorThr(){
        SuperTempHumidReading THR1 = new SuperTempHumidReading(35.5,31.0);
        assertEquals("{35.5F;31.0%}", THR1.toString());
    }
    @Test
    public void testConstructor2Arg(){
        TempHumidReading THR1 = new TempHumidReading(35.5,31.0);
        assertEquals("{35.5F;31.0%}", new SuperTempHumidReading(THR1).toString());
    }


    @Test
    public void testSuperTempReadingHumidReadingEquals(){
        TempHumidReading STHR1 = new TempHumidReading(35.5,31.0);
        TempHumidReading STHR2 = new TempHumidReading(35.5,31.0);
        assertEquals(false, STHR1.equals(STHR2));

    }
    @Test
    public void testSuperTempReadingHumidReadingEqualsError(){
        TempHumidReading STHR1 = new TempHumidReading(-999.0,-999.0);
        TempHumidReading STHR2 = new TempHumidReading(-999.0,-999.0);
        assertEquals(false, STHR1.equals(STHR2));

    }
    @Test
    public void testSuperTempReadingHumidReadingEqualsFalse(){
        SuperTempHumidReading STHR1 = new SuperTempHumidReading(35.5,31.0);
        SuperTempHumidReading STHR2 = new SuperTempHumidReading(35.6,32.0);
        assertEquals(false, STHR1.equals(STHR2));

    }
    @Test
    public void testSuperTempReadingHumidReadingEqualsFalseEdge(){
        SuperTempHumidReading STHR1 = new SuperTempHumidReading(35.5,31.0);
        assertEquals(false, STHR1.equals(2));
    }



    @Test
    public void testSuperTempReadingToString(){
        SuperTempHumidReading STHR1 = new SuperTempHumidReading(35.5,31.0);
        assertEquals("{35.5F;31.0%}", STHR1.toString());

    }
    @Test
    public void testSuperTempReadingToStringError(){
        SuperTempHumidReading STHR1 = new SuperTempHumidReading(-999.0,-999.0);
        assertEquals("{Err;Err}", STHR1.toString());

    }


    @Test
    public void testMiddleReadingProduce() {
        List<Double> sensorData =
                List.of(20231106010101.0, 45.5, 34.0, 46.6, 40.0, 20231130020202.0, 22.2, 20.0, 35.5, 30.0, -999.0, 31.0, 32.2, -999.0);
        GreenHouseProduce greenHouse1 = new GreenHouseProduce();
        greenHouse1.pollSensorData(sensorData);
        greenHouse1.parse();
        assertEquals(new SuperTempHumidReading(35.5,31.0),greenHouse1.middleReading());
    }

    @Test
    public void testMiddleReadingListOfInvalidsProduce() {
        List<Double> sensorData =
                List.of(20231106010101.0, -999.0, -999.0, 20231130020202.0, -999.0, -999.0);
        GreenHouseProduce greenHouse1 = new GreenHouseProduce();
        greenHouse1.pollSensorData(sensorData);
        assertEquals( new SuperTempHumidReading(-999.0,-999.0),greenHouse1.middleReading());
    }

    @Test
    public void testMiddleReadingNursery() {
        List<Double> sensorData =
                List.of(20231106010101.0, 45.5, 34.0, 46.6, 40.0, 20231130020202.0, 22.2, 20.0, 35.5, 30.0, -999.0, 31.0, 32.2, -999.0);
        GreenHouseNursery greenHouse1 = new GreenHouseNursery();
        greenHouse1.pollSensorData(sensorData);
        assertEquals( new SuperTempHumidReading(35.5,31.0),greenHouse1.middleReading());
    }

    @Test
    public void testMiddleReadingListOfInvalidsNursery() {
        List<Double> sensorData =
                List.of(20231106010101.0, -999.0, -999.0, 20231130020202.0, -999.0, -999.0);
        GreenHouseNursery greenHouse1 = new GreenHouseNursery();
        greenHouse1.pollSensorData(sensorData);
        assertEquals( new SuperTempHumidReading(-999.0,-999.0),greenHouse1.middleReading());
    }

    @Test
    public void testMiddleReadingOnDateNursery(){
        List<Double> sensorData =
                List.of(20231106010101.0,45.5,34.0,46.6,40.0,20231130020202.0,22.2,20.0,35.5,30.0,-999.0,31.0,32.2,-999.0);

        GreenHouseNursery greenHouse1 = new GreenHouseNursery();
        greenHouse1.pollSensorData(sensorData);

        assertEquals(greenHouse1.middleReading(), new SuperTempHumidReading(35.5,31.0));
        assertEquals(greenHouse1.middleReading(20231106), new SuperTempHumidReading(46.6,40.0));
    }
    @Test
    public void testMiddleReadingOnDateProduce(){
        List<Double> sensorData =
                List.of(20231106010101.0,45.5,34.0,46.6,40.0,20231130020202.0,22.2,20.0,35.5,30.0,-999.0,31.0,32.2,-999.0);

        GreenHouseProduce greenHouse1 = new GreenHouseProduce();
        greenHouse1.pollSensorData(sensorData);


        assertEquals(greenHouse1.middleReading(20231130), new SuperTempHumidReading(46.6,40.0));
    }
    @Test
    public void testMiddleReadingOnDateProduceFalse(){
        List<Double> sensorData =
                List.of(20231106010101.0, -999.0, -999.0, 20231130020202.0, -999.0, -999.0);
        GreenHouseProduce greenHouse1 = new GreenHouseProduce();
        greenHouse1.pollSensorData(sensorData);


        assertEquals(greenHouse1.middleReading(20231106), new SuperTempHumidReading(-999.0,-999.0));
    }



    @Test
    public void testPollSpeed(){
        ArrayList<Double> sensorData = new ArrayList<>();
        GreenHouseNursery nursery = new GreenHouseNursery();
        GreenHouseProduce produce = new GreenHouseProduce();
        for (int i = 0; i+1 < 10000; i++) {
            sensorData.add(i/2.89);
        }


        long time1 = System.currentTimeMillis();
        nursery.pollSensorData(sensorData);
        long time2 = System.currentTimeMillis();
        produce.pollSensorData(sensorData);
        long time3 = System.currentTimeMillis();

        System.out.println(String.format("computation1() : computation2() :: %s : %s", time2 - time1, time3 - time2));
        assertTrue(time2 - time1 < time3 - time2); // assert that computation 1 is faster


    }

    @Test
    public void testMiddleReadingTime(){
        ArrayList<Double> sensorData = new ArrayList<>();
        for (int i = 1; i < 10000; i++) {
            sensorData.add(i/2.89);
        }

        GreenHouseNursery nursery = new GreenHouseNursery();
        GreenHouseProduce produce = new GreenHouseProduce();
        nursery.pollSensorData(sensorData);
        produce.pollSensorData(sensorData);

        long time1 = System.currentTimeMillis();
        nursery.middleReading();
        long time2 = System.currentTimeMillis();
        produce.middleReading();
        long time3 = System.currentTimeMillis();

        System.out.println(String.format("computation1() : computation2() :: %s : %s", time2 - time1, time3 - time2));
        assertTrue(time2 - time1 > time3 - time2); // assert that computation 1 is faster

    }
*/








}
