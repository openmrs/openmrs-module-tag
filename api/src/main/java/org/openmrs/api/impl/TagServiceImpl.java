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
import org.hibernate.Hibernate;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.Tag;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.TagDAO;
import org.openmrs.api.db.hibernate.HibernateTagDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	public Tag getTagByUuid(String uuid) throws APIException {
		return dao.getTagByUuid(uuid);
	}
	
	@Override
	public Tag saveTag(Tag tag) throws APIException {
		return dao.saveTag(tag);
	}
	
	@Override
	public void purgeTag(Tag tag) throws APIException {
		dao.deleteTag(tag);
	}
	
	@Override
	public List<Tag> getAllTags() throws APIException {
		return dao.getAllTags();
	}
	
	@Override
	public Tag getTagById(int id) throws APIException {
		return dao.getTagById(id);
	}
	
	@Override
	public List<Tag> getTagByName(String tag) throws APIException {
		return dao.getTagByName(tag);
	}
	
	@Override
	public boolean object_exits(String object_uuid, String object_type) throws Exception {
		Object object = dao.object_exists(object_uuid, object_type);
		System.out.println(object);
		System.out.println(object_type);
		if (!object.equals(null))
			return true;
		return false;
	}

	@Override
	public void addTag(OpenmrsObject openmrsObject, String tag) throws Exception {
		if(object_exits(openmrsObject.getUuid(),openmrsObject.getClass().toString())) {
			Tag tag1 = new Tag(tag, openmrsObject.getUuid(), openmrsObject.getClass().toString());
			//validation method to check if object has already been tagged with this tag
			saveTag(tag1);
		}
		}

	@Override
	public void addTag(List<OpenmrsObject> list, String tag)throws Exception{
		while (list.iterator().hasNext()) {
			OpenmrsObject openmrsObject = list.iterator().next();
			if (object_exits(openmrsObject.getUuid(), openmrsObject.getClass().toString())) {
				Tag tag1 = new Tag(tag, openmrsObject.getUuid(), openmrsObject.getClass().toString());
				//validation method to check if object has already been tagged with this tag
				saveTag(tag1);
			}
		}
	}

	@Override
	public void addTag(List<OpenmrsObject> openmrsObjectsList, List<String> tagList) throws Exception {
		while (openmrsObjectsList.iterator().hasNext()) {
			OpenmrsObject openmrsObject = openmrsObjectsList.iterator().next();
			while (tagList.iterator().hasNext()) {
				String tag = tagList.iterator().next();
					if (object_exits(openmrsObject.getUuid(), openmrsObject.getClass().toString())) {
					Tag tag1 = new Tag(tag, openmrsObject.getUuid(), openmrsObject.getClass().toString());
					//validation method to check if object has already been tagged with this tag
					saveTag(tag1);
				}
			}
		}
	}
}
