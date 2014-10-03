package com.nyimbozakristo.users;

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
 *
 *Used to map users table for ease of accessing and usage e
 */

@Entity
@Table(name="users")
public class Users
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int USER_ID;
	@Column	private String USERNAME;
	@Column	private String PASSWORD;
	@Column	private boolean ENABLED;


	
	public Users()
	{
	}
	
	public Users(int USER_ID, String USERNAME,String PASSWORD,boolean ENABLED)
	{
		super();
		this.USER_ID = USER_ID;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
		this.ENABLED=ENABLED;
		
	}
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	public int getUSER_ID()
	{
		return USER_ID;
	}
	public void setUSER_ID(int id)
	{
		this.USER_ID = id;
	}
	public String getUSERNAME()
	{
		return USERNAME;
	}
	public void setUSERNAME(String username)
	{
		this.USERNAME = username;
	}
	
	public String getPASSWORD()
	{
		return PASSWORD;
	}
	public void setPASSWORD(String password)
	{
		this.PASSWORD = password;
	}
	
	public boolean getENABLED()
	{
		return ENABLED;
	}
	public void setENABLED(boolean ENABLED)
	{
		this.ENABLED = ENABLED;
	}
	
	
	
}
