package com.my.classes;

import com.opensymphony.xwork2.ActionSupport;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.User;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private User userBean;
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    private static final String REGEX_NAME = "^[a-zA-ZА-Яа-яіІЇїєЄ]{3,}";

    public String execute() throws Exception {
        if (userBean == null) {
            return INPUT;
        }

        try {
            MySqlUserDAO.initDatabaseConnectionPool();
            new MySqlDAOFactory().getUserDao().create(userBean);
        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR;
        } finally {
            MySqlUserDAO.closeDatabaseConnectionPool();
        }

        return SUCCESS;
    }

    public void validate() {
        if (userBean == null) {
            return;
        }

        if (userBean.getName().length() == 0) {
            addFieldError("userBean.name", "First name is required.");
        } else if (userBean.getName().length() < 3) {
            addFieldError("userBean.name", "First name must contain at least 3 characters.");
        } else if (!Pattern.compile(REGEX_NAME).matcher(userBean.getName()).matches()) {
            addFieldError("userBean.name", "First name is incorrect.");
        }

        if (userBean.getSurname().length() == 0) {
            addFieldError("userBean.surname", "Last name is required.");
        } else if (userBean.getSurname().length() < 3) {
            addFieldError("userBean.surname", "Last name must contain at least 3 characters.");
        } else if (!Pattern.compile(REGEX_NAME).matcher(userBean.getSurname()).matches()) {
            addFieldError("userBean.surname", "Last name is incorrect.");
        }

        if (userBean.getEmail().length() == 0) {
            addFieldError("userBean.email", "Email is required.");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(userBean.getEmail()).matches()) {
            addFieldError("userBean.email", "Email format is invalid.");
        } else if (userExist()) {
            addFieldError("userBean.email", "This email already exists.");
        }

        if (userBean.getPassword().length() == 0) {
            addFieldError("userBean.password", "Password is required.");
        } else if (userBean.getPassword().length() < 6 || userBean.getPassword().length() > 18) {
            addFieldError("userBean.password", "The password must contain at least 6 characters and no more than 18.");
        }

    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User user) {
        userBean = user;
    }

    private boolean userExist() {
        try {
            MySqlUserDAO.initDatabaseConnectionPool();
            new MySqlUserDAO().read(userBean.getEmail());
        } catch (SQLException e) {
            return false;
        } finally {
            MySqlUserDAO.closeDatabaseConnectionPool();
        }
        return true;
    }

}