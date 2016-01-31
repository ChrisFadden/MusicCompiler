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
    
    //Test (This is how I should handle scoping)
    ArrayList<AstNode> test = new ArrayList<AstNode>();
    AstNode scope = new AstNode(); 
    for(int i = 0; i < 5; i++){ 
        AstNode added = new AstNode(); 
        test.add(added);  
    }
    /*
     * THIS IS VERY IMPORTANT
     */
    scope = test.get(1);
    test.get(1).setBegin(4);
    System.out.println(scope.getBegin());
    /*
     *  This is how I'll do the copy and scoping
     */

    FileWriter writer = new FileWriter(filename.substring(0,filename.length() - 6) + ".dot");
    writer.write("digraph G{");
    writer.write("\r\n"); 
    Parser parser = new Parser(tokens,filename,writer);
    writer.write("}");
    writer.close(); 
  }//end main
}//end class
