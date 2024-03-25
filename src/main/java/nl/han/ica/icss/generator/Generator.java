package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;

import java.util.stream.Collectors;

import java.util.StringJoiner;

public class Generator {

	private final StringJoiner stringJoiner;

	public Generator() {
		this.stringJoiner = new StringJoiner("\n\n");
	}

	public String generate(AST ast) {
		generateNode(ast.root);
		return stringJoiner.toString();
	}

	private void generateNode(ASTNode astNode) {
		for (ASTNode node : astNode.getChildren()) {
			if (node instanceof Stylerule) {
				generateStylerule((Stylerule) node);
			}
		}
	}

	private void generateStylerule(Stylerule stylerule) {
		StringJoiner ruleJoiner = new StringJoiner("\n");
		generateSelectors(stylerule, ruleJoiner);
		generateDeclarations(stylerule, ruleJoiner);
		stringJoiner.add(ruleJoiner.toString());
	}

	private void generateSelectors(Stylerule stylerule, StringJoiner ruleJoiner) {
		StringJoiner selectors = new StringJoiner(", ");
		stylerule.selectors.forEach(selector -> selectors.add(selector.toString()));
		ruleJoiner.add(selectors.toString() + " {");
	}

	private void generateDeclarations(Stylerule stylerule, StringJoiner ruleJoiner) {
		stylerule.getChildren().stream()
				.filter(node -> node instanceof Declaration)
				.map(node -> (Declaration) node)
				.forEach(declaration -> {
					ruleJoiner.add("  " +
							declaration.property.name + ": " +
							expressionToString(declaration.expression) + ";");
				});
		ruleJoiner.add("}");
	}

	private String expressionToString(Expression expression) {
		if (expression instanceof PercentageLiteral) {
			return ((PercentageLiteral) expression).value + "%";
		}
		if (expression instanceof PixelLiteral) {
			return ((PixelLiteral) expression).value + "px";
		}
		if (expression instanceof ColorLiteral) {
			return ((ColorLiteral) expression).value;
		}
		return "";
	}
}

