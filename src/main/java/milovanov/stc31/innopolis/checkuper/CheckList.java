package milovanov.stc31.innopolis.checkuper;

public class CheckList {
    private static int task_id;
    private static String name;
    private static String descr;

    public static int getTask_id() {
        return task_id;
    }

    public static void setTask_id(int task_id) {
        CheckList.task_id = task_id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CheckList.name = name;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        CheckList.descr = descr;
    }
}
