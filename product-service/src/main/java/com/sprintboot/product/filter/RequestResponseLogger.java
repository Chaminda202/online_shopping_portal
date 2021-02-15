package com.sprintboot.product.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

@Component
@Slf4j
public class RequestResponseLogger implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CustomsHttpServletRequestWrapper customsHttpServletRequestWrapper = new CustomsHttpServletRequestWrapper((HttpServletRequest) request);
        log.info("Request URI: {}", customsHttpServletRequestWrapper.getRequestURI());
        log.info("Request Method: {}", customsHttpServletRequestWrapper.getMethod());
        log.info("Request Body: {}", new String(customsHttpServletRequestWrapper.getByteArray()).replaceAll("\n", " ").replaceAll("\\s+",""));

        CustomsHttpServletResponseWrapper customsHttpServletResponseWrapper = new CustomsHttpServletResponseWrapper((HttpServletResponse) response);
        chain.doFilter(customsHttpServletRequestWrapper, customsHttpServletResponseWrapper);

        log.info("Response Status: {}", customsHttpServletResponseWrapper.getStatus());
        log.info("Response Body: {}", new String(customsHttpServletResponseWrapper.getByteArrayOutputStream().toByteArray()));
    }

    private class CustomsHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private byte [] byteArray;
        public CustomsHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            try {
                this.byteArray = IOUtils.toByteArray(request.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Issue in reading request stream ", e);
            }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new CustomDelegatingServletInputStream(new ByteArrayInputStream(byteArray));
        }

        public byte[] getByteArray() {
            return byteArray;
        }
    }

    private class CustomsHttpServletResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        private PrintStream printStream = new PrintStream(byteArrayOutputStream);

        public CustomsHttpServletResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new CustomDelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), printStream));
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(new TeeOutputStream(super.getOutputStream(), printStream));
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return byteArrayOutputStream;
        }
    }
}
