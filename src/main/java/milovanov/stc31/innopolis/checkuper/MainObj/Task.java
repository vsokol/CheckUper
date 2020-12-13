package milovanov.stc31.innopolis.checkuper.MainObj;

public class Task {

    private static String info;
    private static boolean is_required;
    private static String  descr;

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        Task.info = info;
    }

    public static boolean isIs_required() {
        return is_required;
    }

    public static void setIs_required(boolean is_required) {
        Task.is_required = is_required;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        Task.descr = descr;
    }
}
