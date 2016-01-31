import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstFunctionNode extends AstNode{
  private String FunctionName;
  int numForLoop;
  int numWhileLoop;
  int numIfStatement;

  public AstFunctionNode(FileWriter filename){
    writer = filename;
    name = "\"Function Name Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
  public void setName(String newName){
    FunctionName = newName; 
  }
  
  public AST_Type getType(){
    return AST_Type.Function;
  }
  
  public void haveAllInfo(){
    name = "\"Function ";
    name += FunctionName;
    name += " (";
    name += begin;
    name += ":";
    name += end;
    name += " )\"";
  }

  public void addForLoop(){
    numForLoop++;
  }
  
  public void addWhileLoop(){
    numWhileLoop++;
  }
  
  public void addIfStatement(){
    numIfStatement++;
  }
  
  public int getNumForLoop(){
    return numForLoop;
  }
  
  public int getNumWhileLoop(){
    return numWhileLoop;
  }
  
  public int getNumIfStatement(){
    return numIfStatement;
  }
}
