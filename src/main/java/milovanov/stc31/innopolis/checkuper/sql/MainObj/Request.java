package milovanov.stc31.innopolis.checkuper.sql.MainObj;

import java.util.Date;

public class Request {
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
        Request.name = name;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Request.status = status;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        Request.descr = descr;
    }
}
