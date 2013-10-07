package com.technophobia.substeps.intellij.parser

import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import com.technophobia.substeps.intellij.lexer._

class ParseResult

class ParseSuccess(remaining: PsiBuilder)

abstract class Parser[T] {

  def apply(builder: PsiBuilder)
}

class FeatureParser extends PsiParser {

  def featureFile : Parser[FeatureFileElement] = opt(tags) ~ featureHeading ~ rep(scenario)
  def featureHeading = FeatureElement ~ featureName ~ EolElement
  def featureName = rep(TextElement)
  def tags = TagsElement ~ rep(tag) ~ EolElement
  def tag = TextElement
  def scenario = tags ~ scenarioHeading ~ rep(step)
  def scenarioHeading = ScenarioElement ~ scenarioName ~ EolElement
  def scenarioName = rep(TextElement)
  def step = rep(TextElement) ~ EolElement


  def parse(root: IElementType, builder: PsiBuilder): ASTNode = {

   var tokenType = builder.getTokenType

   while(tokenType != null) {



     builder.advanceLexer()
     tokenType = builder.getTokenType
   }


    builder.getTreeBuilt
  }
}
