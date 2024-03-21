// Generated from nl\han\ica\icss\parser\ICSS.g4 by ANTLR 4.8
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#styleRule}.
	 * @param ctx the parse tree
	 */
	void enterStyleRule(ICSSParser.StyleRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#styleRule}.
	 * @param ctx the parse tree
	 */
	void exitStyleRule(ICSSParser.StyleRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idSelector}
	 * labeled alternative in {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterIdSelector(ICSSParser.IdSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idSelector}
	 * labeled alternative in {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitIdSelector(ICSSParser.IdSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code classSelector}
	 * labeled alternative in {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterClassSelector(ICSSParser.ClassSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code classSelector}
	 * labeled alternative in {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitClassSelector(ICSSParser.ClassSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tagSelector}
	 * labeled alternative in {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterTagSelector(ICSSParser.TagSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tagSelector}
	 * labeled alternative in {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitTagSelector(ICSSParser.TagSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(ICSSParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(ICSSParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code colorLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code colorLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolLiteral}
	 * labeled alternative in {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pixelLiteral}
	 * labeled alternative in {@link ICSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pixelLiteral}
	 * labeled alternative in {@link ICSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code scalarLiteral}
	 * labeled alternative in {@link ICSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code scalarLiteral}
	 * labeled alternative in {@link ICSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code percentageLiteral}
	 * labeled alternative in {@link ICSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code percentageLiteral}
	 * labeled alternative in {@link ICSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variableReference}.
	 * @param ctx the parse tree
	 */
	void enterVariableReference(ICSSParser.VariableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variableReference}.
	 * @param ctx the parse tree
	 */
	void exitVariableReference(ICSSParser.VariableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cVal}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCVal(ICSSParser.CValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cVal}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCVal(ICSSParser.CValContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplyOperation}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplyOperation}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addOperation}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddOperation(ICSSParser.AddOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addOperation}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddOperation(ICSSParser.AddOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code var}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVar(ICSSParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code var}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVar(ICSSParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subtractOperation}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractOperation(ICSSParser.SubtractOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subtractOperation}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractOperation(ICSSParser.SubtractOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#styleRuleBody}.
	 * @param ctx the parse tree
	 */
	void enterStyleRuleBody(ICSSParser.StyleRuleBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#styleRuleBody}.
	 * @param ctx the parse tree
	 */
	void exitStyleRuleBody(ICSSParser.StyleRuleBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 */
	void enterIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 */
	void exitIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(ICSSParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(ICSSParser.ElseClauseContext ctx);
}