package com.my.classes;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionSupport;
import db.dao.PBKDF2;
import db.dao.mysql.MySqlUserDAO;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpSession;

import db.dao.mysql.entity.User;

public class Login extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private User userBean;
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";


    public String execute() {
        try {
            MySqlUserDAO.initDatabaseConnectionPool();
            User user = new MySqlUserDAO().read(userBean.getEmail());

            if (user != null && PBKDF2.validatePassword(userBean.getPassword(), user.getPassword())) {
                ServletActionContext.getRequest().getSession().setAttribute("userName", user.getName());
                ServletActionContext.getRequest().getSession().setAttribute("userSurname", user.getSurname());
                ServletActionContext.getRequest().getSession().setAttribute("userEmail", user.getEmail());
                return "success";
            }
        } catch (Exception e) {
            return "error";
        } finally {
            MySqlUserDAO.closeDatabaseConnectionPool();
        }

        return "error";
    }

    public void validate() {
        if (userBean == null) {
            return;
        }

        if (userBean.getEmail().length() == 0) {
            addFieldError("userBean.email", "Email is required.");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(userBean.getEmail()).matches()) {
            addFieldError("userBean.email", "Email format is invalid.");
        }

        if (userBean.getPassword().length() == 0) {
            addFieldError("userBean.password", "Password is required.");
        }
    }

    public String logout() {
        ServletActionContext.getRequest().getSession().invalidate();
        return "success";
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User user) {
        userBean = user;
    }

}