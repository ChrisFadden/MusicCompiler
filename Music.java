import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;
import java.io.FileWriter;

public class Music {
 public static void main(String[] args) throws IOException {
    String filename = args[0];
    ArrayList<ArrayList<Lexer.Token>> tokens = 
      new ArrayList<ArrayList<Lexer.Token>>();
    for (String input : Files.readAllLines(Paths.get(filename))) {
      tokens.add(Lexer.lex(input));
    }
    
    FileWriter writer = new FileWriter(filename.substring(0,filename.length() - 6) + ".dot");
    writer.write("digraph G{");
    writer.write("\r\n"); 
    Parser parser = new Parser(tokens,filename,writer);
    writer.write("}");
    writer.close(); 
  }//end main
}//end class
