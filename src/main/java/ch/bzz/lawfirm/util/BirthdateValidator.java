package ch.bzz.lawfirm.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class BirthdateValidator implements ConstraintValidator<Birthdate, String> {

    protected long minYear;

    @Override
    public void initialize(Birthdate ageValue) {
        this.minYear = ageValue.value();
    }

    @Override
    public boolean isValid(String stringDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate date;
        try {
            date = LocalDate.parse(stringDate);
        } catch (Exception e) {
            return false;
        }
        // null values are invalid
        if ( date == null ) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return date.getYear() >= minYear && today.isAfter(date);
    }
}
