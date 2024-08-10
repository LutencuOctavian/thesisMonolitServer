package dom.com.thesismonolitserver.convertors.specific_convertors;

import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.enteties.ImageEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component("converterImageEntity2ImageDTO")
public class ConverterImageEntity2ImageDTO implements Converter<ImageEntity, ImageDTO> {

    Base64.Encoder encoder =Base64.getEncoder();
    @Override
    public ImageDTO convert(ImageEntity imageEntity) {
        ImageEntity.HemisphereEnum latitudeHemisphere = imageEntity.getLatitudeHemisphere();
        ImageEntity.HemisphereEnum longitudeHemisphere = imageEntity.getLongitudeHemisphere();
        Double longitudeDegrees = imageEntity.getLongitudeDegrees();
        Double latitudeDegrees = imageEntity.getLatitudeDegrees();
        return ImageDTO.builder()
                .id(imageEntity.getId())
                .location(imageEntity.getLocation())
                .date(formatLocalDateFromImageToString(imageEntity))
                .latitudeDegrees(latitudeDegrees == null ? 0.0 : latitudeDegrees)
                .longitudeDegrees(longitudeDegrees == null ? 0.0 : longitudeDegrees)
                .latitudeHemisphere(latitudeHemisphere== null ? "" : latitudeHemisphere.name())
                .longitudeHemisphere(longitudeHemisphere == null ? "" : longitudeHemisphere.name())
                .annotation(imageEntity.getAnnotation())
                .imageBase64(encoder.encodeToString(imageEntity.getImageArrayOfBytes()))
                .build();
    }

    @Override
    public <U> Converter<ImageEntity, U> andThen(Converter<? super ImageDTO, ? extends U> after) {
        return Converter.super.andThen(after);
    }

    private String formatLocalDateFromImageToString(ImageEntity image){
        LocalDate date = image.getDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(dateTimeFormatter);
    }
}
