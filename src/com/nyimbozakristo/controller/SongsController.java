package com.nyimbozakristo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.nyimbozakristo.songs.Songs;
import com.nyimbozakristo.songs.SongsDAO;
import com.nyimbozakristo.users.UsersDAO;
import com.nyimbozakristo.users.roles.UserRolesDAO;

/**
 * @author nelson
 * 
 *         Songs controller used to manage the access of songs- save,search and
 *         view
 */
@Controller
public class SongsController {

	@Autowired
	private SongsDAO songsDAO;
	private JSONArray songsPayload;
	private JSONObject songsMobilePayload;

	@RequestMapping(value = "/saveSongs.do", method = RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");//
		String songLyrics = request.getParameter("song");//
		String audio = request.getParameter("audio");
		String video = request.getParameter("video");
		String id = request.getParameter("id");
		if (!id.isEmpty()) {
			Songs song = songsDAO.getById(Integer.parseInt(id));
			song.setTitle(title);
			song.setSong(songLyrics);
			song.setAudio(audio);
			song.setVideo(video);
			songsDAO.updateSong(song);
		} else {
			Songs songs = songsDAO.getSongsBySongTitle(title);
			if (songs != null) {
				try {
					response.getWriter().print("found");
					response.flushBuffer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Songs song = new Songs();
				song.setTitle(title);
				song.setSong(songLyrics);
				song.setAudio(audio);
				song.setVideo(video);
				songsDAO.saveSongs(song);
			}
		}
	}

	@RequestMapping("/getSongs.do")
	public void getAllContacts(HttpServletRequest request,
			HttpServletResponse response) {
		List<Songs> songs = songsDAO.getAllSongs();
		String sSearch = request.getParameter("sSearch");//
		JSONObject json = new JSONObject();
		JSONArray jsonPayload = new JSONArray();
		List<Songs> songsList = null;
		int size = songs.size();
		try {
			if (size != 0) {
				if (!sSearch.isEmpty()) {
					if (StringUtils.isNumeric(sSearch)) {
						songsList = songsDAO.searchSongsNumber(Integer
								.parseInt(sSearch));
					} else {
						songsList = songsDAO.searchSongsByTitle(sSearch);
					}
					for (int i = 0; i < songsList.size(); i++) {
						jsonPayload.put(getMobileSearchPayload(songsList, i));
					}
					json.put("payload", jsonPayload);
				} else {
					for (int i = 0; i < size; i++) {
						json.accumulate("aaData", getArraySearch(songs, i));
					}
					json.accumulate("iTotalRecords", size);
					json.accumulate("iTotalDisplayRecords", size);
					if (!json.has("aaData")) {
						JSONArray datad2 = new JSONArray();
						datad2.put("None");
						datad2.put("None");
						datad2.put("None");
						datad2.put("None");
						datad2.put("None");
						datad2.put("None");
						datad2.put("None");
						json.accumulate("aaData", datad2);
					}
				}
			}
			response.getWriter().print(json);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/deleteSongs.do")
	public void deleteLocation(HttpServletRequest request,
			HttpServletResponse response) {
		String delete = request.getParameter("id");//
		songsDAO.deleteSong(Integer.parseInt(delete));
		try {
			response.getWriter().print("found");
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String maskReplace(String x, int n) {
		String output = "";
		for (int i = 0; i < n; i++) {
			output += "*";
		}
		output += x.substring(n);
		return output;
	}

	public synchronized JSONArray getArraySearch(List<Songs> songs, int size) {

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

	public synchronized JSONObject getMobileSearchPayload(List<Songs> songs,
			int size) {

		songsMobilePayload = new JSONObject();
		try {
			songsMobilePayload.put("no", songs.get(size).getId());
			songsMobilePayload.put("title", songs.get(size).getTitle());
			songsMobilePayload.put("song", songs.get(size).getSong());
			songsMobilePayload.put("audio", songs.get(size).getAudio());
			songsMobilePayload.put("video", songs.get(size).getVideo());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return songsMobilePayload;
	}

}