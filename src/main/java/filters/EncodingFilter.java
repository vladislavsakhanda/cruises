package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName="EncodingFilter", urlPatterns="/url1/*")
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

        request.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        filterChain.doFilter(request, response);
    }

    public void destroy() {
        // nothing todo
    }

}