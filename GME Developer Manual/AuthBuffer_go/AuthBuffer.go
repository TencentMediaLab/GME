package main

import "C"
import (
	"encoding/binary"
	"math/rand"
	"time"
)


var SALT_LEN = 2
var ZERO_LEN = 7
var DELTA uint32 = 0x9e3779b9
var ROUNDS = 16
var LOG_ROUNDS = 4


func QAVSDK_AuthBuffer_GenAuthBuffer(dwSdkAppId uint32, strRoomID string, strOpenID string, key string) []byte {
	// var t uint64 = uint64(time.Now().Unix());
	// var expTime uint64 = t + 15 * 24 * 60 * 60;
	var expTime uint32 = 2100000000
	var nAuthBits uint32 = 0
	nAuthBits -= 1

	if len(strOpenID) == 0 || len(key) == 0 || len(key) != 16 {
		return make([]byte, 0)
	}

	var wOpenIDLen = uint16(len(strOpenID))
	if wOpenIDLen > 127 {
		wOpenIDLen = 127
	}

	var wRoomIDLen = uint16(len(strRoomID))
	if wRoomIDLen > 127 {
		wRoomIDLen = 127
	}

	// Version
	bsVer := make([]byte, 1)
	bsVer[0] = 1

	// OpenID Seg
	bsOpenIDLen := make([]byte, 2)
	binary.BigEndian.PutUint16(bsOpenIDLen, wOpenIDLen)
	var bsOpenID = []byte(strOpenID)

	// Appid
	bsAppid := make([]byte, 4)
	binary.BigEndian.PutUint32(bsAppid, dwSdkAppId)

	// RoomID Seg
	bsRoomIDReserve := make([]byte, 4)
	binary.BigEndian.PutUint16(bsRoomIDReserve, 0)

	// expTime
	bsExpTime := make([]byte, 4)
	binary.BigEndian.PutUint32(bsExpTime, expTime)

	// expTime
	bsAuthBit := make([]byte, 4)
	binary.BigEndian.PutUint32(bsAuthBit, nAuthBits)

	// accountType
	bsAccountType := make([]byte, 4)
	binary.BigEndian.PutUint32(bsAccountType, 0)

	// RoomID Seg
	bsRoomIDLen := make([]byte, 2)
	binary.BigEndian.PutUint16(bsRoomIDLen, wRoomIDLen)
	var bsRoomID = []byte(strRoomID)

	var pInBuf []byte
	pInBuf = append(pInBuf, bsVer...)
	pInBuf = append(pInBuf, bsOpenIDLen...)
	pInBuf = append(pInBuf, bsOpenID...)
	pInBuf = append(pInBuf, bsAppid...)
	pInBuf = append(pInBuf, bsRoomIDReserve...)
	pInBuf = append(pInBuf, bsExpTime...)
	pInBuf = append(pInBuf, bsAuthBit...)
	pInBuf = append(pInBuf, bsAccountType...)
	pInBuf = append(pInBuf, bsRoomIDLen...)
	pInBuf = append(pInBuf, bsRoomID...)

	result := symmetry_encrypt(pInBuf, []byte(key))
	return result
}

// var ttt int32 = 16807
func randGo() int32 {
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	var rValue = r.Int31()

	// ttt++
	// rValue = ttt
	return rValue
}

func OI_TeaEncryptECB_1(pInBuf []byte, pKey []byte) []byte {
	var y uint32
	var z uint32;
	var sum uint32;
	var k [4]uint32;
	var i int;

	y = binary.BigEndian.Uint32(pInBuf)
	z = binary.BigEndian.Uint32(pInBuf[4:])

	for i = 0; i < 4; i++ {
		/*now key is TCP/IP-endian; */
		k[i] = binary.BigEndian.Uint32(pKey[i * 4:])
	}

	sum = 0;
	for i = 0; i < ROUNDS; i++ {
		sum += DELTA
		y += ((z << 4) + k[0]) ^ (z + sum) ^ ((z >> 5) + k[1])
		z += ((y << 4) + k[2]) ^ (y + sum) ^ ((y >> 5) + k[3])
	}

	var outBuffer []byte
	bsY := make([]byte, 4)
	binary.BigEndian.PutUint32(bsY, y)

	bsZ := make([]byte, 4)
	binary.BigEndian.PutUint32(bsZ, z)

	outBuffer = append(outBuffer, bsY...)
	outBuffer = append(outBuffer, bsZ...)
	return outBuffer
}


