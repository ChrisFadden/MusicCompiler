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
    Else,
    Block,
    Collection,
    BooleanExpression,
    ArithmeticExpression,
    EqualExpression, 
    Variable,
    FuncCall,
    Default,
    Error;
  }//end enum
  
  public ArrayList<AstNode> children;
  public FileWriter writer; 
  public String name;
  
  public double begin;
  public double  end;

  public AstNode(){
    begin = -1;
    end = -1; 
  }
  public AstNode(FileWriter filename){
    writer = filename;
    name = "\"Default\"";
    children = new ArrayList<AstNode>(); 
    begin = -1;
    end = -1; 
  }//end constructor

  final public void makeGraph() throws IOException {  
    
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
  
  public double getBegin(){
    return begin;
  }
  
  public double getEnd(){
    return end;
  }

  public void haveAllInfo(){}

  public FileWriter getFileWriter(){
    return writer;
  }
}//end AstNode class















