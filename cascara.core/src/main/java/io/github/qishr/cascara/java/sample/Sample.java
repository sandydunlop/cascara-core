package io.github.qishr.cascara.java.sample;

import java.util.List;

// Annotation (decorator)
@interface MyAnnotation {}

// Enum
enum Color { RED, GREEN, BLUE } // enumMember

// Interface
interface Drawable {
    void draw(); // method
}

// Record
record Point(int x, int y) {} // parameter

// Class
@MyAnnotation // decorator
public class Sample implements Drawable { // type
    public Color color; // variable
    private List<Color> colors;

    // Constructor
    public Sample(Color color) { // parameter
        this.color = color; // operator
    }

    // Method
    public void draw() { // method
        System.out.println("Drawing a " + color); // string
        Sample.main(null);
    }

    // Main method
    public static void main(String[] args) { // function
        Sample shape = new Sample(Color.RED); // variable
        shape.draw(); // event
        shape.color = Color.GREEN;
    }
}
