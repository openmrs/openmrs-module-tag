package org.openmrs.web.rest.resource;

import org.openmrs.Tag;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class TagResourceTest extends BaseDelegatingResourceTest<TagResource, Tag> {

    public static final String TAG_RESOURCE_DATASET = "TagResourceDataSet.xml";

    @Before
    public void setup() throws Exception {
        executeDataSet(TAG_RESOURCE_DATASET);
    }

    @Override
    public Tag newObject() {
        return Context.getService(TagService.class).getTagByUuid(getUuidProperty());
    }

    @Override
    public void validateDefaultRepresentation() throws Exception {
        super.validateDefaultRepresentation();
        assertPropEquals("tag", getObject().getTag());
        assertPropEquals("uuid", getObject().getUuid());
    }

    @Override
    public void validateFullRepresentation() throws Exception {
        super.validateFullRepresentation();
        assertPropEquals("tag", getObject().getTag());
        assertPropEquals("objectType", getObject().getObjectType());
        assertPropEquals("objectUuid", getObject().getObjectUuid());
        assertPropEquals("uuid",getObject().getUuid());
    }

    @Override
    public void asRepresentation_shouldReturnValidRefRepresentation() throws Exception {
    }

    @Override
    public String getDisplayProperty() {
        return null;
    }

    @Override
    public String getUuidProperty() {
        return "7379a456-296a-2331-9c83-a3714b82d7ac";
    }

    @Test
    public void shouldLoadResource() throws Exception {
        TagResource resource = getResource();
    }
}