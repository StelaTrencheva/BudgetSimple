package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.ImageModel;
import budget.simple.budgetsimple_back_end.logic.mapper.ImageMapper;
import budget.simple.budgetsimple_back_end.repository.IImageData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageManager {

    private final IImageData imageData;
    private final ImageMapper imageMapper;

    public ImageManager(IImageData imageData, ImageMapper imageMapper) {
        this.imageData = imageData;
        this.imageMapper = imageMapper;
    }

    public ImageModel createImage(MultipartFile image) throws IOException {
        ImageModel imageModel = imageMapper.mapToModel(image);
        imageData.SaveImage(image, imageModel.getId());
        return imageModel;
    }
}
