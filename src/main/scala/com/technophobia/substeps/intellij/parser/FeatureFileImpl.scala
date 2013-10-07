package com.technophobia.substeps.intellij.parser

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.technophobia.substeps.intellij.filetypes.FeatureFileType
import com.intellij.openapi.fileTypes.FileType

class FeatureFileImpl(fvp: FileViewProvider) extends PsiFileBase(fvp, FeatureFileType.getLanguage) {

  def getFileType: FileType = FeatureFileType

}
