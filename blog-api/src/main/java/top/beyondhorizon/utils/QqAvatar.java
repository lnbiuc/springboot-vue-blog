package top.beyondhorizon.utils;


/**
 * ClassName: QqAvatar
 * date: 2022/9/7 17:40
 *
 * @author ayanamirei
 */


import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.beyondhorizon.model.dto.QqAvatarRespDto;

import java.util.Map;

/**
 * 根据qq号获取头像 昵称
 * {"qq":"2857621299","nickname":"MyDearAyanamiRei","avatar":"https://q1.qlogo.cn/g?b=qq&nk=2857621299&s=40","email":"2857621299@qq.com","url":"https://user.qzone.qq.com/2857621299"}
 */
public class QqAvatar {
    
    public QqAvatarRespDto getInfoByQqNumber(String qqNumber) {
        String url = "https://api.lixingyong.com/api/qq?id=" + qqNumber;
        RestTemplate restTemplate = new RestTemplate();
        String object = restTemplate.getForObject(url, String.class);
        QqAvatarRespDto dto = JSONObject.parseObject(object, QqAvatarRespDto.class);
        return dto;
    }
}
