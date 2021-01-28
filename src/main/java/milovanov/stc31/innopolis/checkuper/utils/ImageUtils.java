package milovanov.stc31.innopolis.checkuper.utils;

import java.util.Base64;

public class ImageUtils {
    public String getImgData(byte[] byteData) {
        return Base64.getMimeEncoder().encodeToString(byteData != null ? byteData : new byte[]{});
    }
}
