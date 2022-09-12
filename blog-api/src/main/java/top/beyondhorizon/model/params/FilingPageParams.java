package top.beyondhorizon.model.params;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: FilingPage
 * date: 2022/6/12 14:09
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
public class FilingPageParams implements Serializable
{
    private Integer pageNumber;
    
    private Integer pageSize;
    
    private Integer filingId;
}
