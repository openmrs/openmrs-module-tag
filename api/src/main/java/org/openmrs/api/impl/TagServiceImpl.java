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
import org.openmrs.util.OpenmrsClassLoader;
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
	
	/**
	 * @see TagService#getTagByUuid(String)
	 */
	@Override
	public Tag getTagByUuid(String uuid) {
		return dao.getTagByUuid(uuid);
	}
	
	/**
	 * @see TagService#saveTag(Tag)
	 */
	@Override
	@Transactional(readOnly = false)
	public Tag saveTag(Tag tag) {
		if (getObject(toClass(tag.getObjectType()), tag.getObjectUuid()) == null) {
			log.warn("Object does not exist in the the database");
		}
		return dao.saveTag(tag);
	}
	
	/**
	 * @see TagService#purgeTag(Tag)
	 */
	@Override
	@Transactional(readOnly = false)
	public void purgeTag(Tag tag) {
		dao.deleteTag(tag);
	}
	
	/**
	 * @see TagService#removeTag(OpenmrsObject, String)
	 */
	@Override
	@Transactional(readOnly = false)
	public boolean removeTag(OpenmrsObject openmrsObject, String tag) {
		Tag tag1 = dao.getTag(openmrsObject.getClass().getName(), openmrsObject.getUuid(), tag);
		if (tag1 == null) {
			log.warn("Tag does not exist");
		} else {
			purgeTag(tag1);
			return true;
		}
		return false;
	}
	
	/**
	 * @see TagService#getAllTags()
	 */
	@Override
	public List<String> getAllTags() {
		return dao.getAllTags();
	}
	
	/**
	 * @see TagService#getTag(Integer)
	 */
	@Override
	public Tag getTag(Integer tagId) {
		return dao.getTag(tagId);
	}
	
	/**
	 * @see TagService#getTags(String, boolean)
	 */
	@Override
	public List<Tag> getTags(String searchPhrase, boolean exactMatch) {
		return dao.getTags(searchPhrase, exactMatch);
	}
	
	/**
	 * Retrieves an Object with given object uuid and object type from the Openmrs database.
	 */
	private <T extends OpenmrsObject> T getObject(Class<T> objectType, String objectUuid) {
		return dao.getObject(objectType, objectUuid);
		
	}
	
	/**
	 * @see TagService#addTag(OpenmrsObject, String)
	 */
	@Override
	@Transactional(readOnly = false)
	public Tag addTag(OpenmrsObject openmrsObject, String tag) {
		if (hasTag(openmrsObject, tag)) {
			log.warn("duplicate Tag for " + openmrsObject);
		} else {
			return saveTag(new Tag(tag, openmrsObject.getUuid(), openmrsObject.getClass().getName()));
		}
		return null;
	}
	
	/**
	 * Validation Method which returns false if u try to add a duplicate tag to an OpenmrsObject
	 */
	public boolean hasTag(OpenmrsObject openmrsObject, String tag) {
		return dao.getTag(openmrsObject.getClass().getName(), openmrsObject.getUuid(), tag) != null;
	}
	
	/**
	 * @see TagService#getTags(OpenmrsObject)
	 */
	@Override
	public List<Tag> getTags(OpenmrsObject openmrsObject) {
		return dao.getTags(openmrsObject);
	}
	
	/**
	 * @see TagService#getTags(String, String)
	 */
	@Override
	public List<Tag> getTags(String objectType, String objectUuid) {
		return dao.getTags(objectType, objectUuid);
	}
	
	/**
	 * @see TagService#getTags(List, List)
	 */
	@Override
	public List<Tag> getTags(List<Class<? extends OpenmrsObject>> objectTypes, List<String> tags) {
		if (tags.isEmpty()) {
			throw new APIException("tags cannot be empty");
		}
		List<String> types = new ArrayList<String>();
		Iterator<Class<? extends OpenmrsObject>> iterator = objectTypes.iterator();
		while (iterator.hasNext()) {
			types.add(iterator.next().getName());
		}
		
		return dao.getTags(types, tags);
	}
	
	/**
	 * Convenience method to return a Class using a string parameter with OpenmrsClassLoader
	 */
	private <T extends OpenmrsObject> Class<T> toClass(String className) {
		try {
			return (Class<T>) OpenmrsClassLoader.getInstance().loadClass(className);
		}
		catch (ClassNotFoundException e) {
			throw new APIException(e);
		}
	}
	
	/**
	 * Gets a list of OpenMrs Objects which have a matching object type, and all the tags.
	 * 
	 * @param objectTypes the permissible object_types to be searched against (objectTypes should be
	 *            the Java Class)
	 * @param tags the list of tags that all objects should have
	 * @return a list of Openmrs Objects
	 */
	private List<OpenmrsObject> getObjectsWithAllTags(List<Class<? extends OpenmrsObject>> objectTypes, List<String> tags) {
		List<String> types = new ArrayList<String>();
		Iterator<Class<? extends OpenmrsObject>> iterator = objectTypes.iterator();
		while (iterator.hasNext()) {
			types.add(iterator.next().getName());
		}
		List<Tag> tagList = dao.getTags(types, tags);
		List<OpenmrsObject> finalList = new ArrayList<OpenmrsObject>();
		Map<OpenmrsObject, List<Tag>> map = new HashMap<OpenmrsObject, List<Tag>>();
		for (Tag tag : tagList) {
			OpenmrsObject key = getObject(toClass(tag.getObjectType()), tag.getObjectUuid());
			List<Tag> list = new ArrayList<Tag>();
			if (map.containsKey(key)) {
				map.get(key).add(tag);
				list.addAll(map.get(key));
			} else {
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
