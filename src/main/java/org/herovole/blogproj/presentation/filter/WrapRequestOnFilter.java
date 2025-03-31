package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
@Order(1)
public class WrapRequestOnFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(WrapRequestOnFilter.class.getSimpleName());

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        itsLogger.info("doFilterInternal");
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        //-----------------------------------------------------
        // Get all header names
        Enumeration<String> headerNames2 = wrappedRequest.getHeaderNames();

        // Iterate over header names and print each header's name and value
        while (headerNames2.hasMoreElements()) {
            String headerName = headerNames2.nextElement();
            String headerValue = request.getHeader(headerName);
            itsLogger.info("attempt 2 " + headerName + ": " + headerValue);
        }
        //-----------------------------------------------------

        // Obtain internal cache
        wrappedRequest.getParameterMap();

        AppServletRequest servletRequest = AppServletRequest.of(wrappedRequest);

        // Initialize Admin User
        servletRequest.storeAdminUserToAttribute(AdminUser.empty());
        // Initialize User Id
        servletRequest.storeUserIdToAttribute(IntegerPublicUserId.empty());
        filterChain.doFilter(wrappedRequest, response);
    }

    public static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
        private final String body;

        public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            if (request.getContentType() != null
                    && request.getContentType().startsWith("multipart/")) {
                body = "";
            } else {
                body = request.getReader().lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new StringReader(body));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }


}

