package bkd.src.salus.notificator.Application.Utils;

public class Converter {

    public static int ConvertHoursToMilly(float hours){
        float millis = (hours * 3600000);
        return Math.round(millis);
    }
}
