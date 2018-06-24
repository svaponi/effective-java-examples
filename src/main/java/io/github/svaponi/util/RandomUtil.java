package io.github.svaponi.util;

import java.util.Random;

public class RandomUtil {

    static Random random = new Random();

    public static Random random() {
        return random;
    }

    /**
     * @param collection the source collection
     * @param <T>
     * @return a random element from the collection
     */
    public static <T> T randomize(final T... collection) {
        final int index = random.nextInt(collection.length);
        return collection[index];
    }

    /**
     * @return a random boolean
     */
    public static boolean randomBool() {
        return random.nextBoolean();
    }

    /**
     * @return a random double
     */
    public static double randomDouble() {
        return random.nextDouble();
    }

    /**
     * @param max the upper limit
     * @return a random int
     */
    public static int randomInt(final int max) {
        return random.nextInt(max);
    }

    /**
     * @param len length of the output string
     * @return a random string of length len, composed only by digits
     */
    public static String randomDigits(final int len) {
        return __random(DIGITS, len);
    }

    /**
     * @param len length of the output string
     * @return a random string of length len, composed only by alpha-numeric chars
     */
    public static String randomAlphaNumeric(final int len) {
        return __random(ALPHA_NUM, len);
    }

    /**
     * @param len length of the output string
     * @return a random string of length len, composed only by hexadecimal chars
     */
    public static String randomHex(final int len) {
        return __random(HEX_CHARS, len);
    }

    /**
     * @param len length of the output string
     * @return a random string of length len
     */
    public static String randomString(final int len) {
        return __random(ALL_CHARS, len);
    }


    /*
        private stuff ...
     */

    private static final String DIGITS = "1234567890";
    private static final String HEX_CHARS = "1234567890abcdf";
    private static final String ALPHA_NUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890`~!@#$$%^&*()_-+={}[]\\|;:'\",.<>/?";

    private static String __random(final String candidateChars, final int len) {
        final StringBuilder sb = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }
        return sb.toString();
    }
}