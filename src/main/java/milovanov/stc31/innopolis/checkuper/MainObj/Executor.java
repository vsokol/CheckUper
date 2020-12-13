package milovanov.stc31.innopolis.checkuper.MainObj;

public class Executor {
       private static String name;
       private static boolean available;
       private static String descr;


    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Executor.name = name;
    }

    public static boolean isAvailable() {
        return available;
    }

    public static void setAvailable(boolean available) {
        Executor.available = available;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        Executor.descr = descr;
    }
}
