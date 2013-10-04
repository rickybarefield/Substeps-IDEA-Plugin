package com.technophobia.substeps.intellij.filetypes

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon
import com.intellij.openapi.util.IconLoader
import org.jetbrains.annotations.{Nullable, NotNull}

object FeatureFileType extends LanguageFileType(new FeatureLanguage){

  final val FEATURE_ICON: Icon = IconLoader.getIcon("/icons/feature-file.gif")

  @NotNull def getName: String = {
    return "Feature"
  }

  @NotNull def getDescription: String = {
    return "Feature files"
  }

  @NotNull def getDefaultExtension: String = {
    return "feature"
  }

  @Nullable def getIcon: Icon = {
    return FEATURE_ICON
  }
}