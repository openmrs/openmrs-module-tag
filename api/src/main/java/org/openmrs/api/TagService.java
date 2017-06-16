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

import java.util.List;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */

public interface TagService extends OpenmrsService {
	
	/**
	 * Returns a tag by uuid. It can be called by any authenticated user. It is fetched in read only
	 * transaction.
	 * 
	 * @param uuid
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public Tag getTagByUuid(String uuid);
	
	/**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction.
	 * 
	 * @param tag
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public Tag saveTag(Tag tag);
	
	/**
	 * Completely delete the tag from the database.
	 * 
	 * @param tag The Tag to remove from the system
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public void removeTag(Tag tag);
	
	/**
	 * Returns a tag by id.
	 * 
	 * @param id
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public Tag getTagById(int id);
	
	/**
	 * Returns a list of tags, by tag(tag name).
	 * 
	 * @param tag
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public List<Tag> getTags(String tag);
	
	/**
	 * Returns a list of All Tags.
	 * 
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public List<Tag> getAllTags();
	
	/**
	 * Returns true if an object of object_type, and object_uuid exists in the database.
	 * 
	 * @param object_uuid
	 * @param object_type
	 * @return
	 */
	public boolean object_exits(String object_uuid, String object_type) throws ClassNotFoundException;
	
	/**
	 * It adds a tag to the openmrs object, if such a tag does not already exist on the object.
	 * 
	 * @param openmrsObject
	 * @param tag
	 * @throws Exception
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public Tag addTag(OpenmrsObject openmrsObject, String tag) throws Exception;
	
	/**
	 * It returns a list of tags added to the openmrs object
	 * 
	 * @param openmrsObject
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public List<String> getTags(OpenmrsObject openmrsObject);
	
	/**
	 * It returns a list of Tag objects if an object of object_type is having all tags (if
	 * matchAllTags = true) or any, one of the tags (if matchAllTags = false)
	 * 
	 * @param object_types
	 * @param tags
	 * @param matchAllTags
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	public List<Tag> getTags(List<String> object_types, List<String> tags, boolean matchAllTags)
	        throws ClassNotFoundException;
}
