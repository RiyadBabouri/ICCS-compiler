grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';

//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
DIV: '/';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---
stylesheet: (variableAssignment | styleRule)*;
variableAssignment: variableReference ASSIGNMENT_OPERATOR (expression | value) SEMICOLON;
styleRule: selector OPEN_BRACE styleRuleBody CLOSE_BRACE;
selector: ID_IDENT #idSelector | CLASS_IDENT #classSelector | LOWER_IDENT #tagSelector;
declaration: property COLON (expression | value) SEMICOLON;
property: LOWER_IDENT;
booleanLiteral: TRUE | FALSE;
value: COLOR #colorLiteral |  booleanLiteral #boolLiteral;
calcValue: PIXELSIZE #pixelLiteral | SCALAR #scalarLiteral | PERCENTAGE #percentageLiteral;
variableReference: CAPITAL_IDENT;
expression:
        calcValue #cVal | variableReference #var
        | expression MUL expression #multiplyOperation
        | expression PLUS expression #addOperation
        | expression MIN expression #subtractOperation;

styleRuleBody: (declaration | variableAssignment | ifClause)*;
ifClause: IF BOX_BRACKET_OPEN (variableReference | booleanLiteral) BOX_BRACKET_CLOSE OPEN_BRACE styleRuleBody CLOSE_BRACE elseClause?;
elseClause: ELSE OPEN_BRACE styleRuleBody CLOSE_BRACE;

