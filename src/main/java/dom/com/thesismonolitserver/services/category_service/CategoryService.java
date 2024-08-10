package dom.com.thesismonolitserver.services.category_service;

import dom.com.thesismonolitserver.dtos.CategoryDTO;
import dom.com.thesismonolitserver.dtos.SubcategoryDTO;
import dom.com.thesismonolitserver.enteties.CategoryEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.exceptions.CategoryException;
import dom.com.thesismonolitserver.exceptions.SubCategoryException;
import dom.com.thesismonolitserver.repositories.CategoryRepository;
import dom.com.thesismonolitserver.services.subcategory_service.ISubCategoryService;
import dom.com.thesismonolitserver.services.user_service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("categoryService")
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    private final IUserService userService;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           @Qualifier("defaultUserService") IUserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public void createNewCategory(Long userId, String category){
        checkIfThisCategoryExistAlready(userId, category);
        UserDataEntity userFromDB = userService.findUserById(userId);
        categoryRepository.save(new CategoryEntity(category, userFromDB));
    }

    public void updateCategory(Long categoryId, String category){
        CategoryEntity categoryFromDB = findCategoryById(categoryId);
        categoryFromDB.setCategoryName(category);
        categoryRepository.save(categoryFromDB);
    }

    public void deleteCategory(Long categoryId){
        CategoryEntity categoryFromDB = findCategoryById(categoryId);
        categoryRepository.delete(categoryFromDB);
    }

    public List<String> getAllCategoriesForUser(Long userId){
        return categoryRepository.findAllCategoriesForUser(userId).orElseThrow(()-> new CategoryException("Category table doesn't exist"));
    }

    public List<CategoryDTO> getCategoryAndSubcategoryForNavbar(Long userId){
        List<CategoryDTO> listCategoriesDTO = categoryRepository.findAllCategoriesDTOForUserId(userId).orElseThrow(()->new CategoryException("Category table is empty in dataBase"));

        listCategoriesDTO.forEach(category->{
            List<SubcategoryDTO> subcategoryList = categoryRepository.getAllSubcategoryDTOForCategoryId(category.getIdCategory()).orElseThrow(() -> new CategoryException("Category table is empty in dataBase"));
            category.setListOfSubcategories(subcategoryList);
        });
     return listCategoriesDTO;
    }

    public CategoryEntity findCategoryById(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryException("No such category id: " + categoryId + " in database"));
    }

    private void checkIfThisCategoryExistAlready(Long userId, String category) {
        List<String> categoryList = categoryRepository.findCategoryByName(userId, category)
                .orElseThrow(() -> new CategoryException("Category table is empty in dataBase"));

        if(categoryList.contains(category)){
            throw new CategoryException("This category (" + category + ") exist already in dataBase");
        }
    }
}
