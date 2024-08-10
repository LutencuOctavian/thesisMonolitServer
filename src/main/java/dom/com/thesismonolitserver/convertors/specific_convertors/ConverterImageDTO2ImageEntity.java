package dom.com.thesismonolitserver.convertors.specific_convertors;

import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.enteties.ImageEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component("converterImageDTO2ImageEntity")
public class ConverterImageDTO2ImageEntity implements Converter<ImageDTO, ImageEntity> {

    @Override
    public ImageEntity convert(ImageDTO imageDTO) {
        Base64.Decoder decoder = Base64.getDecoder();
        String[] stringArray = imageDTO.getImageBase64().split(",");
        return ImageEntity.builder()
                .id(imageDTO.getId())
                .location(imageDTO.getLocation())
                .date(formatStringDateFromImageToLocalDate(imageDTO))
                .latitudeDegrees(imageDTO.getLatitudeDegrees())
                .longitudeDegrees(imageDTO.getLongitudeDegrees())
                .latitudeHemisphere(getLatitudeHemisphereFromImageDTO(imageDTO))
                .longitudeHemisphere(getLongitudeHemisphereFromImageDTO(imageDTO))
                .annotation(imageDTO.getAnnotation())
                .imageArrayOfBytes(decoder.decode(stringArray[1]))
                .build();

    }

    @Override
    public <U> Converter<ImageDTO, U> andThen(Converter<? super ImageEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }

    private LocalDate formatStringDateFromImageToLocalDate(ImageDTO image){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(image.getDate(), dateTimeFormatter);
    }

    private ImageEntity.HemisphereEnum getLongitudeHemisphereFromImageDTO(ImageDTO imageDTO){
        String hemisphere = imageDTO.getLongitudeHemisphere();
        return hemisphere.isBlank() ? null : ImageEntity.HemisphereEnum.valueOf(hemisphere);
    }

    private ImageEntity.HemisphereEnum getLatitudeHemisphereFromImageDTO(ImageDTO imageDTO){
        String hemisphere = imageDTO.getLatitudeHemisphere();
        return hemisphere.isBlank() ? null : ImageEntity.HemisphereEnum.valueOf(hemisphere);
    }
}
