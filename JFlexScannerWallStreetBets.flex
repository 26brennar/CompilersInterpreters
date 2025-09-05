package scanner;
/**
* This file defines a simple lexer for the compilers course 2017-2018
* Comment this file
*/
import java.io.*;


%%
/* lexical functions */
/* specify that the class will be called Scanner and the function to get the next
 * token is called nextToken.  
 */
%class ScannerWSB
%unicode
%line
%public
%function nextToken
/*  return String objects - the actual lexemes */
/*  returns the String "EOF: at end of file */
%type String
%eofval{
return "EOF";
%eofval}

/**
 * Pattern definitions
 */
AllLetters = [a-zA-Z]
LowercaseLetters = [a-z]
Digit = [0-9]
Punctuation = [.|_|-|\-|'|’|\"|\“|\”|:|!|?|(|)|\[|\]|/]
Symbol = [$|#|@|%|\^|&|*|+|=|<|>|~]
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
AllCharacters = [^,]+

UserID = ("pf")({LowercaseLetters} | {Digit}){4}
SubredditID = (2th52)
Time = {Digit}{10}
PostURL = ("https://old.reddit.com/r/wallstreetbets/"){AllCharacters}
AttachmentURL = ("https://"){AllCharacters}
Domain = {AllLetters}+[.]{AllLetters}+([.]{AllLetters})?
Word = {AllLetters}+
Number = {Digit}+

%%
/**
 * lexical rules
 */
{UserID} {return "USER ID: " + yytext(); }
{SubredditID} {return "SUBREDDIT ID: " + yytext();}
{Time} {return "POST CREATION TIME: " + yytext();}
{PostURL} {return "POST URL: " + yytext();}
{AttachmentURL} {return "ATTACHMENT URL: " + yytext();}
{Domain} {return "DOMAIN: " + yytext();}
{Word} {return "WORD: " + yytext(); }
{Number} {return "NUMBER: " + yytext(); }
{Punctuation} {return "PUNCTUATION: " + yytext();}
{Symbol} {return "SYMBOL: " + yytext(); }
{WhiteSpace}		{}
[,]			{}
[^] {return "UNKNOWN: " + yytext();}