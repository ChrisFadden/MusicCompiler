import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
  public static enum TokenType {
    //Number/Operation Tokens
    NumberTok("-?[0-9]+"),
    BinaryOpTok("[*|/|+|-]"),
    BooleanOpTok("(<=|>=|<|>|==|!=)"),
    EqualOpTok("(=)"),
    WhitespaceTok("[ \t\f\r\n]+"),
     
    //If tokens 
    IfTok("(if|IF)"),
    ElseifTok("(elseif|ELSEIF)"),
    ElseTok("(else|ELSE)"),
    EndifTok("(end if|END IF)"),
    
    //Loop tokens
    ForTok("(for|FOR)"),
    WhileTok("(while|WHILE)"),
    EndForTok("(end for|END FOR)"),
    EndWhileTok("(end while|END WHILE)"),
    
    //Function Tokens
    FunctionTok("(function|FUNCTION)"),
    EndFunctionTok("(end function|END FUNCTION)"),
    ReturnTok("(return|RETURN)"),
    
    //Class Tokens
    ClassTok("(class|CLASS)"),
    EndClassTok("(end class|END CLASS)"),

    //Program tokens
    ProgramTok("(program|PROGRAM)"),
    EndProgramTok("(end program|END PROGRAM)"),

    //Type Tokens
    TypeTok("(int|double|char|bool|INT|DOUBLE|CHAR|BOOL)"),
    NameTok("[_a-zA-Z][_a-zA-Z0-9]{0,100}"),
    
    //Comment Tokens
    HashTok("(#)"),

    //Character Tokens
    OpenParenTok("[(]"),
    EndParenTok("[)]"),
    OpenBracketTok("(\\[)"),
    EndBracketTok("(\\])"),
    OpenBraceTok("(\\{)"),
    EndBraceTok("(\\})"),
    CommaTok("(,)"),
    ColonTok("(:)"), 
    QuoteTok("(\")"),
    SemicolonTok("(;)"),
    AtTok("(@)"),
    ExclamationMarkTok("(!)"),
    DollarSignTok("(\\$)"),
    PercentTok("(%)"),
    CarotTok("\\^"),
    AmpersandTok("(&)"),
    DotTok("(\\.)");
    
    public final String pattern;

    private TokenType(String pattern){
      this.pattern = pattern;
    }
  }
  
  public static class Token {
    public TokenType type;
    public String data;

    public Token(TokenType type, String data){
      this.type = type;
      this.data = data;
    }
  }

  public static ArrayList<Token> lex(String input) {
    // The tokens to return
    ArrayList<Token> tokens = new ArrayList<Token>();

    // Lexer logic begins here
    StringBuffer tokenPatternsBuffer = new StringBuffer();
    for (TokenType tokenType : TokenType.values())
      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", 
                                 tokenType.name(), 
                                 tokenType.pattern));

    Pattern tokenPatterns = Pattern.compile(
                            new String(tokenPatternsBuffer.substring(1)));

    // Begin matching tokens
    Matcher matcher = tokenPatterns.matcher(input);
    
    while(matcher.find()){
      for(TokenType tk : TokenType.values()){
        if(matcher.group(TokenType.WhitespaceTok.name()) != null)
          continue;
        else if(matcher.group(tk.name()) != null){
          tokens.add(new Token(tk, matcher.group(tk.name())));
          break;
        } //end if token in group 
      }//end loop over tokens
    }//end finding in the matcher

    return tokens;
  }

}

