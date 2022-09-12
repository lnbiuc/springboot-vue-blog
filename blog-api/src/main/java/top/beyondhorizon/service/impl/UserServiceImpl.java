package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.beyondhorizon.model.dto.UserDto;
import top.beyondhorizon.entity.User;
import top.beyondhorizon.model.vo.AuthorVo;
import top.beyondhorizon.model.vo.UserVo;
import top.beyondhorizon.mapper.UserMapper;
import top.beyondhorizon.service.UserService;

import java.util.Date;

/**
 * ClassName: UserServiceImpl
 * date: 2022/5/30 21:05
 *
 * @author ayanamirei
 */


@Service
public class UserServiceImpl implements UserService
{
    private final UserMapper userMapper;
    
    public UserServiceImpl(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }
    
    @Override
    public User getUserByUsernameReUser(String username)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username)
                .select(User::getId)
                .last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }
    
    @Override
    public AuthorVo getAuthorByUserId(String userId)
    {
        User user = userMapper.getUserByUserId(userId);
        AuthorVo authorVo = new AuthorVo();
        BeanUtils.copyProperties(user, authorVo);
        return authorVo;
    }
    
    @Override
    public UserDto getUserByUsername(String username)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username)
                .select(User::getId, User::getUsername, User::getPassword)
                .last("limit 1");
        User user = userMapper.selectOne(queryWrapper);
        if (user == null)
        {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
    
    @Override
    public String getSaltByUsername(String username)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username).
                select(User::getSalt).
                last("limit 1");
        return userMapper.selectOne(queryWrapper).getSalt();
    }
    
    @Override
    public String getUserHead(String id)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id).
                select(User::getHeadPortrait).
                last("limit 1");
        return userMapper.selectOne(queryWrapper).getHeadPortrait();
    }
    
    @Override
    public String getSaltById(String id)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id).
                select(User::getHeadPortrait).
                last("limit 1");
        return userMapper.selectOne(queryWrapper).getSalt();
    }
    
    @Override
    public User getUserByTel(String tel)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getTel, tel).
                select(User::getId).
                last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }
    
    @Override
    public int saveUser(String uuid, String username, String encodePwd, String tel, String salt, Date date)
    {
        User user = new User();
        user.setId(uuid);
        user.setUsername(username);
        user.setPassword(encodePwd);
        user.setSalt(salt);
        user.setTel(tel);
        user.setRegTime(date);
        return userMapper.insert(user);
    }
    
    @Override
    public boolean updateLastLogin(String id)
    {
        User user = new User();
        user.setLastLogin(new Date());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        int i = userMapper.update(user, queryWrapper);
        return i > 0;
    }
    
    @Override
    public UserVo getUSerVoById(String authorId)
    {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, authorId);
        User user = userMapper.selectOne(queryWrapper);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
