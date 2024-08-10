package dom.com.thesismonolitserver.services.gps_service;

import dom.com.thesismonolitserver.dtos.GPSDTO;
import org.springframework.stereotype.Service;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import java.io.IOException;
import java.util.Base64;

@Service("gpsService")
public class GPSService implements IGPSService{
    public GPSDTO getGPSDataForImage(String imageBase64) throws IOException, ImageReadException {
        String[] arrayImageBase64 = imageBase64.split(",");
        Base64.Decoder decoder = Base64.getDecoder();
        final ImageMetadata metadata = Imaging.getMetadata(decoder.decode(arrayImageBase64[1]));
        if (metadata instanceof JpegImageMetadata) {
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

            final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
            double longitude = 0.0;
            double latitude = 0.0;
            if (null != exifMetadata) {
                final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                if (null != gpsInfo) {
                    longitude = gpsInfo.getLongitudeAsDegreesEast();
                    latitude = gpsInfo.getLatitudeAsDegreesNorth();
                }
            }

            final TiffField gpsLatitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            final TiffField gpsLongitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            if (gpsLatitudeRefField != null && gpsLongitudeRefField != null) {
                // all of these values are strings.
                final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
                final String gpsLongitudeRef = (String) gpsLongitudeRefField.getValue();

                String latitudeHemisphere = gpsLatitudeRef.equalsIgnoreCase("N") ? "NORTH" : "SOUTH";
                String longitudeHemisphere = gpsLongitudeRef.equalsIgnoreCase("E") ? "EAST" : "WEST";

                return GPSDTO.builder()
                        .latitudeDegree(latitude)
                        .longitudeDegree(longitude)
                        .latitudeHemisphere(latitudeHemisphere)
                        .longitudeHemisphere(longitudeHemisphere)
                        .build();
            }
        }
        return GPSDTO.getDefaultGPSDTO();
    }
}
