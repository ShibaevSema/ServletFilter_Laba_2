import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class SessionFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("#filter date: " + new Date());

       HttpServletRequest request = (HttpServletRequest) servletRequest;
       HttpServletResponse response = (HttpServletResponse) servletResponse;
       HttpSession session = request.getSession(false);
       String loginURI = request.getContextPath() + "/authorization";
       boolean loggedIn = session != null && session.getAttribute("userName") != null ;
       boolean loginRequest = request.getRequestURI().equals(loginURI);
          if (loggedIn || loginRequest) {
            filterChain.doFilter(servletRequest,servletResponse);
          } else {
              request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
          }
    }


}