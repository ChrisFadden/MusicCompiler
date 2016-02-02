import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstWhileNode extends AstNode{
  
  public AstWhileNode(FileWriter filename){
    writer = filename;
    name = "\"While Loop Name Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
   
  public AST_Type getType(){
    return AST_Type.WhileLoop;
  }
  
  public void haveAllInfo(){
    name = "\"While Loop ("; 
    name += begin;
    name += ":";
    name += end;
    name += " )\"";
  }
}
