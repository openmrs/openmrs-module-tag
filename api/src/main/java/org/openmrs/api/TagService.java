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
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */

public interface TagService extends OpenmrsService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional(readOnly = true)
	Tag getTagByUuid(String uuid) throws APIException;
	
	/**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction.
	 * 
	 * @param tag
	 * @return
	 * @throws APIException
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional
	Tag saveTag(Tag tag) throws APIException;
	
	/**
	 * Completely delete the tag from the database.
	 * 
	 * @param tag The Tag to remove from the system
	 * @throws APIException
	 * @should delete tag from the database
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	void removeTag(Tag tag) throws APIException;
	
	/**
	 * @param id
	 * @return
	 * @throws APIException
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional(readOnly = true)
	Tag getTagById(int id) throws APIException;
	
	/**
	 * @param tag
	 * @return
	 * @throws APIException
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional(readOnly = true)
	List<Tag> getTagByName(String tag) throws APIException;
	
	/**
	 * Returns a list of All Tags.
	 * 
	 * @return
	 * @throws APIException
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional(readOnly = true)
	List<Tag> getAllTags() throws APIException;
	
	/**
	 * @param object_uuid
	 * @param object_type
	 * @return
	 * @throws APIException
	 */
	boolean object_exits(String object_uuid, String object_type) throws Exception;
	
	/**
	 * @param openmrsObject
	 * @param tag
	 * @throws Exception
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	void addTag(OpenmrsObject openmrsObject, String tag) throws Exception;
	
	/**
	 * @param openmrsObject
	 * @return
	 * @throws Exception
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional(readOnly = true)
	List<Tag> getTags(OpenmrsObject openmrsObject) throws Exception;
	
	/**
	 * @param object_types
	 * @param tags
	 * @param matchAllTags
	 * @return
	 */
	@Authorized(TagConstants.MANAGE_TAGS)
	@Transactional(readOnly = true)
	List<Tag> getTags(List<String> object_types, List<String> tags, boolean matchAllTags) throws Exception;
}
