//
// Created by Aneury Perez on 10/9/20.
//

#include "Auth.h"

#include <string.h>
#include <jni.h>
#include <android/log.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <string>
#include <unistd.h>
#include<arpa/inet.h>

static const char *ATAG = "Auth.cpp";
#define LOG_ME(...) ((void)__android_log_print(ANDROID_LOG_INFO, ATAG, __VA_ARGS__))

int hostname_to_ip(char * hostname , char* ip)
{
    struct hostent *he;
    struct in_addr **addr_list;
    int i;

    if ( (he = gethostbyname( hostname ) ) == NULL)
    {
        // get the host info
        herror("gethostbyname");
        return 1;
    }

    addr_list = (struct in_addr **) he->h_addr_list;

    for(i = 0; addr_list[i] != NULL; i++)
    {
        //Return the first one;
        strcpy(ip , inet_ntoa(*addr_list[i]) );
        return 0;
    }

    return 1;
}


std::string getRequestFromServer(const char *url)
{
    int fd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    auto res =  -1;
    sockaddr_in address;
    memset(&address, 0x00, sizeof(address));
    hostent *h = gethostbyname("url");
    address.sin_port = htons(80);
    address.sin_family = AF_INET;
    char ip[16]={0};
    hostname_to_ip(const_cast<char *>(url), ip);
    inet_aton(ip, &address.sin_addr);
    res = connect(fd, (sockaddr*)&address, sizeof(address));
    if(res !=0 )
    {
        LOG_ME("SOCKET CANT CONNECT");
        return std::string("cant connect");
    }
    std::string request = "GET /index.html HTTP/1.1\r\nHost: google.com\r\nConnection: close\r\n\r\n";
    char buffer[4096]={0};
    res = send(fd, request.c_str(), request.size(), 0);
    res = recv(fd, buffer, 4095, 0);
    shutdown(fd, SHUT_RDWR);
    ///close(fd);
    return (res>0)?std::string(buffer):"No Data Available";
}



extern "C" JNIEXPORT jstring
Java_info_aneury_androidtuto_MainActivity_getHelloWorld(JNIEnv* env, jobject thiz){
    return (env)->NewStringUTF(
            ///"hello World From ndk world"
            ///
            getRequestFromServer("google.com").c_str()
            );
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_aneury_androidtuto_MainActivity_getRequestByUrl(JNIEnv *env, jobject thiz, jstring url) {
    return (env)->NewStringUTF(
            ///"hello World From ndk world"
            ///
            getRequestFromServer((const char *)env->GetStringUTFChars(url, 0)).c_str()
    );
}