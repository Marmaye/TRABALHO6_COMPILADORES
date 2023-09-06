package br.ufscar.dc.compiladores.GymPlanner;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.PrintWriter;
import java.util.BitSet;

//Classe responsável por customizar as mensagens de erro
public class CustomErrorListener implements ANTLRErrorListener {
    //Variável para escrever no arquivo .txt
    PrintWriter pw;

    public CustomErrorListener(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        Token t = (Token) offendingSymbol;
        String message;
        String tokenTypeName = GymPlannerLexer.VOCABULARY.getDisplayName(t.getType());
        int charPosition = t.getCharPositionInLine();

        //Verifica se o erro é devido a um caractere desconhecido
        if (tokenTypeName.equals("DESCONHECIDO")) {
            String invalidChar = t.getText();

            //Erro devido ao não fechamento de comentário
            if (invalidChar.equals("/*")) {
                message = "Linha " + line + ":" + charPosition + " comentario nao fechado";
            } else {        //Erro devido ao não reconhecimento de símbolos
                message = "Linha " + line + ":" + charPosition + " " + t.getText() + " - simbolo nao identificado";
            }
        } else if (t.getType() == Token.EOF) {      //Erro devido à não finalização do algoritmo
            message = "Linha " + line + ":" + charPosition + " erro sintatico proximo a EOF";
        } else {        //Erro sintático
            message = "Linha " + line + ":" + charPosition + " erro sintatico proximo a " + t.getText();
        }

        pw.write(message + "\n");
        pw.write("Fim da compilacao\n");
        throw new ParseCancellationException("Fim");
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {

    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {

    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {

    }
}
