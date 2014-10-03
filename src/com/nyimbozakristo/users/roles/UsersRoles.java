package com.nyimbozakristo.users.roles;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Nelson
 *Used to map user_roles table for ease of accessing and usage 
 */

@Entity
@Table(name="user_roles")
public class UsersRoles
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int USER_ROLE_ID;
	@Column	private int USER_ID;
	@Column	private String AUTHORITY;


	
	public UsersRoles()
	{
	}
	
	public UsersRoles(int id, int userid,String authority)
	{
		super();
		this.USER_ROLE_ID = id;
		this.USER_ID = userid;
		this.AUTHORITY = authority;
		
		
	}
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	public int getId()
	{
		return USER_ROLE_ID;
	}
	public void setId(int id)
	{
		this.USER_ROLE_ID = id;
	}
	public int getUser_id()
	{
		return USER_ID;
	}
	public void setUser_id(int user_id)
	{
		this.USER_ID = user_id;
	}
	
	
	
	public String getAuthority()
	{
		return AUTHORITY;
	}
	public void setAuthority(String authority)
	{
		this.AUTHORITY = authority;
	}
	
	
	
}
