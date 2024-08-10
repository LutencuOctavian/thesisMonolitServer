package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

@Entity
@Table(name="image_subcategory")
public class ImageSubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subcategory_id")
    private SubCategoryEntity subCategoryEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="image_id")
    private ImageEntity imageEntity;

    public ImageSubCategoryEntity() {}

    public ImageSubCategoryEntity(SubCategoryEntity subCategoryEntity, ImageEntity imageEntity) {
        this.subCategoryEntity = subCategoryEntity;
        this.imageEntity = imageEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubCategoryEntity getSubCategoryEntity() {
        return subCategoryEntity;
    }

    public void setSubCategoryEntity(SubCategoryEntity subCategoryEntity) {
        this.subCategoryEntity = subCategoryEntity;
    }

    public ImageEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }
}
