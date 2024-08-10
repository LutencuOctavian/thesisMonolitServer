package dom.com.thesismonolitserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GPSDTO {
    private Double longitudeDegree;
    private Double latitudeDegree;
    private String latitudeHemisphere;
    private String longitudeHemisphere;

    public static GPSDTO getDefaultGPSDTO(){
        return GPSDTO.builder()
                .latitudeDegree(0.0)
                .longitudeDegree(0.0)
                .latitudeHemisphere("NORTH")
                .longitudeHemisphere("EAST")
                .build();
    }
}
