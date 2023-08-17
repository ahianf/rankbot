package cl.ahianf.rankbot.config.interceptor;

import cl.ahianf.rankbot.config.annotation.RateLimited;
import cl.ahianf.rankbot.rest.RankbotController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.*;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final ConcurrentMap<String, Integer> requestCounts;
    private ScheduledExecutorService scheduler;
    private final Logger logger = LoggerFactory.getLogger(RateLimitingInterceptor.class);

    public RateLimitingInterceptor() {
        requestCounts = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduleResetTask(15);

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RateLimited rateLimited = handlerMethod.getMethod().getAnnotation(RateLimited.class);
            if (rateLimited != null) {
                int limit = rateLimited.value();
                String clientKey = getClientKey(request); // Replace with your logic to get the client key
                int count = requestCounts.getOrDefault(clientKey, 0);
                if (count >= limit) {
                    response.setStatus(429);
                    response.getWriter().write("Rate limit exceeded. Please try again later.");
                    logger.info("Rate limiting happened: IP " + clientKey);
                    return false;
                }
                count++;
                requestCounts.put(clientKey, count);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // No action needed
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RateLimited rateLimited = handlerMethod.getMethod().getAnnotation(RateLimited.class);
            if (rateLimited != null) {
                String clientKey = getClientKey(request); // Replace with your logic to get the client key
                int count = requestCounts.getOrDefault(clientKey, 0);
//                if (count > 0) {
//                    requestCounts.put(clientKey, count - 1);
//                }
            }
        }
    }

    private String getClientKey(HttpServletRequest request) {
        // Implement your logic to get the client key (e.g., client IP address, user identifier)
        return request.getRemoteAddr(); // Return the client key
    }

    private void scheduleResetTask(long resetInterval) {
        scheduler.scheduleAtFixedRate(requestCounts::clear, resetInterval, resetInterval, TimeUnit.SECONDS);
    }
}
