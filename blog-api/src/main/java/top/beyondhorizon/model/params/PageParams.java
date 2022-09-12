package top.beyondhorizon.model.params;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: PageParamas
 * date: 2022/5/30 18:23
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams implements Serializable
{
    private Integer pageNumber;
    
    private Integer pageSize;
    
}
