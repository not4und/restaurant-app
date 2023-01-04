package restaurant.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import restaurant.dto.request.UserRegistrationRequestDto;

public class PasswordCheckValidator
        implements ConstraintValidator<PasswordCheck, UserRegistrationRequestDto> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(UserRegistrationRequestDto userRegistrationRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(userRegistrationRequestDto)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(userRegistrationRequestDto)
                .getPropertyValue(fieldMatch);
        return fieldValue != null && fieldValue.equals(fieldMatchValue);
    }
}
