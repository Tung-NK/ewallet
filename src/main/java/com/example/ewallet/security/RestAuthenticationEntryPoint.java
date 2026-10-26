package com.example.ewallet.security;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Trả response 401 theo format {@link ApiResponse} khi request
 * KHÔNG có token (khác với trường hợp có token nhưng sai)
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public RestAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(ErrorCode.AUTH_TOKEN_INVALID.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Void> body = ApiResponse.error(
                ErrorCode.AUTH_TOKEN_INVALID.getDefaultMessage(),
                ErrorCode.AUTH_TOKEN_INVALID.name());

        objectMapper.writeValue(response.getWriter(), body);
    }
}
