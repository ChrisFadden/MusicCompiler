import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstForNode extends AstNode{
  
  int numForLoop;
  int numWhileLoop;
  int numIfStatement;

  public AstForNode(FileWriter filename){
    writer = filename;
    name = "\"For Loop Name Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
   
  public AST_Type getType(){
    return AST_Type.ForLoop;
  }
  
  public void haveAllInfo(){
    name = "\"For Loop ("; 
    name += begin;
    name += ":";
    name += end;
    name += " )\"";
  }
}
