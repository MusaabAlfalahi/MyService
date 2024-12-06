package net.shahid.microservices.myservice.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import net.shahid.microservices.myservice.domain.entity.UserContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String acceptLanguage = httpServletRequest.getHeader("Accept-Language");
        UserContext.setUserLanguage(acceptLanguage);
        try {
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.clear();
        }
    }
}
