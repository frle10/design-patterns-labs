#include <stdlib.h>

typedef char const* (*PTRFUN)();
typedef struct Parrot Parrot;

char const* name(Parrot* parrot);
char const* greet();
char const* menu();

void* create(char const* name);
void construct(char const* name, Parrot* memorySpace);
size_t size_of();

struct Parrot {
    PTRFUN *funTable;
    const char *name;
};

PTRFUN vtable[] = {name, greet, menu};

char const* name(Parrot* parrot) {
    return parrot->name;
}

char const* greet() {
    return "squawk!";
}

char const* menu() {
    return "sjemenke";
}

void* create(char const* name) {
    Parrot* parrot = (Parrot*)malloc(sizeof(Parrot));
    construct(name, parrot);
    return parrot;
}

void construct(char const* name, Parrot* parrot) {
    parrot->funTable = vtable;
    parrot->name = name;
}

size_t size_of() {
    return sizeof(Parrot);
}
