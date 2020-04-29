/**
1. Za objekt poc memorija se alocira u sljedećoj liniji asemblera:
    lea	eax, [ebp-16]

Iza toga se registar eax prebacuje u ecx (mov ecx, eax) i tamo se nalazi adresa početka objekta poc, pa je
instrukcija lea efektivno alocirala memoriju za njega. Ovdje nema poziva operatora new niti
konstruktora kao za *pb jer je objekt samo deklariran, a ne i definiran.

Deklariranjem objekta, ali ne i njegovom definicijom, jedino što se dogodilo je rezervacija adrese u memoriji i unošenje novog imena u program,
ali nije došlo do pozivanja konstruktora koji bi inicijalizirao tablicu virtualnih funkcija ili išta slično. Možemo vidjeti da ta rezervirana adresa
za objekt završava na stogu (deklarirano kao lokalna varijabla) otkud ju i dohvaćamo gore navedenom linijom.

Za objekt *pb memorija se alocira izvršavanjem operatora new koji pak poziva funkciju operator_new(t_size size) koja
alocira memoriju veličine size bajtova za objekt koji se kreira.

Asembler za alokaciju memorije za *pb:
    mov	DWORD PTR [esp], 8
	call	__Znwj

Vidimo da se konstanta 8 stavlja na vrh stoga pa se poziva operator new. Ta konstanta označava upravo argument size, koji
za objekte tipa CoolClass iznosi 8 bajtova.

2. Objekt *pb alocira memoriju operatorom new eksplicitnim pozivom, a objekt poc dobiva memorijsku lokaciju implicitno
u asembleru prije poziva metode set().

3. Asemblerski kod za poziv konstruktora objekta poc ne postoji u mojoj .s datoteci. Razlog tomu je što se konstruktor u kodu
nikada ne poziva, niti implicitno niti eksplicitno. Zato nema potrebe da kompajler generira kod za poziv konstruktora, čime se
postiže optimizacija.

4. Dio asemblerskog koda koji poziva konstruktor objekta *pb je sljedeći:
    mov	ecx, ebx
	call	__ZN9CoolClassC1Ev

U registru ecx se nalazi pokazivač na adresu početka objekta *pb u trenutku poziva konstruktora naredbom call.
U samom kodu za poziv konstruktora događaju se dvije važne stvari od interesa:
    1. Poziva se konstruktor nadklase Base.
    2. Inicijalizira se tablica virtualnih funkcija klase CoolClass

5. Poziv "pb->set(42);" zahtijeva više instrukcija nego poziv "poc.set(42)" (9 > 5). Poziv za objekt
poc jednostavno prebaci u registar ecx pokazivač na početak objekta poc te se direktno naredbom call
poziva dio asemblera zadužen za poziv funkcije set(int).

Kod objekta *pb, najprije se mora pokazivač na objekt pozicionirati u ecx kao i kod poc, ali još dodatno
moramo pronaći adresu virtualne funkcije set(int) u tablici virtualnih funkcija objekta *pb. Zato
imamo više instrukcija za poziv.

Poziv "poc.set(42)" bi optimirajući prevoditelj mogao generirati bez funckije CALL. Poziv "*pb->set(42)" se
ne bi mogao izvesti na taj način jer virtualne funkcije ne mogu biti "inline" funkcije.

6. Asemblerski kod za definiciju i inicijalizaciju tablice virtualnih funkcija razreda CoolClass:
    mov	edx, OFFSET FLAT:__ZTV9CoolClass+8
	mov	eax, DWORD PTR [ebp-12]
	mov	DWORD PTR [eax], edx

Labela __ZTV9CoolClass predstavlja "_vtable for CoolClass" prema stranici "demangler.com".
*/

#include <stdio.h>

class Base
{
public:
    //if in doubt, google "pure virtual"
    virtual void set(int x)=0;
    virtual int get()=0;
};

class CoolClass: public Base
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

int main()
{
    PlainOldClass poc;
    Base* pb=new CoolClass;
    poc.set(42);
    pb->set(42);
}
