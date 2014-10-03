package com.nyimbozakristo.users.roles;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Nelson
 * Used to manage the process of adding,deleting and updating UserRoles in the database using hibernate
 * 
 * Its currently set to ROLE_USER as the default one
 *
 */
@Repository
@Transactional
public class UserRolesDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public UsersRoles getById(int id)
	{
		return (UsersRoles) sessionFactory.getCurrentSession().get(UsersRoles.class, id);
	}
	
	public UsersRoles getByUserId(int USER_ID)
	{
		return (UsersRoles) sessionFactory.getCurrentSession().get(UsersRoles.class, USER_ID);
	}
	
	@SuppressWarnings("unchecked")
	public UsersRoles searchContacts(int name)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UsersRoles.class);
		criteria.add(Expression.eq("USER_ID", name));
		
		@SuppressWarnings("unchecked")
		List<UsersRoles> doses = criteria.list();
		if (null == doses || doses.isEmpty()) {
			return null;
		}
		return doses.get(0);
	}
	
	public int save(UsersRoles contact)
	{
		return (Integer) sessionFactory.getCurrentSession().save(contact);
	}
	
	public void update(UsersRoles contact)
	{
		sessionFactory.getCurrentSession().merge(contact);
	}
	
	public void delete(int id)
	{
		UsersRoles c = getById(id);
		sessionFactory.getCurrentSession().delete(c);
	}

}
