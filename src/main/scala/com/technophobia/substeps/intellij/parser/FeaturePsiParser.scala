package com.technophobia.substeps.intellij.parser

import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import com.technophobia.substeps.intellij.lexer._
import scala.util.parsing.combinator.Parsers
import com.technophobia.substeps.nodes._
import com.technophobia.substeps.nodes.BasicScenario
import scala.Some
import com.technophobia.substeps.nodes.UnresolvedSubstepUsage
import com.technophobia.substeps.nodes.Feature
import scala.util.parsing.input.{Position, Reader}

class FeaturePsiParser extends PsiParser {

  def parse(root: IElementType, builder: PsiBuilder): ASTNode = {

   val start = builder.mark()

   new FeatureParser().apply(builder)

   start.done(root)

   builder.getTreeBuilt
  }
}

class FeatureParser extends Parsers {

  override type Elem = (IElementType, String)
  override type Input = Reader[Elem]

  def apply(in: PsiBuilder) {

    featureFile.apply(PsiBuilderImmutableAdapter(in))
  }

  implicit class ElementParser(tokenToMatch: IElementType) extends Parser[String] {

    def apply(in: Reader[Elem]): ParseResult[String] = {

      def createSuccess = {

        in match {

          case psiReader: PsiBuilderImmutableAdapter => {

            val marker = psiReader.mark()
            val success = Success(in.first._2, in.rest)
            marker.done(tokenToMatch)
            success
          }
          case _ => throw new RuntimeException("Wrong Reader Type")

        }

      }

      val (token, tokenText) = in.first

      token match {

        case x if x == tokenToMatch => createSuccess
        case _                      => {
          print(s"Parsing failed at ${tokenText}, expected ${tokenToMatch}")
          Failure(s"Expected ${tokenToMatch} but found ${token}", in)
        }

      }
    }
  }

  implicit class MarkingParser(wrapped: Parser[String]) extends Parser[String] {

    def apply(in: FeatureParser.this.type#Input): FeatureParser.this.type#ParseResult[String] = {

      val psiBuilder = in.asInstanceOf[PsiBuilderImmutableAdapter]

      val mark = psiBuilder.mark()

      val result = wrapped(in)

      result match {

        case Success(_,_) => mark.done(FeatureNameElement)
        case _ => mark.rollbackTo()

      }

      result
    }
  }

  private def featureFile: Parser[Feature] = opt(tagDef <~ rep1(EolElement)) ~ (featureDef <~ rep1(EolElement)) ~ (rep(scenario) <~ rep(EolElement)) ^^ {

    case (Some(tags) ~ featureName ~ scenarios) => Feature(featureName, tags, scenarios)
    case (None ~ featureName ~ scenarios) => Feature(featureName, Nil, scenarios)
  }

  private def tagDef: Parser[List[String]] = TagsMarkerElement ~> rep(tag)

  private def tag: Parser[String] = TextElement

  private def featureDef: MarkingParser = FeatureMarkerElement ~> rep(TextElement) ^^ (x => x.mkString(" "))

  private def scenario: Parser[BasicScenario] = (opt(tagDef <~ rep1(EolElement)) ~ scenarioDef <~ rep1(EolElement)) ~ rep1sep(substepUsage, EolElement) <~ rep(EolElement) ^^ {

    case (Some(tags) ~ scenarioName ~ substeps) => BasicScenario(scenarioName, tags, substeps)
    case (None ~ scenarioName ~ substeps) => BasicScenario(scenarioName, Nil, substeps)
  }

  def substepUsage: Parser[SubstepUsage] = rep(TextElement) ^^ ((x) => UnresolvedSubstepUsage(x.mkString(" ")))

  private def scenarioDef: Parser[String] = ScenarioMarkerElement ~> rep(TextElement) ^^ (x => x.mkString(" "))

  case class PsiBuilderImmutableAdapter(builder: PsiBuilder) extends Reader[FeatureParser.this.Elem] {

    val first = (builder.getTokenType, builder.getTokenText)

    val advanced = false

    def mark() = builder.mark()

    val pos: Position = new Position {

      val column: Int = builder.getCurrentOffset

      val line: Int = 0

      protected val lineContents = builder.getOriginalText.toString
    }

    val atEnd: Boolean = first == null

    def rest: Input = {

      if(advanced) throw new RuntimeException("Builder already advanced")

      builder.advanceLexer()

      PsiBuilderImmutableAdapter(builder)
    }

  }

}
