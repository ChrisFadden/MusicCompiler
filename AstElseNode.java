import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstElseNode extends AstNode{
  
  public AstElseNode(FileWriter filename){
    writer = filename;
    name = "\"Else Statement Name Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
  
  public void setLine(double lineNum){
    begin = lineNum;
  }

  public AST_Type getType(){
    return AST_Type.Else;
  }
  
  public void haveAllInfo(){
    name = "\"Else Statement ("; 
    name += begin;
    name += " )\"";
  }
}
