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

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openmrs.api.TagService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.Tag;
import org.openmrs.api.db.TagDAO;
import org.openmrs.api.db.hibernate.HibernateTagDao;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@org.springframework.test.context.ContextConfiguration(locations = { "classpath:TestingApplicationContext.xml" }, inheritLocations = true)
public class TagDaoTest extends BaseModuleContextSensitiveTest {
	
	private static final String TAG_INITIAL_XML = "TagServiceDataset.xml";
	
	@Autowired
	HibernateTagDao tagDao;
	
	@Autowired
	UserService userService;
	
	@Before
	public void runBeforeEachTest() throws Exception {
		executeDataSet(TAG_INITIAL_XML);
	}
	
	@Test
	//@Ignore("Unignore if you want to make the Tag class persistable, see also Tag and liquibase.xml")
	public void saveTag_shouldSaveAllPropertiesInDb() {
		//Given
		
		Tag tag = new Tag();
		tag.setTag("Initial");
		tag.setObject_uuid("asndassdwrasndassdwrasndassdwrasndassd");
		tag.setObject_type("Concept");
		tag.setCreator(userService.getUser(1));
		Date date = new Date();
		date.setTime(1112);
		tag.setDateCreated(date);
		
		//	item.setDescription("some description");
		//	item.setOwner(userService.getUser(1));
		
		//When
		tagDao.saveTag(tag);
		
		//Let's clean up the cache to be sure getTagByUuid fetches from DB and not from cache
		Context.flushSession();
		Context.clearSession();
		
		//Then
		Tag savedTag = tagDao.getTagByUuid(tag.getUuid());
		
		assertThat(savedTag, hasProperty("uuid", is(tag.getUuid())));
		//	assertThat(savedTag, hasProperty("owner", is(item.getOwner())));
		//	assertThat(savedTag, hasProperty("description", is(item.getDescription())));
	}
}
