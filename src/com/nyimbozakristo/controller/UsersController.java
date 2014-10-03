package com.nyimbozakristo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nyimbozakristo.songs.Songs;
import com.nyimbozakristo.users.Users;
import com.nyimbozakristo.users.UsersDAO;
import com.nyimbozakristo.users.roles.UserRolesDAO;
import com.nyimbozakristo.users.roles.UsersRoles;

/**
 * @author nelson
 * 
 * 
 *         Users controller used to manage the access of users- save, delete and
 *         view
 */
@Controller
public class UsersController {

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private UserRolesDAO usersRolesDAO;
	private JSONArray userPayload, songsPayload;

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public void register(HttpServletRequest request,
			HttpServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("inputPassword");
		List<Users> contacts = usersDAO.getAllUsers();
		String found = null;
		String notfound = null;
		int size = contacts.size();

		if (size != 0) {
			for (int i = 0; i < size; i++) {

				if (contacts.get(i).getUSERNAME().equalsIgnoreCase(username)) {
					found = "yes";
				} else {
					notfound = "no";
				}
			}
		}
		if (found != null) {
			if (found.equalsIgnoreCase("yes")) {
				try {
					response.getWriter().print("found");
					response.flushBuffer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (notfound == null) {
			Save(username, password, response);
		} else if (notfound.equalsIgnoreCase("no") && found == null) {
			Save(username, password, response);
		}
	}

	public void Save(String username, String password,
			HttpServletResponse response) {
		Users users = new Users();
		users.setPASSWORD(encodePasswordWithBCrypt(password));
		users.setUSERNAME(username);
		users.setENABLED(true);
		usersDAO.save(users);
		UsersRoles usersRoles = new UsersRoles();
		usersRoles.setAuthority("ROLE_USER");
		usersRoles.setUser_id(users.getUSER_ID());
		usersRolesDAO.save(usersRoles);
		try {
			response.getWriter().print("saved");
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String encodePasswordWithBCrypt(String plainPassword) {
		return new BCryptPasswordEncoder().encode(plainPassword);
	}

	@RequestMapping("/users.do")
	public void getUsers(HttpServletRequest request,
			HttpServletResponse response) {
		List<Users> contacts = usersDAO.getAllUsers();
		String sSearch = request.getParameter("sSearch");
		JSONObject json = new JSONObject();
		int size = contacts.size();
		try {

			if (size != 0) {

				if (!sSearch.isEmpty()) {
					List<Users> co = usersDAO.searchContacts(sSearch);
					int si = co.size();
					for (int i = 0; i < si; i++) {
						json.accumulate("aaData", getUsers(co, i));
					}
				} else {
					for (int i = 0; i < size; i++) {
						json.accumulate("aaData", getUsers(contacts, i));
					}
				}
			}
			if (!json.has("aaData")) {
				JSONArray datad2 = new JSONArray();
				datad2.put("None");
				datad2.put("None");
				datad2.put("None");
				datad2.put("None");
				datad2.put("None");
				datad2.put("None");
				json.accumulate("aaData", datad2);
			}
			json.accumulate("iTotalRecords", size);
			json.accumulate("iTotalDisplayRecords", size);
			response.getWriter().print(json);
			response.flushBuffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	@RequestMapping(value = "/deleteUser.do", method = RequestMethod.POST)
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) {
		String sname = request.getParameter("id");
		Users users = usersDAO.getById(Integer.parseInt(sname));
		users.setENABLED(false);
		usersDAO.update(users);
		UsersRoles userRoles = usersRolesDAO.searchContacts(Integer
				.parseInt(sname));
		usersRolesDAO.delete(userRoles.getId());
		try {
			response.getWriter().print("found");
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized JSONArray getSongsArray(List<Songs> songs, int size) {
		songsPayload = new JSONArray();
		songsPayload.put("");
		songsPayload.put(songs.get(size).getId());
		songsPayload.put(songs.get(size).getTitle());
		songsPayload.put(songs.get(size).getSong());
		songsPayload.put(songs.get(size).getAudio());
		songsPayload.put(songs.get(size).getVideo());
		songsPayload.put("");

		return songsPayload;
	}

	public synchronized JSONArray getUsers(List<Users> user, int size) {
		userPayload = new JSONArray();
		userPayload.put("");
		userPayload.put(user.get(size).getUSER_ID());
		userPayload.put(user.get(size).getUSERNAME());
		userPayload.put(user.get(size).getENABLED());
		if (user.get(size).getENABLED()) {
			UsersRoles contacts = usersRolesDAO.searchContacts(user.get(size)
					.getUSER_ID());
			userPayload.put(contacts.getAuthority());
		} else
			userPayload.put("");
		userPayload.put("");
		return userPayload;
	}
}