package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns="/url3/*")
public class SessionLocaleFilter implements Filter {
    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("SessionLocaleFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getParameter("language") != null) {
            req.getSession().setAttribute("lang", req.getParameter("language"));
        } else if (req.getParameter("language") != null) {
            req.getSession().setAttribute("lang", req.getParameter("language"));
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }


}
