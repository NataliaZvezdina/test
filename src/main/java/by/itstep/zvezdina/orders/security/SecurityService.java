package by.itstep.zvezdina.orders.security;

public class SecurityService {

    private static boolean loggedIn = false;
    private static Integer userId;

    public static void logIn(Integer id) {
        loggedIn = true;
        userId = id;
    }

    public static void logOut() {
        loggedIn = false;
        userId = null;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static Integer getUserId() {
        return userId;
    }
}
