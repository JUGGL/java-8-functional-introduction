package us.juggl.intro.functional.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author <a href="https://github.com/InfoSec812">Deven Phillips</a>
 */
public class Lambdas {

    
    public static final void main(String[] args) {
        
        // Old School!! - The plain old Java way of creating a simple Runnable using anonymous implementations of 
        // an Interface
        Runnable r1 = new Runnable() {

            @Override
            public void run() {
                System.out.println("Hello World Old School!");
            }
        };
        
        r1.run();
        
        // New Cool! - Using a Lambda function to implement the Runnable Interface
        
        Runnable r2 = () -> System.out.println("Hello World New Cool!");
        r2.run();
        
        // Composition/Currying
        // In this example, we define a function: add = (i,j) -> i+j
        final Function<Integer, Function<Integer, Integer>> add = i -> j -> i + j;

        // Then we curry the function by defining: add2 = (j) -> add(j, 2)
        final Function<Integer, Integer> add2 = add.apply(2);
        
        // Finally, we call the curried function: add2(6)
        System.out.println("Adding 6 plus 2 = "+add2.apply(6));
        
        
        
        {
            // Closures - WRONG!
            ArrayList<String> a = new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c"}));
            ArrayList<String> b = new ArrayList<>(Arrays.asList(new String[]{"d", "e", "f"}));
            System.out.println("Closures 1 - Before Size = "+(a.size()+b.size()));
            
            // Java does not actually do closures (yet?). So this will not work as expected.
            Runnable r3 = () -> System.out.println("Closures 1 - During Size = "+(a.size()+b.size()));

            a.add("z");
            b.add("x");
            System.out.println("Closures 1 - After Size = "+(a.size()+b.size()));

            r3.run();
        }
        
        
        
        {
            // Closures - RIGHT!
            // Technically, Java 8 does NOT do closures... They can "fake" it by using immutable values.
            ArrayList<String> a = new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c"}));
            ArrayList<String> b = new ArrayList<>(Arrays.asList(new String[]{"d", "e", "f"}));
        
            // To overcome the lack of closures, we have to create an immutable copy of the Collection
            /* effectively final */ List c = Arrays.asList(a.toArray());
            /* effectively final */ List d = Arrays.asList(b.toArray());
            System.out.println("Closures 2 - Before Size = "+(a.size()+b.size()));
            Runnable r4 = () -> System.out.println("Closures 2 - During Size = "+(c.size()+d.size()));

            a.add("z");
            b.add("x");
            System.out.println("Closures 2 - After Size = "+(a.size()+b.size()));

            r4.run();
        }
    }
}
