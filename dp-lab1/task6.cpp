/**
U ranijoj vježbi s analizom asemblera primijetio sam kako se registar ecx koristi kao "this" pokazivač na objekt
nad kojim je određena metoda pozvana.

On se prije poziva instrukcijom "call" puni adresom početka dotičnog objekta. Na taj način, kada se uđe u dio asemblerskog
koda koji je zadužen za izvođenje dotične virtualne funkcije, može se izvesti ona implementacija koja je najbliža razredu
this objekta, odnosno pretražuje se tablica virtualnih funkcija objekta od njega prema nadklasama.

Pristupanje pokazivaču virtualnih funkcija može se vidjeti u sljedećem asemblerskom kodu funkcije "metoda()" razreda Base:
    mov	DWORD PTR [ebp-12], ecx # prebaci adresu pokazivača "this" (tj. početka objekta) na adresu zapisanu u (ebp-12)

    mov	eax, DWORD PTR [ebp-12] # adresu početka objekta na adresi (ebp-12) prebaci u registar eax
	mov	eax, DWORD PTR [eax]    # adresu početka tablice virtualnih funkcija [eax] prebaci u reigstar eax
	mov	edx, DWORD PTR [eax]    # adresu prve funkcije u tablici virtualnih funkcija (virtualnaMetoda) prebaci u edx
	mov	eax, DWORD PTR [ebp-12] # adresu početka objekta na adresi (ebp-12) prebaci u registar eax
	mov	ecx, eax                # adresu početka objekta prebaci iz eax u ecx
	call	edx                 # pozovi virtualnu funkciju virtualnaMetoda

Dio koda koji inicijalizira tablicu virtualnih funkcija nalazi se u konstruktorima razreda Base i Derived. Oba konstruktora imaju
gotovo identičan asemblerski kod za tu zadaću. Evo koda za razred Base:
    mov	DWORD PTR [ebp-12], ecx         # prebaci adresu početka objekta (this) na adresu u (ebp-12)
	mov	edx, OFFSET FLAT:__ZTV4Base+8   # prebaci adresu početka tablice virtualnih funkcija u registar edx
	mov	eax, DWORD PTR [ebp-12]         # prebaci adresu početka objekta u registar eax
	mov	DWORD PTR [eax], edx            # prebaci adresu početka tablice virtualnih funkcija na adresu početka objekta (gdje i očekujemo da se nalazi)

Time je virtualna tablica inicijalizirana.

Sada nam je jasno da pozivom "pd->metoda", registar ecx se puni adresom objekta pd koji je tipa Derived, te će se u trenutku poziva metode virtualnaMetoda() pozvati
izvedena implementacija.

Tokom konstrukcije objekta tipa Derived, najprije se poziva konstruktor Base razreda čime će se ecx napuniti adresom početka tog objekta, i pozvat će se najprije bazna implementacija
virtualne metode.
Nakon toga, nastavlja se izvođenje konstruktora razreda Derived i ponovno se poziva metoda, ali ovaj puta pozvat će se virtualnaMetoda izvedene implementacije, pa tokom
konstrukcije objekta imamo dva ispisa (jedan za baznu i jedan za izvedenu implementaciju), te treći ispis (izvedene implementacije) eksplicitnim pozivom metode u zadnjoj liniji metode main.
*/

#include <stdio.h>

class Base
{
public:
    Base()
    {
        metoda();
    }

    virtual void virtualnaMetoda()
    {
        printf("ja sam bazna implementacija!\n");
    }

    void metoda()
    {
        printf("Metoda kaze: ");
        virtualnaMetoda();
    }
};

class Derived: public Base
{
public:
    Derived(): Base()
    {
        metoda();
    }
    virtual void virtualnaMetoda()
    {
        printf("ja sam izvedena implementacija!\n");
    }
};

int main()
{
    Derived* pd=new Derived();
    pd->metoda();
}
