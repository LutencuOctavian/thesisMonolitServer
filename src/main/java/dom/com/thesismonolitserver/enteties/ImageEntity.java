package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="images", indexes = {@Index(name="ind_annotation", columnList = "annotation"),
        @Index(name="ind_location", columnList = "location"),})
@AllArgsConstructor
@Builder
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="location")
    private String location;

    @Column(name="date")
    private LocalDate date;

    @Column(name="latitude_degrees")
    private Double latitudeDegrees;

    @Column(name="longitude_degrees")
    private Double longitudeDegrees;

    @Enumerated(EnumType.STRING)
    @Column(name="latitude_hemisphere")
    private HemisphereEnum latitudeHemisphere;

    @Enumerated(EnumType.STRING)
    @Column(name="longitude_hemisphere")
    private HemisphereEnum longitudeHemisphere;

    @Column(name="annotation")
    private String annotation;

    @Lob
    @Column(name="image_array_bytes", columnDefinition = "longblob")
    private byte[] imageArrayOfBytes;

    @OneToMany(mappedBy = "imageEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ImageSubCategoryEntity> imageSubCategoryEntityList;

    public ImageEntity() {}

    public ImageEntity(Long id, String location, LocalDate date, String annotation, byte[] imageArrayOfBytes) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.annotation = annotation;
        this.imageArrayOfBytes = imageArrayOfBytes;
    }

    public ImageEntity(String location, LocalDate date, Double latitudeDegrees,
                       Double longitudeDegrees, HemisphereEnum latitudeHemisphere,
                       HemisphereEnum longitudeHemisphere, String annotation, byte[] imageBase64) {
        this.location = location;
        this.date = date;
        this.latitudeDegrees = latitudeDegrees;
        this.longitudeDegrees = longitudeDegrees;
        this.latitudeHemisphere = latitudeHemisphere;
        this.longitudeHemisphere = longitudeHemisphere;
        this.annotation = annotation;
        this.imageArrayOfBytes = imageBase64;
    }

    public enum HemisphereEnum {
        NORTH("NORTH"),
        EAST("EAST"),
        WEST("WEST"),
        SOUTH("SOUTH");
        private final String label;

        HemisphereEnum(String grantType) {
            this.label = grantType;
        }

        public static HemisphereEnum valueOfLabel(String label) {
            for (HemisphereEnum hemisphere : values()) {
                if (hemisphere.label.equals(label)) {
                    return hemisphere;
                }
            }
            return null;
        }

        public static List<HemisphereEnum> getAllValuesOfEnum() {
            return List.of(values());
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public void setLatitudeDegrees(Double latitudeDegrees) {
        this.latitudeDegrees = latitudeDegrees;
    }

    public Double getLongitudeDegrees() {
        return longitudeDegrees;
    }

    public void setLongitudeDegrees(Double longitudeDegrees) {
        this.longitudeDegrees = longitudeDegrees;
    }

    public HemisphereEnum getLatitudeHemisphere() {
        return latitudeHemisphere;
    }

    public void setLatitudeHemisphere(HemisphereEnum latitudeHemisphere) {
        this.latitudeHemisphere = latitudeHemisphere;
    }

    public HemisphereEnum getLongitudeHemisphere() {
        return longitudeHemisphere;
    }

    public void setLongitudeHemisphere(HemisphereEnum longitudeHemisphere) {
        this.longitudeHemisphere = longitudeHemisphere;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public byte[] getImageArrayOfBytes() {
        return imageArrayOfBytes;
    }

    public void setImageArrayOfBytes(byte[] image) {
        this.imageArrayOfBytes = image;
    }

    public List<ImageSubCategoryEntity> getImageSubCategoryEntityList() {
        return imageSubCategoryEntityList;
    }

    public void setImageSubCategoryEntityList(List<ImageSubCategoryEntity> imageSubCategoryEntityList) {
        this.imageSubCategoryEntityList = imageSubCategoryEntityList;
    }
}
