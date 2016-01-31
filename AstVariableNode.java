import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstVariableNode extends AstNode{
  private String varName;
  private String varType;
  private int lineNum;
  public AstVariableNode(int lineNumber){
    varName = "\"Variable Name Not Set\"";
    varType = "\"Variable Type Not set\"";
    lineNum = lineNumber;
  }
  public void setName(String newName){varName = newName;}
  public void setType(String newType){varType = newType;}
  public String getName(){
    name = "\"Variable:  ";
    name += varType;
    name += " ";
    name += varName;
    name += " (";
    name += lineNum;
    name += ")\"";
    return name;
  }
  
  public void makeGraph(){}

  public AST_Type getType(){
    return AST_Type.Variable;
  }
}
