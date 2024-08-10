package dom.com.thesismonolitserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryDTO {
    private Long idSubcategory;
    private String labelSubcategory;
}
