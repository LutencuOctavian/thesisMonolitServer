package dom.com.thesismonolitserver.controllers;

import dom.com.thesismonolitserver.dtos.GPSDTO;
import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.services.gps_service.IGPSService;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path="/api/gps")
public class GPSController {

    private final IGPSService gpsService;

    @Autowired
    public GPSController(@Qualifier("gpsService") IGPSService gpsService) {
        this.gpsService = gpsService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/get-data-image", method = RequestMethod.POST)
    public ResponseEntity<Object> getGPSDataFromImage(@RequestBody ImageDTO imageDTO) throws IOException, ImageReadException {
        GPSDTO gpsDataForImage = gpsService.getGPSDataForImage(imageDTO.getImageBase64());
        return ResponseEntity.ok(gpsDataForImage);
    }
}
