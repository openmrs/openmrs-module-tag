/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api.db;

import org.junit.Test;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.Tag;
import org.openmrs.api.db.hibernate.HibernateTagDao;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class TagDaoTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	HibernateTagDao tagDao;
	
	@Autowired
	UserService userService;
	
	@Test
	public void saveTag_shouldSaveAllPropertiesInDb() {
		//Given
		
		Tag tag = new Tag();
		tag.setTag("Initial");
		tag.setObjectUuid("0dde1358-7fcf-4341-a330-f119241a46e8");
		tag.setObjectType("org.openmrs.Concet");
		
		//When
		tagDao.saveTag(tag);
		
		//Let's clean up the cache to be sure getTagByUuid fetches from DB and not from cache
		Context.flushSession();
		Context.clearSession();
		//Then
		Tag savedTag = tagDao.getTagByUuid(tag.getUuid());
		assertThat(savedTag, hasProperty("uuid", is(tag.getUuid())));
		assertThat(savedTag, hasProperty("creator", is(tag.getCreator())));
		assertThat(savedTag, hasProperty("tag", is(tag.getTag())));
		assertThat(savedTag, hasProperty("id", is(tag.getId())));
		assertThat(savedTag, hasProperty("objectType", is(tag.getObjectType())));
		assertThat(savedTag, hasProperty("objectUuid", is(tag.getObjectUuid())));
	}
}
