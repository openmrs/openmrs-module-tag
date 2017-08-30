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
package org.openmrs.tag.web.rest.resource;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.OpenmrsObject;
import org.openmrs.tag.Tag;
import org.openmrs.tag.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.RepHandler;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.annotation.SubResource;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.response.GenericRestException;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.util.OpenmrsClassLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		
		try {
			UUID uuid1 = UUID.fromString(uuid);
			return Context.getService(TagService.class).getTagByUuid(uuid);
		}
		catch (IllegalArgumentException e) {
			List<Tag> tagList = Context.getService(TagService.class).getTags(uuid, true);
			if (tagList.size() == 0) {
				return null;
			}
			Tag tag = new Tag();
			tag.setTag(uuid);
			return tag;
		}
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
	 * Returns the DefaultRepresentation
	 */
	@RepHandler(DefaultRepresentation.class)
	public SimpleObject asDef(Tag delegate) throws ConversionException, ClassNotFoundException {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("tag");
		if (StringUtils.isNotBlank(delegate.getObjectType())) {
			description.addProperty("uuid");
			description.addProperty("objectType");
			description.addProperty("objectUuid");
		}
		List<Tag> tagList = Context.getService(TagService.class).getTags(delegate.getTag(), true);
		Iterator<Tag> tagIterator = tagList.iterator();
		while (tagIterator.hasNext()) {
			Tag iterator = tagIterator.next();
			description.addLink(getResourceName(iterator.getObjectType()),
			    getUriOfObject(iterator.getObjectType(), iterator.getObjectUuid()));
		}
		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		return convertDelegateToRepresentation(delegate, description);
	}
	
	/**
	 * Returns the FullRepresentation
	 */
	@RepHandler(FullRepresentation.class)
	public SimpleObject asFull(Tag delegate) throws ConversionException {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (StringUtils.isNotBlank(delegate.getObjectType())) {
			description.addProperty("uuid");
			description.addProperty("tag");
			description.addProperty("objectType");
			description.addProperty("objectUuid");
			description.addProperty("auditInfo");
		} else {
			List<Tag> tags = getService().getTags(delegate.getTag(), true);
			SimpleObject simpleObject = new SimpleObject();
			List<Map<String, Object>> taggedObjects = new ArrayList<Map<String, Object>>();
			for (Tag t : tags) {
				Map<String, Object> taggedObject = new LinkedHashMap<String, Object>();
				taggedObject.put("tag", t.getTag());
				taggedObject.put("uuid", t.getUuid());
				taggedObject.put("objectType", t.getObjectType());
				taggedObject.put("objectUuid", t.getObjectUuid());
				taggedObjects.add(taggedObject);
			}
			simpleObject.put("taggedObjects", taggedObjects);
			return simpleObject;
		}
		description.addSelfLink();
		return convertDelegateToRepresentation(delegate, description);
	}
	
	/**
	 * @see BaseDelegatingResource#getRepresentationDescription(Representation)
	 */
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("display");
			description.addProperty("uuid");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	/**
	 * Property getter for 'display'
	 */
	@PropertyGetter("display")
	public String getDisplay(Tag instance) {
		return instance.getTag();
	}
	
	/**
	 * @return the TagService
	 */
	private TagService getService() {
		return Context.getService(TagService.class);
	}
	
	/**
	 * @see BaseDelegatingResource#getCreatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		
		description.addRequiredProperty("tag");
		description.addRequiredProperty("objectType");
		description.addRequiredProperty("objectUuid");
		
		return description;
	}
	
	/**
	 * @see DelegatingCrudResource#doGetAll(RequestContext)
	 */
	@Override
	protected NeedsPaging<Tag> doGetAll(RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	/**
	 * Method to obtain the Uri of any OpenrmrsObject
	 * 
	 * @param objectType the Java class name of the object
	 * @param objectUuid the Uuid of the object
	 * @return the Uri of the given object
	 */
	private String getUriOfObject(String objectType, String objectUuid) {
		Class<?> className = null;
		try {
			className = Context.loadClass(objectType);
		}
		catch (ClassNotFoundException e) {}
		BaseDelegatingResource resource = (BaseDelegatingResource) Context.getService(RestService.class)
		        .getResourceBySupportedClass(className);
		return resource.getUri(resource.getByUniqueId(objectUuid));
	}
	
	/**
	 * Extracts resource name from a resource handler
	 * 
	 * @return resource name
	 */
	private String getResourceName(String objectType) {
		
		Class<?> className = null;
		try {
			className = Context.loadClass(objectType);
		}
		catch (ClassNotFoundException e) {}
		DelegatingResourceHandler resourceHandler = (DelegatingResourceHandler<?>) Context.getService(RestService.class)
		        .getResourceBySupportedClass(className);
		Resource annotation = resourceHandler.getClass().getAnnotation(Resource.class);
		
		String resourceName = null;
		if (annotation != null) {
			resourceName = annotation.name();
		} else {
			SubResource subResourceAnnotation = resourceHandler.getClass().getAnnotation(SubResource.class);
			if (subResourceAnnotation != null) {
				resourceName = subResourceAnnotation.path();
			}
		}
		return resourceName;
	}
	
	/**
	 * @see DelegatingCrudResource#doSearch(RequestContext)
	 */
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String tagName = context.getRequest().getParameter("tag");
		String objectType = context.getRequest().getParameter("objectType");
		String objectUuid = context.getRequest().getParameter("objectUuid");
		if (StringUtils.isNotBlank(objectType)) {
			ArrayList<Class<? extends OpenmrsObject>> openmrsObjects = new ArrayList<Class<? extends OpenmrsObject>>();
			try {
				openmrsObjects.add((Class<? extends OpenmrsObject>) OpenmrsClassLoader.getInstance().loadClass(objectType));
			}
			catch (ClassNotFoundException e) {
				log.error("Class of" + objectType + "Not Found", e);
			}
			if (StringUtils.isNotBlank(tagName)) {
				ArrayList<String> tagNames = new ArrayList<String>();
				tagNames.add(tagName);
				return new NeedsPaging<Tag>(getService().getTags(openmrsObjects, tagNames), context);
			} else if (StringUtils.isNotBlank(objectUuid)) {
				return new NeedsPaging<Tag>(getService().getTags(objectType, objectUuid), context);
			} else {
				throw new GenericRestException();
			}
		} else {
			throw new GenericRestException();
		}
	}
}
