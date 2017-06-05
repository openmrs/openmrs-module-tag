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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.User;
import org.openmrs.Tag;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.TagDAO;
import org.openmrs.api.impl.TagServiceImpl;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * This is a unit test, which verifies logic in TagService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class TagServiceTest {
	
	@InjectMocks
	TagServiceImpl basicModuleService;
	
	@Mock
	TagDAO dao;
	
	@Mock
	UserService userService;
	
	@Mock
	ConceptService conceptService;
	
	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveTag_shouldSetCreatorIfNotSet() {
		//Given
		Tag tag = new Tag();
		
		when(dao.saveTag(tag)).thenReturn(tag);
		
		User user = new User();
		when(userService.getUser(1)).thenReturn(user);
		
		//When
		basicModuleService.saveTag(tag);
		
		//Then
		assertThat(tag, hasProperty("creator", is(user)));
	}
	
	@Test
	//	@Ignore
	public void saveTag_shouldPersist() {
		Tag tag = new Tag();
		tag.setTagName("Initial");
		tag.setObjectUuid("asndassdwrasndassdwrasndassdwrasndassd");
		tag.setObjectType("Concept");
		tag.setCreator(userService.getUser(1));
		Date date = new Date();
		date.setTime(1112);
		tag.setDateCreated(date);
		dao.saveTag(tag);
		System.out.println(tag);
		Context.flushSession();
		Context.clearSession();
		Tag saveTag = new Tag();
		saveTag = dao.getTagByUuid("asndassdwrasndassdwrasndassdwrasndassd");
		System.out.println(tag);
	}
}
