import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstBlockNode extends AstNode{
    
  public AstBlockNode(){
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>();  
  }
  public AstBlockNode(FileWriter filename, double lineNumber){
    writer = filename; 
    begin = lineNumber;
    end = -1;
    children = new ArrayList<AstNode>();
  }
  
  public String getName(){
    name = "\"BLOCK:  ";
    name += " (";
    name += begin;
    name += " : ";
    name += end;
    name += ")\"";
    return name;
  }
  
  public AST_Type getType(){
    return AST_Type.Block;
  }
}
