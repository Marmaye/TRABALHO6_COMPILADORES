package br.ufscar.dc.compiladores.GymPlanner.semantico;

import br.ufscar.dc.compiladores.GymPlanner.Mensagens;
import br.ufscar.dc.compiladores.GymPlanner.GymPlannerParser;
import org.antlr.v4.runtime.Token;

import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

/*
    Utilitários para o Analisador Semântico
 */
public class GymPlannerSemanticoUtils {

    public static List<String> errosSemanticos = new ArrayList<>();

    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        int coluna = t.getCharPositionInLine();
        errosSemanticos.add(String.format("Linha %d:%d - %s", linha, coluna, mensagem));
    }

    private static boolean verificarDiaDoMes(int ano, int mes, GymPlannerParser.Dia_do_mesContext ctx) {
        int dia = Integer.parseInt(ctx.dia.getText());
        int diasNoMes = YearMonth.of(ano, mes).lengthOfMonth();
        if (dia < 1 || diasNoMes < dia) { // data incorreta
            GymPlannerSemanticoUtils.adicionarErroSemantico(ctx.start,
                    String.format(Mensagens.ERRO_DATA_INVALIDA, ctx.start.getText()));
            return false;
        }
        return true;
    }

    private static boolean verificarDiaMes(int ano, GymPlannerParser.Dia_mesContext ctx) {
        int dia = Integer.parseInt(ctx.dia.getText());
        int mes = Integer.parseInt(ctx.mes.getText());
        int diasNoMes = YearMonth.of(ano, mes).lengthOfMonth();
        if (dia < 1 || diasNoMes < dia) { // data incorreta
            GymPlannerSemanticoUtils.adicionarErroSemantico(ctx.start,
                    String.format(Mensagens.ERRO_DATA_INVALIDA, ctx.start.getText()));
            return false;
        }
        return true;
    }

    private static boolean verificaHorario(GymPlannerParser.HorarioContext ctx) {
        int hora = Integer.parseInt(ctx.hora.getText());
        int minuto = Integer.parseInt(ctx.minuto.getText());
        if (hora < 0 || 23 < hora || minuto < 0 || 59 < minuto) { // data invalida
            GymPlannerSemanticoUtils.adicionarErroSemantico(
                    ctx.start,
                    String.format(Mensagens.ERRO_HORARIO_INVALIDO, ctx.getText()));
            return false;
        }
        return true;
    }

    /*
     * Verifica se as datas estão corretas e se representam um intervalo válido
     */
    public static void verificarIntervalo(GymPlannerParser.Data_semanalContext ctx) {
        boolean horariosCorretos = true;
        if (ctx.horario_inicio != null) {
            horariosCorretos = verificaHorario(ctx.horario_inicio);
        }
        if (ctx.horario_fim != null) {
            horariosCorretos = verificaHorario(ctx.horario_fim) && horariosCorretos;
        }
        if (!horariosCorretos) {
            return;
        }
        int iDia = getDiaDaSemana(ctx.dia_inicio);
        LocalTime iHorario = LocalTime.of(0, 0);
        if (ctx.horario_inicio != null) {
            int iHora = Integer.parseInt(ctx.horario_inicio.hora.getText());
            int iMinuto = Integer.parseInt(ctx.horario_inicio.minuto.getText());
            iHorario = LocalTime.of(iHora, iMinuto);
        }

        if (ctx.dia_fim != null) {
            int fDia = getDiaDaSemana(ctx.dia_fim);
            LocalTime fHorario = LocalTime.of(0, 0);

            if (ctx.horario_fim != null) {
                int fHora = Integer.parseInt(ctx.horario_fim.hora.getText());
                int fMinuto = Integer.parseInt(ctx.horario_fim.minuto.getText());
                fHorario = LocalTime.of(fHora, fMinuto);
            }

            if (iDia > fDia ||
                    iDia == fDia && iHorario.compareTo(fHorario) > 0) {
                GymPlannerSemanticoUtils.adicionarErroSemantico(
                        ctx.start,
                        String.format(Mensagens.ERRO_INICIO_FIM_INCOMPATIVES, ctx.dia_inicio.getText(),
                                ctx.horario_inicio.getText(), ctx.dia_fim.getText(),
                                ctx.horario_fim.getText()));
            }
        }
    }

    public static Calendar parseData(int ano, int mes, GymPlannerParser.Dia_do_mesContext diaCtx,
            GymPlannerParser.HorarioContext horarioCtx) {
        int dia = Integer.parseInt(diaCtx.getText());
        int hora;
        int minuto;
        if (horarioCtx != null) {
            hora = Integer.parseInt(horarioCtx.hora.getText());
            minuto = Integer.parseInt(horarioCtx.minuto.getText());
        } else {
            hora = 0;
            minuto = 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, dia, hora, minuto);
        return cal;
    }

    public static Calendar parseData(int ano, GymPlannerParser.Dia_mesContext diaMesContext,
            GymPlannerParser.HorarioContext horarioCtx) {
        int dia = Integer.parseInt(diaMesContext.dia.getText());
        int mes = Integer.parseInt(diaMesContext.mes.getText());
        int hora;
        int minuto;
        if (horarioCtx != null) {
            hora = Integer.parseInt(horarioCtx.hora.getText());
            minuto = Integer.parseInt(horarioCtx.minuto.getText());
        } else {
            hora = 0;
            minuto = 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, dia, hora, minuto);
        return cal;
    }

    public static Calendar parseData(GymPlannerParser.Dia_da_semanaContext diaDaSemanaCtx,
            GymPlannerParser.HorarioContext horarioCtx) {
        int diaDaSemana = getDiaDaSemana(diaDaSemanaCtx);
        int hora;
        int minuto;
        if (horarioCtx != null) {
            hora = Integer.parseInt(horarioCtx.hora.getText());
            minuto = Integer.parseInt(horarioCtx.minuto.getText());
        } else {
            hora = 0;
            minuto = 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.NOVEMBER, 20 + diaDaSemana, hora, minuto);
        return cal;
    }

    private static int getDiaDaSemana(GymPlannerParser.Dia_da_semanaContext ctx) {
        switch (ctx.start.getType()) {
            case GymPlannerParser.DOMINGO:
                return 0;
            case GymPlannerParser.SEGUNDA:
                return 1;
            case GymPlannerParser.TERCA:
                return 2;
            case GymPlannerParser.QUARTA:
                return 3;
            case GymPlannerParser.QUINTA:
                return 4;
            case GymPlannerParser.SEXTA:
                return 5;
            default: // GymPlannerParser.SABADO:
                return 6;
        }
    }
}
