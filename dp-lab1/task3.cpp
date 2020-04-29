/**
CoolClass ima jednu privatnu int varijablu koja zauzima 4 bajta memorije po objektu.
Dodatno, CoolClass ima dvije virtualne funkcije koje mogu biti nadjačane u podrazredima. Za njih se u svakom
instanciranom objektu stvara pokazivač na tablicu virtualnih funkcija, koja sadrži pokazivače na konkretne
implementacije tih funkcija. Taj jedan pokazivač po objektu zauzima 4 bajta memorije pa sve zajedno CoolClass
ima veličinu 8 bajtova, što je i ispis programa na mom računalu.

PlainOldClass sadrži jednu privatnu int varijablu veličine 4 bajta. Sadrži i dvije funkcije, međutim one nisu
virtualne i ne mogu se nikada nadjačati. Njihova implementacija se jednom kompajlira i onda linka kad god dođe
poziv neke od tih funkcija. Zbog toga one ne zauzimaju dodatno mjesto u memoriji po objektu kada se stvori novi objekt.
Zbog toga je ispis sizeof(PlainOldClass) = 4 bajta.
*/

#include <stdio.h>

class CoolClass
{
public:
    virtual void set(int x)
    {
        x_=x;
    };
    virtual int get()
    {
        return x_;
    };
private:
    int x_;
};

class PlainOldClass
{
public:
    void set(int x)
    {
        x_=x;
    };
    int get()
    {
        return x_;
    };
private:
    int x_;
};

int main() {
    printf("Size of CoolClass: %d\n", sizeof(CoolClass));
    printf("Size of PlainOldClass: %d\n", sizeof(PlainOldClass));
    return 0;
}
