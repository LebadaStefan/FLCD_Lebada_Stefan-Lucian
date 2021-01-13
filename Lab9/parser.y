%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}

%token intreg
%token booli
%token stringi
%token oare
%token altfel
%token inject
%token eject
%token ejectS
%token arrayi
%token whale
%token ford
%token si
%token sau
%token id
%token ct
%token plus
%token MINUS
%token MULTIPLY
%token DIVISION
%token REMAINDER
%token LESS
%token LESS_OR_EQUAL
%token EQUAL
%token GREATER_OR_EQUAL
%token GREATER
%token ASSIGN
%token OPEN_ACCOL
%token CLOSED_ACCOL
%token OPEN_SQUARE
%token CLOSED_SQUARE
%token OPEN_ROUND
%token CLOSED_ROUND
%token SEMI_COL
%token COMMA

%start program

%%

program: StmtList;

StmtList: Stmt StmtList
        | Stmt;

Stmt: Decl 
    | Ifstmt
    | Forstmt
    | Assignstmt
    | Iostmt;

Decl: DType id SEMI_COL
    | DType id COMMA Cont;
Cont: id COMMA Cont
    | id SEMI_COL;

DType: intreg OPEN_SQUARE ct CLOSED_SQUARE
     | stringi OPEN_SQUARE ct CLOSED_SQUARE
     | intreg
     | stringi
     | booli;

Ifstmt: oare OPEN_ROUND Condition CLOSED_ROUND OPEN_ACCOL StmtList CLOSED_ACCOL altfel OPEN_ACCOL StmtList CLOSED_ACCOL
      | oare OPEN_ROUND Condition CLOSED_ROUND OPEN_ACCOL StmtList CLOSED_ACCOL;

Condition: Expression Relation Expression si Condition
         | Expression Relation Expression sau Condition
         | Expression Relation Expression;

Relation: LESS
        | LESS_OR_EQUAL
        | EQUAL
        | GREATER_OR_EQUAL
        | GREATER;

Forstmt: ford ForCond ForBody;

ForCond: OPEN_ROUND Assignstmt Condition SEMI_COL Assignstmt CLOSED_ROUND;

ForBody: OPEN_ACCOL StmtList CLOSED_ACCOL;

Assignstmt: id ASSIGN Expression SEMI_COL;
Iostmt: Istmt
      | Ostmt;

Istmt: inject id SEMI_COL;

Ostmt: eject OPEN_ROUND id CLOSED_ROUND SEMI_COL
     | ejectS OPEN_ROUND ct CLOSED_ROUND SEMI_COL;

Expression: ArithmExpr;

ArithmExpr : term
           | term plus ArithmExpr
           | term MINUS ArithmExpr
           | term MULTIPLY ArithmExpr
           | term DIVISION ArithmExpr
           | term REMAINDER ArithmExpr
           | OPEN_ROUND ArithmExpr CLOSED_ROUND

term : id | ct ;    

%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if (argc > 1) 
    yyin = fopen(argv[1], "r");
  if ( (argc > 2) && ( !strcmp(argv[2], "-d") ) ) 
    yydebug = 1;
  if ( !yyparse() ) 
    fprintf(stderr,"\t No errors detected\n");
}