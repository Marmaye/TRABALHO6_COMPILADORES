package br.ufscar.dc.compiladores.GymPlanner;

import br.ufscar.dc.compiladores.GymPlanner.gerador.GeradorHTML;
import br.ufscar.dc.compiladores.GymPlanner.semantico.GymPlannerSemantico;
import br.ufscar.dc.compiladores.GymPlanner.semantico.GymPlannerSemanticoUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.IOException;
import java.io.PrintWriter;

public class Principal {
    
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: java -jar <programa> <arquivo-de-entrada> <arquivo-de-saida>");
            return;
        }

        // Nome do arquivo de entrada
        String arquivoEntrada = args[0];

        // Nome do arquivo de saída
        String arquivoSaida = args[1];

        try (PrintWriter pw = new PrintWriter(arquivoSaida)) {
            CustomErrorListener cel = new CustomErrorListener(pw);
            CharStream cs = CharStreams.fromFileName(arquivoEntrada);

            GymPlannerLexer lexer = new GymPlannerLexer(cs);
            lexer.removeErrorListeners();
            lexer.addErrorListener(cel);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            GymPlannerParser parser = new GymPlannerParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(cel);

            GymPlannerParser.ProgramaContext arvore = parser.programa();
            GymPlannerSemantico pls = new GymPlannerSemantico();
            pls.visitPrograma(arvore);

            if (!GymPlannerSemanticoUtils.errosSemanticos.isEmpty()) {
                GymPlannerSemanticoUtils.errosSemanticos.forEach(pw::println);
                pw.println("Fim da compilacao");
            } else {
                // Se não houver erros, gerar saida HTML
                GeradorHTML gh = new GeradorHTML();
                gh.visitPrograma(arvore);
                pw.write(gh.saida.toString());
            }

        } catch (ParseCancellationException e) {
             // Sair no primeiro erro
        }
    }
    
}
