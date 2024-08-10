package dom.com.thesismonolitserver.services.image_service;

import dom.com.thesismonolitserver.convertors.GenericConverterType;
import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.enteties.ImageEntity;
import dom.com.thesismonolitserver.enteties.SubCategoryEntity;
import dom.com.thesismonolitserver.exceptions.ImageException;
import dom.com.thesismonolitserver.exceptions.SubCategoryException;
import dom.com.thesismonolitserver.repositories.ImageRepository;
import dom.com.thesismonolitserver.repositories.SearchViewRepository;
import dom.com.thesismonolitserver.services.image_subcategory_service.IImageSubCategoryService;
import dom.com.thesismonolitserver.services.subcategory_service.ISubCategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("imageService")
public class ImageService implements IImageService{

    private final SearchViewRepository searchViewRepository;
    private final GenericConverterType genericConverterType;
    private final ImageRepository imageRepository;
    private final ISubCategoryService subCategoryService;
    private final IImageSubCategoryService imageSubCategoryService;

    @Autowired
    public ImageService(SearchViewRepository searchViewRepository, GenericConverterType genericConverterType, ImageRepository imageRepository,
                        @Qualifier("subCategoryService") ISubCategoryService subCategoryService,
                        @Qualifier("imageSubCategoryService") IImageSubCategoryService imageSubCategoryService) {
        this.searchViewRepository = searchViewRepository;
        this.genericConverterType = genericConverterType;
        this.imageRepository = imageRepository;
        this.subCategoryService = subCategoryService;
        this.imageSubCategoryService = imageSubCategoryService;
    }
    @Transactional
    public void saveNewImage(Long subCategoryId, ImageDTO imageDTO){
        Converter<ImageDTO, ImageEntity> converter = (Converter<ImageDTO, ImageEntity>)genericConverterType.getConverter(ImageEntity.class);
        ImageEntity imageEntity = converter.convert(imageDTO);
        ImageEntity imageEntityFromDB = imageRepository.save(imageEntity);
        SubCategoryEntity subCategory = subCategoryService.findSubCategoryById(subCategoryId);
        imageSubCategoryService.saveImageSubCategory(subCategory, imageEntityFromDB);
    }

    @Transactional
    public void updateImage(ImageDTO imageDTO){
        Converter<ImageDTO, ImageEntity> converter = (Converter<ImageDTO, ImageEntity>)genericConverterType.getConverter(ImageEntity.class);
        ImageEntity imageEntityFromUser = converter.convert(imageDTO);
        ImageEntity imageEntityFromDB = findImageEntityById(imageDTO.getId());
        copyDataFromUserIntoDBEntity(imageEntityFromUser, imageEntityFromDB);
        imageRepository.save(imageEntityFromDB);
    }

    public ImageEntity findImageEntityById(Long imageId) {
        return imageRepository.findImageEntityById(imageId)
                .orElseThrow(()-> new ImageException("No such ImageEntity with id: " + imageId + " in database"));
    }

    @Transactional
    public void deleteImage(Long imageId){
        ImageEntity imageEntity = findImageEntityById(imageId);
        imageRepository.delete(imageEntity);
    }

    public List<ImageDTO> gelAllImageForUser(Long userId){
        List<ImageEntity> listOfImagesForUser = imageRepository.findAllImagesForUser(userId)
                .orElseThrow(() -> new ImageException("User with id: " + userId + " has some SQL exception"));
        Converter<ImageEntity, ImageDTO> converter = (Converter<ImageEntity, ImageDTO>)genericConverterType.getConverter(ImageDTO.class);
        return listOfImagesForUser.parallelStream()
                .map(converter::convert)
                .toList();
    }

    public List<ImageDTO> findAllImagesForSpecificSubcategoryId(Long subcategoryId){
        List<ImageEntity> imageEntityList = imageRepository.findAllImagesForSpecificSubcategoryId(subcategoryId).orElseThrow(() -> new SubCategoryException("Doesn't exist such subcategoryId " + subcategoryId));
        Converter<ImageEntity, ImageDTO> converter = (Converter<ImageEntity, ImageDTO>)genericConverterType.getConverter(ImageDTO.class);
        return imageEntityList.parallelStream()
                .map(converter::convert)
                .toList();
    }

    public List<ImageDTO> queryByAnnotationLocationCategorySubcategory(Long userId, String annotation, String location,
                                                                       String category, String subcategory,
                                                                       String startDateString, String endDateString){
        List<ImageEntity> imageEntityList = searchViewRepository.queryByAnnotationLocationCategorySubcategory(userId, annotation, location, category, subcategory).orElseThrow(() -> new ImageException("Some issue in SearchView"));
        Converter<ImageEntity, ImageDTO> converter = (Converter<ImageEntity, ImageDTO>)genericConverterType.getConverter(ImageDTO.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(!startDateString.isBlank() && !endDateString.isBlank()) {
            LocalDate statDate = LocalDate.parse(startDateString, dateTimeFormatter);
            LocalDate endDate = LocalDate.parse(endDateString, dateTimeFormatter);
            imageEntityList = imageEntityList.parallelStream()
                    .filter(image-> image.getDate().isAfter(statDate))
                    .filter(image -> image.getDate().isBefore(endDate))
                    .toList();
        }

        return imageEntityList.parallelStream()
                .map(converter::convert)
                .toList();
    }

    public ImageDTO getDataForImageById(Long imageId){
        ImageEntity imageEntity = imageRepository.getDataForImageById(imageId).orElseThrow(() -> new ImageException("No such image with id = " + imageId + " in database"));
        Converter<ImageEntity, ImageDTO> converter = (Converter<ImageEntity, ImageDTO>)genericConverterType.getConverter(ImageDTO.class);
        return converter.convert(imageEntity);
    }

    public File downloadImageUsingId(Long imageId){
        return new File("image4.jpg");
    }
    private void copyDataFromUserIntoDBEntity(ImageEntity imageUser, ImageEntity imageDB) {
        imageDB.setId(imageUser.getId());
        imageDB.setLocation(imageUser.getLocation());
        imageDB.setDate(imageUser.getDate());
        imageDB.setLatitudeDegrees(imageUser.getLatitudeDegrees());
        imageDB.setLongitudeDegrees(imageUser.getLongitudeDegrees());
        imageDB.setLatitudeHemisphere(imageUser.getLatitudeHemisphere());
        imageDB.setLongitudeHemisphere(imageUser.getLongitudeHemisphere());
        imageDB.setAnnotation(imageUser.getAnnotation());
        imageDB.setImageArrayOfBytes(imageUser.getImageArrayOfBytes());
    }
}
