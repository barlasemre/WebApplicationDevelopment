package com.bau.blog;

import com.bau.blog.dao.UserDao;
import com.bau.blog.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/girisyap")
    public String index(){
        return "login";
    }

    @RequestMapping(path = "/girisyap", method= RequestMethod.POST)
    public ModelAndView loginUser(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  HttpSession session){
        ModelAndView mav = null;

        if( isForDataValid(username, password) ){
            User user = userDao.getUser(username);
            if( user.getPassword().equals(password) ){
                mav = new ModelAndView(new RedirectView("/"));
                session.setAttribute("user", user);
            }
        }

        if( mav == null ) {
            mav = new ModelAndView(new RedirectView("/girisyap"));
            mav.addObject("error", true);
        }

        return mav;
    }

    @RequestMapping("/cikis")
    public ModelAndView logoutUser(HttpSession session){
        session.removeAttribute("user");

        return new ModelAndView(new RedirectView("/"));
    }

    private boolean isForDataValid(String username, String password) {
        return StringUtils.isNoneBlank(username, password);
    }
}
