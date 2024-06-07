package org.jap.core.filter;


import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.jap.api.dto.ErrorResponse;
import org.jap.api.dto.ValidationError;
import org.jap.core.annotation.Secured;
import org.jap.core.util.JWTUtil;
import org.jap.core.util.UtilConstants;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Secured
@Provider
@ApplicationScoped
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {



    @Context
    private ResourceInfo resourceInfo;
    @Context
    private SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Skip authentication for public endpoints

        Method method = resourceInfo.getResourceMethod();
        if (method == null || !method.isAnnotationPresent(Secured.class)) {
            System.out.println("sale filtro");
            return;
        }

        String authorizationHeader = requestContext.getHeaderString(UtilConstants.AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(UtilConstants.BEARER_PREFIX)) {
            System.out.println("No se encuentra los token ");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","",List.of())).build());
            return;
        }

        String token = authorizationHeader.substring(UtilConstants.BEARER_PREFIX.length());

        try {
            String username = JWTUtil.getUsernameFromToken(token);
            String csrfToken = requestContext.getHeaderString(UtilConstants.CSRF_HEADER);
            String tokenCsrfToken = JWTUtil.getCSRFTokenFromToken(token);
            if (!csrfToken.equals(tokenCsrfToken)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(new ErrorResponse("Invalid CSRF Token","",List.of())).build());
                return;
            }

            if (JWTUtil.isTokenExpired(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("Token Expired","", List.of())).build());
                return;
            }

            final SecurityContext currentSecurityContext = securityContext;
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return currentSecurityContext.isUserInRole(role);
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return currentSecurityContext.getAuthenticationScheme();
                }
            });
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("Invalid Token","",List.of())).build());
        }
    }

}
