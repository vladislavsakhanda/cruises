package filters;

import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebFilter(filterName = "CheckRoleAdminFilter", urlPatterns = "/url2/*")
public class CheckRoleAdminFilter implements Filter {
    final private List<String> accessesForAdmin = Collections.unmodifiableList(new ArrayList<String>() {{
        add("RequestsCatalog");
    }});

    final private List<String> accessesForUsers = Collections.unmodifiableList(new ArrayList<String>() {{
        add("BookTour");
        add("CruisesCatalog");
        add("CruisesCatalogLiner");
    }});

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String currentRole = (String) req.getSession().getAttribute("role");

        if (accessesForAdmin.contains(request.getParameter("command"))) {
            if (currentRole != null && currentRole.equals("admin")) {
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect("/cruises?command=Home");
            }
        } else if (accessesForUsers.contains(request.getParameter("command"))) {
            if (currentRole == null) {
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect("/cruises?command=Home");
            }
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
