/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.web.rest.resource;

import org.openmrs.Tag;
import org.openmrs.annotation.Handler;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 */
@Resource(name = RestConstants.VERSION_1 + "/tag", supportedClass = Tag.class, supportedOpenmrsVersions = { "1.11.*",
        "1.12.*", "2.0.*", "2.1.*" })
public class TagResource extends DelegatingCrudResource<Tag> {
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#newDelegate()
	 */
	@Override
	public Tag newDelegate() {
		return new Tag();
	}
	
	/**
	 * @see BaseDelegatingResource#getByUniqueId(String)
	 */
	@Override
	public Tag getByUniqueId(String uuid) {
		return getService().getTagByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#save(Object)
	 */
	@Override
	public Tag save(Tag tag) {
		return getService().saveTag(tag);
	}
	
	/**
	 * @see BaseDelegatingResource#delete(Object,String, RequestContext)
	 */
	@Override
	protected void delete(Tag tag, String reason, RequestContext context) {
		throw new ResourceDoesNotSupportOperationException("delete not allowed on tag");
	}
	
	/**
	 * @see BaseDelegatingResource#purge(Object, RequestContext)
	 */
	@Override
	public void purge(Tag tag, RequestContext context) {
		getService().purgeTag(tag);
	}
	
	/**
	 * @see BaseDelegatingResource#getRepresentationDescription(Representation)
	 */
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (rep instanceof DefaultRepresentation || rep instanceof RefRepresentation) {
			description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("tag");
			description.addProperty("objectType");
			description.addProperty("objectUuid");
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else if (rep instanceof FullRepresentation) {
			description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("tag");
			description.addProperty("objectType");
			description.addProperty("objectUuid");
			description.addProperty("auditInfo");
			description.addSelfLink();
		}
		return description;
	}
	
	/**
	 * @return the TagService
	 */
	private TagService getService() {
		return Context.getService(TagService.class);
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		
		description.addRequiredProperty("tag");
		description.addRequiredProperty("objectType");
		description.addRequiredProperty("objectUuid");
		
		return description;
	}
	
	@Override
	protected NeedsPaging<Tag> doGetAll(RequestContext context) throws ResponseException {
		return new NeedsPaging<Tag>(Context.getService(TagService.class).getTags(""), context);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String tagName = context.getRequest().getParameter("tag");
		if (tagName != null) {
			return new NeedsPaging<Tag>(getService().getTags(tagName), context);
		}
		return super.doSearch(context);
	}
}
