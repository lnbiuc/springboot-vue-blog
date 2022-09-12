package top.beyondhorizon.model.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: RetMsg
 * date: 2022/5/30 18:04
 *
 * @author ayanamirei
 */

@Data
public class RetMsg implements Serializable
{
    private Integer code;
    private String msg;
    private Object data;
    
    public static RetMsg success(Object data)
    {
        RetMsg retMsg = new RetMsg();
        retMsg.setCode(200);
        retMsg.setMsg("success");
        retMsg.setData(data);
        return retMsg;
    }
    
    public static RetMsg fail(Integer code, String msg)
    {
        RetMsg retMsg = new RetMsg();
        retMsg.setCode(code);
        retMsg.setMsg(msg);
        return retMsg;
    }
    
    public static RetMsg error(String msg)
    {
        RetMsg retMsg = new RetMsg();
        retMsg.setCode(500);
        retMsg.setMsg(msg);
        retMsg.setData("系统错误，请练习管理员");
        return retMsg;
    }
}
