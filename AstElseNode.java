import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstElseNode extends AstNode{
  
  private int lineNumber;

  public AstElseNode(FileWriter filename){
    writer = filename;
    name = "\"Else Statement Name Not Set\"";
    begin = -1;
    end = -1;
    lineNumber = -1;
    children = new ArrayList<AstNode>(); 
  }
  
  public void setLine(int lineNum){
    lineNumber = lineNum;
  }

  public AST_Type getType(){
    return AST_Type.Else;
  }
  
  public void haveAllInfo(){
    name = "\"Else Statement ("; 
    name += lineNumber;
    name += " )\"";
  }
}
