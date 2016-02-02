import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstBooleanExpressionNode extends AstNode{
  private String boolExpression;
  private int lineNum; 
  public AstBooleanExpressionNode(){
    boolExpression = "\"Boolean Expression Name Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>();  
  }
  public AstBooleanExpressionNode(int lineNumber){
    boolExpression = "\"Boolean Expression Name Not Set\"";
    begin = -1;
    end = -1;
    lineNum = lineNumber;
    children = new ArrayList<AstNode>();
  }
  public void setName(String newName){boolExpression = newName;}
  public String getName(){
    name = "\"BOOL EXPR:  ";
    name += boolExpression;
    name += " (";
    name += lineNum;
    name += ")\"";
    return name;
  }
  
  public void makeGraph(){}

  public AST_Type getType(){
    return AST_Type.BooleanExpression;
  }
}
