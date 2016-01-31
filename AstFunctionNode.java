import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstFunctionNode extends AstNode{
  String FunctionName;
  public AstFunctionNode(FileWriter filename){
    writer = filename;
    name = "\"Function Name Not Set\"";
    children = new ArrayList<AstNode>(); 
  }
  public void setName(String newName, int lineNumber){
    FunctionName = newName;
    name = "\"Function ";
    name += FunctionName;
    name += " (";
    name += lineNumber;
    name += ")\"";
  }
  
  public AST_Type getType(){
    return AST_Type.Function;
  }
}
