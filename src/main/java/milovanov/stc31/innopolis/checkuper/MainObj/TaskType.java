package milovanov.stc31.innopolis.checkuper.MainObj;

public class TaskType {
    private static String code;
    private static String   name;
    private static String   descr;

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        TaskType.code = code;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        TaskType.name = name;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        TaskType.descr = descr;
    }
}
