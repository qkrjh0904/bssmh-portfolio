package com.bssmh.portfolio.web.config.profile;

import org.springframework.core.env.Environment;

import java.util.stream.Stream;

public final class SpringProfiles {

    // 서버 종류
    public static final String TEST = "test";
    public static final String LOCAL = "local";
    public static final String DEVELOPMENT = "kr-development";
    public static final String PRODUCTION = "kr-production";

    /**
     * 활성화된 프로필인가?
     */
    public static boolean isActive(Environment env, String... targetProfiles) {
        return Stream.of(env.getActiveProfiles())
                .anyMatch(p -> {
                    for (String profile : targetProfiles) {
                        if (p.equals(profile)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    public static boolean isNotProduct(Environment env) {
        return isActive(env, LOCAL, DEVELOPMENT, TEST, PRODUCTION);
    }
}

