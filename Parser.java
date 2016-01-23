//Parser class
//
//This will take in a lexer ArrayList of tokens and parse them to form
//an AST eventually

import java.util.*;
import java.io.*;

public class Parser {
  
  private ArrayList<ArrayList<Lexer.Token>> unchangedLexInput;
  //private ArrayList<ArrayList<Lexer.Token>> lexInput;
  
  private String ProgramName;

  public Parser(ArrayList<ArrayList<Lexer.Token>> tokens, String filename){     
    unchangedLexInput = tokens;
    //lexInput = tokens;
       
    ProgramName = "";
    
    int i = 1;
    for(ArrayList<Lexer.Token> token : unchangedLexInput)
    {
      if(token.size() > 0){ 
        if(token.get(0).type == Lexer.TokenType.FunctionTok){
          ParseFunction(token,i,filename); 
        } 
        else if(token.get(0).type == Lexer.TokenType.ProgramTok){
          ParseProgName(token,i,filename); 
        }
      }//end token size check
      i++;
    }//end Lex loop
  }//end Parser constructor
  
  public void ParseErrorReport(int lineNum, String filename, String err){
        System.out.print("\033[1m\033[33m");
        System.out.print("Parse Error(");
        System.out.print(filename);
        System.out.print(": ");
        System.out.print(lineNum);
        System.out.print(") ");
        System.out.print(err);
        System.out.println("\033[0m");
    return;
  }

  public void ParseProgName(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){  
    
    String err;
    
    for(Lexer.Token token : LexInput){
      if(token.type == Lexer.TokenType.NameTok){
        ProgramName += token.data;
      } else if(token.type == Lexer.TokenType.ProgramTok){
      } else{
          err = "Only program names are allowed after a PROGRAM statement";
          ParseErrorReport(lineNum,filename,err);
        break;      
      }
    }//end token array loop  
  }//end ParseProgName
  
  public void ParseFunction(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
    String functionName = ""; 
    String returnType = ""; 
    ArrayList<ArrayList<String>> functionArgs = new ArrayList<ArrayList<String>>(); //type = 0, name = 1
    
    String err;
    
    if(LexInput.get(1).type == Lexer.TokenType.NameTok){
      returnType = LexInput.get(1).data; 
    } else if (LexInput.get(1).type == Lexer.TokenType.TypeTok){
      returnType = LexInput.get(1).data; 
    } else {
      err = "Invalid return type";
      ParseErrorReport(lineNum,filename,err);
      return; 
    }
  
    if(LexInput.get(2).type == Lexer.TokenType.NameTok){
      functionName = LexInput.get(2).data; 
    } else {  
      err = "Invalid Function Name";
      ParseErrorReport(lineNum,filename,err); 
      return; 
    }
    
    if(LexInput.get(3).type != Lexer.TokenType.OpenParenTok)
    { 
      err = "You forgot parenthesis in your function definition";
      ParseErrorReport(lineNum,filename,err); 
      return; 
    }
    functionArgs.add(new ArrayList<String>(Arrays.asList("","")));
    
    int j = 0;
    int k = 0; 
    for(int i = 4; i < LexInput.size(); i++)
    {
      if(LexInput.get(i).type == Lexer.TokenType.NameTok){
        if(k < functionArgs.get(j).size())
          functionArgs.get(j).set(k,LexInput.get(i).data);
        k++;
      }else if(LexInput.get(i).type == Lexer.TokenType.TypeTok){
        if(k < functionArgs.get(j).size()) 
          functionArgs.get(j).set(k,LexInput.get(i).data);   
        k++; 
      }else if(LexInput.get(i).type == Lexer.TokenType.CommaTok){
        functionArgs.add(new ArrayList<String>(Arrays.asList("",""))); 
        if(k != 2){ 
          err = "Invalid function definition in argument ";
          err += j+1;
          err += " (mismatch between types and variables)";
          ParseErrorReport(lineNum,filename,err); 
          return; 
        }
        k = 0;   
        j++;
      } else if(LexInput.get(i).type == Lexer.TokenType.EndParenTok){
        return;
      } else {
        err = "Invalid function definition in argument ";
        err += j+1;
        err += " invalid character: ";
        err += LexInput.get(i).data;
        ParseErrorReport(lineNum,filename,err);
        return; 
      }
    }//end arg loop 
  
    System.out.println(functionName);  
  
  }//end ParseFunction

}//end class























