package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    @Query("select image " +
            "from ImageEntity image " +
            "where image.id = :imageId")
    Optional<ImageEntity> findImageEntityById(@Param("imageId")Long imageId);

    @Query("select image " +
            "from ImageEntity image " +
            "inner join ImageSubCategoryEntity  imgSubCat on imgSubCat.imageEntity.id = image.id " +
            "inner join SubCategoryEntity subCat on subCat.id = imgSubCat.subCategoryEntity.id " +
            "inner join CategoryEntity cat on cat.id = subCat.categoryEntity.id " +
            "where cat.userDataEntity.id = :userId ")
    Optional<List<ImageEntity>> findAllImagesForUser(@Param("userId") Long userId);

    @Query( "select image " +
        "from ImageEntity image " +
        "inner join ImageSubCategoryEntity imagSub on image.id = imagSub.imageEntity.id " +
        "where imagSub.subCategoryEntity.id = :subcategoryId " +
        "order by image.date desc, image.location asc ")
    Optional<List<ImageEntity>> findAllImagesForSpecificSubcategoryId(@Param("subcategoryId") Long subcategoryId);

    @Query("select image " +
            "from ImageEntity image " +
            "where image.id=:imageId")
    Optional<ImageEntity> getDataForImageById(@Param("imageId") Long imageId);
}
