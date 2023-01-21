package com.my.filters;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding = "utf-8";

    public void init(FilterConfig filterConfig)
            throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");

        if (encoding == null)
            encoding = "UTF-8";
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("\nEncodingFilter#" + encoding);

        request.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        filterChain.doFilter(request, response);
    }

    public void destroy() {
        // nothing todo
    }

}