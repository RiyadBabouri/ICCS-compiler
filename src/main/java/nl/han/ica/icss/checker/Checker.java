package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import javax.swing.text.Style;
import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        checkStyleSheet(ast.root);
    }
    private void checkStyleSheet(Stylesheet sheet) {
        checkStylerule((Stylerule)sheet.getChildren().get(0));
    }

    private void checkStylerule(Stylerule rule) {
        for (ASTNode child: rule.getChildren()) {
            if(child instanceof Declaration) {
                checkDeclaration((Declaration)child);
            }
        }
    }

    private void checkDeclaration(Declaration declaration) {
        if (declaration.property.name.equals("width")) {
            if(!(declaration.expression instanceof PixelLiteral)) {
                declaration.setError("Property 'width' must be a PixelLiteral");
            }
        }
    }
}