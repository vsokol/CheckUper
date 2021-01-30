package milovanov.stc31.innopolis.checkuper.pojo;

public class ResultCheck {
    final private boolean isOk;
    final private String attributeName;
    final private String message;

    public ResultCheck(boolean isOk) {
        this(isOk, null, null);
    }

    public ResultCheck(boolean isOk, String attributeName, String message) {
        this.isOk = isOk;
        this.attributeName = attributeName;
        this.message = message;
    }

    public boolean getOk() {
        return isOk;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getMessage() {
        return message;
    }
}
