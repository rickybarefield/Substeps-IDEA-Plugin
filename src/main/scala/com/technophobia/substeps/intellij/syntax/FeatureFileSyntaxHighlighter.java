package com.technophobia.substeps.intellij.syntax;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import com.technophobia.substeps.intellij.lexer.IntelliJFeatureFileLexer;
import org.jetbrains.annotations.NotNull;

public class FeatureFileSyntaxHighlighter implements SyntaxHighlighter {

    public FeatureFileSyntaxHighlighter() {

        System.out.println("Created");
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new IntelliJFeatureFileLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return new TextAttributesKey[0];
    }
}
