package com.technophobia.substeps.intellij.lexer

import com.intellij.psi.tree.IElementType
import com.technophobia.substeps.intellij.filetypes.{SubstepsFileType, SubstepsLanguage}
import com.technophobia.substeps.lexer.{FeatureFileLexer, SubstepsTokens}
import scala.util.parsing.combinator.token.Tokens
import com.intellij.psi.TokenType

object TextElement extends IElementType("Text", SubstepsFileType.getLanguage)

object KeywordElement extends IElementType("Keyword", SubstepsFileType.getLanguage)

object ParameterElement extends IElementType("Parameter", SubstepsFileType.getLanguage)

object CommentElement extends IElementType("Comment", SubstepsFileType.getLanguage)


class SubstepsFeatureFileElementFactory(featureFileLexer: FeatureFileLexer) extends Tokens {

  def convert(token: featureFileLexer.Token) = {



    token match {

      case _: featureFileLexer.Text => TextElement
      case _: featureFileLexer.Keyword => KeywordElement
      case _: featureFileLexer.NewLine => TokenType.NEW_LINE_INDENT
      case _: featureFileLexer.WhiteSpace => TokenType.WHITE_SPACE
      case _: featureFileLexer.Parameter => ParameterElement
      case _: featureFileLexer.Comment => CommentElement

    }
  }

}

