'''
Metodu get rječnika možemo koristiti kao slobodnu funkciju
tako što predajemo samo referencu na nju. Funkcije u Pythonu
su objekti prvog reda, baš kao i svi ostali tipovi podataka
na koje smo navikli kao što su liste ili setovi. To je još
jedan razlog zašto se mogu ponašati slobodno.
'''
def mymax(iterable, key = lambda i: i):
    # inicijaliziraj maksimalni element i maksimalni ključ
    max_x=max_key=None

    #obiđi sve elemente
    for x in iterable:
        # ako je key(x) najveći -> ažuriraj max_x i max_key
        if max_x == None:
            max_x = key(x);
            max_key = x
        else:
            if key(x) > max_x:
                max_x = key(x)
                max_key = x

    #vrati rezultat
    return max_x

#Main
maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
maxchar = mymax("Suncana strana ulice")
maxstring = mymax([
  "Gle", "malu", "vocku", "poslije", "kise",
  "Puna", "je", "kapi", "pa", "ih", "njise"])

D={'burek':8, 'buhtla':5}
maxarticle = mymax(D, D.get)

print(maxint)
print(maxchar)
print(maxstring)
print(maxarticle)

persons = [
    ('Ivan', 'Skorupan'),
    ('Frano', 'Bošković'),
    ('Ivan', 'Zirkić'),
    ('Branko', 'Brankić')
    ]
maxperson = mymax(persons)
print(maxperson)
