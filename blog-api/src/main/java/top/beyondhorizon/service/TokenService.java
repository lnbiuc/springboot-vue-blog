package top.beyondhorizon.service;

import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.vo.UserVo;

/**
 * ClassName: TokenService
 * date: 2022/5/31 21:05
 *
 * @author ayanamirei
 */


public interface TokenService {
    UserVo getUserByToken(String token);
    UserVo status(String token);
}
