package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.dtos.CategoryDTO;
import dom.com.thesismonolitserver.dtos.SubcategoryDTO;
import dom.com.thesismonolitserver.enteties.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("select cat.categoryName " +
            "from CategoryEntity cat " +
            "where cat.categoryName = :category and cat.userDataEntity.id = :userId " +
            "order by cat.categoryName asc")
    Optional<List<String>> findCategoryByName(@Param("userId") Long userId,
                                              @Param("category") String category);

    @Query("select cat " +
            "from CategoryEntity cat " +
            "where cat.id = :categoryId")
    Optional<CategoryEntity> findCategoryById(@Param("categoryId") Long categoryId);

    @Query("select cat.categoryName " +
            "from CategoryEntity cat " +
            "where cat.userDataEntity.id = :userId " +
            "order by cat.categoryName asc")
    Optional<List<String>> findAllCategoriesForUser(@Param("userId") Long userId);

    @Query("select new dom.com.thesismonolitserver.dtos.CategoryDTO(cat.id, cat.categoryName) " +
            "from CategoryEntity cat " +
            "where cat.userDataEntity.id = :userId " +
            "order by cat.categoryName asc")
    Optional<List<CategoryDTO>> findAllCategoriesDTOForUserId(@Param("userId") Long userId);

    @Query("select new dom.com.thesismonolitserver.dtos.SubcategoryDTO(subCat.id, subCat.subCategoryName) " +
            "from CategoryEntity  cat " +
            "inner join SubCategoryEntity subCat on subCat.categoryEntity.id = cat.id " +
            "where cat.id = :categoryId " +
            "order by cat.categoryName asc")
    Optional<List<SubcategoryDTO>> getAllSubcategoryDTOForCategoryId(@Param("categoryId") Long categoryId);
}
