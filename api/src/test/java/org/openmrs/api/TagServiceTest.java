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
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.OpenmrsObject;
import org.openmrs.Tag;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a unit test, which verifies logic in TagService.
 */
public class TagServiceTest extends BaseModuleContextSensitiveTest {
	
	private static final String TAG_INITIAL_XML = "TagServiceDataset.xml";
	
	TagService tagService;
	
	@Before
	public void runBeforeTests() throws Exception {
		executeDataSet(TAG_INITIAL_XML);
		tagService = Context.getService(TagService.class);
	}
	
	@Test
	public void getAllTags_shouldFetchAllUniqueStringTags() throws Exception {
		List<String> list = tagService.getAllTags();
		assertEquals(6, list.size());
	}
	
	@Test
	public void getTag_shouldFetchUniqueMatchingTag() throws Exception {
		Tag tag = tagService.getTag(3);
		assertEquals(tag.getUuid(), "e12c432c-1b9f-343e-b332-f3ef6c88ad3f");
	}
	
	@Test
	public void getTags_shouldFetchListOfMatchingTags() throws Exception {
		List<Tag> tagList = tagService.getTags("Vip", false);
		assertEquals(tagList.size(), 2);
	}
	
	@Test
	public void getTags_shouldFetchListOfExactMatchingTags() throws Exception {
		List<Tag> tagList = tagService.getTags("Vip-Encounters", true);
		assertEquals(tagList.size(), 1);
	}
	
	@Test
	public void getTags_shouldReturnListOfTagsOnAnOpenmrsObject() throws Exception {
		Encounter encounter = Context.getEncounterService().getEncounterByUuid("e403fafb-e5e4-42d0-9d11-4f52e89d148c");
		List<Tag> tags = tagService.getTags(encounter);
		assertEquals(tags.size(), 3);
	}
	
	@Test
	public void getTags_shouldReturnListOfTagsOnAnOpenmrsObjectTakingObjectTypeAndObjectUuidParameters() throws Exception {
		List<Tag> tags = tagService.getTags("org.openmrs.Encounter", "e403fafb-e5e4-42d0-9d11-4f52e89d148c");
		assertEquals(tags.size(), 3);
	}
	
	@Test
	public void getTags_shouldReturnTagsWhichMatchObjectTypeAndHaveAnyTag() throws Exception {
		List<Class<? extends OpenmrsObject>> types = new ArrayList<Class<? extends OpenmrsObject>>();
		types.add(Encounter.class);
		types.add(Obs.class);
		List<String> tags = new ArrayList<String>();
		tags.add("Initial");
		tags.add("FollowUp");
		List<Tag> tagList = tagService.getTags(types, tags);
		assertEquals(tagList.size(), 7);
	}
	
	@Test
	public void getTags_shouldIgnoreMatchingByTypeIfObjectTypesListIsEmpty() {
		List<Class<? extends OpenmrsObject>> types = new ArrayList<Class<? extends OpenmrsObject>>();
		List<String> tags = new ArrayList<String>();
		tags.add("Initial");
		tags.add("FollowUp");
		List<Tag> tagList = tagService.getTags(types, tags);
		assertEquals(tagList.size(), 9);
	}
	
	@Test
	public void addTag_shouldAddTagToObject() {
		Obs obs = Context.getObsService().getObsByUuid("2f616900-5e7c-4667-9a7f-dcb260abf1de");
		List<Tag> list = tagService.getTags(obs);
		assertEquals(list.size(), 2);
		tagService.addTag(obs, "Important");
		List<Tag> list1 = tagService.getTags(obs);
		assertEquals(list1.size(), 3);
	}
	
	@Test
	public void removeTag_shouldRemoveTagFromObject() {
		Obs obs = Context.getObsService().getObsByUuid("2f616900-5e7c-4667-9a7f-dcb260abf1de");
		List<Tag> list = tagService.getTags(obs);
		assertEquals(list.size(), 2);
		assertTrue(tagService.removeTag(obs, "Initial"));
		List<Tag> list1 = tagService.getTags(obs);
		assertEquals(list1.size(), 1);
	}
}
