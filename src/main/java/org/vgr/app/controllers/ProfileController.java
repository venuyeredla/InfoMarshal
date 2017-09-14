package org.vgr.app.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.vgr.app.domain.Image;
import org.vgr.app.domain.Profile;
import org.vgr.app.service.ProfileService;
import org.vgr.http.server.HttpRequest;
import org.vgr.http.server.HttpResponse;
import org.vgr.http.server.HttpSession;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Handler;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.web.FormReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(id = "profileController")
public class ProfileController {
	private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

	@Inject(ref = "profileService")
	private ProfileService profileService = null;

	@Handler(path = "/newprofile.htm")
	public String create(HttpRequest servletRequest,	HttpResponse servletResponse) {
		try {
			FormReader<Profile> dataReader = new FormReader<Profile>();
			//dataReader.getFormData(new Profile(), servletRequest);
			dataReader = null;
			Profile profile = new Profile();
			profile.setFirstName(servletRequest.getParameter("fname"));
			profile.setLastName(servletRequest.getParameter("lname"));
			profile.setEmail(servletRequest.getParameter("email"));
			profile.setPassword(servletRequest.getParameter("password"));
			String cpwd = servletRequest.getParameter("password");
			String date = servletRequest.getParameter("date");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
			Date dob = new Date(formatter.parse(date).getTime());
			profile.setDob(dob);

			String gender = servletRequest.getParameter("gender");
			int genderNbr = gender.equals("Male") ? 1 : 2;
			profile.setGenderNbr(genderNbr);

			// profileService.createProfile(profile);
			HttpSession session = servletRequest.getSession(true);
			session.setAttribute("loginUser", profile);
			session.setAttribute("valid", true);

		} catch (Exception e) {
			e.printStackTrace();
			return "index";
		}
		return "redirect:user.htm";
	}

	@Handler(path = "/profile.htm")
	public String getProfile(HttpRequest servletRequest,HttpResponse servletResponse) {
		return "profile";
	}

	@Handler(path = "/images.htm")
	public String getImageList(HttpRequest servletRequest,HttpResponse servletResponse) {
		HttpSession sess = servletRequest.getSession(false);
		Profile profile = (Profile) sess.getAttribute("loginUser");
		List<Image> images = profileService.getImages(profile.getUserId());
		for (Image image : images) {
			image.setPath("static/" + profile.getUserId() + "/"
					+ image.getPath());
		}
		StringBuilder imageString = new StringBuilder();
		imageString.append("{\"images\":[");
		for (Image img : images) {
			imageString.append("{\"nbr\":\"" + img.getNbr() + "\" ,\"head\":\""
					+ img.getHeader() + "\",\"path\":\"" + img.getPath()
					+ "\", \"desc\":\"" + img.getDesc() + "\"},");
		}
		imageString.append("]}");
		int len = imageString.length() - 3;
		imageString.replace(len, len + 3, "]}");

		servletRequest.setAttribute("imageString", imageString);
		return "imagejson";
	}

	@Handler(path = "/vmail.htm")
	public String validateMail(HttpRequest servletRequest,
			HttpResponse servletResponse) {
		int size = profileService.isValidMail(servletRequest
				.getParameter("mail"));
		StringBuilder str = new StringBuilder();
		str.append("{\"valid\" :\"");
		if (size == 0)
			str.append("true");
		else
			str.append("false");
		str.append("\"}");
		servletRequest.setAttribute("jsonString", str);
		return "json";
	}

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
}
