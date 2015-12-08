package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Class for working with date.
 */
public class DateUtil {
    /**
     * Date format.
     */
    private static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT, Locale.getDefault());

    /**
     * Parses string into date.
     * @param date string with date.
     * @return date.
     */
    public static Date parse(String date)
    {
        df.setLenient(false);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Samara"));
        try {
            Date d = df.parse(date);
            return d;
        } catch (ParseException e) {
            return null;
        }

    }
    /**
     * Formats date into string.
     * @param date date.
     * @return string with date.
     */
    public static String format(Date date)
    {
        df.setLenient(false);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Samara"));
        return df.format(date);
    }
    /**
     * Validates the entered date.
     * @param date the entered date.
     * @return true if the date is correct, else - false.
     */
    public static boolean isCorrect(Date date)
    {
        long delta = date.getTime() - Calendar.getInstance().getTimeInMillis();
        return delta > 0;
    }
}

