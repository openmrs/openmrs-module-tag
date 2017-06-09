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
import org.openmrs.Encounter;
import org.openmrs.User;
import org.openmrs.Tag;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.TagDAO;
import org.openmrs.api.impl.TagServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import javax.validation.constraints.AssertTrue;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * This is a unit test, which verifies logic in TagService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class TagServiceTest extends BaseModuleContextSensitiveTest {
	
	private static final String TAG_INITIAL_XML = "TagServiceDataset.xml";
	
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
	public void getAlltags_shouldFetchAllTags() throws Exception {
		executeDataSet(TAG_INITIAL_XML);
		List<Tag> list = Context.getService(TagService.class).getAllTags();
		assertEquals(3, list.size());
	}
	
	@Test
	public void getTagById_shouldFetchUniqueMatchingTag() throws Exception {
		executeDataSet(TAG_INITIAL_XML);
		Tag tag = Context.getService(TagService.class).getTagById(3);
		assertEquals(tag.getUuid(), "e12c432c-1b9f-343e-b332-f3ef6c88ad3f");
	}
	
	@Test
	public void getTagByName_shouldFetchListOfMatchingTags() throws Exception {
		executeDataSet(TAG_INITIAL_XML);
		List<Tag> tagList = Context.getService(TagService.class).getTagByName("FollowUp");
		assertEquals(tagList.size(), 1);
	}
	
	@Test
	public void object_existsMethod_shouldreturntrueifobjectexists() throws Exception {
		boolean object = Context.getService(TagService.class).object_exits("0dde1358-7fcf-4341-a330-f119241a46e8",
		    "org.openmrs.Concept");
		assertTrue(object);
	}
}
