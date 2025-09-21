package me.baero.core.app;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import me.baero.core.annotation.AppVersion;
import me.baero.core.exception.InfraErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AppVersionArgResolver implements HandlerMethodArgumentResolver {
    private static final Pattern APP_PATTERN = Pattern.compile("MARCHEN/(\\d+\\.\\d+\\.\\d+)");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AppVersion.class)
                && Version.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        return Optional.of(webRequest)
                .map(it -> it.getNativeRequest(HttpServletRequest.class))
                .map(it -> it.getHeader("User-Agent"))
                .map(this::parseAppVersionFromUserAgent)
                .orElse(null);
    }

    private Version parseAppVersionFromUserAgent(String userAgent) {
        if (StringUtils.isEmpty(userAgent)) {
            throw InfraErrorCode.PARSING_VERSION_FAILED.toException();
        }

        Matcher appPatternMatcher = APP_PATTERN.matcher(userAgent);
        if (appPatternMatcher.find()) {
            String version = appPatternMatcher.group(1);
            return Version.parse(version);
        }

        throw InfraErrorCode.PARSING_VERSION_FAILED.toException();
    }
}
