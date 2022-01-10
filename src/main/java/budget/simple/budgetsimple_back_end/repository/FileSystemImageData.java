package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.ImageModel;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class FileSystemImageData implements IImageData {

    private String uploadLocation = "images";
    @Override
    public void SaveImage(MultipartFile uploadedImage, String filename) throws IOException {
        if (!uploadedImage.isEmpty() && isRepositoryExists()) {
            try(
                    OutputStream outputStream = new FileOutputStream(uploadLocation + filename);
                    ) {
                outputStream.write(uploadedImage.getBytes());
            }
            catch(IOException e){
            }
        }
    }
    public boolean isRepositoryExists() {
        File file = new File(uploadLocation);
        if (!file.exists()) {
            if (file.mkdir()) {
                return true;
            } else {
                return false;
            }
        } else return true;
    }

}
