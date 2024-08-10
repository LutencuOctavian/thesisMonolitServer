package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="sub_categories", indexes = {@Index(name="ind_subcategory_name", columnList = "subCategoryName")})
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subCategoryName")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "subCategoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ImageSubCategoryEntity> imageSubCategoryEntityList;

    public SubCategoryEntity() {}

    public SubCategoryEntity(String subCategoryName, CategoryEntity categoryEntity) {
        this.subCategoryName = subCategoryName;
        this.categoryEntity = categoryEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public List<ImageSubCategoryEntity> getImageSubCategoryEntityList() {
        return imageSubCategoryEntityList;
    }

    public void setImageSubCategoryEntityList(List<ImageSubCategoryEntity> imageSubCategoryEntityList) {
        this.imageSubCategoryEntityList = imageSubCategoryEntityList;
    }
}
