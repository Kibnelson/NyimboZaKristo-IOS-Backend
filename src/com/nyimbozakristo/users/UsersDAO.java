package com.nyimbozakristo.users;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nelson
 *
 *Data access object for table users that allows for deleting,updating and adding a user to the database
 */
@Repository
@Transactional

public class UsersDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public Users getById(int id)
	{
		return (Users) sessionFactory.getCurrentSession().get(Users.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> searchContacts(String name)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.ilike("USERNAME", name+"%"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Users getUser(String name)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Expression.eq("USERNAME", name));
	
      @SuppressWarnings("unchecked")
      List<Users> medicationCategory = criteria.list();
      if (null == medicationCategory || medicationCategory.isEmpty()) {
          return null;
      }
      return medicationCategory.get(0);
}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Users> getAllUsers()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		return criteria.list();
	}
	
	public int save(Users contact)
	{
		return (Integer) sessionFactory.getCurrentSession().save(contact);
	}
	
	public void update(Users contact)
	{
		sessionFactory.getCurrentSession().merge(contact);
	}
	
	public void delete(int id)
	{
		Users c = getById(id);
		sessionFactory.getCurrentSession().delete(c);
	}



}
