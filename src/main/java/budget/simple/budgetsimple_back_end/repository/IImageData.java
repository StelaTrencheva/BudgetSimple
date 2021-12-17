package budget.simple.budgetsimple_back_end.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IImageData {
    void SaveImage(MultipartFile image, String fileName) throws IOException;
}
