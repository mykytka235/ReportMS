package mykytka235.ms.report.configuration.web.interceptor;

import mykytka235.ms.report.util.CorrelationIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static mykytka235.ms.report.util.CorrelationIdUtil.CORRELATION_ID_HEADER;

public class CorrelationIdWebInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (!StringUtils.isBlank(correlationId)) {
            CorrelationIdUtil.setId(correlationId);
        } else {
            CorrelationIdUtil.generateId();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        response.setHeader(CORRELATION_ID_HEADER, CorrelationIdUtil.getId());
    }

}
