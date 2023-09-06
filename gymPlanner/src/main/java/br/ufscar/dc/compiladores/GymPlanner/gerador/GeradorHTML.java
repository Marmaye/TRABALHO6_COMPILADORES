package br.ufscar.dc.compiladores.GymPlanner.gerador;

import br.ufscar.dc.compiladores.GymPlanner.GymPlannerBaseVisitor;
import br.ufscar.dc.compiladores.GymPlanner.GymPlannerParser;
import br.ufscar.dc.compiladores.GymPlanner.semantico.GymPlannerSemanticoUtils;
import br.ufscar.dc.compiladores.GymPlanner.Tarefa;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

/*
    Visitante usado para geração de uma saida HTML
 */
public class GeradorHTML extends GymPlannerBaseVisitor<Void> {

    // Estrutura para fazer o agrupamento das tarefas
    private final Map<Integer, ArrayList<Tarefa>> tds;
    public StringBuilder saida;

    public GeradorHTML() {
        saida = new StringBuilder();
        tds = new HashMap<>();
    }

    @Override
    public Void visitPrograma(GymPlannerParser.ProgramaContext ctx) {
        saida.append("<!DOCTYPE html>\n")
                .append("<html>\n");

        visitFormato(ctx.formato());
        saida.append("</html>\n");
        return null;
    }

    private void adicionarHead(String titulo, String styles) {
        saida.append("<head>\n")
                .append("<meta charset=\"UTF-8\"/>\n")
                .append("<title>").append(titulo).append("</title>\n")
                .append(styles)
                .append("</head>\n");
    }

    @Override
    public Void visitCorpo_semanal(GymPlannerParser.Corpo_semanalContext ctx) {
        for (var tarefa : ctx.tarefa_semanal()) {
            Calendar inicio = GymPlannerSemanticoUtils.parseData(
                    tarefa.data_semanal().dia_inicio, tarefa.data_semanal().horario_inicio);
            Calendar fim;
            if (tarefa.data_semanal().dia_fim != null) {
                fim = GymPlannerSemanticoUtils.parseData(
                        tarefa.data_semanal().dia_fim, tarefa.data_semanal().horario_fim);
            } else {
                fim = inicio;
            }
            String descricao;
            if (tarefa.campo_descricao() != null) {
                descricao = tarefa.campo_descricao().DESCRICAO().getText();
            } else {
                descricao = "";
            }
            String id = tarefa.TAREFA().getText();

            // Tarefas são agrupadas por dia da semana
            Integer key = inicio.get(Calendar.DAY_OF_WEEK);
            tds.computeIfAbsent(key, k -> new ArrayList<>());
            tds.get(key).add(new Tarefa(id, inicio, fim, descricao));
        }

        // Gera head e body
        adicionarHead("GymPlanner Semanal", GeradorHTMLUtils.SEMANAL_STYLES);

        saida.append("<body>\n");

        saida.append("<div class=\"container\">\n");
        saida.append("<h1>GymPlanner Semanal</h1>");

        saida.append("<div class=\"calendar\">\n");

        // Lista as tarefas agrupadas por dia da semana (simplificado)
        for (int i = 0; i < GeradorHTMLUtils.DIAS_NA_SEMANA; i++) {
            List<Tarefa> tarefas = tds.get(i);
            saida.append("<div class=\"dia-da-semana\">\n");
            saida.append("<h3>")
                    .append(GeradorHTMLUtils.diasDaSemana[i])
                    .append("</h3>\n<ul>\n");
            if (tarefas != null) {
                for (var tarefa : tarefas) {
                    saida.append("<li>")
                            .append(tarefa.getId())
                            .append("</li>\n");
                }
            }
            saida.append("</ul>\n")
                    .append("</div>\n"); // .dia-da-semana
        }
        saida.append("</div>\n"); // .calendar

        // Lista as tarefas de modo mais detalhado
        saida.append("<h2>Alunos</h2>\n")
                .append("<div class=\"container-tarefa\">\n");
        for (int i = 0; i < GeradorHTMLUtils.DIAS_NA_SEMANA; i++) {
            List<Tarefa> tarefas = tds.get(i);
            if (tarefas != null) {
                for (var tarefa : tarefas) {
                    saida.append("<div class=\"tarefa\">\n");
                    saida.append("<h4>")
                            .append(tarefa.getId())
                            .append("</h4>\n");
                    saida.append("<p><span class=\"bold\">Inicio: </span>")
                            .append(formatDate_semanal(tarefa.getInicio()))
                            .append(" - <span class=\"bold\">Fim: </span>")
                            .append(formatDate_semanal(tarefa.getFim()))
                            .append("</p>\n");
                    saida.append("<p><span class=\"bold\">Descrição: </span>")
                            .append(tarefa.getDescricao())
                            .append("</p>\n");
                    saida.append("</div>\n"); // .tarefa
                }
            }
        }
        saida.append("</div>\n") // .container-tarefa
                .append("</div>\n") // .container
                .append("</body>\n");

        return null;
    }

    /*
     * Função de ajuda para formatar datas
     */
    private String formatDate(Calendar c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM [HH:mm]");

        return dateFormat.format(c.getTime());
    }

    /*
     * Função de ajuda para formatar datas semanais
     */
    private String formatDate_semanal(Calendar c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" [HH:mm]");
        return GeradorHTMLUtils.diasDaSemana[c.get(Calendar.DAY_OF_WEEK)] + dateFormat.format(c.getTime());
    }
}
