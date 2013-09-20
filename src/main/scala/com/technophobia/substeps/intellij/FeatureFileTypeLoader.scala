package com.technophobia.substeps.intellij

import com.intellij.openapi.fileTypes.{FileTypeConsumer, FileTypeFactory}

class FeatureFileTypeLoader extends FileTypeFactory {

  override def createFileTypes(consumer: FileTypeConsumer) = consumer.consume(FeatureFileType)
}