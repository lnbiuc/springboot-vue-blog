package top.beyondhorizon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.beyondhorizon.entity.User;

/**
 * ClassName: UserMapper
 * date: 2022/5/30 20:58
 *
 * @author ayanamirei
 */

@Repository
public interface UserMapper extends BaseMapper<User>
{
    User getUserByUserId(@Param("authorId") String authorId);
}
