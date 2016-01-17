import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;

public class Music {

  public static void main(String[] args) throws IOException{
    
    String filename = args[0]; 
    int i = 1; 
    for(String input : Files.readAllLines(Paths.get(filename))){  
      ArrayList<Lexer.Token> tokens = Lexer.lex(input);
      for(Lexer.Token token : tokens)
      {
        /*if(token.type.name() == "NullptrTok")
        {
          System.out.print("line: ");
          System.out.print(i); 
          System.out.print(" ");
          System.out.println(input);
          break; 
        }
       */

        if(i == 112)
        {
          System.out.print(token.type.name());
          System.out.print(" ");
          System.out.println(token.data);
        }
      }
      i++;
    }
  }
}
