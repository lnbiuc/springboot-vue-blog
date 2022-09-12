package top.beyondhorizon.service;


import top.beyondhorizon.model.dto.UserDto;
import top.beyondhorizon.entity.User;
import top.beyondhorizon.model.vo.AuthorVo;
import top.beyondhorizon.model.vo.UserVo;

import java.util.Date;

/**
 * ClassName: UserService
 * date: 2022/5/30 21:05
 *
 * @author ayanamirei
 */


public interface UserService
{
    AuthorVo getAuthorByUserId(String userId);
    User getUserByUsernameReUser(String username);
    UserDto getUserByUsername(String username);
    String getSaltByUsername(String username);
    String getUserHead(String id);
    String getSaltById(String id);
    User getUserByTel(String tel);
    int saveUser(String uuid, String username, String encodePwd, String tel, String salt, Date date);
    boolean updateLastLogin(String id);
    UserVo getUSerVoById(String authorId);
}
