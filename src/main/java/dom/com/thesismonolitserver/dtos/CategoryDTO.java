package dom.com.thesismonolitserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long idCategory;
    private String labelCategory;
    private List<SubcategoryDTO> listOfSubcategories;

    public CategoryDTO(Long idCategory, String labelCategory) {
        this.idCategory = idCategory;
        this.labelCategory = labelCategory;
    }
}
