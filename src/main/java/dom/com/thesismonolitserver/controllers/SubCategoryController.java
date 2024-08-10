package dom.com.thesismonolitserver.controllers;

import dom.com.thesismonolitserver.services.subcategory_service.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/subcategory")
public class SubCategoryController {

    private final ISubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(ISubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/new-subcategory/{idCategory}", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSubCategory(@PathVariable("idCategory") Long idCategory,
                                                    @RequestParam("subcategory") String subCategory){
        subCategoryService.addNewSubCategory(idCategory, subCategory);
        return ResponseEntity.ok("SUPPER");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/update-subcategory/{idSubCategory}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSubCategory(@PathVariable("idSubCategory") Long idSubCategory,
                                                    @RequestParam("subcategory") String subCategory){
        subCategoryService.updateSubCategory(idSubCategory, subCategory);
        return ResponseEntity.ok("SUPPER");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/delete-subcategory/{idSubCategory}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSubCategory(@PathVariable("idSubCategory") Long idSubCategory){
        subCategoryService.deleteSubCategory(idSubCategory);
        return ResponseEntity.ok("SUPPER");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path = "/get-all-subcategory/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSubCategoryForUser(@PathVariable("userId") Long userId){
        List<String> allSubCategoryForUser = subCategoryService.getAllSubCategoryForUser(userId);
        return ResponseEntity.ok(allSubCategoryForUser);
    }
}
