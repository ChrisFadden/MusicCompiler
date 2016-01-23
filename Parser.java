//Parser class
//
//This will take in a lexer ArrayList of tokens and parse them to form
//an AST eventually

import java.util.ArrayList;
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
  
  public void ParseProgName(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){  
    for(Lexer.Token token : LexInput){
      if(token.type == Lexer.TokenType.NameTok){
        ProgramName += token.data;
      } else if(token.type == Lexer.TokenType.ProgramTok){
      } else{
        System.out.print("\033[1m\033[33m");
        System.out.print("Parse Error(");
        System.out.print(filename);
        System.out.print(": ");
        System.out.print(lineNum);
        System.out.print(") Only program names are allowed ");
        System.out.print("after a PROGRAM statement");
        System.out.println("\033[0m");
        break;
      }
    }//end token array loop  
  }//end ParseProgName
  
  public void ParseFunction(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
    String functionName; 
    String returnType; 
    ArrayList<ArrayList<String>> functionArgs; //type = 0, name = 1
        
    if(LexInput.get(1).type == Lexer.TokenType.NameTok){
      returnType = Lexer.get(1).data; 
    } else if (LexInput.get(1).type == Lexer.TokenType.TypeTok){
      returnType = Lexer.get(1).data;
    }
  
    if(LexInput.get(2).type == Lexer.TokenType.NameTok){
      functionName = Lexer.get(2).data; 
    } else {
        System.out.print("\033[1m\033[33m");
        System.out.print("Parse Error(");
        System.out.print(filename);
        System.out.print(": ");
        System.out.print(lineNum);
        System.out.print(") Invalid Function Name");
        System.out.println("\033[0m");
    }
  
    functionArgs.add(new ArrayList<String>(Arrays.asList("","")));
    int  
    for(int i = 2; i < LexInput.size(); i++)
    {
      if(LexInput.get(i).type == Lexer.TokenType.NameTok){
        functionArgs.get(i).set(0,LexInput.get(i).data);
      }else if(LexInput.get(i).type == Lexer.TokenType.TypeTok){
        functionArgs.get(i).set(0,LexInput.get(i).data); 
      }else if(LexInput.get(i).type == Lexer.TokenType.CommaTok){
        functionArgs.add(new ArrayList<String>(Arrays.asList("",""))); 
      }
    }//end arg loop
  }//end ParseFunction

}//end class























