package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.ImageEntity;
import dom.com.thesismonolitserver.enteties.views.SearchViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchViewRepository extends JpaRepository<SearchViewEntity, Long> {

    @Query("select new dom.com.thesismonolitserver.enteties.ImageEntity(searchView.id, searchView.location, searchView.date, searchView.annotation, searchView.imageArrayOfBytes) " +
            "from SearchViewEntity searchView " +
            "where (:annotation is null or :annotation='' or searchView.annotation = :annotation) and " +
            "(:location is null or :location='' or searchView.location = :location) and " +
            "(:category is null or :category='' or searchView.categoryName = :category) and " +
            "(:subcategory is null or :subcategory='' or searchView.subcategoryName = :subcategory) and " +
            "searchView.userId = :userId")
    Optional<List<ImageEntity>> queryByAnnotationLocationCategorySubcategory(@Param("userId") Long userId,
                                                                            @Param("annotation") String annotation,
                                                                             @Param("location") String location,
                                                                             @Param("category") String category,
                                                                             @Param("subcategory") String subcategory);
}
