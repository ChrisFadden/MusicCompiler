import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstArithmeticExpressionNode extends AstNode{
  
  public AstArithmeticExpressionNode(FileWriter filename){
    writer = filename;
    name = "\"Equal Expression Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
   
  public AST_Type getType(){
    return AST_Type.EqualExpression;
  }
  
  public void setName(String newName){
    name = "\""; 
    name += newName;
  }

  public void haveAllInfo(){
    name += "("; 
    name += begin;
    name += " )\"";
  }
}
