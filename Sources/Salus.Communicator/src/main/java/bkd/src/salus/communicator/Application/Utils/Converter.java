package bkd.src.salus.communicator.Application.Utils;

public class Converter {

    public static int ConvertHoursToMilly(float hours){
        float millis = (hours * 3600000);
        return Math.round(millis);
    }

    public static int ConvertMinutesToMilly(float minutes){
        float millis = (minutes * 60000);
        return Math.round(millis);
    }
}
