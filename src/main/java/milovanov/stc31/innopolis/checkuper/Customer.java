package milovanov.stc31.innopolis.checkuper;

public class Customer {
    private static String name;
    private static boolean available;
    private static String descr;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Customer.name = name;
    }

    public static boolean isAvailable() {
        return available;
    }

    public static void setAvailable(boolean available) {
        Customer.available = available;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        Customer.descr = descr;
    }
}
