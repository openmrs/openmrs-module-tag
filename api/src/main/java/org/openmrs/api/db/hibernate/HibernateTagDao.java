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
import org.apache.regexp.RE;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.TagDAO;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tagDAO")
public class HibernateTagDao implements TagDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public DbSessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
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
	public List<Tag> getAllTags() {
		return getSession().createCriteria(Tag.class).list();
	}
	
	@Override
	public Tag getTagById(int id) throws DAOException {
		Criteria criteria = getSession().createCriteria(Tag.class);
		criteria.add(Restrictions.eq("id", id));
		return (Tag) criteria.uniqueResult();
	}
	
	@Override
	public List<Tag> getTagByName(String tag) throws DAOException {
		Criteria criteria = getSession().createCriteria(Tag.class);
		return criteria.add(Restrictions.eq("tag", tag)).list();
	}
	
	@Override
	public List<Tag> getTags(OpenmrsObject openmrsObject) throws DAOException {
		Criteria criteria = getSession().createCriteria(Tag.class);
		return criteria.add(
		    Restrictions.and(Restrictions.eq("object_type", openmrsObject.getClass().toString().substring(6)),
		        Restrictions.eq("object_uuid", openmrsObject.getUuid()))).list();
	}
	
	@Override
	public Object object_exists(String object_uuid, String object_type) throws APIException, ClassNotFoundException {
		Criteria criteria = getSession().createCriteria(Class.forName(object_type));
		return criteria.add(Restrictions.eq("uuid", object_uuid)).uniqueResult();
	}
	
	@Override
	public List<Tag> getTags(List<String> object_types, List<String> tags) throws DAOException, ClassNotFoundException {
		Criteria criteria = getSession().createCriteria(Tag.class);
		return criteria.add(Restrictions.and(Restrictions.in("object_type", object_types), Restrictions.in("tag", tags)))
		        .list();
	}
	
	@Override
	public List<Tag> getTags(String object_type, String tag) throws Exception {
		Criteria criteria = getSession().createCriteria(Tag.class);
		return criteria.add(Restrictions.eq("object_type", object_type)).add(Restrictions.eq("tag", tag)).list();
	}
}
