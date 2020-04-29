/**
* Dodavanjem razreda romb u program, morao sam promijeniti funkcije drawShapes(), moveShapes() i printCenters() te nadodati dodatne utility funkcije
* koje koriste razred Rhombus. Kad sam namjerno pokrenuo program bez ispravljanja funkcije moveShapes(), program je puknuo.
*/
#include <iostream>
#include <assert.h>
#include <stdlib.h>

struct Point {
	int x = 0;
	int y = 0;
};

struct Shape {
	enum EType {circle, square, rhombus};
	EType type_;
};

struct Circle {
	Shape::EType type_;
	double radius_;
	Point center_;
};

struct Square {
	Shape::EType type_;
	double side_;
	Point center_;
};

struct Rhombus {
	Shape::EType type_;
	double side_;
	Point center_;
};

void drawSquare(struct Square*) {
	std::cerr <<"in drawSquare\n";
}

void drawCircle(struct Circle*) {
	std::cerr <<"in drawCircle\n";
}

void drawRhombus(struct Rhombus*) {
	std::cerr <<"in drawRhombus\n";
}

void printSquareCenter(struct Square* square) {
	std::cout << "Square center: " << square->center_.x << ", " << square->center_.y << "\n";
}
void printCircleCenter(struct Circle* circle) {
	std::cout << "Circle center: " << circle->center_.x << ", " << circle->center_.y << "\n";
}

void printRhombusCenter(struct Rhombus* rhombus) {
	std::cout << "Rhombus center: " << rhombus->center_.x << ", " << rhombus->center_.y << "\n";
}

void translateSquare(struct Square* square, int translateX, int translateY) {
	square->center_.x += translateX;
	square->center_.y += translateY;
}

void translateCircle(struct Circle* circle, int translateX, int translateY) {
	circle->center_.x += translateX;
	circle->center_.y += translateY;
}

void translateRhombus(struct Rhombus* rhombus, int translateX, int translateY) {
	rhombus->center_.x += translateX;
	rhombus->center_.y += translateY;
}

void drawShapes(Shape** shapes, int n) {
	for (int i=0; i<n; ++i) {
		struct Shape* s = shapes[i];
		switch (s->type_) {
		case Shape::square:
			drawSquare((struct Square*)s);
			break;
		case Shape::circle:
			drawCircle((struct Circle*)s);
			break;
		case Shape::rhombus:
			drawRhombus((struct Rhombus*)s);
			break;
		default:
			assert(0);
			exit(0);
		}
	}
}

void moveShapes(Shape** shapes, int n, int translateX, int translateY) {
	for (int i=0; i<n; ++i) {
		struct Shape* s = shapes[i];
		switch (s->type_) {
		case Shape::square:
			translateSquare((struct Square*)s, translateX, translateY);
			break;
		case Shape::circle:
			translateCircle((struct Circle*)s, translateX, translateY);
			break;
		case Shape::rhombus:
			translateRhombus((struct Rhombus*)s, translateX, translateY);
			break;
		default:
			assert(0);
			exit(0);
		}
	}
}

void printCenters(Shape** shapes, int n) {
	for (int i=0; i<n; ++i) {
		struct Shape* s = shapes[i];
		switch (s->type_) {
		case Shape::square:
			printSquareCenter((struct Square*)s);
			break;
		case Shape::circle:
			printCircleCenter((struct Circle*)s);
			break;
		case Shape::rhombus:
			printRhombusCenter((struct Rhombus*)s);
			break;
		default:
			assert(0);
			exit(0);
		}
	}
}

int main() {
	Shape* shapes[5];
	shapes[0]=(Shape*)new Circle;
	shapes[0]->type_=Shape::circle;
	shapes[1]=(Shape*)new Square;
	shapes[1]->type_=Shape::square;
	shapes[2]=(Shape*)new Square;
	shapes[2]->type_=Shape::square;
	shapes[3]=(Shape*)new Circle;
	shapes[3]->type_=Shape::circle;
	shapes[4]=(Shape*)new Rhombus;
	shapes[4]->type_=Shape::rhombus;

	drawShapes(shapes, 5);
	moveShapes(shapes, 5, 4, 5);
	printCenters(shapes, 5);

	return 0;
}
