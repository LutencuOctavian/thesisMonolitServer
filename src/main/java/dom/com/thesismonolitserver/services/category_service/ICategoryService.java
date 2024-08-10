package dom.com.thesismonolitserver.services.category_service;

import dom.com.thesismonolitserver.dtos.CategoryDTO;
import dom.com.thesismonolitserver.dtos.SubcategoryDTO;
import dom.com.thesismonolitserver.enteties.CategoryEntity;

import java.util.List;
import java.util.Map;

public interface ICategoryService {

    void createNewCategory(Long userId, String category);

    void updateCategory(Long categoryId, String category);

    void deleteCategory(Long categoryId);

    List<String> getAllCategoriesForUser(Long userId);

    CategoryEntity findCategoryById(Long categoryId);
    List<CategoryDTO> getCategoryAndSubcategoryForNavbar(Long userId);
}
