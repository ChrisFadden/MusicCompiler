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
        } else if(token.get(0).type == Lexer.TokenType.HashTok){
          ParseCompileDirective(token,i,filename);       
        } else if(token.get(0).type == Lexer.TokenType.IfTok){
          ParseIfStatement(token,i,filename);
        } else if(token.get(0).type == Lexer.TokenType.ElseifTok){
          ParseIfStatement(token,i,filename); 
        } else if(token.get(0).type == Lexer.TokenType.CollectionTok){
          ParseCollectionName(token,i,filename);
        } else if(token.get(0).type == Lexer.TokenType.ProgramTok){
          ParseProgName(token,i,filename); 
        } else if(token.get(0).type == Lexer.TokenType.EndifTok){
        } else if(token.get(0).type == Lexer.TokenType.EndProgramTok){
        } else if(token.get(0).type == Lexer.TokenType.EndForTok){
        } else if(token.get(0).type == Lexer.TokenType.EndWhileTok){
        } else {
          ParseExpression(token,i,filename); 
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
     
    if(LexInput.get(LexInput.size()-1).type != Lexer.TokenType.EndParenTok){
      err = "You forgot parenthesis at the end of your function definition\n";
      err += "You may have also put invalid code after a function definition";
      ParseErrorReport(lineNum,filename,err);    
    }

    functionArgs.add(new Pair<String,String>());
    functionArgs.get(0).setA("");
    functionArgs.get(0).setB("");

    int j = 0;
    int k = 0; 
    for(int i = 4; i < LexInput.size()-1; i++)
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
          err = "Invalid function definition in argument ";
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
      err = "Invalid FOR loop declaration\n";
      err += "iterator is incorrect";
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
      err = "Invalid WHILE loop\nParenthesis must folllow WHILE";
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
        err = "Invalid WHILE loop\nToo many semicolons";
        ParseErrorReport(lineNum,filename,err);
        return; 
      }  
    }//end LexInput loop
  }//end Parse While Loop

  public void ParseCompileDirective(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){ 
    String err; 
    String importedFilename = "";
    String debugPrint = ""; 
    int lineStartJazzBlock;
    int lineEndJazzBlock;

    if(LexInput.get(1).data.equals("remix")){ 
      importedFilename = LexInput.get(2).data;
      if(LexInput.get(LexInput.size()-1).type != Lexer.TokenType.SemicolonTok){
        err = "Missing semicolon after import statement";
        ParseErrorReport(lineNum,filename,err);
      }
    } else if(LexInput.get(1).data.equals("Jazz")){
      lineStartJazzBlock = lineNum; 
    } else if(LexInput.get(1).data.equals("end Jazz")){
      lineEndJazzBlock = lineNum;
    } else if(LexInput.get(1).data.equals("Rehearse")){
      for(int i = 4; i < LexInput.size()-2; i++){
        debugPrint += LexInput.get(i).data;
      }//end rehearse lex input
    }
  }//end Parse CompileDirective
  public void ParseIfStatement(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
    String err;
    ArrayList<Lexer.Token> ifExpression = new ArrayList<Lexer.Token>(LexInput.size() - 3);
    if(LexInput.get(1).type != Lexer.TokenType.OpenParenTok){
      err = "You forgot an opening parenthesis in your IF/ELSEIF statement";
      ParseErrorReport(lineNum,filename,err);
    }
    if(LexInput.get(LexInput.size()-1).type != Lexer.TokenType.EndParenTok){
      err = "You forget a closing parenthesis in your IF/ELSEIF statement"; 
      ParseErrorReport(lineNum,filename,err); 
    }
    
    for(int i = 2; i < LexInput.size()-1; i++){
      ifExpression.add(LexInput.get(i));
    }

  }//end Parse If Statement
  
  public void ParseCollectionName(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
    String err;
    String collectionName;
    
    if(LexInput.get(1).type != Lexer.TokenType.NameTok){
      err = "Invalid Collection Name";
      ParseErrorReport(lineNum,filename,err);
      return; 
    }

    collectionName = LexInput.get(1).data;
    
    return;
  }//end Parse Collection
    
  public void ParseExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
    int i = 0; 
    for(Lexer.Token tk : LexInput){
      if(tk.type == Lexer.TokenType.EqualOpTok){
        ParseEqualExpression(LexInput,lineNum,filename,i);
        return; 
      }else if(tk.type == Lexer.TokenType.OpenParenTok){
        ParseOpenParenExpression(LexInput,lineNum,filename,i);
        return; 
      }else if(tk.type == Lexer.TokenType.BooleanOpTok){
        ParseBooleanExpression(LexInput,lineNum,filename,i);
        return;
      } else if(tk.type == Lexer.TokenType.BinaryOpTok){
        ParseArithmeticExpression(LexInput,lineNum,filename,i);
        return; 
      } else if(tk.type == Lexer.TokenType.CommaTok){
        ParseCommaExpression(LexInput,lineNum,filename,i); 
        return;
      } else if(tk.type == Lexer.TokenType.ColonTok){
        ParseColonExpression(LexInput,lineNum,filename,i);
      } else if(tk.type == Lexer.TokenType.DotTok){
        ParseDotExpression(LexInput,lineNum,filename,i);
      }
      i++;
    }

  }//end Parse Expression
  
  public void ParseEqualExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){
    int equalIndex = i;
    boolean equalOp = false;
    String err;
    
    for(int j = i+1; j < LexInput.size(); j++){
      if(LexInput.get(j).type == Lexer.TokenType.EqualOpTok){ 
        err = "You cannot have more than one equal sign in an expression";
        ParseErrorReport(lineNum,filename,err);  
      }
    }

    ArrayList<Lexer.Token> leftEqualOpArray = new ArrayList<Lexer.Token>(equalIndex);
    ArrayList<Lexer.Token> rightEqualOpArray = new ArrayList<Lexer.Token>(LexInput.size() - equalIndex - 2);

    for(int j = 0; i < equalIndex; j++){
      leftEqualOpArray.add(LexInput.get(j));
    }

    for(int j = equalIndex+1; j < LexInput.size(); j++){ 
      rightEqualOpArray.add(LexInput.get(j));
    }

    ParseExpression(rightEqualOpArray,lineNum,filename);
    ParseExpression(leftEqualOpArray,lineNum,filename);

  }//end ParseEqualExpression

  public void ParseBooleanExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){
    int boolIndex = i;
    String err;
    
    if(LexInput.size() - boolIndex - 2 >= 0){
      ArrayList<Lexer.Token> leftBoolOpArray = new ArrayList<Lexer.Token>(boolIndex);
      ArrayList<Lexer.Token> rightBoolOpArray = new ArrayList<Lexer.Token>(LexInput.size() - boolIndex - 2);

      for(int j = 0; j < boolIndex; j++){
        leftBoolOpArray.add(LexInput.get(j));
      }

      for(int j = boolIndex+1; j < LexInput.size(); j++){ 
        rightBoolOpArray.add(LexInput.get(j));
      }

      ParseExpression(rightBoolOpArray,lineNum,filename);
      ParseExpression(leftBoolOpArray,lineNum,filename);
    } else{
      err = "Invalid boolean expression";
      ParseErrorReport(lineNum,filename,err);
    }
  }//end Parse Boolean Expression

  public void ParseArithmeticExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){
    int arithIndex = i;
    String err;
    
    if((LexInput.size() - arithIndex - 2) >= 0){
      ArrayList<Lexer.Token> leftArithOpArray = new ArrayList<Lexer.Token>(arithIndex);
      ArrayList<Lexer.Token> rightArithOpArray = new ArrayList<Lexer.Token>(LexInput.size() - arithIndex - 2);

      for(int j = 0; j < arithIndex; j++){
        leftArithOpArray.add(LexInput.get(j));
      }

      for(int j = arithIndex+1; j < LexInput.size(); j++){ 
        rightArithOpArray.add(LexInput.get(j));
      }

      ParseExpression(rightArithOpArray,lineNum,filename);
      ParseExpression(leftArithOpArray,lineNum,filename);
    } else {
      err = "Invalid arithmetic expression";
      ParseErrorReport(lineNum,filename,err);
    }
  }//end Parse Arithmetic Expression
  
  public void ParseOpenParenExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){ 
    if(i > 0){
      //Parenthesis corresponds to a function call
      if(LexInput.get(i-1).type == Lexer.TokenType.NameTok){
        int endFuncCallIndex = i+1; 
        for(int j = i+1; j < LexInput.size(); j++){
          if(LexInput.get(j).type == Lexer.TokenType.EndParenTok){
            endFuncCallIndex = j;
          }
        }
        ArrayList<Lexer.Token> funcCallArray = new ArrayList<Lexer.Token>(endFuncCallIndex - (i-1));
          for(int j = i-1; j < endFuncCallIndex; j++){
            funcCallArray.add(LexInput.get(j));
          }
        ParseFunctionCallExpression(funcCallArray,lineNum,filename);
        return;
      } else {
        //nested in the expression is a ( , that isn't a function call 
        ArrayList<Lexer.Token> expression = new ArrayList<Lexer.Token>(LexInput.subList(i+1,LexInput.size()));
        ParseExpression(expression,lineNum,filename);  
        return; 
      }
    } else {
      //Expression starts with a (
      ArrayList<Lexer.Token> expression = new ArrayList<Lexer.Token>(LexInput.subList(i+1,LexInput.size()));
      ParseExpression(expression,lineNum,filename); 
      return; 
    }
  }//end Parse OpenParen Expression
  
  //This does just call ParseExpression, but it will return a FunctionAST.
  public void ParseFunctionCallExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename){
      String err; 
      if(LexInput.size() >= 2){ 
        String functionName = LexInput.get(0).data;
        ArrayList<Lexer.Token> functionExpression = new ArrayList<Lexer.Token>(LexInput.subList(2,LexInput.size())); 
        ParseExpression(functionExpression,lineNum,filename); 
      } else{
        err = "Invalid function call expression";
        ParseErrorReport(lineNum,filename,err);
      }
    return;
  }//end ParseFunction Call

  public void ParseCommaExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){
    int commaIndex = i;
    String err;
    
    if((LexInput.size() - commaIndex - 2) >= 0){
      ArrayList<Lexer.Token> leftCommaArray = new ArrayList<Lexer.Token>(commaIndex);
      ArrayList<Lexer.Token> rightCommaArray = new ArrayList<Lexer.Token>(LexInput.size() - commaIndex - 2);

      for(int j = 0; j < commaIndex; j++){
        leftCommaArray.add(LexInput.get(j));
      }

      for(int j = commaIndex+1; j < LexInput.size(); j++){ 
        rightCommaArray.add(LexInput.get(j));
      }
    } else {
      err = "Misplaced comma";
      ParseErrorReport(lineNum,filename,err);
    }
  }//end Parse Comma Expression
  
  public void ParseColonExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){
    String err; 
    String scope; 
    if(i > 0 && LexInput.size() > i+1){
      scope = LexInput.get(i-1).data; 
      if(LexInput.get(i+1).type != Lexer.TokenType.ColonTok){
        //Could be a range 
      }
      ArrayList<Lexer.Token> scopedExpression = new ArrayList<Lexer.Token>(LexInput.subList(2,LexInput.size()));
      ParseExpression(scopedExpression,lineNum,filename);
    }else {
      err = "Invalid Scope or Range operator";
      ParseErrorReport(lineNum,filename,err);
    }
    return;
  }//end Parse Colon Expression
  
  public void ParseDotExpression(ArrayList<Lexer.Token> LexInput, int lineNum, String filename, int i){
    String err;
    
    if(LexInput.size() - i - 2 >= 0){
      ArrayList<Lexer.Token> leftDotArray = new ArrayList<Lexer.Token>(i);
      ArrayList<Lexer.Token> rightDotArray = new ArrayList<Lexer.Token>(LexInput.size() - i - 2);
       
      for(int j = 0; j < i; j++){
        leftDotArray.add(LexInput.get(j));
      }

      for(int j = i+1; j < LexInput.size(); j++){ 
        rightDotArray.add(LexInput.get(j));
      }
    } else {
      err = "Misplaced period";
      ParseErrorReport(lineNum,filename,err);
    }
  }//end ParseDot Expression 
}//end class























