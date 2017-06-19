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
	Tag getTagByUuid(String uuid);
	
	/**
	 * @see org.openmrs.api.TagService#getTag(Integer)
	 */
	Tag getTag(Integer id);
	
	/**
	 * @see org.openmrs.api.TagService#getTags(String)
	 */
	List<Tag> getTags(String tag);
	
	/**
	 * @see org.openmrs.api.TagService#getTags(OpenmrsObject)
	 */
	List<Tag> getTags(OpenmrsObject openmrsObject);
	
	/**
	 * @see org.openmrs.api.TagService#saveTag(Tag)
	 */
	Tag saveTag(Tag tag);
	
	/**
	 * @see org.openmrs.api.TagService#deleteTag(Tag)
	 */
	void deleteTag(Tag tag);
	
	/**
	 * @see org.openmrs.api.TagService#getAllTags()
	 */
	List<String> getAllTags();
	
	/**
	 * @see org.openmrs.api.TagService#getTags(List, List)
	 */
	List<Tag> getTags(List<String> objectType, List<String> tags);
	
	/**
	 * @see org.openmrs.api.TagService#getObject(Class, String)
	 */
	Object getObject(String objectUuid, String objectType);
	
	/**
	 * @see org.openmrs.api.TagService#removeTag(OpenmrsObject, String)
	 */
	Tag getTag(OpenmrsObject openmrsObject, String tag);
}
