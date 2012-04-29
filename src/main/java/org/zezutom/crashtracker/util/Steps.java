package org.zezutom.crashtracker.util;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 27/04/2012
 * Time: 08:16
 * Credit goes to Alex: http://www.lordofthejars.com/2011/04/are-you-locked-up-in-world-thats-been.html
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Steps {
}
