package dom.com.thesismonolitserver.validators.implementation;

import dom.com.thesismonolitserver.validators.NotEmptyList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<String>> {
    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext constraintValidatorContext) {
        return !list.isEmpty();
    }
}
