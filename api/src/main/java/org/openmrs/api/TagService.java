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
	 * Fetches a tag by uuid. It can be called by any authenticated user.
	 * 
	 * @param uuid Universally Unique Identifier for the given tag
	 * @return tag or null
	 */
	@Authorized(TagConstants.GET_TAGS)
	Tag getTagByUuid(String uuid);
	
	/**
	 * Saves a tag. Tags can only be added not edited.
	 * 
	 * @param tag the Tag Object to be saved
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
	void purgeTag(Tag tag);
	
	/**
	 * Removes a tag(if exists) from an OpenmrsObject.
	 * 
	 * @param openmrsObject the openmrsObject from which the tags is to be removed
	 * @param tag the textual tag to be removed
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	boolean removeTag(OpenmrsObject openmrsObject, String tag);
	
	/**
	 * Fetches a tag by id.
	 * 
	 * @param tagId the id of the Tag Object to be retrieved
	 * @return Tag with given internal identifier
	 */
	@Authorized(TagConstants.GET_TAGS)
	Tag getTag(Integer tagId);
	
	/**
	 * Fetches a list of Tag objects having the given search Phrase in their tag. Partial Search
	 * supported
	 * 
	 * @param searchPhrase the phrase which the tags should contain
	 * @param exactMatch boolean input which returns tags with exact match (if true)
	 * @return List of Tag Objects
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(String searchPhrase, boolean exactMatch);
	
	/**
	 * Fetches a list of All Unique String Tags.
	 * 
	 * @return list Of Tags (String)
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<String> getAllTags();
	
	/**
	 * Adds a tag to the Openmrs object, if such a tag does not already exist on the object. A
	 * warning will be logged if the object does not exist in the database
	 * 
	 * @param openmrsObject the object to which the tag has to be added
	 * @param tag the tag (string) which is to be added
	 * @return The Tag object of tag on the Object
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	Tag addTag(OpenmrsObject openmrsObject, String tag);
	
	/**
	 * Fetches a list of tags objects added to the openmrs object
	 * 
	 * @param openmrsObject the object whose tags needs to obtained
	 * @return a List of Tag Objects having the same object type and uuid as the Openmrs Object
	 *         parameter
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(OpenmrsObject openmrsObject);
	
	/**
	 * Fetches a list of tags objects added to the openmrs object of give type and uuid.
	 * 
	 * @param objectType the java class name of the object
	 * @param objectUuid the uuid of the object
	 * @return a list of tag objects having the same object type and uuid
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(String objectType, String objectUuid);
	
	/**
	 * Fetches all tag objects where the type is in the specified list of types and the tag is in
	 * the specified list of tags. Throws an APIException if tags list is empty.
	 * 
	 * @param objectTypes a list of object types (objectTypes should be the Java Class ex.
	 *            Encounter.class)
	 * @param tags a list of tags
	 * @return a list of tag objects which have an object_type and a tag from the parameters
	 */
	@Authorized(TagConstants.GET_TAGS)
	List<Tag> getTags(List<Class<? extends OpenmrsObject>> objectTypes, List<String> tags);
}
