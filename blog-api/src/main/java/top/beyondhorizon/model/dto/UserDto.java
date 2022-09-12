package top.beyondhorizon.model.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserDto
 * date: 2022/6/1 11:53
 *
 * @author ayanamirei
 */

@Data
public class UserDto implements Serializable
{
    private String id;
    private String username;
    private String password;
}
