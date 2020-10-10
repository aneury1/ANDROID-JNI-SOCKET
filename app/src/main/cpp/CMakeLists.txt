cmake_minimum_required(VERSION 3.4.1)

add_library(hworld SHARED Auth.cpp)


target_link_libraries(
        hworld
        android
        log
)