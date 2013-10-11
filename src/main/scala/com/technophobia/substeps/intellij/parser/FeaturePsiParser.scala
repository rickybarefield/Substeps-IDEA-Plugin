package com.technophobia.substeps.intellij.parser

import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import com.technophobia.substeps.intellij.lexer._
import scala.util.parsing.combinator.{Parsers, RegexParsers}
import scala.util.parsing.combinator.Parsers.Parser
import com.technophobia.substeps.nodes._
import scala.util.parsing.combinator.Parsers.~
import scala.Some
import com.technophobia.substeps.nodes.BasicScenario
import scala.Some
import com.technophobia.substeps.nodes.UnresolvedSubstepUsage
import com.technophobia.substeps.nodes.Feature
import com.technophobia.substeps.AbstractParser

class FeaturePsiParser extends PsiParser {



  def parse(root: IElementType, builder: PsiBuilder): ASTNode = {

    new FeatureParser().parse(r)

   var tokenType = builder.getTokenType

   while(tokenType != null) {



     builder.advanceLexer()
     tokenType = builder.getTokenType
   }


    builder.getTreeBuilt
  }
}


class FeatureParser extends Parsers {

  class ElementParser extends Parser[IElementType] {

    def apply(in: FeatureParser.this.type#Input): FeatureParser.this.type#ParseResult[IElementType] = {


    }
  }


  private def featureFile: Parser[Feature] = opt(tagDef <~ rep1(EolElement)) ~ (featureDef <~ rep1(eol)) ~ (rep(scenario) <~ rep(eol)) ^^ {

    case (Some(tags) ~ featureName ~ scenarios) => Feature(featureName, tags, scenarios)
    case (None ~ featureName ~ scenarios) => Feature(featureName, Nil, scenarios)
  }

  private def tagDef: Parser[List[String]] = TagsMarkerElement ~> rep(tag)

  private def tag: Parser[String]   = TextElement

  private def featureDef: Parser[String] = FeatureMarkerElement ~> rep(TextElement)

  private def scenario: Parser[BasicScenario] = (opt(tagDef <~ eol) ~ scenarioDef <~ eol) ~ rep1sep(substepUsage, eol) <~ rep(eol) ^^ {

    case (Some(tags) ~ scenarioName ~ substeps) => BasicScenario(scenarioName, tags, substeps)
    case (None ~ scenarioName ~ substeps) => BasicScenario(scenarioName, Nil, substeps)
  }

  def substepUsage: Parser[SubstepUsage] = """([^:\r\n])+""".r ^^ ((x) => UnresolvedSubstepUsage(x.trim))

  private def scenarioDef: Parser[String] = "Scenario:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r


}
