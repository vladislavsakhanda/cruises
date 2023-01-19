package com.my.classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class Profile {
    public String execute() {
        if (ServletActionContext.getRequest().getSession().getAttribute("userEmail") != null) {
            return "success";
        } else {
            return "error";
        }
    }
}