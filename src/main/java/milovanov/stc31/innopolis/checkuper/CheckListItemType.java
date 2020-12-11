package milovanov.stc31.innopolis.checkuper;

public class CheckListItemType {
    private static String code;
    private static String   name;
    private static String   descr;

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        CheckListItemType.code = code;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CheckListItemType.name = name;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        CheckListItemType.descr = descr;
    }
}
