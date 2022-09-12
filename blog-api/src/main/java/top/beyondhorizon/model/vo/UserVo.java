package top.beyondhorizon.model.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserVo
 * date: 2022/5/31 21:07
 *
 * @author ayanamirei
 */

@Data
public class UserVo implements Serializable
{
    private String id;
    private String username;
    private String headPortrait;
}
