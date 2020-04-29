/**
* Ovo je implementirano rješenje s predavanja. U odnosu na prošlo, kruto rješenje,
* ovdje u slučaju dodavanja novog tipa geometrijskog lika ne moramo uopće mijenjati
* funkcije poput moveShapes() ili drawShapes(). Moramo samo napraviti novi razred za
* naš novi lik i naslijediti razred Shape. Naravno, moramo implementirati sve apstraktne
* funkcije iz razreda Shape, međutim kada je to gotovo, naš novi tip objekta je spreman
* za korištenje. Ne moramo mijenjati nikakav stari kod, već samo dodajemo novi.
*/
#include <iostream>
#include <assert.h>
#include <stdlib.h>
#include <list>

struct Point {
	int x = 0;
	int y = 0;
};

class Shape {
public:
    virtual void draw() = 0;
    virtual void translate(int, int) = 0;
    virtual void printCenter() = 0;
};

class Circle : public Shape {
    virtual void draw() {
        std::cerr <<"in drawCircle\n";
    }

    virtual void translate(int translateX, int translateY) {
        center_.x += translateX;
        center_.y += translateY;
    }

    virtual void printCenter() {
        std::cout << "Circle center: " << center_.x << ", " << center_.y << "\n";
    }

    double radius;
    Point center_;
};

class Square : public Shape {
    virtual void draw() {
        std::cerr <<"in drawSquare\n";
    }

    virtual void translate(int translateX, int translateY) {
        center_.x += translateX;
        center_.y += translateY;
    }

    virtual void printCenter() {
        std::cout << "Square center: " << center_.x << ", " << center_.y << "\n";
    }

    double side;
    Point center_;
};

class Rhombus : public Shape {
    virtual void draw(){
        std::cerr <<"in drawRhombus\n";
    }

    virtual void translate(int translateX, int translateY) {
        center_.x += translateX;
        center_.y += translateY;
    }

    virtual void printCenter() {
        std::cout << "Rhombus center: " << center_.x << ", " << center_.y << "\n";
    }

    double side;
    Point center_;
};

void drawShapes(const std::list<Shape*>& fig) {
	std::list<Shape*>::const_iterator it;
	for (it = fig.begin(); it != fig.end(); ++it) {
        (*it)->draw();
	}
}

void moveShapes(const std::list<Shape*>& fig, int translateX, int translateY) {
	std::list<Shape*>::const_iterator it;
	for (it = fig.begin(); it != fig.end(); ++it) {
        (*it)->translate(translateX, translateY);
	}
}

void printCenters(const std::list<Shape*>& fig) {
	std::list<Shape*>::const_iterator it;
	for (it = fig.begin(); it != fig.end(); ++it) {
        (*it)->printCenter();
	}
}

int main() {
    std::list<Shape*> fig;
    fig.push_back(new Circle);
    fig.push_back(new Square);
    fig.push_back(new Square);
    fig.push_back(new Circle);
    fig.push_back(new Rhombus);

	drawShapes(fig);
	moveShapes(fig, 4, 5);
	printCenters(fig);

	return 0;
}
