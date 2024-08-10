package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.dtos.SubcategoryDTO;
import dom.com.thesismonolitserver.enteties.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {
    @Query("select subCat " +
            "from SubCategoryEntity subCat " +
            "where subCat.id = :idSubCategory")
    Optional<SubCategoryEntity> findSubCategoryById(@Param("idSubCategory") Long idSubCategory);

    @Query("select subCat.subCategoryName " +
            "from SubCategoryEntity subCat " +
            "inner join CategoryEntity cat on cat.id = subCat.categoryEntity.id " +
            "where cat.userDataEntity.id = :userId " +
            "order by subCat.subCategoryName asc")
    Optional<List<String>> findAllSubCategoryByUserId(@Param("userId") Long userId);

    @Query("select subCat.subCategoryName " +
            "from SubCategoryEntity subCat " +
            "inner join CategoryEntity cat on cat.id = subCat.categoryEntity.id " +
            "where subCat.subCategoryName = :subCategory and cat.id = :categoryId " +
            "order by subCat.subCategoryName asc")
    Optional<List<String>> findSubCategoryByName(@Param("categoryId")Long categoryId,
                                                 @Param("subCategory")String subCategory);

    @Query("select new dom.com.thesismonolitserver.dtos.SubcategoryDTO(subCat.id, subCat.subCategoryName) " +
            "from SubCategoryEntity subCat " +
            "where subCat.categoryEntity.id = :categoryId")
    Optional<List<SubcategoryDTO>> findAllSubcategoryDTOForCategoryId(@Param("categoryId") Long categoryId);
}
