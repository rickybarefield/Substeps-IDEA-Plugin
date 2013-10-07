package com.technophobia.substeps.intellij.lexer

import com.intellij.psi.tree.IElementType
import com.technophobia.substeps.intellij.filetypes.{SubstepsFileType, SubstepsLanguage}
import com.technophobia.substeps.lexer.{FeatureFileLexer, SubstepsTokens}
import scala.util.parsing.combinator.token.Tokens
import com.intellij.psi.TokenType

object FeatureFileElement extends IElementType("FeatureFile", SubstepsFileType.getLanguage)

object FeatureElement extends IElementType("Parameter", SubstepsFileType.getLanguage)

object TextElement extends IElementType("Text", SubstepsFileType.getLanguage)

object ParameterElement extends IElementType("Parameter", SubstepsFileType.getLanguage)

object ScenarioElement extends IElementType("Scenario", SubstepsFileType.getLanguage)

object TagsElement extends IElementType("Tags", SubstepsFileType.getLanguage)

object CommentElement extends IElementType("Comment", SubstepsFileType.getLanguage)

object EolElement extends IElementType("EOL", SubstepsFileType.getLanguage)

class SubstepsFeatureFileElementFactory(featureFileLexer: FeatureFileLexer) extends Tokens {

  def convert(token: featureFileLexer.Token) = {

    token match {

      case featureFileLexer.NewLineToken => TokenType.NEW_LINE_INDENT
      case featureFileLexer.WhiteSpaceToken => TokenType.WHITE_SPACE
      case featureFileLexer.ScenarioToken => ScenarioElement
      case featureFileLexer.TagsToken => TagsElement
      case featureFileLexer.NewLineToken => EolElement
      case _: featureFileLexer.TextToken => TextElement
      case _: featureFileLexer.ParameterToken => ParameterElement
      case _: featureFileLexer.CommentToken => CommentElement

    }
  }

}

