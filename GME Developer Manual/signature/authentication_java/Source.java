import java.util.Base64;

public class Source {
    public static void main(String [] args) {
        int sdkAppID = 14000xxxxx;                                  // 为云控制台获取的AppID
        byte[] strkey = "ae8c7e0428xxxxx".getBytes();               // 为云控制台获取的Key， 与AppID成对
        byte[] strRoomID = "2018060920180609".getBytes();           // 分配的房间号
        byte[] strOpenID = "95549554955495549554".getBytes();       // 此用户的OpenID
        byte[] ret = GMEAuthbuffer.QAVSDK_AuthBuffer_GenAuthBuffer(sdkAppID, strRoomID, strOpenID, strkey);

        String helloHex = Base64.getEncoder().encodeToString(ret);
        System.out.printf("return hex: %s\n", helloHex);
    }
}
