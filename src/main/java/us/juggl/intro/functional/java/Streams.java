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
                    .seq(s, toString)
                    .filter(startsWith.apply("c"))
                    .sorted()
                    .skip(2)
                    .limit(5)
                    .forEach(System.out::println);
            }
        }
    }
}