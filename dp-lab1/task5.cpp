#include <stdio.h>

class B
{
public:
    virtual int __cdecl prva()=0;
    virtual int __cdecl druga(int)=0;
};

class D: public B
{
public:
    virtual int __cdecl prva()
    {
        return 42;
    }
    virtual int __cdecl druga(int x)
    {
        return prva()+x;
    }
};

void accessVirtualFunctions(B*);

int main()
{
    B* pb = new D;
    accessVirtualFunctions(pb);
    return 0;
}

void accessVirtualFunctions(B* pb) {
    // access vptr address
    int* vptr = *(int**)pb;

    printf("%d\n", ((int (*)()) vptr[0])());
    printf("%d\n", ((int (*)(B*, int)) vptr[1])(pb, 8));
}
