package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="categories", indexes = {@Index(name="ind_category_name", columnList = "categoryName")})
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "categoryName")
    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubCategoryEntity> subCategoryEntityList;

    public UserDataEntity getUserDataEntity() {
        return userDataEntity;
    }

    public void setUserDataEntity(UserDataEntity userDataEntity) {
        this.userDataEntity = userDataEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserDataEntity userDataEntity;


    public CategoryEntity() {}

    public CategoryEntity(String categoryName, UserDataEntity userDataEntity) {
        this.categoryName = categoryName;
        this.userDataEntity = userDataEntity;
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

    public List<SubCategoryEntity> getSubCategoryEntityList() {
        return subCategoryEntityList;
    }

    public void setSubCategoryEntityList(List<SubCategoryEntity> subCategoryEntityList) {
        this.subCategoryEntityList = subCategoryEntityList;
    }
}
