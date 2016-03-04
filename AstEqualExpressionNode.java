import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstEqualExpressionNode extends AstNode{
  
  public AstEqualExpressionNode(FileWriter filename){
    writer = filename;
    name = "\"Equal Expression Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
   
  public AST_Type getType(){
    return AST_Type.EqualExpression;
  }
  
  public void haveAllInfo(){
    name = "\"= ("; 
    name += begin;
    name += " )\"";
  }
}
