package nl.han.ica.icss.parser;

import java.util.Properties;
import java.util.Stack;


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

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

	//Accumulator attributes:
	private AST ast;


	//Use this to keep track of the parent nodes when recursively traversing the ast
	private Stack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();

		currentContainer = new Stack<>();
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
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		ASTNode selectorNode;
		if (ctx.CLASS_IDENT() != null) {
			selectorNode = new ClassSelector(ctx.getText());
		} else if (ctx.ID_IDENT() != null) {
			selectorNode = new IdSelector(ctx.getText());
		} else {
			selectorNode = new TagSelector(ctx.getText());
		}
		currentContainer.push(selectorNode);
	}
	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		Selector selector = (Selector) currentContainer.pop();
		currentContainer.peek().addChild(selector);
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
		PropertyName property = new PropertyName();
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

}