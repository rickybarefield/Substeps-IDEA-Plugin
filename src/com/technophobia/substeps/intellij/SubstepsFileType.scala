package com.technophobia.substeps.intellij

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon
import com.intellij.openapi.util.IconLoader
import org.jetbrains.annotations.{Nullable, NotNull}

object SubstepsFileType extends LanguageFileType(new SubstepsLanguage){

  final val SUBSTEPS_ICON: Icon = IconLoader.getIcon("/icons/substeps.gif")

  @NotNull def getName: String = {
    return "Substeps"
  }

  @NotNull def getDescription: String = {
    return "Substeps files"
  }

  @NotNull def getDefaultExtension: String = {
    return "substeps"
  }

  @Nullable def getIcon: Icon = {
    return SUBSTEPS_ICON
  }
}