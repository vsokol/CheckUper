package milovanov.stc31.innopolis.checkuper.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IServiceUtils {
    void saveImageToFile(MultipartFile avatar, String username) throws IOException;
}
