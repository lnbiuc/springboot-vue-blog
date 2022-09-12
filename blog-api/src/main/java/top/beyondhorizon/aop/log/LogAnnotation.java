package top.beyondhorizon.aop.log;


import java.lang.annotation.*;

/**
 * date: 2022/6/4 02:54<br/>
 *
 * @author ayanamirei<br />
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation
{

    String module() default "";

    String operation() default "";
}
