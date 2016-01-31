import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstFileNode extends AstNode{
  String FileName;
  public AstFileNode(FileWriter filename){
    writer = filename;
    name = "\"File Name Not Set\"";
    children = new ArrayList<AstNode>(); 
  }
  public void setName(String newName){
    FileName = newName;
  }
  
  public AST_Type getType(){
    return AST_Type.File;
  }

  public FileWriter getFileWriter(){
    return writer;
  }

  public void haveAllInfo(){
    name = "\"File: ";
    name += FileName;
    name += "\""; 
  }

}
