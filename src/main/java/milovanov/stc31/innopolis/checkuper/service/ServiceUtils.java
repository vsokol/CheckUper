package milovanov.stc31.innopolis.checkuper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ServiceUtils implements IServiceUtils {
    private String imagePath;

    @Override
    public void saveImageToFile(MultipartFile avatar, String username) throws IOException {
        if (avatar == null || avatar.getOriginalFilename().isEmpty() || username == null) {
            return;
        }
        File imageDir = new File(imagePath);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        String ext = getFileExtension(avatar.getOriginalFilename());
        File file = new File(imagePath + "/" + username + (ext.isEmpty() ? "" : "." + ext));
        avatar.transferTo(file);
    }

    public String getImagePath() {
        return imagePath;
    }

    @Value("${checkuper.images.path}")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String getFileExtension(String fileName) {
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1
                && fileName.lastIndexOf(".") != 0) {
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        else return "";
    }
}
