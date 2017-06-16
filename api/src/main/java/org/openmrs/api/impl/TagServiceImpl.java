/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.UserService;
import org.openmrs.Tag;
import org.openmrs.api.TagService;
import org.openmrs.api.db.TagDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Transactional(readOnly = true)
public class TagServiceImpl extends BaseOpenmrsService implements TagService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private TagDAO dao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(TagDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public Tag getTagByUuid(String uuid) {
		return dao.getTagByUuid(uuid);
	}
	
	@Override
	public Tag saveTag(Tag tag) {
		return dao.saveTag(tag);
	}
	
	@Override
	public void removeTag(Tag tag) {
		dao.deleteTag(tag);
	}
	
	@Override
	@Transactional
	public List<Tag> getAllTags() {
		return dao.getAllTags();
	}
	
	@Override
	@Transactional
	public Tag getTagById(int id) {
		return dao.getTagById(id);
	}
	
	@Override
	@Transactional
	public List<Tag> getTags(String tag) {
		return dao.getTags(tag);
	}
	
	@Override
	public boolean object_exits(String object_uuid, String object_type) throws ClassNotFoundException {
		Object object = dao.object_exists(object_uuid, object_type);
		if (!object.equals(null))
			return true;
		return false;
	}
	
	@Override
	public Tag addTag(OpenmrsObject openmrsObject, String tag) throws Exception {
		if (duplicateTag(openmrsObject, tag)) {
			log.warn("duplicate Tag for " + openmrsObject);
			return null;
		} else {
			Tag tag1 = new Tag(tag, openmrsObject.getUuid(), openmrsObject.getClass().toString().substring(6));
			return saveTag(tag1);
		}
	}
	
	/**
	 * Validation Method which logs a warning if u try to add a duplicate tag to an OpenmrsObject
	 */
	public boolean duplicateTag(OpenmrsObject openmrsObject, String tag) throws Exception {
		List<String> list = getTags(openmrsObject);
		Iterator<String> listIterator = list.iterator();
		while (listIterator.hasNext()) {
			if (listIterator.next().equals(tag)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	@Transactional
	public List<String> getTags(OpenmrsObject openmrsObject) {
		List<String> tags = dao.getTags(openmrsObject);
		return tags;
	}
	
	@Override
	@Transactional
	public List<Tag> getTags(List<String> object_types, List<String> tags, boolean matchAllTags)
	        throws ClassNotFoundException {
		if (!matchAllTags) {
			List<Tag> tagList = dao.getTags(object_types, tags);
			return tagList;
		} else {
			List<Tag> finalList = new ArrayList<Tag>();
			List<Tag> tagList = (dao.getTags(object_types, tags));
			Map<String, List<Tag>> map = new HashMap<String, List<Tag>>();
			List<String> uniqueObjects = new ArrayList<String>();
			for (Tag tag1 : tagList) {
				String key = tag1.getObjectUuid();
				if (map.containsKey(key)) {
					List<Tag> list = map.get(key);
					list.add(tag1);
					
				} else {
					List<Tag> list = new ArrayList<Tag>();
					uniqueObjects.add(key);
					list.add(tag1);
					map.put(key, list);
				}
			}
			Iterator<String> uniqueObjectsIterator = uniqueObjects.iterator();
			while (uniqueObjectsIterator.hasNext()) {
				String keyMap = uniqueObjectsIterator.next();
				if (map.get(keyMap).size() == tags.size())
					finalList.addAll(map.get(keyMap));
			}
			return finalList;
		}
	}
}
