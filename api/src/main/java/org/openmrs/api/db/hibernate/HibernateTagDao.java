/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.TagService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.TagDAO;
import org.openmrs.Tag;
import org.openmrs.util.OpenmrsClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("tagDAO")
public class HibernateTagDao implements TagDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Tag getTagByUuid(String uuid) {
		return (Tag) getSession().createCriteria(Tag.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Tag saveTag(Tag tag) {
		getSession().saveOrUpdate(tag);
		return tag;
	}
	
	@Override
	public void deleteTag(Tag tag) {
		getSession().delete(tag);
	}
	
	@Override
	public List<String> getAllTags() {
		return getSession().createCriteria(Tag.class).setProjection(Projections.property("tag")).list();
	}
	
	@Override
	public Tag getTag(Integer id) {
		return (Tag) getSession().get(Tag.class, id);
	}
	
	@Override
	public List<Tag> getTags(String tag) {
		Criteria criteria = getSession().createCriteria(Tag.class);
		return criteria.add(Restrictions.like("tag", tag, MatchMode.ANYWHERE)).list();
	}
	
	@Override
	public List<Tag> getTags(OpenmrsObject openmrsObject) {
		Criteria criteria = getSession().createCriteria(Tag.class);
		criteria.add(Restrictions.eq("objectType", openmrsObject.getClass().getName()));
		criteria.add(Restrictions.eq("objectUuid", (openmrsObject.getUuid())));
		return criteria.list();
	}
	
	@Override
	public Object getObject(String objectUuid, String objectType) {
		Criteria criteria = null;
		try {
			criteria = getSession().createCriteria(OpenmrsClassLoader.getInstance().loadClass(objectType));
		}
		catch (ClassNotFoundException e) {
			log.warn("Error in obtaining object of type" + objectType, e);
		}
		return criteria.add(Restrictions.eq("uuid", objectUuid)).uniqueResult();
	}
	
	@Override
	public List<Tag> getTags(List<String> objectTypes, List<String> tags) {
		Criteria criteria = getSession().createCriteria(Tag.class);
		if (objectTypes != null) {
			criteria.add(Restrictions.in("objectType", objectTypes));
		}
		criteria.add(Restrictions.in("tag", tags));
		return criteria.list();
	}
	
	@Override
	public Tag getTag(OpenmrsObject openmrsObject, String tag) {
		Criteria criteria = getSession().createCriteria(Tag.class);
		criteria.add(Restrictions.eq("objectType", openmrsObject.getClass().getName()));
		criteria.add(Restrictions.eq("objectUuid", openmrsObject.getUuid()));
		criteria.add(Restrictions.eq("tag", tag));
		return (Tag) criteria.uniqueResult();
	}
}
