package com.nyimbozakristo.songs;

import java.util.List;
import javax.persistence.Column;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nelson
 * Data access object for table songs that allows for deleting,updating and adding a song to the database
 */
@Repository
@Transactional
public class SongsDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Songs getById(int id) {
		return (Songs) sessionFactory.getCurrentSession().get(Songs.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Songs> searchSongsByTitle(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Songs.class);
		criteria.add(Restrictions.ilike("title", name + "%"));
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<Songs> searchSongsNumber(Integer number) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Songs.class);
		criteria.add(Expression.eq("id", number));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Songs getSongsBySongTitle(String title) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Songs.class);
		criteria.add(Expression.eq("title", title));

		@SuppressWarnings("unchecked")
		List<Songs> doses = criteria.list();
		if (null == doses || doses.isEmpty()) {
			return null;
		}
		return doses.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Songs> getAllSongs() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Songs.class);
		return criteria.list();
	}

	public int saveSongs(Songs contact) {
		return (Integer) sessionFactory.getCurrentSession().save(contact);
	}

	public void updateSong(Songs contact) {
		sessionFactory.getCurrentSession().merge(contact);
	}

	public void deleteSong(int id) {
		Songs c = getById(id);
		sessionFactory.getCurrentSession().delete(c);
	}

}
