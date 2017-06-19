/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api;

import org.openmrs.OpenmrsObject;
import org.openmrs.TagConstants;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.Tag;
import org.openmrs.api.context.Context;

import java.util.List;

/**
 * Contains services for adding/removing/obtaining Tags. Example Usage: <br>
 * <code>
 *   List&lt;Tag&gt; tags = Context.getService(TagService.class).getAllTags();
 * </code>
 * 
 * @see org.openmrs.Tag
 */

public interface TagService extends OpenmrsService {
	
	/**
	 * Returns a tag by uuid. It can be called by any authenticated user. It is fetched in read only
	 * transaction.
	 * 
	 * @param uuid
	 * @return tag or null
	 */
	@Authorized(TagConstants.GET_TAGS)
	Tag getTagByUuid(String uuid);
	
	/**
	 * Saves a tag. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction. Tags can only be added, not edited.
	 * 
	 * @param tag
	 * @return Tag that has been saved
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	Tag saveTag(Tag tag);
	
	/**
	 * Completely delete the tag from the database.
	 * 
	 * @param tag The Tag to remove from the system
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	void deleteTag(Tag tag);
	
	/**
	 * Removes a tag(if exists) from an OpenmrsObject.
	 * 
	 * @param openmrsObject the openmrsObject from which the tags is to be removed
	 * @param tag the textual tag to be removed
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	void removeTag(OpenmrsObject openmrsObject, String tag);
	
	/**
	 * Returns a tag by id.
	 * 
	 * @param tagId the id of the Tag Object to be retrieved
	 * @return Tag with given internal identifier
	 */
	@Authorized(TagConstants.GET_TAGS)
	Tag getTag(Integer tagId);
	
	/**
	 * Returns a list of Tag objects having the given tag, or having tags containing the substring.
	 * Partial Search supported
	 * 
	 * @param tag the tag (String) which the
	 * @return List of Tag Objects
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(String tag);
	
	/**
	 * Returns a list of All Tags.
	 * 
	 * @return list Of Tags (String)
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<String> getAllTags();
	
	/**
	 * Retrieves an Object with given object uuid and object type from the Openmrs database.
	 * 
	 * @param objectUuid the UUID of the object
	 * @param objectType the java class of the object (eg. Concept.class)
	 * @return
	 */
	<T extends OpenmrsObject> T getObject(Class<T> objectType, String objectUuid);
	
	/**
	 * It adds a tag to the openmrs object, if such a tag does not already exist on the object. A
	 * warning will be logged if the object does not exist in the database
	 * 
	 * @param openmrsObject the object to which the tag has to be added
	 * @param tag the tag (string) which is to be added
	 */
	@Authorized(TagConstants.GET_TAGS)
	Tag addTag(OpenmrsObject openmrsObject, String tag);
	
	/**
	 * It returns a list of tags added to the openmrs object
	 * 
	 * @param openmrsObject the object whose tags needs to obtained
	 * @return a List of Tag Objects having the same object type and uuid as the Openmrs Object
	 *         parameter
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(OpenmrsObject openmrsObject);
	
	/**
	 * returns a List of Tag Objects which have an Object Type that belongs to the objectTypes
	 * parameter and a tag which belongs to the tags (List<String>) parameter. returns a list of Tag
	 * Objects which have a tag belonging to the List, if objectTypes list is empty. Throws an API
	 * Exception if tags list is empty.
	 * 
	 * @param objectTypes a list of object types (objectTypes should be the Java Class Name)
	 * @param tags a list of tags
	 * @return a list of tag objects which have an object_type and a tag from the parameters
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(List<String> objectTypes, List<String> tags);
	
	/**
	 * returns a list of OpenMrs Objects which have an objectType from the parameters, and have all
	 * the tags.
	 * 
	 * @param objectTypes the permissible object_types to be searched against
	 * @param tags the list of tags that all objects should have
	 * @return a list of Openmrs Objects
	 */
	List<OpenmrsObject> getObjectsWithAllTags(List<String> objectTypes, List<String> tags);
	
	/**
	 * Obtains the java class from a String.
	 * 
	 * @param className the string java class name (ex. org.openmrs.Concept)
	 * @param <T>
	 * @return the OpenmrsObject class
	 */
	public <T extends OpenmrsObject> T toClass(String className);
}
