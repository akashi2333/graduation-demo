package fymxy.ckmp_server.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * @author tangWu
 */
@Data
@AllArgsConstructor
public class Respone implements Serializable {
    private int code;
    private  String msg;
    private Object data;

    public static Respone loginFail(){
        return new Respone(400,"登录失败",null);
    }
    public static Respone loginSucee(){
        return new Respone(200,"登录成功",null);
    }
}
