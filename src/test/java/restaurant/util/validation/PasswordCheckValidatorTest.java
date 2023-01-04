package restaurant.util.validation;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dto.request.UserRegistrationRequestDto;

@ExtendWith(MockitoExtension.class)
class PasswordCheckValidatorTest {
    private static final String PASSWORD = "1q2w3e4r";
    private PasswordCheckValidator passwordCheckValidator;
    private ConstraintValidatorContext constraintValidatorContext;
    private UserRegistrationRequestDto userRegistrationRequestDto;
    @Mock
    private PasswordCheck constraintAnnotation;

    @BeforeEach
    void setUp() {
        Mockito.when(constraintAnnotation.field()).thenReturn("password");
        Mockito.when(constraintAnnotation.fieldMatch()).thenReturn("repeatPassword");
        passwordCheckValidator = new PasswordCheckValidator();
        passwordCheckValidator.initialize(constraintAnnotation);
        userRegistrationRequestDto = new UserRegistrationRequestDto();
        userRegistrationRequestDto.setPassword(PASSWORD);
        userRegistrationRequestDto.setRepeatPassword(PASSWORD);
    }

    @Test
    void isValid_ok() {
        Assertions.assertTrue(passwordCheckValidator.isValid(userRegistrationRequestDto,
                constraintValidatorContext));
    }

    @Test
    void isValid_notOk() {
        isValidPasswordTest(PASSWORD + ".");
    }

    @Test
    void isValid_nullPassword_notOk() {
        isValidPasswordTest(null);
    }

    @Test
    void isValid_nullRepeatPassword_notOk() {
        userRegistrationRequestDto.setRepeatPassword(null);
        Assertions.assertFalse(passwordCheckValidator.isValid(userRegistrationRequestDto,
                constraintValidatorContext));
    }

    private void isValidPasswordTest(String password) {
        userRegistrationRequestDto.setPassword(password);
        Assertions.assertFalse(passwordCheckValidator.isValid(userRegistrationRequestDto,
                constraintValidatorContext));
    }
}
