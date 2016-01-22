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

  public Parser(ArrayList<ArrayList<Lexer.Token>> tokens){     
    unchangedLexInput = tokens;
    //lexInput = tokens;
    
    System.out.print("This file has line number equal to: ");
    System.out.println(tokens.size());
    
    ProgramName = "";
    
    for(ArrayList<Lexer.Token> token : unchangedLexInput)
    {
      if(token.size() > 0){ 
        if(token.get(0).type == Lexer.TokenType.FunctionTok){
        } 
        else if(token.get(0).type == Lexer.TokenType.ProgramTok){
          ParseProgName(token); 
        }
      }//end token size check
    }//end Lex loop
  }//end Parser constructor
  
  public void ParseProgName(ArrayList<Lexer.Token> LexInput){  
        for(Lexer.Token token : LexInput){
          if(token.type == Lexer.TokenType.NameTok){
            ProgramName += token.data;
          } else if(token.type == Lexer.TokenType.ProgramTok){
          } else{
            System.out.print("\033[1m\033[33m");
            System.out.print("Parse Error:  Only program names are allowed ");
            System.out.print("after a PROGRAM statement");
            System.out.println("\033[0m");
            break;
          }
      }  
    System.out.print("The program name is: ");
    System.out.println(ProgramName);
  }//end ParseProgName

}//end class
