/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Please note that a corresponding table schema must be created in liquibase.xml.
 */
public class Tag extends BaseOpenmrsData implements Serializable {
	
	private Integer id;
	
	private String tag;
	
	private String object_uuid;
	
	private String object_type;

	private User creator;
	
	private Date dateCreated;
	
	private User changedBy;
	
	private Date date_changed;
	
	private String Uuid = UUID.randomUUID().toString();

	public Tag(){}

	public Tag(String tag, String object_uuid, String object_type) {
		this.tag = tag;
		this.object_uuid = object_uuid;
		this.object_type = object_type;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getObject_uuid() {
		return object_uuid;
	}
	
	public void setObject_uuid(String object_uuid) {
		this.object_uuid = object_uuid;
	}
	
	public String getObject_type() {
		return object_type;
	}
	
	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@Override
	public Date getDateCreated() {
		return dateCreated;
	}
	
	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Override
	public User getChangedBy() {
		return changedBy;
	}
	
	@Override
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	public Date getDate_changed() {
		return date_changed;
	}
	
	public void setDate_changed(Date date_changed) {
		this.date_changed = date_changed;
	}
	
	@Override
	public String getUuid() {
		return Uuid;
	}
	
	@Override
	public void setUuid(String uuid) {
		Uuid = uuid;
	}
}
