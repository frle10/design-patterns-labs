#include <stdlib.h>

typedef char const* (*PTRFUN)();
typedef struct Tiger Tiger;

char const* name(Tiger* tiger);
char const* greet();
char const* menu();

void* create(char const* name);
void construct(char const* name, Tiger* tiger);
size_t size_of();


struct Tiger {
    PTRFUN *funTable;
    const char *name;
};

PTRFUN vtable[] = {name, greet, menu};

char const* name(Tiger* tiger) {
    return tiger->name;
}

char const* greet() {
    return "grrr!";
}

char const* menu() {
    return "svo meso";
}

void* create(char const* name) {
    Tiger* tiger = (Tiger*)malloc(sizeof(Tiger));
    construct(name, tiger);
    return tiger;
}

void construct(char const* name, Tiger* tiger) {
    tiger->funTable = vtable;
    tiger->name = name;
}

size_t size_of() {
    return sizeof(Tiger);
}
