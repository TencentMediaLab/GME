import java.util.Base64;

public class Source {
    public static void main(String [] args) {
        int sdkAppID = 1400089356;
        byte[] strRoomID = "2018060920180609".getBytes();
        byte[] strOpenID = "95549554955495549554".getBytes();
        byte[] strkey = "ae8c7e0428e341a2".getBytes();
        byte[] ret = GMEAuthbuffer.QAVSDK_AuthBuffer_GenAuthBuffer(sdkAppID, strRoomID, strOpenID, strkey);

        String helloHex = Base64.getEncoder().encodeToString(ret);
        System.out.printf("return hex: %s\n", helloHex);
    }
}
