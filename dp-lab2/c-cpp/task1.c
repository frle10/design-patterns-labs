#include <stdio.h>
#include <string.h>

const void* mymax(const void *, size_t, size_t, int (*)(const void *, const void *));
const int gt_int(const void *, const void *);
const int gt_char(const void *, const void *);
const int gt_str(const void *, const void *);

int main(void) {
	int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
	char arr_char[]="Suncana strana ulice";
	const char* arr_str[] = {
		"Gle", "malu", "vocku", "poslije", "kise",
		"Puna", "je", "kapi", "pa", "ih", "njise"
	};

	printf("%d\n", *((const int*)mymax(arr_int, sizeof(arr_int) / sizeof(arr_int[0]), sizeof(int), gt_int)));
	printf("%c\n", *((const char*)mymax(arr_char, sizeof(arr_char) / sizeof(arr_char[0]), sizeof(char), gt_char)));
	printf("%s\n", *(const char**)mymax(arr_str, sizeof(arr_str) / sizeof(arr_str[0]), sizeof(const char *), gt_str));

	return 0;
}

const void* mymax(const void *base, size_t nmemb, size_t size, int (*compar)(const void *, const void *)) {
    const char* byte = base;
    const void* max = &byte[0];

    for (size_t i = 1; i < nmemb; i++) {
        if(compar(&byte[i * size], max) > 0) {
            max = &byte[i * size];
        }
    }

    return max;
}

const int gt_int(const void * first, const void * second) {
    return *(int *)first > *(int *) second;
}

const int gt_char(const void * first, const void * second) {
    return *(char *)first > *(char *) second;
}

const int gt_str(const void * first, const void * second) {
    return (strcmp(*(const char**)first, *(const char**)second) > 0) ? 1 : 0;
}
