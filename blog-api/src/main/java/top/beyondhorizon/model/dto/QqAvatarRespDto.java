package top.beyondhorizon.model.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: QqAvatarRespDto
 * date: 2022/9/7 20:18
 *
 * @author ayanamirei
 */

@Data
public class QqAvatarRespDto implements Serializable {
    
    private String qq;
    
    private String nickname;
    
    private String avatar;
    
    private String email;
    
    private String url;
}
