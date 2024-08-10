package dom.com.thesismonolitserver.validators.implementation;

import dom.com.thesismonolitserver.validators.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern =
                Pattern.compile("^\\+[1-9]{1}[0-9]{1,14}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
