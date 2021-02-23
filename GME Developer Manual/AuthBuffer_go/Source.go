package main

import "C"
import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"net/http/cgi"
	"strconv"
)

type response struct {
	ErrorCode   int64    `json:"errorCode"`
	UserSig 	string   `json:"userSig"`
}

func main() {
	http.HandleFunc("/authbuffer", func(w http.ResponseWriter, r *http.Request){
		handler := new(cgi.Handler)

		fmt.Println(r.URL.Path)
		fmt.Println(string("-------new request--------"))

        //
        // Replace appID & appKey to your own project
        //
		var appID uint32 = 0; //"Replace appID & appKey to your own project"
		var appKey = "Replace appID & appKey to your own project"
		//

		var roomID = string(r.FormValue("roomid"))
		var openID = string(r.FormValue("openid"))

		var bsAuthBuffer = QAVSDK_AuthBuffer_GenAuthBuffer(appID, roomID, openID, appKey)
		encodeString := base64.StdEncoding.EncodeToString(bsAuthBuffer)

		response := &response{}
		response.ErrorCode = 0
		response.UserSig = encodeString
		data, _ := json.Marshal(response)
		b := []byte(string(data))

		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Access-Control-Allow-Methods", "*")
		w.Header().Set("readme", "this is a test key cannot be used to publish")
		w.WriteHeader(200)
		fmt.Println("GenAuthBuffer : appID=" + strconv.FormatUint(uint64(appID), 10) + ", roomID=" + roomID + ", openID=" + openID + ", authBuffer=" + string(b))
		fmt.Println(string("-------body--------"))
		w.Write(b)

		handler.ServeHTTP(w, r)
	})
	log.Fatal(http.ListenAndServe(":8989",nil))
}
