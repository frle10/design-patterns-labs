#include <stdio.h>
#include <stdlib.h>

void testAnimals(void);

struct Animal* createDog();
struct Animal* createCat();

void constructDog(struct Animal* memSpace, const char* name);
void constructCat(struct Animal* memSpace, const char* name);

void animalPrintGreeting(struct Animal*);
void animalPrintMenu(struct Animal*);

char const* dogGreet(void);
char const* dogMenu(void);
char const* catGreet(void);
char const* catMenu(void);

struct Animal* nDogsCreate(int);

typedef char const* (*PTRFUN)();

struct Animal {
    const char *name;
    PTRFUN *funTable;
};

PTRFUN dogTable[2] = {dogGreet, dogMenu};
PTRFUN catTable[2] = {catGreet, catMenu};

// Main function just calls the testAnimals() function
int main(void) {
    testAnimals();
    return 0;
}

void testAnimals() {
    // Initial test function from lab description
    struct Animal* p1=createDog("Hamlet");
    struct Animal* p2=createCat("Ofelija");
    struct Animal* p3=createDog("Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    free(p1); free(p2); free(p3);
    printf("\n");

    // Use function to create n dogs using only one malloc call and sufficient constructDog calls
    struct Animal* dogs = nDogsCreate(5);
    for (int i = 0; i < 5; i++) {
        printf("%s\n", (dogs + i)->name);
    }

    free(dogs);

    // Create a dog on the stack
    struct Animal myDogStack;
    myDogStack.name = "Peso";
    myDogStack.funTable = dogTable;

    printf("\n%s\n", myDogStack.name);

    // Create a dog on the heap
    struct Animal* myDogHeap = (struct Animal*)malloc(sizeof(struct Animal));
    myDogHeap->name = "Pesonja";
    myDogHeap->funTable = dogTable;

    printf("\n%s\n", myDogHeap->name);
    free(myDogHeap);
}

struct Animal* createDog(const char* name) {
    struct Animal* dog = (struct Animal*)malloc(sizeof(struct Animal));
    constructDog(dog, name);
    return dog;
}

struct Animal* createCat(const char* name) {
    struct Animal* cat = (struct Animal*)malloc(sizeof(struct Animal));
    constructCat(cat, name);
    return cat;
}

void constructDog(struct Animal* memSpace, const char* name) {
    memSpace->name = name;
    memSpace->funTable = dogTable;
}

void constructCat(struct Animal* memSpace, const char* name) {
    memSpace->name = name;
    memSpace->funTable = catTable;
}

void animalPrintGreeting(struct Animal* animal) {
    printf("%s pozdravlja: %s\n", animal->name, animal->funTable[0]());
}

void animalPrintMenu(struct Animal* animal) {
    printf("%s voli %s\n", animal->name, animal->funTable[1]());
}

char const* dogGreet(){
    return "vau!";
}

char const* dogMenu(){
    return "kuhanu govedinu";
}

char const* catGreet(){
    return "mijau!";
}

char const* catMenu(){
    return "konzerviranu tunjevinu";
}

struct Animal* nDogsCreate(int n) {
    struct Animal* dogs = (struct Animal*)malloc(n * sizeof(struct Animal));

    for(int i = 0; i < n; i++) {
        constructDog(dogs + i, "Pas");
    }

    return dogs;
}
