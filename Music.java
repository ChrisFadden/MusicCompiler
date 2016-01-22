import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;

public class Music {
 public static void main(String[] args) throws IOException {
    String filename = args[0];
    ArrayList<ArrayList<Lexer.Token>> tokens = 
      new ArrayList<ArrayList<Lexer.Token>>();
    for (String input : Files.readAllLines(Paths.get(filename))) {
      tokens.add(Lexer.lex(input));
    }
    
    Parser parser = new Parser(tokens,filename);
  }//end main
}//end class
