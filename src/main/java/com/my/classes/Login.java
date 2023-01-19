package com.my.classes;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import db.dao.PBKDF2;
import db.dao.mysql.MySqlUserDAO;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpSession;

public class Login implements SessionAware {
    private String email;
    private String password;
    SessionMap<String, String> sessionmap;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String execute() {
        try {
            MySqlUserDAO.initDatabaseConnectionPool();
            if (PBKDF2.validatePassword(password, new MySqlUserDAO().read(email).getPassword())) {
                return "success";
            }
        } catch (Exception e) {
            return "error";
        } finally {
            MySqlUserDAO.closeDatabaseConnectionPool();
        }

        return "error";
    }

    public void setSession(Map map) {
        ServletActionContext.getRequest().getSession().setAttribute("m", "mmm");
        sessionmap = (SessionMap) map;
        sessionmap.put("login", "true");
    }

    public String logout() {
        sessionmap.invalidate();
        return "success";
    }

}