package milovanov.stc31.innopolis.checkuper;

import java.util.Date;

public class Task {
    private static String name;
    private static String status;
    Date dt_start;
    Date dt_end;
    Date dt_complete;
    private static String descr;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Task.name = name;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Task.status = status;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        Task.descr = descr;
    }
}
