package dom.com.thesismonolitserver.services.subcategory_service;

import dom.com.thesismonolitserver.dtos.SubcategoryDTO;
import dom.com.thesismonolitserver.enteties.SubCategoryEntity;

import java.util.List;

public interface ISubCategoryService {
    void addNewSubCategory(Long idCategory, String subCategory);

    void updateSubCategory(Long idSubCategory, String subCategory);
    SubCategoryEntity findSubCategoryById(Long idSubCategory);
    void deleteSubCategory(Long idSubCategory);

    List<String> getAllSubCategoryForUser(Long userId);
}
