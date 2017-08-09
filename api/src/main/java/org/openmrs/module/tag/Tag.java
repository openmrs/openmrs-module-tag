/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.tag;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Tag is a textual label which can be applied any OpenmrsObject. A Separate tag_tag table is
 * maintained to store information regarding Tags. A Tag Object contains three important properties;
 * <i>objectType</i>, the java class name of the OpenmrsObject, <i>objectUuid</i>, the unique
 * identifier of the object, and <i>tag</i> the text label to be attached to the OpenmrsObject.
 */
public class Tag extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 6713376997114869435L;
	
	private Integer tagId;
	
	private String tag;
	
	private String objectUuid;
	
	private String objectType;
	
	private String Uuid = UUID.randomUUID().toString();
	
	public Tag() {
	}
	
	public Tag(String tag, String objectUuid, String objectType) {
		this.tag = tag;
		this.objectUuid = objectUuid;
		this.objectType = objectType;
	}
	
	@Override
	public Integer getId() {
		return tagId;
	}
	
	@Override
	public void setId(Integer tagId) {
		this.tagId = tagId;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getObjectUuid() {
		return objectUuid;
	}
	
	public void setObjectUuid(String objectUuid) {
		this.objectUuid = objectUuid;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
}
