package com.technophobia.substeps.intellij.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.technophobia.substeps.lexer.FeatureFileLexer;
import org.jetbrains.annotations.Nullable;
import scala.util.parsing.combinator.lexical.Scanners;
import scala.util.parsing.combinator.token.Tokens;
import scala.util.parsing.input.Position;

public class IntelliJFeatureFileLexer extends LexerBase {

    private FeatureFileLexer featureFileLexer = new FeatureFileLexer();
    private SubstepsFeatureFileElementFactory elementFactory = new SubstepsFeatureFileElementFactory(featureFileLexer);
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


        Tokens.Token token = (Tokens.Token) scanner.first();

        if(scanner.atEnd()) return null;

        if(token instanceof  Tokens.ErrorToken) {
            return TokenType.ERROR_ELEMENT;
        }


        return elementFactory.convert(token);
    }

    @Override
    public int getTokenStart() {

        return scanner.offset();
    }

    @Override
    public int getTokenEnd() {
        return scanner.rest().offset();
    }

    @Override
    public void advance() {
        scanner = (Scanners.Scanner) scanner.drop(1);
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
