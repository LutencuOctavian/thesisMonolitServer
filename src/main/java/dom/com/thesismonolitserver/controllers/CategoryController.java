package dom.com.thesismonolitserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dom.com.thesismonolitserver.dtos.CategoryDTO;
import dom.com.thesismonolitserver.dtos.SubcategoryDTO;
import dom.com.thesismonolitserver.services.category_service.ICategoryService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(@Qualifier("categoryService") ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/new-category/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewCategory(@RequestParam("category") String category, @PathVariable("userId") Long userId) throws ParseException {
        categoryService.createNewCategory(userId, category);
        return ResponseEntity.ok("SUPPER");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/update-category/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCategory(@RequestParam("category") String category, @PathVariable(name = "id") Long categoryId ) throws ParseException {
        categoryService.updateCategory(categoryId, category);
        return ResponseEntity.ok("SUPPER");
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/delete-category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategory(@PathVariable(name = "id") Long categoryId ) throws ParseException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("SUPPER");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/get-all-category/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCategoryForUser(@PathVariable("id") Long userId ) throws ParseException {
        List<String> allCategoriesForUser = categoryService.getAllCategoriesForUser(userId);
        return ResponseEntity.ok(allCategoriesForUser);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/category-subcategory-navbar/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategoryAndSubcategoryForNavbarUser(@PathVariable("id") Long userId ) throws ParseException, JsonProcessingException {
        List<CategoryDTO> categoryAndSubcategoryForNavbar = categoryService.getCategoryAndSubcategoryForNavbar(userId);
        return ResponseEntity.ok(categoryAndSubcategoryForNavbar);
    }

}
