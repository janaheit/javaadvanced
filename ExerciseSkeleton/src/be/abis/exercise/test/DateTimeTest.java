package be.abis.exercise.test;

import java.text.DateFormat;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeTest {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate dateToday = LocalDate.now();
        System.out.println("Which day is it in 3 years, 2 months and 15 days from now ("+ dateToday.format(formatter)+")?");
        LocalDate newDate = dateToday.plusYears(3).plusMonths(2).plusDays(15);
        System.out.println(newDate.format(formatter));

        System.out.println("------------------------------------------");

        Locale de = new Locale("de");
        LocalDate birthdate = LocalDate.of(1998,01,06);
        System.out.println("Which day of the week was I born? (" + birthdate.format(formatter) + ")");
        System.out.println(birthdate.getDayOfWeek().getDisplayName(TextStyle.FULL, de));

        System.out.println("------------------------------------------");

        LocalDate birthday2023 = LocalDate.of(2023, 01, 06);
        System.out.println("How many days to go until the next birthday? (" + birthday2023.format(formatter) + ")");
        System.out.println(ChronoUnit.DAYS.between(dateToday, birthday2023));

        System.out.println("------------------------------------------");

        System.out.println("How many days old are you today? (" + birthdate.format(formatter) + ")");
        System.out.println(ChronoUnit.DAYS.between(birthdate, dateToday));

        System.out.println("------------------------------------------");

        System.out.println("What is the time difference between Brussels and Calcutta?");

        LocalDateTime dateTimeBE = LocalDateTime.now(ZoneId.of("Europe/Brussels"));
        LocalDateTime dateTimeKA = LocalDateTime.now(ZoneId.of("Asia/Calcutta"));
        double durationInMinutes = Duration.between(dateTimeBE, dateTimeKA).toMinutes();
        System.out.println(durationInMinutes);
        double hours = Math.floor(durationInMinutes/60);
        double min = durationInMinutes%60;
        System.out.println(hours + " hours " + min + " minutes");


        //System.out.println(duration.toHoursPart() + " hours " + duration.toMinutesPart() + " minutes");

    }

}
