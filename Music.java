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
    
    AstNode test = new AstNode(writer,0);
    AstNode testChild1 = new AstNode(writer,1);
    AstNode testChild2 = new AstNode(writer,2);
    AstNode testChild3 = new AstNode(writer,3);  
    test.addChild(testChild1);
    test.addChild(testChild2);
    test.addChild(testChild3);
    test.makeGraph();
    writer.write("}");
    writer.close();
    Parser parser = new Parser(tokens,filename);
    
  }//end main
}//end class
