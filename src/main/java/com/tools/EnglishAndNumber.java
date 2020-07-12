package com.tools;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE ,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy={})
@Documented
@Pattern(regexp= EnglishAndNumber.PATTERN,message="تنها حروف و اعداد انگلیسی مجاز می باشد")
public @interface EnglishAndNumber {
	String message() default "{com.iansar.clubapp.validation.constraints.English}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String PATTERN = "^[a-z A-Z0-9]*$";
}
