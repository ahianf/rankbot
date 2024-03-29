/* (C)2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimited {
    int value() default 10; // Default rate limit value (requests per minute)
}
