/**
Razredi Square i Linear nasljeđuju razred Unary_Function. Pri tome razred Square
ne dodaje nikakve nove članske varijable niti funkcije, samo nadjačava apstraktnu metodu "value_at(int)".

Razred Linear nadodaje dvije nove članske varijable a i b tipa double te nadjačava apstraktnu metodu "value_at(int)".

Tablica virtualnih funkcija za pojedine razrede izgledaju ovako (navesti ću ime razreda pa onda redom kako se funkcije
nalaze u tablici za taj razred):

    Unary_Function:
        1. value_at --> none (apstraktna metoda, nema implementacije)
        2. negative_value_at --> adresa od Unary_Function::negative_value_at

    Square:
        1. value_at --> adresa od Square::value_at
        2. negative_value_at --> adresa od Unary_Function::negative_value_at

    Linear:
        1. value_at --> adresa od Linear::value_at
        2. negative_value_at --> adresa od Unary_Function::negative_value_at
*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Unary_Function Unary_Function;
typedef struct Square Square;
typedef struct Linear Linear;

typedef double (*PTRDOUBLEFUN)(Unary_Function*, double);
typedef void (*PTRVOIDFUN)(Unary_Function*);

struct Unary_Function {
    int lower_bound;
    int upper_bound;
    PTRVOIDFUN tabulate;
    PTRDOUBLEFUN* funTable;
};

struct Square {
    Unary_Function uf;
};

struct Linear {
    Unary_Function uf;
    double a;
    double b;
};

double negative_value_at(Unary_Function*, double);
double value_at_square(Unary_Function*, double);
double value_at_linear(Unary_Function*, double);

Square* createSquare(int, int);
Linear* createLinear(int, int, double, double);

void tabulate(Unary_Function*);

bool same_functions_for_ints(Unary_Function*, Unary_Function*, double);

PTRDOUBLEFUN squareTable[2] = {value_at_square, negative_value_at};
PTRDOUBLEFUN linearTable[2] = {value_at_linear, negative_value_at};

int main() {
    Unary_Function *f1 = (Unary_Function*)createSquare(-2, 2);
    f1->tabulate(f1);

    Unary_Function *f2 = (Unary_Function*)createLinear(-2, 2, 5, -2);
    f2->tabulate(f2);

    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", f2->funTable[1](f2, 1.0));

    free(f1); free(f2);
    return 0;
}

Unary_Function* createUnary(int lower_bound, int upper_bound) {
    Unary_Function* unary = (Unary_Function*)malloc(sizeof(Unary_Function));
    unary->lower_bound = lower_bound;
    unary->upper_bound = upper_bound;
    unary->tabulate = tabulate;
    return unary;
}

Square* createSquare(int lower_bound, int upper_bound) {
    Unary_Function* uf = createUnary(lower_bound, upper_bound);
    uf->funTable = squareTable;

    Square* square = (Square*)malloc(sizeof(Square));
    square->uf = *uf;
    return square;
}

Linear* createLinear(int lower_bound, int upper_bound, double a, double b) {
    Unary_Function* uf = createUnary(lower_bound, upper_bound);
    uf->funTable = linearTable;

    Linear* linear = (Linear*)malloc(sizeof(Linear));
    linear->uf = *uf;
    linear->a = a;
    linear->b = b;
    return linear;
}

double negative_value_at(Unary_Function *uf, double x) {
    return -(uf->funTable[0](uf, x));
}

double value_at_square(Unary_Function *square, double x) {
    return x * x;
}

double value_at_linear(Unary_Function *linear, double x) {
    Linear *lin = (Linear*)linear;
    return lin->a * x + lin->b;
}

void tabulate(Unary_Function* uf) {
    for(int x = uf->lower_bound; x <= uf->upper_bound; x++) {
        printf("f(%d)=%lf\n", x, uf->funTable[0](uf, x));
    }
}

bool same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance) {
      if(f1->lower_bound != f2->lower_bound) return false;
      if(f1->upper_bound != f2->upper_bound) return false;

      for (int x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = f1->funTable[0](f1, x) - f2->funTable[0](f2, x);
        if(delta < 0) delta = -delta;
        if(delta > tolerance) return false;
      }

      return true;
}
