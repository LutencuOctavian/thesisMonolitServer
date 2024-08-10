package dom.com.thesismonolitserver.services.image_service;

import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.enteties.ImageEntity;

import java.io.File;
import java.util.List;

public interface IImageService {
    void saveNewImage(Long subCategoryId, ImageDTO imageDTO);

    void updateImage(ImageDTO imageDTO);
    ImageEntity findImageEntityById(Long imageId);
    void deleteImage(Long imageId);
    List<ImageDTO> gelAllImageForUser(Long userId);
    List<ImageDTO> findAllImagesForSpecificSubcategoryId(Long subcategoryId);
    List<ImageDTO> queryByAnnotationLocationCategorySubcategory(Long userId, String annotation, String location, String category, String subcategory, String startDate, String endDate);
    ImageDTO getDataForImageById(Long imageId);
    File downloadImageUsingId(Long imageId);
}
