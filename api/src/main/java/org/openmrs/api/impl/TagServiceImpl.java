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

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.Tag;
import org.openmrs.api.TagService;
import org.openmrs.api.dao.TagDao;

public class TagServiceImpl extends BaseOpenmrsService implements TagService {
	
	TagDao dao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(TagDao dao) {
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
		if (tag.getOwner() == null) {
			tag.setOwner(userService.getUser(1));
		}
		
		return dao.saveTag(tag);
	}
}
