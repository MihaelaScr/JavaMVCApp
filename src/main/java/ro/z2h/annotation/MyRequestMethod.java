package ro.z2h.annotation;

import java.lang.annotation.*;

/**
 * Created by Miha on 11/11/2014.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMethod {
    String urlPath(); //bucatica mai mica din controller(url-ul fiecarei metode)
    String methodType() default "GET"; //defineste care metoda http sa apeleze
}
