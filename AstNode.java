import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstNode {
  public static enum AST_Type{
    Program,
    File,
    Function,
    ForLoop,
    WhileLoop,
    If,
    Collection,
    BooleanExpression,
    ArithmeticExpression,
    Variable,
    FuncCall,
    Default,
    Error;
  }//end enum
  
  public ArrayList<AstNode> children;
  public FileWriter writer; 
  public String name;
  
  int begin;
  int end;

  public AstNode(){}
  public AstNode(FileWriter filename, int lineNumber){
    writer = filename;
    name = "\"Default(";
    name += lineNumber;
    name += ")\"";
    children = new ArrayList<AstNode>(); 
  }//end constructor

  public void makeGraph() throws IOException { 
    for(AstNode node : children){
      writer.write(name);
      writer.write(" -> ");
      writer.write(node.getName());
      writer.write("\r\n");
      node.makeGraph(); 
    } 
  }
  
  public AST_Type getType(){
    return AST_Type.Default;  
  }
  
  public void addChild(AstNode child){ 
    children.add(child); 
  }
  
  public String getName(){
    return name;
  } 

  public void setBegin(int b){
    begin = b;
  }

  public void setEnd(int e){
    end = e;
  }
  public void haveAllInfo(){}
}















