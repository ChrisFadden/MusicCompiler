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
        } else if(token.get(0).type == Lexer.TokenType.ForTok){
          ParseForLoop(token,i,filename); 
        } else if(token.get(0).type == Lexer.TokenType.WhileTok){
          ParseWhileLoop(token,i,filename);
        } else if(token.get(0).type == Lexer.TokenType.ProgramTok){
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
     
    ArrayList<Pair<String,String>> functionArgs = new ArrayList<Pair<String,String>>();

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
     
    functionArgs.add(new Pair<String,String>());
    functionArgs.get(0).setA("");
    functionArgs.get(0).setB("");

    int j = 0;
    int k = 0; 
    for(int i = 4; i < LexInput.size(); i++)
    {
      if(LexInput.get(i).type == Lexer.TokenType.NameTok){
        if(k > 0){
          functionArgs.get(j).setB(LexInput.get(i).data);
        }else{
          functionArgs.get(j).setA(LexInput.get(i).data);
        }
        k++;
      }else if(LexInput.get(i).type == Lexer.TokenType.TypeTok){
        if(k == 0){ 
          functionArgs.get(j).setA(LexInput.get(i).data);   
        } else{
          err = "Invalid function definition in arguemnt ";
          err += j+1;
          err += " (you cannot pass in a type as a variable)";
          ParseErrorReport(lineNum,filename,err);
        }
        k++; 
      }else if(LexInput.get(i).type == Lexer.TokenType.CommaTok){
        functionArgs.add(new Pair<String,String>());
        functionArgs.get(0).setA("");
        functionArgs.get(0).setB(""); 
        
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
   
  }//end ParseFunction
  
  public void ParseForLoop(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
    String err = "";
    String iter = "";
    ArrayList<Lexer.Token> Collection = new ArrayList<Lexer.Token>(LexInput.size() - 3);
    
    if(LexInput.get(1).type == Lexer.TokenType.NameTok){
      iter = LexInput.get(1).data; 
    }else{
      err = "Invalid iterator in FOR loop";
      ParseErrorReport(lineNum,filename,err);
      return; 
    }

    if(LexInput.get(2).type != Lexer.TokenType.InTok){
      err = "Invalid FOR loop declaration; iterator is incorrect";
      ParseErrorReport(lineNum,filename,err);
      return; 
    }
    
    for(int i = 3; i < LexInput.size(); i++)
    {
      //Copy the LexInput into Collection, minus the first 3 tokens
      Collection.add(LexInput.get(i)); 
    } 
    //Parse Collection with the expression parser.
  }//end Parse For loop

  public void ParseWhileLoop(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){ 
    /* These conditions will be passed to the expression parser
     * which can then better describe what exactly the while loop
     * is doing */
    
    ArrayList<Lexer.Token> InitCondition = new ArrayList<Lexer.Token>(); 
    ArrayList<Lexer.Token> TermCondition = new ArrayList<Lexer.Token>();
    ArrayList<Lexer.Token> IterCondition = new ArrayList<Lexer.Token>();
    
    String err;

    int j = 0;

    if(LexInput.get(1).type != Lexer.TokenType.OpenParenTok){
      err = "Parse Error: Invalid WHILE loop.  Parenthesis must folllow WHILE";
      ParseErrorReport(lineNum,filename,err);
      return; 
    }

    for(int i = 2; i < LexInput.size() - 3; i++){
      if(LexInput.get(i+j).type == Lexer.TokenType.SemicolonTok){ 
        j++;
      }
      if(j == 0)
        InitCondition.add(LexInput.get(i));
      if(j == 1)
        TermCondition.add(LexInput.get(i+1));
      if(j == 2)
        IterCondition.add(LexInput.get(i+2));
      if(j > 2){
        err = "Parse Error: Invalid WHILE loop. Too many semicolons";
        ParseErrorReport(lineNum,filename,err);
        return; 
      }  
    }//end LexInput loop

  }//end Parse While Loop
}//end class























