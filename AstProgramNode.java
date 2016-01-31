import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class AstProgramNode extends AstNode{
  String ProgramName;
  public AstProgramNode(FileWriter filename){
    writer = filename;
    name = "\"Program Name Not Set\"";
    children = new ArrayList<AstNode>(); 
  }
  public void setName(String newName, int lineNumber){
    ProgramName = newName;
    name = "\"Program ";
    name += ProgramName;
    name += " (";
    name += lineNumber;
    name += ")\"";
  }
  
  public AST_Type getType(){
    return AST_Type.Program;
  }

  public FileWriter getFileWriter(){
    return writer;
  }
}
