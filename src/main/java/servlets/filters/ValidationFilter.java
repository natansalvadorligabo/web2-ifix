package servlets.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/home","/createCustomer", "/createServiceOrder",
        "/home.jsp", "/customer.jsp", "/serviceOrder.jsp"},
        filterName = "Authorization")
public class ValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.sendRedirect(httpRequest.getContextPath()+"/login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }
}
