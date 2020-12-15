package milovanov.stc31.innopolis.checkuper.pojo;

import java.util.Date;

public class Request {
    private String name;
    private String status;
    Date dtStart;
    Date dtEnd;
    Date dtComplete;
    private String descr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
