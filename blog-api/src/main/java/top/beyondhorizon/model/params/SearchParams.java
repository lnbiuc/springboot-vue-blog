package top.beyondhorizon.model.params;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: SearchParams
 * date: 2022/7/2 13:15
 *
 * @author ayanamirei
 */


@Data
public class SearchParams implements Serializable
{
    private Integer total;
    
    private Object data;
}
