#include <stdlib.h>
#include <windows.h>

//void* myfactory(char const* libname, char const* ctorarg) {
//    HINSTANCE handle = LoadLibraryA(libname);
//    FARPROC funAddress = GetProcAddress(handle, "create");
//    void* objPointer = (void*)funAddress(ctorarg);
//    return objPointer;
//}

void myfactory(char const* libname, char const* ctorarg, void* memSpace) {
    HINSTANCE handle = LoadLibraryA(libname);
    FARPROC funAddress = GetProcAddress(handle, "construct");
    funAddress(ctorarg, memSpace);
}
