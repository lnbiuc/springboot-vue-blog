package top.beyondhorizon.utils;


/**
 * ClassName: VerificationCode
 * date: 2022/6/1 15:37
 *
 * @author ayanamirei
 */


public class VerificationCode
{
    public static String getCode()
    {
        return Math.round((Math.random() * 9 + 1) * 100000) + "";
    }
    
}
