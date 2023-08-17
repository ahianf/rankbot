package cl.ahian.rankbot.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubdomainRequestMatcher implements RequestMatcher {

    private static final Pattern SUBDOMAIN_PATTERN = Pattern.compile("^https?://([^/]+)");

    private final String subdomain;

    public SubdomainRequestMatcher(String subdomain) {
        this.subdomain = subdomain.toLowerCase(); // Normalize subdomain to lowercase
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String originHeader = request.getHeader("Origin");
        if (originHeader != null) {
            String originSubdomain = extractSubdomain(originHeader);
            return originSubdomain != null && originSubdomain.equals(subdomain);
        }
        return false;
    }

    private String extractSubdomain(String originHeader) {
        System.out.println("originHeader " + originHeader);
        Matcher matcher = SUBDOMAIN_PATTERN.matcher(originHeader);
        if (matcher.find()) {
            String group = matcher.group(1);
            System.out.println("group " + group);
            return group;
        }
        return null;
    }
}


