package org.openmrs.web.rest.controller;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.Tag;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.openmrs.web.rest.TagRestTestConstants;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TagRestControllerTest extends MainResourceControllerTest {
	
	private TagService tagService;
	
	private static final String TAG_RESOURCE_DATASET = "TagServiceDataset.xml";
	
	@Before
	public void setup() throws Exception {
		tagService = Context.getService(TagService.class);
		executeDataSet(TAG_RESOURCE_DATASET);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Override
	public String getURI() {
		return TagRestTestConstants.TAG_URI;
	}
	
	@Override
	public String getUuid() {
		return TagRestTestConstants.TAG_UUID;
	}
	
	@Override
	public long getAllCount() {
		return 0;
	}
	
	@Override
	@Test
	public void shouldGetAll() throws Exception {
		thrown.expect(ResourceDoesNotSupportOperationException.class);
		super.shouldGetAll();
	}
	
	@Test
	public void shouldFindTagByUuid() throws Exception {
		MockHttpServletRequest req = request(RequestMethod.GET, getURI() + "/" + getUuid());
		SimpleObject result = deserialize(handle(req));
		assertThat((String) PropertyUtils.getProperty(result, "uuid"), is(getUuid()));
	}
	
	@Test
	public void shouldFindTagByTagName() throws Exception {
		MockHttpServletRequest req = request(RequestMethod.GET, getURI() + "/" + "Initial");
		SimpleObject result = deserialize(handle(req));
		assertThat((String) PropertyUtils.getProperty(result, "tag"), is("Initial"));
	}
	
	@Test
	public void shouldFindTagByTagObjectTypeAndTagName() throws Exception {
		SimpleObject response = deserialize(handle(newGetRequest(getURI(), new Parameter("tag", "Initial"), new Parameter(
		        "objectType", "org.openmrs.Encounter"), new Parameter("v", "default"))));
		List<Object> results = Util.getResultsList(response);
		assertEquals(3, results.size());
	}
	
	@Test
	public void shouldFindTagByTagObjectTypeAndObjectUuid() throws Exception {
		SimpleObject response = deserialize(handle(newGetRequest(getURI(), new Parameter("objectType",
		        "org.openmrs.Encounter"), new Parameter("objectUuid", "e403fafb-e5e4-42d0-9d11-4f52e89d148c"),
		    new Parameter("v", "full"))));
		List<Object> results = Util.getResultsList(response);
		assertEquals(3, results.size());
	}
	
	@Test
	public void deleteTags_shouldPurgeTag() throws Exception {
		assertNotNull(tagService.getTagByUuid(getUuid()));
		handle(newDeleteRequest(getURI() + "/" + getUuid(), new Parameter("purge", "")));
		assertNull(tagService.getTagByUuid(getUuid()));
	}
	
	@Test
	public void addTags_shouldCreateNewTagObject() throws Exception {
		SimpleObject tag = new SimpleObject();
		String objectType = "org.openmrs.Obs";
		String objectUuid = "2f616900-5e7c-4667-9a7f-dcb260abf1de";
		tag.add("tag", "testName");
		tag.add("objectType", objectType);
		tag.add("objectUuid", objectUuid);
		List<Tag> tagList = Context.getService(TagService.class).getTags(objectType, objectUuid);
		assertEquals(2, tagList.size());
		SimpleObject result = deserialize(handle(newPostRequest(getURI(), tag)));
		List<Tag> tagList1 = Context.getService(TagService.class).getTags(objectType, objectUuid);
		assertEquals(3, tagList1.size());
	}
}
