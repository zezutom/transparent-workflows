package org.zezutom.crashtracker.util;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 24/04/2012
 * Time: 08:34
 *
 */
public class AppUtil {

    private static final ThreadLocal<String> login = new ThreadLocal<String>();

    private AppUtil() {}

    public static void login(String username) {
        login.set(username);
    }

    public static String getUsername() {
        return login.get();
    }

    public static Date now() {
        return new Date();
    }

    public static Date hoursFromNow(int hours) {
        return DateUtils.addHours(now(), hours);
    }

}
