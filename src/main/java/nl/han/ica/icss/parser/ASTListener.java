package nl.han.ica.icss.parser;

import java.util.Properties;
import java.util.Stack;


import com.google.errorprone.annotations.Var;
import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import org.checkerframework.checker.units.qual.C;

import javax.swing.text.html.HTML;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
		currentContainer.push(ast.root);
	}
	public AST getAST() {
		return ast;
	}

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet sheet = new Stylesheet();
		currentContainer.push(sheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet sheet = (Stylesheet) currentContainer.pop();
		ast.root = sheet;
	}

	@Override
	public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		Stylerule rule = new Stylerule();
		currentContainer.push(rule);
	}
	@Override
	public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		Stylerule rule =  (Stylerule) currentContainer.pop();
		currentContainer.peek().addChild(rule);
	}

	@Override
	public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
		ClassSelector classSelector = new ClassSelector(ctx.getText());
		currentContainer.push(classSelector);
	}

	@Override
	public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
		ClassSelector classSelector = (ClassSelector) currentContainer.pop();
		currentContainer.peek().addChild(classSelector);
	}

	@Override
	public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
		IdSelector idSelector = new IdSelector(ctx.getText());
		currentContainer.push(idSelector);
	}

	@Override
	public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
		IdSelector idSelector = (IdSelector) currentContainer.pop();
		currentContainer.peek().addChild(idSelector);
	}

	@Override
	public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
		TagSelector tagSelector = new TagSelector(ctx.getText());
		currentContainer.push(tagSelector);
	}

	@Override
	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
		TagSelector tagSelector = (TagSelector) currentContainer.pop();
		currentContainer.peek().addChild(tagSelector);
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = new Declaration();
		currentContainer.push(declaration);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = (Declaration) currentContainer.pop();
			currentContainer.peek().addChild(declaration);
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		PropertyName property = new PropertyName(ctx.getText());
		currentContainer.push(property);

	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		PropertyName property = (PropertyName) currentContainer.pop();
		currentContainer.peek().addChild(property);
	}

	@Override
	public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		BoolLiteral bool = new BoolLiteral(ctx.getText());
		currentContainer.push(bool);
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		BoolLiteral bool = (BoolLiteral) currentContainer.pop();
		currentContainer.peek().addChild(bool);
	}

	@Override
	public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ColorLiteral color = new ColorLiteral(ctx.getText());
		currentContainer.push(color);
	}

	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ColorLiteral color = (ColorLiteral) currentContainer.pop();
		currentContainer.peek().addChild(color);
	}

	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		PixelLiteral pixel = new PixelLiteral(ctx.getText());
		currentContainer.push(pixel);
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		PixelLiteral pixel = (PixelLiteral) currentContainer.pop();
		currentContainer.peek().addChild(pixel);
	}

	@Override
	public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		PercentageLiteral percentage = new PercentageLiteral(ctx.getText());
		currentContainer.push(percentage);
	}

	@Override
	public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		PercentageLiteral percentage = (PercentageLiteral) currentContainer.pop();
		currentContainer.peek().addChild(percentage);
	}

	@Override
	public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ScalarLiteral scalar = new ScalarLiteral(ctx.getText());
		currentContainer.push(scalar);
	}

	@Override
	public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ScalarLiteral scalar = (ScalarLiteral) currentContainer.pop();
		currentContainer.peek().addChild(scalar);
	}

	@Override
	public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		VariableAssignment variableAssignment = new VariableAssignment();
		currentContainer.push(variableAssignment);
	}

	@Override
	public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx)  {
		VariableAssignment variableAssignment = (VariableAssignment) currentContainer.pop();
		currentContainer.peek().addChild(variableAssignment);
	}

	@Override
	public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
		VariableReference variableReference = new VariableReference(ctx.getText());
		currentContainer.push(variableReference);
	}

	@Override
	public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
		VariableReference variableReference = (VariableReference) currentContainer.pop();
		currentContainer.peek().addChild(variableReference);
	}

	@Override
	public void enterAddOperation(ICSSParser.AddOperationContext ctx) {
		AddOperation addOperation = new AddOperation();
		currentContainer.push(addOperation);
	}

	@Override
	public void exitAddOperation(ICSSParser.AddOperationContext ctx) {
		AddOperation addOperation = (AddOperation) currentContainer.pop();
		currentContainer.peek().addChild(addOperation);
	}

	@Override
	public void enterSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
		SubtractOperation subtractOperation = new SubtractOperation();
		currentContainer.push(subtractOperation);
	}

	@Override
	public void exitSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
		SubtractOperation subtractOperation = (SubtractOperation) currentContainer.pop();
		currentContainer.peek().addChild(subtractOperation);
	}

	@Override
	public void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
		MultiplyOperation multiplyOperation = new MultiplyOperation();
		currentContainer.push(multiplyOperation);
	}

	@Override
	public void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
		MultiplyOperation multiplyOperation = (MultiplyOperation) currentContainer.pop();
		currentContainer.peek().addChild(multiplyOperation);
	}
}