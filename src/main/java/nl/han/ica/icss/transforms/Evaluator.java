package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private LinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new LinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new LinkedList<>();
        applyStylesheet(ast.root);

    }

    private void applyStylesheet(Stylesheet stylesheet) {
        applyStyleRule((Stylerule)stylesheet.getChildren().get(0));
    }

    private void applyStyleRule(Stylerule rule) {
        for (ASTNode node : rule.getChildren()) {
            if (node instanceof Declaration) {
                applyDeclaration((Declaration) node);
            }
        }
    }

    private void applyDeclaration(Declaration declaration) {
        declaration.expression = calculateExpression(declaration.expression);

    }

    private Expression calculateExpression(Expression expression) {
        if (expression instanceof Literal) {
            return (Literal) expression;
        } else {
            return evaluateExpression(expression);
        }
    }

    private Expression evaluateExpression(Expression expression) {
        if (expression instanceof AddOperation) {
            return evaluateAddOperation((AddOperation) expression);
        } else if (expression instanceof SubtractOperation) {
            return evaluateSubtractOperation((SubtractOperation) expression);
        } else if (expression instanceof MultiplyOperation) {
            return evaluateMultiplyOperation((MultiplyOperation) expression);
        }
        else {
            return expression;
        }
    }

    private Expression evaluateMultiplyOperation(MultiplyOperation expression) {
        // (waarschijnlijk) iets toevoegen over verschillende litterals
        PixelLiteral left = (PixelLiteral) evaluateExpression(expression.lhs);
        PixelLiteral right = (PixelLiteral) evaluateExpression(expression.rhs);
        return new PixelLiteral(left.value * right.value);
    }

    private Expression evaluateSubtractOperation(SubtractOperation expression) {
        // (waarschijnlijk) iets toevoegen over verschillende litterals
        PixelLiteral left = (PixelLiteral) evaluateExpression(expression.lhs);
        PixelLiteral right = (PixelLiteral) evaluateExpression(expression.rhs);
        return new PixelLiteral(left.value - right.value);
    }

    private Expression evaluateAddOperation(Operation expression) {
        // (waarschijnlijk) iets toevoegen over verschillende litterals
        PixelLiteral left = (PixelLiteral) evaluateExpression(expression.lhs);
        PixelLiteral right = (PixelLiteral) evaluateExpression(expression.rhs);
        return new PixelLiteral(left.value + right.value);
    }


}