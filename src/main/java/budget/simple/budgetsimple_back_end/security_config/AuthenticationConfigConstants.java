package budget.simple.budgetsimple_back_end.security_config;

public class AuthenticationConfigConstants {
    public static final String SECRET = "Java_to_Dev_Secret";
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String SIGN_UP_URL = "/user/createUser";
    public static final String AUTH_COOKIE = "auth";
}
