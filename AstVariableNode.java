import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstVariableNode extends AstNode{
  private String varName;
  private String varType;
  private int lineNum;
  
  public AstVariableNode(){
    varName = "\"Variable Name Not Set\"";
    varType = "\"Variable Type Not set\"";
    lineNum = -1;
    begin = -1;
    end = -1;
  }
  public AstVariableNode(int lineNumber){
    varName = "\"Variable Name Not Set\"";
    varType = "\"Variable Type Not set\"";
    lineNum = lineNumber;
    begin = -1;
    end = -1;
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
  public AST_Type getType(){
    return AST_Type.Variable;
  }
}
