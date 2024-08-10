package dom.com.thesismonolitserver.controllers;

import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.services.image_service.IImageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(path="/api/image")
public class ImageController {

    private final IImageService imageService;

    @Autowired
    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/new-image/{subCategoryId}", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewImage(@PathVariable(name = "subCategoryId") Long subCategoryId,
                                              @RequestBody @Valid ImageDTO imageDTO){
        imageService.saveNewImage(subCategoryId, imageDTO);
        return ResponseEntity.ok("Super okkkkk!  :-))");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/all-image/{subcategoryId}", method = RequestMethod.GET)
    public ResponseEntity<Object> gelAllImageForSpecificSubcategory(@PathVariable("subcategoryId") Long subcategoryId){
        List<ImageDTO> imageList = imageService.findAllImagesForSpecificSubcategoryId(subcategoryId);
        return ResponseEntity.status(HttpStatus.OK).body(imageList);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/all-image/{subcategoryId}", method = RequestMethod.POST)
    public ResponseEntity<Object> gelAllImageForSpecificSubcategoryPost(@PathVariable("subcategoryId") Long subcategoryId){
        List<ImageDTO> imageList = imageService.findAllImagesForSpecificSubcategoryId(subcategoryId);
        return ResponseEntity.ok(imageList);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/filter-image", method = RequestMethod.GET)
    public ResponseEntity<Object> queryByAnnotationLocationCategorySubcategory(
            @RequestParam("userId") Long userId,
            @RequestParam("annotation") String annotation,
            @RequestParam("location") String location,
            @RequestParam("category") String category,
            @RequestParam("subcategory") String subcategory,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate){
        List<ImageDTO> imageList = imageService.queryByAnnotationLocationCategorySubcategory(userId, annotation, location, category, subcategory, startDate, endDate);
        return ResponseEntity.ok(imageList);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/image-data/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<Object> updateImage(@PathVariable("imageId")Long imageId){
        ImageDTO dataForImageById = imageService.getDataForImageById(imageId);
        return ResponseEntity.ok(dataForImageById);
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
//    @RequestMapping(path="/update-image", method = RequestMethod.PUT)
//    public ResponseEntity<Object> updateImage(@RequestBody @Valid ImageDTO imageDTO){
//        imageService.updateImage(imageDTO);
//        return ResponseEntity.ok("Super okkkkk!  :-))");
//    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(path="/delete-image/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteImage(@PathVariable("imageId") Long imageId){
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("Super okkkkk!  :-))");
    }

    @RequestMapping(path="/download-image/{imageId}", method = RequestMethod.GET)
    public void downloadImage(@PathVariable("imageId") Long imageId, HttpServletResponse response) throws IOException {
        byte[] byteArray = imageService.findImageEntityById(imageId).getImageArrayOfBytes();
        response.setContentType("image/jpeg");
        response.setContentLengthLong(byteArray.length);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("image4.jpg", StandardCharsets.UTF_8)
                .build()
                .toString());
        InputStream targetStream = new ByteArrayInputStream(byteArray);
        IOUtils.copy(targetStream, response.getOutputStream());
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
//    @RequestMapping(path="/all-image", method = RequestMethod.GET)
//    public ResponseEntity<Object> gelAllImageForUser(AbstractOAuth2TokenAuthenticationToken<?> auth){
//        Long userId = getUserIdFromAuthorizationToken(auth);
//        List<ImageDTO> imageList = imageService.gelAllImageForUser(userId);
//        return ResponseEntity.ok(imageList);
//    }
//
}
