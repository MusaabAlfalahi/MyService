package net.shahid.microservices.myservice.domain.entity;

public class UserContext {
    private static final ThreadLocal<String> USERCONTEXT = new ThreadLocal<>();

    public static void setUserLanguage(String language) {
        USERCONTEXT.set(language);
    }

    public static String getUserLanguage() {
        return USERCONTEXT.get();
    }

    public static void clear() {
        USERCONTEXT.remove();
    }
}
