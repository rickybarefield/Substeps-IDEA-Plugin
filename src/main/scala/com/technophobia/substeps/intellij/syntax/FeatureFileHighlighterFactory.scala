package com.technophobia.substeps.intellij.syntax

import com.intellij.openapi.fileTypes.{SyntaxHighlighter, SyntaxHighlighterFactory}
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class FeatureFileHighlighterFactory extends SyntaxHighlighterFactory {

  def getSyntaxHighlighter(project: Project, virtualFile: VirtualFile): SyntaxHighlighter = new FeatureFileSyntaxHighlighter
}