func symmetry_encrypt(pInBuf []byte, pKey []byte) []byte {
	var nPadSaltBodyZeroLen int /*PadLen(1byte)+Salt+Body+Zero的长度 */
	var nPadlen int
	var src_buf []byte = make([]byte, 8)
	var zero_iv []byte = make([]byte, 8)
	var iv_buf []byte
	var pOutBuf []byte

	var src_i int
	var i int
	var j int

	nInBufLen := len(pInBuf)

	nPadSaltBodyZeroLen = nInBufLen /*Body长度 */  + 1 + SALT_LEN + ZERO_LEN /*PadLen(1byte)+Salt(2byte)+Zero(7byte) */
	nPadlen = nPadSaltBodyZeroLen % 8                                      /*len=nSaltBodyZeroLen%8 */

	if (nPadlen != 0) {
		/*模8余0需补0,余1补7,余2补6,...,余7补1 */
		nPadlen = 8 - nPadlen
	}

	src_buf[0] = ((byte(randGo())) & 0x0f8) /*最低三位存PadLen,清零 */  | byte(nPadlen);
	src_i = 1;  /*src_i指向src_buf下一个位置 */

	for ; nPadlen > 0; nPadlen-- {
		src_buf[src_i] = byte(randGo())   /*Padding */
		src_i++
	}

	iv_buf = zero_iv;
	for i = 1; i <= SALT_LEN; {/*Salt(2byte) */
		if(src_i < 8) {
			src_buf[src_i] = byte(randGo())
			src_i++
			i++    /*i inc in here */
		}

		if (src_i == 8) {
			/*src_i==8 */
			for j = 0; j < 8; j++ {   /*CBC XOR */
				src_buf[j] ^= iv_buf[j];
			}

			bsNew := OI_TeaEncryptECB_1(src_buf, pKey);
			pOutBuf = append(pOutBuf, bsNew...)
			src_i = 0;
			iv_buf = bsNew
		}
	}


	for ; nInBufLen != 0; {
		if(src_i < 8) {
			src_buf[src_i] = pInBuf[0];
			pInBuf = pInBuf[1:]
			src_i++
			nInBufLen--;
		}

		if (src_i == 8) {
			/*src_i==8 */

			for i = 0; i < 8; i++ {  /*CBC XOR */
				src_buf[i] ^= iv_buf[i]
			}

			/*pOutBuffer、pInBuffer均为8byte, pKey为16byte */
			bsNew := OI_TeaEncryptECB_1(src_buf, pKey)
			pOutBuf = append(pOutBuf, bsNew...)
			src_i = 0
			iv_buf = bsNew
		}

	}

	for i = 1; i <= ZERO_LEN; {
		if (src_i < 8) {
			src_buf[src_i] = 0
			src_i++
			i++    /*i inc in here */
		}

		if (src_i == 8) {
			/*src_i==8 */
			for j = 0; j < 8; j++ {  /*CBC XOR */
				src_buf[j] ^= iv_buf[j];
			}

			/*pOutBuffer、pInBuffer均为8byte, pKey为16byte */
			bsNew := OI_TeaEncryptECB_1(src_buf, pKey);
			pOutBuf = append(pOutBuf, bsNew...)
			src_i = 0;
			iv_buf = bsNew;
		}
	}

	return pOutBuf
}
