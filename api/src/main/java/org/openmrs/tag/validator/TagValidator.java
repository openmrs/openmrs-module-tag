package org.openmrs.tag.validator;

import org.openmrs.tag.Tag;
import org.openmrs.annotation.Handler;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Handler(supports = { Tag.class })
public class TagValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Tag.class.isAssignableFrom(aClass);
	}
	
	/**
	 * checks if a given Tag object is valid
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		if (obj == null || !(obj instanceof Tag)) {
			throw new IllegalArgumentException("The parameter obj should not be null and must be of type" + Tag.class);
		}
		
		ValidationUtils.rejectIfEmpty(errors, "tag", "Tag.tag.empty");
		ValidationUtils.rejectIfEmpty(errors, "objectUuid", "Tag.object_uuid.empty");
		ValidationUtils.rejectIfEmpty(errors, "objectType", "Tag.object_type.empty");
	}
}
