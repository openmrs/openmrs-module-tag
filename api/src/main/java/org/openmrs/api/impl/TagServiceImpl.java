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
import org.openmrs.api.APIException;
import org.openmrs.Tag;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
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
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(TagDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public Tag getTagByUuid(String uuid) {
		return dao.getTagByUuid(uuid);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Tag saveTag(Tag tag) {
		return dao.saveTag(tag);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteTag(Tag tag) {
		dao.deleteTag(tag);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeTag(OpenmrsObject openmrsObject, String tag) {
		Tag tag1 = dao.getTag(openmrsObject, tag);
		if (tag1.equals(null)) {
			log.warn("Tag does not exist");
		} else {
			deleteTag(tag1);
		}
	}
	
	@Override
	public List<String> getAllTags() {
		return dao.getAllTags();
	}
	
	@Override
	public Tag getTag(Integer tagId) {
		return dao.getTag(tagId);
	}
	
	@Override
	public List<Tag> getTags(String tag) {
		return dao.getTags(tag);
	}
	
	@Override
	public <T extends OpenmrsObject> T getObject(Class<T> objectType, String objectUuid) {
		Object object;
		object = dao.getObject(objectUuid, objectType.getName());
		return objectType.cast(object);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Tag addTag(OpenmrsObject openmrsObject, String tag) {
		boolean check = false;
		try {
			check = hasTag(openmrsObject, tag);
		}
		catch (Exception e) {}
		if (check) {
			log.warn("duplicate Tag for " + openmrsObject);
		} else {
			Tag tag1 = new Tag(tag, openmrsObject.getUuid(), openmrsObject.getClass().getName());
			return saveTag(tag1);
		}
		return null;
	}
	
	/**
	 * Validation Method which logs a warning if u try to add a duplicate tag to an OpenmrsObject
	 */
	public boolean hasTag(OpenmrsObject openmrsObject, String tag) {
		Tag tag1;
		tag1 = dao.getTag(openmrsObject, tag);
		if (tag1.equals(null)) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Tag> getTags(OpenmrsObject openmrsObject) {
		List<Tag> tags = dao.getTags(openmrsObject);
		return tags;
	}
	
	@Override
	public List<Tag> getTags(List<String> objectTypes, List<String> tags) {
		List<Tag> tagList;
		if (tags.isEmpty()) {
			throw new APIException("tags cannot be empty");
		}
		try {
			tagList = dao.getTags(objectTypes, tags);
		}
		catch (Exception ex) {
			throw new APIException(ex);
		}
		return tagList;
	}
	
	@Override
	public <T extends OpenmrsObject> T toClass(String className) {
		Class<?> name;
		Object object;
		try {
			name = Class.forName(className);
			object = name.newInstance();
		}
		catch (Exception e) {
			throw new APIException(e);
		}
		Class<T> T = (Class<T>) name;
		return T.cast(object);
	}
	
	@Override
	public List<OpenmrsObject> getObjectsWithAllTags(List<String> objectTypes, List<String> tags) {
		List<Tag> tagList = dao.getTags(objectTypes, tags);
		List<OpenmrsObject> finalList = new ArrayList<OpenmrsObject>();
		Map<OpenmrsObject, List<Tag>> map = new HashMap<OpenmrsObject, List<Tag>>();
		for (Tag tag : tagList) {
			OpenmrsObject key = Context.getService(TagService.class).getObject(toClass(tag.getObjectType()).getClass(),
			    tag.getObjectUuid());
			if (map.containsKey(key)) {
				List<Tag> list = map.get(key);
				list.add(tag);
			} else {
				List<Tag> list = new ArrayList<Tag>();
				list.add(tag);
				map.put(key, list);
			}
		}
		Iterator<OpenmrsObject> keyIterator = map.keySet().iterator();
		while (keyIterator.hasNext()) {
			OpenmrsObject keyMap = keyIterator.next();
			if (map.get(keyMap).size() == tags.size()) {
				finalList.add(keyMap);
			}
		}
		return finalList;
	}
}
