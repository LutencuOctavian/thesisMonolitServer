package dom.com.thesismonolitserver.services.image_subcategory_service;

import dom.com.thesismonolitserver.enteties.ImageEntity;
import dom.com.thesismonolitserver.enteties.ImageSubCategoryEntity;
import dom.com.thesismonolitserver.enteties.SubCategoryEntity;
import dom.com.thesismonolitserver.repositories.ImageSubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageSubCategoryService")
public class ImageSubCategoryService implements IImageSubCategoryService {

    private final ImageSubCategoryRepository imageSubCategoryRepository;

    @Autowired
    public ImageSubCategoryService(ImageSubCategoryRepository imageSubCategoryRepository) {
        this.imageSubCategoryRepository = imageSubCategoryRepository;
    }

    public void saveImageSubCategory(SubCategoryEntity subCategory, ImageEntity image){
        imageSubCategoryRepository.save(new ImageSubCategoryEntity(subCategory, image));
    }
}
