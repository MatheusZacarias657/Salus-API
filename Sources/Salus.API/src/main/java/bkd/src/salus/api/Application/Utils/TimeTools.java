package bkd.src.salus.api.Application.Utils;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeTools {

    public static TemporalAmount convertStringToTemporalAmount(String timeString) {
        Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
        Matcher matcher = pattern.matcher(timeString);

        if (matcher.matches()) {
            int hours = Integer.parseInt(matcher.group(1));
            int minutes = Integer.parseInt(matcher.group(2));
            int seconds = Integer.parseInt(matcher.group(3));

            return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        } else {
            throw new IllegalArgumentException("Invalid time format: " + timeString);
        }
    }
}
