package bkd.src.salus.api.Application.Utils;

import java.util.Arrays;
import java.util.Random;

public class RandomGenerator {
    private static String CapitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String SmallChars = "abcdefghijklmnopqrstuvwxyz";
    private static String Numbers = "0123456789";

    public static String Otp()
    {
        int size = 6;
        Random random = new Random();
        char[] password = new char[size];

        for (int i = 0; i < size; i++)
        {
            password[i] = Numbers.charAt(random.nextInt(Numbers.length()));
        }

        return new String(password);
    }

    public static String Password()
    {
        int size = 12;
        Random random = new Random();
        char[] randomPassword = new char[size];
        String values = CapitalChars + SmallChars + Numbers;

        for (int i = 0; i < size; i++)
        {
            randomPassword[i] = values.charAt(random.nextInt(values.length()));
        }

        return String.valueOf(randomPassword);
    }
}
