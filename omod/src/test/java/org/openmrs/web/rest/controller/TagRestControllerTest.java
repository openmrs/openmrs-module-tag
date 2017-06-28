package org.openmrs.web.rest.controller;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Tag;
import org.openmrs.TagConstants;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TagRestControllerTest extends MainResourceControllerTest {
	
	TagService tagService;
	
	public static final String TAG_RESOURCE_DATASET = "TagResourceDataSet.xml";
	
	@Before
	public void setup() throws Exception {
		tagService = Context.getService(TagService.class);
		executeDataSet(TAG_RESOURCE_DATASET);
	}
	
	@Override
	public String getURI() {
		return TagConstants.TAG_URI;
	}
	
	@Override
	public String getUuid() {
		return TagConstants.TAG_UUID;
	}
	
	@Override
	public long getAllCount() {
		return tagService.getTags("").size();
	}
	
	@Test
	public void shouldFindTagByUuid() throws Exception {
		MockHttpServletRequest req = request(RequestMethod.GET, getURI() + "/" + "7379a456-296a-2311-9c83-a3714b82d7ac");
		SimpleObject result = deserialize(handle(req));
		assertThat((String) PropertyUtils.getProperty(result, "uuid"), is("7379a456-296a-2311-9c83-a3714b82d7ac"));
	}
	
	@Test
	public void shouldFindTagByTagName() throws Exception {
		SimpleObject response = deserialize(handle(newGetRequest(getURI(), new Parameter("tag", "Init"), new Parameter("v",
		        "full"))));
		List<Object> results = Util.getResultsList(response);
		assertEquals(results.size(), 5);
	}
	
	@Test
	public void deleteTags_shouldPurgeTag() throws Exception {
		assertNotNull(tagService.getTagByUuid(getUuid()));
		handle(newDeleteRequest(getURI() + "/" + getUuid(), new Parameter("purge", "")));
		assertNull(tagService.getTagByUuid(getUuid()));
	}
	
}
