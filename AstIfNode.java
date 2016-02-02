import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstIfNode extends AstNode{
  
  public AstIfNode(FileWriter filename){
    writer = filename;
    name = "\"If Statement Name Not Set\"";
    begin = -1;
    end = -1;
    children = new ArrayList<AstNode>(); 
  }
   
  public AST_Type getType(){
    return AST_Type.If;
  }
  
  public void haveAllInfo(){
    name = "\"If Statement ("; 
    name += begin;
    name += ":";
    name += end;
    name += " )\"";
  }
}
