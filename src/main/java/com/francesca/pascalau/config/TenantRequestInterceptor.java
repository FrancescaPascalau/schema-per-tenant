package com.francesca.pascalau.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * To start, we’ll need to determine which tenant is making requests.
 * We will use Spring Interceptor to intercept the HTTP request and get the tenant information from the header.
 * The selected tenant is then stored in a ThreadLocal variable that is cleared after the request is completed.
 * The interceptor gets the value of the "X-TenantID" HTTP header for every request and sets the current
 * tenant inTenantContext class. If no header is provided, it responds with an error.
 */
@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object) throws Exception {
        String tenantID = request.getHeader("X-TenantID");
        System.out.println("Searching for X-TenantID: " + tenantID);

        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantID);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        TenantContext.clear();
    }
}