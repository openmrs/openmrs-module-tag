package org.openmrs.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.openmrs.Tag;
import org.openmrs.annotation.Handler;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static org.apache.solr.core.SolrConfig.log;

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
	 * @should fail if tag name is empty
	 * @should fail if object_uuid is empty
	 * @should fail if object_type is empty
	 * @should fail if object_type with object_uuid does not exist in database
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		if (obj == null || !(obj instanceof Tag)) {
			throw new IllegalArgumentException("The parameter obj should not be null and must be of type" + Tag.class);
		}
		
		Tag tagInstance = (Tag) obj;
		
		ValidationUtils.rejectIfEmpty(errors, "tag", "Tag.tag.empty");
		ValidationUtils.rejectIfEmpty(errors, "object_uuid", "Tag.object_uuid.empty");
		ValidationUtils.rejectIfEmpty(errors, "object_type", "Tag.object_type.empty");
		
		try {
			if (!Context.getService(TagService.class).object_exits(tagInstance.getObject_uuid(),
			    tagInstance.getObject_type()))
				errors.reject("Object type = " + tagInstance.getObject_type() + " with uuid= "
				        + tagInstance.getObject_uuid() + "does not exist");
		}
		catch (Exception ex) {
			log.error("error in validating if object exists", ex);
		}
	}
}
