public class SuperTempHumidReading extends TempHumidReading{
    public SuperTempHumidReading(double temperature,double humidity){
        super(temperature, humidity);
    }
    public SuperTempHumidReading(){
        super(-999,-999);
    }
    public SuperTempHumidReading(TempHumidReading thr){
        super (thr.temperature,thr.humidity);

    }
    @Override
    public boolean equals(Object thr){
        return super.equals(thr);
    }

    @Override
    public String toString(){
        if(temperature == -999.0 & humidity == -999.0){
            return "{Err;Err}";
        }
        else if (temperature == -999.0){
            return String.format("{Err;%.1f%%}",  humidity);
        }
        else if (humidity == -999.0){
            return String.format("{%.1fF;Err}", temperature);
        }
        else{return String.format("{%.1fF;%.1f%%}", temperature, humidity);}

    }
}
