package br.ufscar.dc.compiladores.GymPlanner.semantico;

import br.ufscar.dc.compiladores.GymPlanner.GymPlannerBaseVisitor;
import br.ufscar.dc.compiladores.GymPlanner.*;
import br.ufscar.dc.compiladores.GymPlanner.EntradaTabelaDeSimbolos;
import br.ufscar.dc.compiladores.GymPlanner.TabelaDeSimbolos;

/*
    Visitante usado para verificação de erros semânticos
 */
public class GymPlannerSemantico extends GymPlannerBaseVisitor<Void> {
    TabelaDeSimbolos<EntradaTabelaDeSimbolos> tds;

    public GymPlannerSemantico() {
        tds = new TabelaDeSimbolos<>();
    }

    @Override
    public Void visitCorpo_semanal(GymPlannerParser.Corpo_semanalContext ctx) {
        for (var tarefa : ctx.tarefa_semanal()) {
            EntradaTabelaDeSimbolos etds = tds.verificar(tarefa.TAREFA().getText());
            if (etds != null) {
                GymPlannerSemanticoUtils.adicionarErroSemantico(
                        tarefa.TAREFA().getSymbol(),
                        String.format(
                                Mensagens.ERRO_TAREFA_JA_CRIADA,
                                tarefa.TAREFA().getText()));
            } else {
                String nomeTarefa = tarefa.TAREFA().getText();
                tds.adicionar(nomeTarefa, new EntradaTabelaDeSimbolos(nomeTarefa));
            }
            GymPlannerSemanticoUtils.verificarIntervalo(tarefa.data_semanal());
            visitData_semanal(tarefa.data_semanal());
        }

        return null;
    }

    @Override
    public Void visitHorario(GymPlannerParser.HorarioContext ctx) {
        //
        return null;
    }
}
