package com.technophobia.substeps.intellij.filetypes.actions

import com.intellij.ide.actions.CreateElementActionBase
import scala.io.Source
import com.intellij.openapi.project.Project
import com.intellij.psi.{PsiFile, PsiFileFactory, PsiElement, PsiDirectory}
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.fileEditor.FileEditorManager
import scala.Array
import com.intellij.CommonBundle
import com.intellij.openapi.fileTypes.FileType

abstract class SubstepsNewFileActionBase(templateFileName: String, fileType: FileType) extends CreateElementActionBase {

  final private val template = Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("/templates/" + templateFileName)).mkString

  def invokeDialog(project: Project, directory: PsiDirectory): Array[PsiElement] = {

    val validator = new MyInputValidator(project, directory)
    Messages.showInputDialog(project, s"Enter name for new ${fileType.getName} file", s"New ${fileType.getName} file", Messages.getQuestionIcon, "", validator)

    return validator.getCreatedElements
  }

  def create(newName: String, directory: PsiDirectory): Array[PsiElement] = {

    var file = PsiFileFactory.getInstance(directory.getProject).createFileFromText(newName + "." + fileType.getDefaultExtension, fileType, template)
    file = directory.add(file).asInstanceOf[PsiFile]

    val opVirtualFile = Option(file.getVirtualFile)

    opVirtualFile match {

      case Some(virtualFile) => FileEditorManager.getInstance(directory.getProject).openFile(virtualFile, true)
      case None => throw new RuntimeException("Unable to create file, virtual file was null")

    }

    Array(file)
  }

  def getErrorTitle: String = CommonBundle.getErrorTitle

  def getCommandName: String = s"Create ${fileType.getName} file"

  def getActionName(p1: PsiDirectory, p2: String): String = s"New ${fileType.getName} file"

}
