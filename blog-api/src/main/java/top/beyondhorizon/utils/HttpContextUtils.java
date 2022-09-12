package top.beyondhorizon.utils;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * ClassName: HttpContextUtils
 * date: 2022/6/4 02:59
 *
 * @author ayanamirei
 */


public class HttpContextUtils
{
    public static HttpServletRequest getHttpServletRequest()
    {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
