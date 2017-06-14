/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.api.db;

import org.openmrs.OpenmrsObject;
import org.openmrs.Tag;
import org.openmrs.api.APIException;

import java.util.List;

public interface TagDAO {
	
	/**
	 * @see org.openmrs.api.TagService#getTagByUuid(String)
	 */
	public Tag getTagByUuid(String uuid) throws DAOException;
	
	/**
	 * @see org.openmrs.api.TagService#getTagById(int)
	 */
	public Tag getTagById(int id) throws DAOException;
	
	/**
	 * @see org.openmrs.api.TagService#getTagByName(String)
	 */
	public List<Tag> getTagByName(String tag) throws DAOException;
	
	/**
	 * @see org.openmrs.api.TagService#getTags(OpenmrsObject)
	 */
	public List<Tag> getTags(OpenmrsObject openmrsObject) throws DAOException;
	
	/**
	 * @see org.openmrs.api.TagService#saveTag(Tag)
	 */
	public Tag saveTag(Tag tag) throws DAOException;
	
	/**
	 * @see org.openmrs.api.TagService#purgeTag(Tag)
	 */
	public void deleteTag(Tag tag) throws DAOException;
	
	/**
	 * @see org.openmrs.api.TagService#getAllTags()
	 */
	public List<Tag> getAllTags();
	
	/**
	 * @param object_type
	 * @param tag
	 * @return
	 */
	List<Tag> getTags(String object_type, String tag) throws Exception;
	
	/**
	 * @see org.openmrs.api.TagService#getTags(List, List, boolean)
	 */
	public List<Tag> getTags(List<String> object_type, List<String> tags) throws DAOException, ClassNotFoundException;
	
	/**
	 * @see org.openmrs.api.TagService#object_exits(String, String)
	 */
	public Object object_exists(String object_uuid, String object_type) throws APIException, ClassNotFoundException;
	
}
