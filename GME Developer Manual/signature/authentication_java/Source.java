import java.util.Base64;

public class Source {
    public static void main(String [] args) {
        int sdkAppID = AppID To Replace Here ;
        byte[] strRoomID = "2018060920180609".getBytes();
        byte[] strOpenID = "95549554955495549554".getBytes();
        byte[] strkey = "Key To Replace Here ".getBytes();
        byte[] ret = GMEAuthbuffer.QAVSDK_AuthBuffer_GenAuthBuffer(sdkAppID, strRoomID, strOpenID, strkey);

        String helloHex = Base64.getEncoder().encodeToString(ret);
        System.out.printf("return hex: %s\n", helloHex);
    }
}
