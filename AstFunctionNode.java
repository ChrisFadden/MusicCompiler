import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstFunctionNode extends AstNode{
  private String FunctionName;
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
}
