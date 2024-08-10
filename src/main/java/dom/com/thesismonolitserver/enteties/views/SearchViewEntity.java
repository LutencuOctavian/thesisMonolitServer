package dom.com.thesismonolitserver.enteties.views;


import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * create view search_view as (select img.id as id, cat.category_name as category,  subcat.sub_category_name as subcategory,
 * 								img.annotation, img.date, img.image_array_bytes, img.location, cat.user_id
 * from images as img
 * inner join image_subcategory as imgsb on img.id = imgsb.image_id
 * inner join sub_categories as subcat on subcat.id = imgsb.subcategory_id
 * inner join categories as cat on cat.id = subcat.category_id
 * order by id asc);
 */

@Entity
@Table(name="search_view")
public class SearchViewEntity {

    @Id
    private Long id;

    @Column(name="category")
    private String categoryName;

    @Column(name="subcategory")
    private String subcategoryName;

    @Column(name="annotation")
    private String annotation;

    @Column(name="date")
    private LocalDate date;

    @Lob
    @Column(name="image_array_bytes", columnDefinition = "longblob")
    private byte[] imageArrayOfBytes;

    @Column(name="location")
    private String location;

    @Column(name="user_id")
    private Long userId;

    public SearchViewEntity() {
    }

    public SearchViewEntity(Long id, String categoryName, String subcategoryName, String annotation,
                            LocalDate date, byte[] imageArrayOfBytes, String location, Long userId) {
        this.id = id;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.annotation = annotation;
        this.date = date;
        this.imageArrayOfBytes = imageArrayOfBytes;
        this.location = location;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getImageArrayOfBytes() {
        return imageArrayOfBytes;
    }

    public void setImageArrayOfBytes(byte[] imageArrayOfBytes) {
        this.imageArrayOfBytes = imageArrayOfBytes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
