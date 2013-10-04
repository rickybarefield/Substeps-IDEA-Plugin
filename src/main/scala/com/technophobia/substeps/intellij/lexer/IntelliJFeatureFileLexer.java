package com.technophobia.substeps.intellij.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.technophobia.substeps.lexer.FeatureFileLexer;
import org.jetbrains.annotations.Nullable;
import scala.util.parsing.combinator.lexical.Scanners;

public class IntelliJFeatureFileLexer extends LexerBase {

    private FeatureFileLexer featureFileLexer = new FeatureFileLexer();
    private Scanners.Scanner scanner;

    @Override
    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {

        scanner = featureFileLexer.createScanner(buffer, startOffset);
    }

    @Override
    public int getState() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Nullable
    @Override
    public IElementType getTokenType() {

        scanner.first();
        return null;
    }

    @Override
    public int getTokenStart() {

        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getTokenEnd() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void advance() {
        scanner.drop(1);
    }

    @Override
    public CharSequence getBufferSequence() {
        return null;
    }

    @Override
    public int getBufferEnd() {
        return 0;
    }
}
