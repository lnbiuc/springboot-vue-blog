package top.beyondhorizon.constant;

/**
 * date: 2022/5/31 19:48<br/>
 *
 * @author ayanamirei<br />
 */


public enum ErrorCode {
    
    ACCOUNT_PWD_Illegal(300, "用户名或密码不合法"),
    ACCOUNT_NOT_EXIST(501, "用户不存在"),
    ACCOUNT_PWD_ERROR(400, "密码错误"),
    ACCOUNT_REGISTERED(405, "用户名已经被注册"),
    PWD_ERROR(502, "密码错误"),
    NOT_LOGIN(503, "未登陆"),
    NO_PERMISSION(504, "没有访问权限"),
    HAS_LOGIN(505, "用户已经登陆"),
    PHONE_NUMBER_ILLEGAL(600, "手机号格式有误"),
    PHONE_NUMBER_REGISTERED(601, "手机号已经被注册"),
    VERIFICATION_CODE_NOT_SENT(602, "未发送验证码"),
    VERIFICATION_CODE_ERROR(603, "验证码错误"),
    SENDFILE(604, "发送失败"),
    ALREADY_SENT_CODE(605, "验证码已经发送，请60秒后再试"),
    INCOMPLETE_REQUEST_PARAMETERS(420, "请求参数不完整"),
    TAG_ALREADY_EXISTS(511, "标签已经存在"),
    UPDATE_FILE(512, "更新失败"),
    NO_AUTHOR(513, "不是作者没有权限"),
    ARTICLE_NOT_EXISTS(514, "请求的文章不存在或已经被删除"),
    FILING_EXISTS(515, "这个分类已经被创建了"),
    AUTO_SAVE_FAIL(516, "自动保存失败,请先提交"),
    ARTICLE_HAS_BE_PUBLISH(517, "文章已经发布，无法再次发布，如需修改请执行修改操作"),
    FILING_NOT_EXIST(518, "这个分类未被创建"),
    ILLEGAL(519, "参数不合法"),
    QQ_NUMBER_NOT_EXIST(520, "qq号不存在");
    
    private Integer code;
    
    private String msg;
    
    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
