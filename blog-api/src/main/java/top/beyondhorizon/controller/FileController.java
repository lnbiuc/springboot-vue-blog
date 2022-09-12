package top.beyondhorizon.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.utils.TencentCOSUploadFileUtil;

/**
 * ClassName: FileController
 * date: 2022/6/4 19:40
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("upload")
public class FileController
{
    @RequestMapping("img")
    public RetMsg upload(@RequestParam("img") MultipartFile file)
    {
        String url = TencentCOSUploadFileUtil.uploadfile(file);
        return RetMsg.success(url);
    }
}
