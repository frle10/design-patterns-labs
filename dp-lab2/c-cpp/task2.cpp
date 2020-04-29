/**
* Prednosti ove implementacije u odnosu na prethodni zadatak:
* - možemo koristiti istu funkciju ne samo za polja, već i za druge vrste spremnika poput vector-a i set-a
* - u kodu se rješavamo groznih void pokazivača zbog čega je mnogo čitljiviji
* - kriterijske funkcije mogu primiti konkretne podatke ili reference na njih, a ne void pokazivače čime je kod još čitljiviji
* - mymax funkcija može primiti jedan parametar manje, jer informaciju o veličini tipa podatka koji sprema spremnik prepoznaje
* kompajler pri pozivu funkcije
*
* Nedostaci:
* - ne vidim nikakve nedostatke u odnosu na prethodni zadatak, ova implementacija može sve što i ona u prošlom zadatku, i više
*/
#include <iostream>
#include <cstring>
#include <vector>
#include <set>

using namespace std;

const int gt_int(int, int);
const int gt_double(double, double);
const int gt_char(char, char);
const int gt_str(const char *, const char *);

template<typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred) {
	Iterator maxIterator = first;

	Iterator i = first;
	i++;
	while(i != last) {
		if (pred(*i, *maxIterator)) {
			maxIterator = i;
		}

		i++;
	}

	return maxIterator;
}

int main(void) {
	int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
	char arr_char[] = "Suncana strana ulice";
	const char* arr_str[] = {
		"Gle", "malu", "vocku", "poslije", "kise",
		"Puna", "je", "kapi", "pa", "ih", "njise"
	};

	vector<double> vec_double;
    vec_double.push_back(3.14);
    vec_double.push_back(9.8);
    vec_double.push_back(1.72);
    vec_double.push_back(-10.89);
    vec_double.push_back(15.7);
    vec_double.push_back(13.1);

    set<int> set_int;
    set_int.insert(5);
    set_int.insert(8);
    set_int.insert(-2);
    set_int.insert(11);
    set_int.insert(0);
    set_int.insert(3);

	const int* maxint = mymax(&arr_int[0], &arr_int[sizeof(arr_int) / sizeof(*arr_int)], gt_int);
	const char* maxchar = mymax(&arr_char[0], &arr_char[sizeof(arr_char) / sizeof(*arr_char)], gt_char);
	const char** maxstring = mymax(&arr_str[0], &arr_str[sizeof(arr_str) / sizeof(*arr_str)], gt_str);
    const vector<double>::iterator maxdouble = mymax(vec_double.begin(), vec_double.end(), gt_double);
    const set<int>::iterator maxsetint = mymax(set_int.begin(), set_int.end(), gt_int);

	cout << *maxint << "\n";
	cout << *maxchar << "\n";
	cout << *maxstring << "\n";
	cout << *maxdouble << "\n";
	cout << *maxsetint << "\n";

	return 0;
}

const int gt_int(int first, int second) {
	return first > second;
}

const int gt_double(double first, double second) {
	return first > second;
}

const int gt_char(char first, char second) {
	return first > second;
}

const int gt_str(const char* first, const char* second) {
	return (strcmp(first, second) > 0) ? 1 : 0;
}
