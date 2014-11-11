package ro.z2h.annotation;

import java.lang.annotation.*;

/**
 * Created by Miha on 11/11/2014.
 */
@Target({ElementType.TYPE})//defineste bucata de obiect pe care pot sa o aplic
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String urlPath(); //urlPath va anunta DispatcherServletul-ul ca application controllerul poate prelucra un anumit requestPath primit de la client
}
