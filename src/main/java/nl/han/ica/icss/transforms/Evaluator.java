package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Evaluator implements Transform {

    private LinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new LinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        Stylesheet stylesheet = ast.root;

        evaluateStylesheet(stylesheet);
    }

    private void evaluateStylesheet(Stylesheet stylesheet) {
        variableValues.addFirst(new HashMap<>());
        List<ASTNode> nodesToDelete = new ArrayList<>();

        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                evaluateVariableAssignment((VariableAssignment) child);
                nodesToDelete.add(child);
            } else if (child instanceof Stylerule) {
                evaluateStyleRule((Stylerule) child);
            }
        }

        variableValues.removeFirst();
        nodesToDelete.forEach(stylesheet::removeChild);
    }

    private void evaluateStyleRule(Stylerule stylerule) {
        variableValues.addFirst(new HashMap<>());
        ArrayList<ASTNode> nodesToAdd = new ArrayList<>();

        for (ASTNode child : stylerule.body) {
            evaluateStyleRuleBody(child, nodesToAdd);
        }

        variableValues.removeFirst();
        stylerule.body = nodesToAdd;
    }

    private void evaluateStyleRuleBody(ASTNode ruleBody, ArrayList<ASTNode> parentBody) {
        if (ruleBody instanceof VariableAssignment) {
            evaluateVariableAssignment((VariableAssignment) ruleBody);
        } else if (ruleBody instanceof Declaration) {
            evaluateDeclaration((Declaration) ruleBody);
            parentBody.add(ruleBody);
        } else if (ruleBody instanceof IfClause) {
            evaluateIfClause((IfClause) ruleBody, parentBody);
        }
    }

    private void evaluateVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;
        variableAssignment.expression = evaluateExpression(expression);

        variableValues.getFirst().put(variableAssignment.name.name, (Literal) variableAssignment.expression);
    }

    private Literal evaluateExpression(Expression expression) {
        if (expression instanceof Operation) {
            return evaluateOperation((Operation) expression);
        }

        if (expression instanceof VariableReference) {
            return getVariableLiteral(((VariableReference) expression).name, variableValues);
        }

        return (Literal) expression;
    }

    private Literal evaluateOperation(Operation operation) {
        Literal left = getLiteralFromExpression(operation.lhs);
        Literal right = getLiteralFromExpression(operation.rhs);

        int leftValue = getLiteralValue(left);
        int rightValue = getLiteralValue(right);

        if (operation instanceof AddOperation) {
            return createLiteral(left, leftValue + rightValue);
        } else if (operation instanceof SubtractOperation) {
            return createLiteral(left, leftValue - rightValue);
        } else if (operation instanceof MultiplyOperation) {
            if (right instanceof ScalarLiteral) {
                return createLiteral(left, leftValue * rightValue);
            } else {
                return createLiteral(right, leftValue * rightValue);
            }
        } else {
            return createLiteral(left, leftValue / rightValue);
        }
    }

    private Literal getLiteralFromExpression(Expression expression) {
        if (expression instanceof Operation) {
            return evaluateOperation((Operation) expression);
        } else if (expression instanceof VariableReference) {
            return getVariableLiteral(((VariableReference) expression).name, variableValues);
        } else {
            return (Literal) expression;
        }
    }

    private int getLiteralValue(Literal literal) {
        if (literal instanceof PixelLiteral) {
            return ((PixelLiteral) literal).value;
        } else if (literal instanceof ScalarLiteral) {
            return ((ScalarLiteral) literal).value;
        } else {
            return ((PercentageLiteral) literal).value;
        }
    }

    private Literal createLiteral(Literal literal, int value) {
        if (literal instanceof PixelLiteral) {
            return new PixelLiteral(value);
        } else if (literal instanceof ScalarLiteral) {
            return new ScalarLiteral(value);
        } else {
            return new PercentageLiteral(value);
        }
    }

    private Literal getVariableLiteral(String key, LinkedList<HashMap<String, Literal>> variableValues) {
        for (HashMap<String, Literal> variableValue : variableValues) {
            Literal result = variableValue.get(key);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    private void evaluateDeclaration(Declaration declaration) {
        declaration.expression = evaluateExpression(declaration.expression);
    }

    private void evaluateIfClause(IfClause ifClause, ArrayList<ASTNode> parentBody) {
        ifClause.conditionalExpression = evaluateExpression(ifClause.conditionalExpression);

        if (((BoolLiteral) ifClause.conditionalExpression).value) {
            if (ifClause.elseClause != null) {
                ifClause.elseClause.body = new ArrayList<>();
            }
        } else {
            if (ifClause.elseClause == null) {
                ifClause.body = new ArrayList<>();
            } else {
                ifClause.body = ifClause.elseClause.body;
                ifClause.elseClause.body = new ArrayList<>();
            }
        }

        for (ASTNode child : ifClause.getChildren()) {
            evaluateStyleRuleBody(child, parentBody);
        }
    }
}
