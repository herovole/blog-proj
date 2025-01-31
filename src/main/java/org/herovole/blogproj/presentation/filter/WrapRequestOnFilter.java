package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.stream.Collectors;

@Component
@Order(1)
public class WrapRequestOnFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(WrapRequestOnFilter.class.getSimpleName());

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        itsLogger.info("doFilterInternal");
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        wrappedRequest.getParameterMap();
        filterChain.doFilter(wrappedRequest, response);
    }

    public static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
        private final String body;

        public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            body = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
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

