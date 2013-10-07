package com.technophobia.substeps.intellij.parser

import com.intellij.lang.{ASTNode, ParserDefinition}
import com.intellij.lang.ParserDefinition.SpaceRequirements
import com.intellij.psi.{TokenType, PsiElement, PsiFile, FileViewProvider}
import com.intellij.psi.tree.{IFileElementType, TokenSet}
import com.intellij.openapi.project.Project
import com.technophobia.substeps.intellij.lexer.{CommentElement, IntelliJFeatureFileLexer}
import com.technophobia.substeps.intellij.filetypes.FeatureFileType

class FeatureParserDefinition extends ParserDefinition {

  final val FEATURE_FILE = new IFileElementType(FeatureFileType.getLanguage)

  def createLexer(p1: Project) = new IntelliJFeatureFileLexer

  def createParser(p1: Project) = new FeatureParser

  def getFileNodeType = FEATURE_FILE

  def getWhitespaceTokens = TokenSet.create(TokenType.WHITE_SPACE)

  def getCommentTokens = TokenSet.create(CommentElement)

  def getStringLiteralElements = TokenSet.EMPTY

  def createElement(p1: ASTNode): PsiElement = {


    null
  }

  def createFile(fvp: FileViewProvider): PsiFile = new FeatureFileImpl(fvp)

  def spaceExistanceTypeBetweenTokens(p1: ASTNode, p2: ASTNode) = SpaceRequirements.MAY
}
