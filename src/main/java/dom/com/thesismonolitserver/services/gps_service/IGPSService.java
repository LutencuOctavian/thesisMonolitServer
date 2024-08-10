package dom.com.thesismonolitserver.services.gps_service;

import dom.com.thesismonolitserver.dtos.GPSDTO;
import org.apache.commons.imaging.ImageReadException;

import java.io.IOException;

public interface IGPSService {
    GPSDTO getGPSDataForImage(String imageBase64) throws IOException, ImageReadException;
}
