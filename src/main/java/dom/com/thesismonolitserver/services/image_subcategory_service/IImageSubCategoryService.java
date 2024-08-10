package dom.com.thesismonolitserver.services.image_subcategory_service;

import dom.com.thesismonolitserver.enteties.ImageEntity;
import dom.com.thesismonolitserver.enteties.SubCategoryEntity;

public interface IImageSubCategoryService {
    void saveImageSubCategory(SubCategoryEntity subCategory, ImageEntity image);
}
