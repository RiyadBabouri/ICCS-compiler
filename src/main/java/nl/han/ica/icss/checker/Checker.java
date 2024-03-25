package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Checker {
    private final LinkedList<HashMap<String, ExpressionType>> variableTypes;

    public Checker() {
        this.variableTypes = new LinkedList<>();
    }

    public void check(AST ast) {
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(ASTNode astNode) {
        Stylesheet stylesheet = (Stylesheet) astNode;
        variableTypes.addFirst(new HashMap<>());

        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment(child);
            } else if (child instanceof Stylerule) {
                variableTypes.addFirst(new HashMap<>());
                checkStyleRule(child);
                variableTypes.removeFirst();
            }
        }
        variableTypes.clear();
    }

    private void checkStyleRule(ASTNode astNode) {
        Stylerule stylerule = (Stylerule) astNode;
        checkRuleBody(stylerule.body);
    }

    private void checkRuleBody(ArrayList<ASTNode> astNodes) {
        for (ASTNode astNode : astNodes) {
            if (astNode instanceof Declaration) {
                checkDeclaration(astNode);
            } else if (astNode instanceof IfClause) {
                checkIfClause(astNode);
            } else if (astNode instanceof VariableAssignment) {
                checkVariableAssignment(astNode);
            }
        }
    }

    private void checkIfClause(ASTNode astNode) {
        IfClause ifClause = (IfClause) astNode;
        variableTypes.addFirst(new HashMap<>());

        Expression conditionalExpression = ifClause.getConditionalExpression();
        ExpressionType expressionType = checkExpressionType(conditionalExpression);

        if (expressionType != ExpressionType.BOOL) {
            ifClause.setError("Conditional expression must be of boolean literal type.");
        }

        checkRuleBody(ifClause.body);

        variableTypes.removeFirst();

        if (ifClause.getElseClause() != null) {
            variableTypes.addFirst(new HashMap<>());
            checkElseClause(ifClause.getElseClause());
            variableTypes.removeFirst();
        }
    }

    private void checkElseClause(ASTNode astNode) {
        ElseClause elseClause = (ElseClause) astNode;
        checkRuleBody(elseClause.body);
    }

    private void checkDeclaration(ASTNode astNode) {
        Declaration declaration = (Declaration) astNode;
        ExpressionType expressionType = checkExpression(declaration.expression);

        switch (declaration.property.name) {
            case "color":
            case "background-color":
                if (expressionType != ExpressionType.COLOR) {
                    astNode.setError("Color value can only be of type color literal.");
                }
                break;
            case "width":
            case "height":
                if (expressionType != ExpressionType.PIXEL && expressionType != ExpressionType.PERCENTAGE) {
                    astNode.setError("Width and height value can only be of type pixel or percentage literal.");
                }
                break;
            default:
                astNode.setError("The only allowed properties are: height, weight, color, or background-color.");
                break;
        }
    }

    private void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment variableAssignment = (VariableAssignment) astNode;
        VariableReference variableReference = variableAssignment.name;
        ExpressionType expressionType = checkExpression(variableAssignment.expression);

        if (expressionType == null || expressionType == ExpressionType.UNDEFINED) {
            astNode.setError("Variable assignment fails because the expression type is undefined.");
            return;
        }

        ExpressionType previousExpressionType = getVariableType(variableReference.name);
        if (variableTypeChanged(expressionType, previousExpressionType)) {
            astNode.setError("A variable cannot change from type: " + previousExpressionType + " to type: " + expressionType);
        }

        putVariableType(variableReference.name, expressionType);
    }

    private ExpressionType checkExpression(ASTNode astNode) {
        Expression expression = (Expression) astNode;

        if (expression instanceof Operation) {
            return checkOperation((Operation) expression);
        }

        return checkExpressionType(expression);
    }

    private ExpressionType checkOperation(Operation operation) {
        ExpressionType left;
        ExpressionType right;

        if (operation.lhs instanceof Operation) {
            left = checkOperation((Operation) operation.lhs);
        } else {
            left = checkExpressionType(operation.lhs);
        }

        if (operation.rhs instanceof Operation) {
            right = checkOperation((Operation) operation.rhs);
        } else {
            right = checkExpressionType(operation.rhs);
        }

        if (left == ExpressionType.COLOR || right == ExpressionType.COLOR ||
                left == ExpressionType.BOOL || right == ExpressionType.BOOL) {
            operation.setError("Booleans and colors are not allowed in an operation.");
            return ExpressionType.UNDEFINED;
        }
        if (operation instanceof MultiplyOperation) {
            if (left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
                operation.setError("Multiplying is only allowed with at least one scalar value.");
                return ExpressionType.UNDEFINED;
            } else {
                if (left != ExpressionType.SCALAR) {
                    return left;
                } else {
                    return right;
                }
            }
        } else if ((operation instanceof SubtractOperation || operation instanceof AddOperation) && left != right) {
            operation.setError("Add and subtract operations are only allowed with the same type literal.");
            return ExpressionType.UNDEFINED;
        }

        return left;
    }


    private ExpressionType checkExpressionType(Expression expression) {
        if (expression instanceof VariableReference) {
            return checkVariableReference((VariableReference) expression);
        } else if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else if (expression instanceof ScalarLiteral) {
            return ExpressionType.SCALAR;
        } else if (expression instanceof BoolLiteral) {
            return ExpressionType.BOOL;
        }

        return ExpressionType.UNDEFINED;
    }

    private ExpressionType checkVariableReference(VariableReference variableReference) {
        ExpressionType expressionType = getVariableType(variableReference.name);
        if (expressionType == null) {
            variableReference.setError("Variable is not yet declared or is not in the same scope.");
            return ExpressionType.UNDEFINED;
        }

        return expressionType;
    }


    private void putVariableType(String name, ExpressionType type) {
        variableTypes.getFirst().put(name, type);
    }

    private ExpressionType getVariableType(String name) {
        for (HashMap<String, ExpressionType> scope : variableTypes) {
            ExpressionType type = scope.get(name);
            if (type != null) {
                return type;
            }
        }

        return null;
    }

    private boolean variableTypeChanged(ExpressionType currentType, ExpressionType previousType) {
        return (previousType != null) && currentType != previousType;
    }
}
