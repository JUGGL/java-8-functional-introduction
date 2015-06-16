package us.juggl.intro.functional.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jooq.lambda.SQL;
import org.jooq.lambda.Unchecked;

/**
 *
 * @author <a href="https://github.com/InfoSec812">Deven Phillips</a>
 */
public class Streams {

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        
        /*
         * This format of the database is a single table names "example" with 4 columns:
         *       id             UUID
         *       username       VARCHAR(255)
         *       givenname      VARCHAR(255)
         *       familyname     VARCHAR(255)
         */
        
        // Iterate over resultset in Java 8
        try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/functional", "dphillips", "password")) {
            String sql = "SELECT id, username, givenname, familyname FROM example";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                
                // Create a lambda function which converts the ResultSet rows to Strings
                Function<ResultSet, String> toString = Unchecked.function(rs -> String.format("%s:%s:%s:%s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                
                // Create a lambda function which acts a Predicate for filtering based on the starting characters
                Function<String, Predicate<String>> startsWith = preface -> str -> str.startsWith(preface);
                
                // Stream the ResultSet and apply various transformations
                SQL
                    .seq(s, toString)               // Convert the row to a String
                    .filter(startsWith.apply("c"))  // Filter for strings starting with the letter "c"
                    .sorted()                       // Sort the list of strings
                    .skip(2)                        // Skip the first 2 in the list
                    .limit(5)                       // Allow the next 5 items to continue processing
                    .forEach(System.out::println);  // Print out the remaining 5 lines
            }
        }
    }
}