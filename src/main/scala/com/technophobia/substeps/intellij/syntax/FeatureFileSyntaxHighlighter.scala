package com.technophobia.substeps.intellij.syntax

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.{HighlighterColors, DefaultLanguageHighlighterColors}
import com.intellij.lexer.Lexer
import com.technophobia.substeps.intellij.lexer._
import com.intellij.openapi.fileTypes.{SyntaxHighlighterBase, SyntaxHighlighter}
import com.intellij.psi.tree.IElementType
import gnu.trove.THashMap
import com.intellij.psi.TokenType
import com.intellij.codeInsight.highlighting.HighlightHandlerBase

class FeatureFileSyntaxHighlighter extends SyntaxHighlighterBase {

  private final val KEYWORD = TextAttributesKey.createTextAttributesKey("FEATURE.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
  private final val PARAMETER = TextAttributesKey.createTextAttributesKey("FEATURE.PARAMETER", DefaultLanguageHighlighterColors.CONSTANT)
  private final val COMMENT = TextAttributesKey.createTextAttributesKey("FEATURE.COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
  private final val NEW_LINE = TextAttributesKey.createTextAttributesKey("FEATURE.NEW_LINE")
  private final val WHITE_SPACE = TextAttributesKey.createTextAttributesKey("FEATURE.WHITESPACE")
  private final val TEXT = HighlighterColors.TEXT;

  private final val ERROR = HighlighterColors.BAD_CHARACTER;

  private final val keys: Map[IElementType, TextAttributesKey] = Map(
    EolElement -> NEW_LINE,
    ParameterElement -> PARAMETER,
    CommentElement -> COMMENT,
    TagsMarkerElement -> KEYWORD,
    ScenarioMarkerElement -> KEYWORD,
    FeatureMarkerElement -> KEYWORD,
    TokenType.ERROR_ELEMENT -> ERROR,
    TextElement -> TEXT,
    TokenType.WHITE_SPACE -> WHITE_SPACE)

  def getHighlightingLexer: Lexer = {

    return new IntelliJFeatureFileLexer
  }

  def getTokenHighlights(tokenType: IElementType): Array[TextAttributesKey] = {

    SyntaxHighlighterBase.pack(keys(tokenType))
  }
}
