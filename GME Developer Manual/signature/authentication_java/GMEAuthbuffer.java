import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class GMEAuthbuffer {
    static int ROUNDS  = 16;
    static int LOG_ROUNDS = 4;
    static long DELTA = makeUint32(0x9e3779b9);
    static int SALT_LEN = 2;
    static int ZERO_LEN = 7;

    static byte[] QAVSDK_AuthBuffer_GenAuthBuffer(int dwSdkAppId,
                                               byte[] strRoomID,
                                               byte[] strOpenID,
                                               byte[] key)
    {
        // unsigned int expTime = t + 15 * 24 * 60 * 60;
        int expTime = 2100000000;
        int nAuthBits = (int)-1;

        if (strOpenID.length == 0 || key.length == 0){
            return new byte[0];
        }

        ByteBuffer buf = ByteBuffer.allocate(512);

        char cVer = 1;
        short wOpenIDLen = (short) strOpenID.length;
        short wRoomIDLen = (short) strRoomID.length;
        buf.put((byte) cVer);
        buf.putShort(wOpenIDLen);
        buf.put(strOpenID);
        buf.putInt(dwSdkAppId);
        buf.putInt(0 /*dwRoomID*/);
        buf.putInt(expTime);
        buf.putInt(nAuthBits);
        buf.putInt(0 /*dwAccountType*/);
        buf.putShort(wRoomIDLen);
        buf.put(strRoomID);

        int pInLen = buf.position();
        int iEncrptyLen = 0;

        byte[] pInBuf = new byte[pInLen];
        System.arraycopy(buf.array(), 0, pInBuf, 0, pInBuf.length);
        byte[] pEncryptOutBuf = symmetry_encrypt(pInBuf, key);

        return pEncryptOutBuf;
    }

    static int radom = 16807;
    static int rand()
    {
        radom++;
        return radom;
        // Random r1 = new Random();
        // return r1.nextInt();
    }

    public static byte[] long2Byte(long value)
    {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putLong(value);

        return Arrays.copyOfRange(bytes, 4, 8);
    }

    public static long byte2Long(byte[] bytes)
    {
        ByteBuffer buffer = ByteBuffer.allocate(8).put(new byte[]{0, 0, 0, 0}).put(bytes);
        buffer.position(0);

        return buffer.getLong();
    }

    static long makeUint32(long l){
        return (l & 0X00000000FFFFFFFFL);
    }

    static byte[] OI_TeaEncryptECB_1(byte[] pInBuf, byte[] pKey)
    {
        long y = 0;
        long z = 0;
        long sum = 0;
        int i = 0;
        long[] k = new long[4];

        /*plain-text is TCP/IP-endian; */

        /*GetBlockBigEndian(in, y, z); */
        ByteBuffer byteBufferIn = ByteBuffer.wrap(pInBuf);
        byte[] bufY = new byte[4];
        byte[] bufZ = new byte[4];
        byteBufferIn.get(bufY);
        byteBufferIn.get(bufZ);

        y = byte2Long(bufY);
        z = byte2Long(bufZ);

        //4个Int型的数组
        ByteBuffer byteBufferKey = ByteBuffer.wrap(pKey);
        byte[] byk = new byte[4];

        byteBufferKey.get(byk);
        k[0] = byte2Long(byk);
        byteBufferKey.get(byk);
        k[1]  = byte2Long(byk);
        byteBufferKey.get(byk);
        k[2]  = byte2Long(byk);
        byteBufferKey.get(byk);
        k[3]  = byte2Long(byk);

        for(i = 0; i < ROUNDS; i++)
        {
            sum += DELTA;
            sum = makeUint32(sum);
            y += (makeUint32(makeUint32(z << 4) + k[0])) ^ (makeUint32(z + sum)) ^ (makeUint32(makeUint32(z >>> 5) + k[1]));
            y = makeUint32(y);
            z += (makeUint32(makeUint32(y << 4) + k[2])) ^ (makeUint32(y + sum)) ^ (makeUint32(makeUint32(y >>> 5) + k[3]));
            z = makeUint32(z);
        }

        /*TCP/IP network byte order (which is big-endian). */
        byte[] by = long2Byte(y);
        byte[] bz = long2Byte(z);

        ByteBuffer byOut = ByteBuffer.allocate(8);
        byOut.put(by);
        byOut.put(bz);
        return byOut.array();
    }


    // byte[] pOutBuf
    static byte[] symmetry_encrypt(byte[] pInBuf, byte[] pKey)
    {
        int nInBufLen = pInBuf.length;
        int nPadSaltBodyZeroLen;        /*PadLen(1byte)+Salt+Body+Zero的长度 */
        int nPadlen;
        byte[] src_buf = new byte[8];
        byte[] zero_iv = new byte[8];
        byte[] iv_buf;
        int src_i, i, j;
        ByteBuffer bufFinal = ByteBuffer.allocate(512);

        /*根据Body长度计算PadLen,最小必需长度必需为8byte的整数倍 */
        nPadSaltBodyZeroLen = nInBufLen /*Body长度 */  + 1 + SALT_LEN + ZERO_LEN /*PadLen(1byte)+Salt(2byte)+Zero(7byte) */ ;

        nPadlen = nPadSaltBodyZeroLen % 8;  /*len=nSaltBodyZeroLen%8 */
        if(nPadlen != 0)
        {
            /*模8余0需补0,余1补7,余2补6,...,余7补1 */
            nPadlen = 8 - nPadlen;
        }

        /* srand( (unsigned)time( NULL ) ); 初始化随机数 */
        /*加密第一块数据(8byte),取前面10byte */
        src_buf[0] = (byte) ((((byte) rand()) & 0x0f8) /*最低三位存PadLen,清零 */  | (byte) nPadlen);
        src_i = 1;  /*src_i指向src_buf下一个位置 */

        while(nPadlen-- != 0)
        {
            src_buf[src_i++] = (byte) rand();   /*Padding */
        }

        /*come here, i must <= 8 */
        //memset(zero_iv, 0, 8);
        iv_buf = zero_iv;   /*make iv */

        for(i = 1; i <= SALT_LEN;)  /*Salt(2byte) */
        {
            if(src_i < 8)
            {
                src_buf[src_i++] = (byte) rand();
                i++;    /*i inc in here */
            }

            if(src_i == 8)
            {
                /*src_i==8 */

                for(j = 0; j < 8; j++)  /*CBC XOR */
                {
                    src_buf[j] ^= iv_buf[j];
                }

                /*pOutBuffer、pInBuffer均为8byte, pKey为16byte */
                byte[] pOutBuf = OI_TeaEncryptECB_1(src_buf, pKey);
                src_i = 0;
                iv_buf = pOutBuf;

                bufFinal.put(pOutBuf);
            }
        }

        ///*src_i指向src_buf下一个位置 */
        int pInBufIndex = 0;
        while(nInBufLen != 0)
        {
            if(src_i < 8)
            {
                src_buf[src_i++] = pInBuf[pInBufIndex++];
                nInBufLen--;
            }

            if(src_i == 8)
            {
                /*src_i==8 */

                for(i = 0; i < 8; i++)  /*CBC XOR */
                {
                    src_buf[i] ^= iv_buf[i];
                }

                /*pOutBuffer、pInBuffer均为8byte, pKey为16byte */
                byte[] pOutBuf = OI_TeaEncryptECB_1(src_buf, pKey);
                src_i = 0;
                iv_buf = pOutBuf;

                bufFinal.put(pOutBuf);
            }
        }

        /*src_i指向src_buf下一个位置 */
        for(i = 1; i <= ZERO_LEN;)
        {
            if(src_i < 8)
            {
                src_buf[src_i++] = 0;
                i++;    /*i inc in here */
            }

            if(src_i == 8)
            {
                /*src_i==8 */

                for(j = 0; j < 8; j++)  /*CBC XOR */
                {
                    src_buf[j] ^= iv_buf[j];
                }

                /*pOutBuffer、pInBuffer均为8byte, pKey为16byte */
                byte[] pOutBuf = OI_TeaEncryptECB_1(src_buf, pKey);
                src_i = 0;
                iv_buf = pOutBuf;
                bufFinal.put(pOutBuf);
            }
        }

        byte[] byRet = new byte[bufFinal.position()];
        bufFinal.position(0);
        bufFinal.get(byRet);
        return byRet;
    }

}
