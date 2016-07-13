package com.pb.tmaWeb.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pb.tmaWeb.Model.User;

@Controller
public class Services {
	UserDao userDao = new UserDao();
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
    public List<User> listUsers() {
	   return userDao.userList();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password){
		if(userDao.checkLogin(username, password)){
			request.getSession().setAttribute("username", username);
			return "Login successful!";
		}
		return "Username or password incorrect!";
	}
}
