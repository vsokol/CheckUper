package milovanov.stc31.innopolis.checkuper.MainObj;

public class Customer {
    private static String name;
    private static String adress;
    private static String descr;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Customer.name = name;
    }

    public static String getAdress() {
        return adress;
    }

    public static void setAdress(String adress) {
        Customer.adress = adress;
    }

    public static String getDescr() {
        return descr;
    }

    public static void setDescr(String descr) {
        Customer.descr = descr;
    }
}
