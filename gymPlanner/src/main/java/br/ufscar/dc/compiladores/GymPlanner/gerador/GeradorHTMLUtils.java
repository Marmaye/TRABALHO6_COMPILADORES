package br.ufscar.dc.compiladores.GymPlanner.gerador;

/*
    Utilitários usados na geração da saida HTML
 */
public class GeradorHTMLUtils {

        public static int DIAS_NA_SEMANA = 7;

        public static int MESES_NO_ANO = 12;

        public static final String[] meses = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };

        public static final String[] diasDaSemana = { "Domingo", "Segunda-feira", "Terça-feira",
                        "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado" };

        public static final String ANUAL_STYLES = "<style>\n" +
                        "        body {\n" +
                        "            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\n" +
                        "            background-color: #f8f8f8;\n" + // Fundo cinza claro
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            max-width: 800px;\n" +
                        "            margin: 0 auto;\n" +
                        "            padding: 20px;\n" +
                        "            background-color: #ff9900;\n" + // Fundo laranja mais forte
                        "            border: 1px solid #e6e6e6;\n" + // Borda cinza claro
                        "            border-radius: 5px;\n" +
                        "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        .calendar {\n" +
                        "            justify-content: space-between;\n" +
                        "            display: flex;\n" +
                        "            flex-wrap: wrap;\n" +
                        "        }\n" +
                        "        .container-tarefa {\n" +
                        "            justify-content: space-between;\n" +
                        "            display: flex;\n" +
                        "            flex-wrap: wrap;\n" +
                        "        }\n" +
                        "        .tarefa {\n" +
                        "            width: 45%;\n" +
                        "            margin-bottom: 15px;\n" +
                        "            padding: 10px;\n" +
                        "            background-color: #ffffff;\n" + // Fundo branco
                        "            border: 1px solid #e6e6e6;\n" + // Borda cinza claro
                        "            border-radius: 5px;\n" +
                        "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        h1 {\n" +
                        "            color: #333333;\n" + // Texto cinza escuro
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        h3 {\n" +
                        "            margin: 0;\n" +
                        "            padding: 15px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        h4 {\n" +
                        "            font-style: italic;\n" +
                        "        }\n" +
                        "        ul {\n" +
                        "            list-style-type: none;\n" +
                        "            margin: 0 20px;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        li {\n" +
                        "            margin-bottom: 5px;\n" +
                        "        }\n" +
                        "        li:hover {\n" +
                        "            background-color: #e6e6e6;\n" + // Fundo cinza claro ao passar o mouse
                        "        }\n" +
                        "        a {\n" +
                        "            text-decoration: none;\n" +
                        "        }\n" +
                        "        .bold {\n" +
                        "            font-weight: bold;\n" +
                        "        }\n" +
                        "    </style>";

        public static final String SEMANAL_STYLES = "    <style>\n" +
                        "        body {\n" +
                        "            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\n" +
                        "            background-color: #f8f8f8;\n" + // Fundo cinza claro
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            margin: auto 20px;\n" +
                        "        }\n" +
                        "        .calendar {\n" +
                        "            display: flex;\n" +
                        "            flex-wrap: wrap;\n" +
                        "        }\n" +
                        "        .dia-da-semana {\n" +
                        "            min-width: 180px;\n" +
                        "            border: 1px solid #e6e6e6;\n" + // Borda cinza claro
                        "            background-color: #ff9900;\n" + // Fundo laranja mais forte
                        "            padding: 10px;\n" +
                        "            border-radius: 5px;\n" +
                        "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        .container-tarefa {\n" +
                        "            justify-content: space-between;\n" +
                        "            display: flex;\n" +
                        "            flex-wrap: wrap;\n" +
                        "        }\n" +
                        "        .tarefa {\n" +
                        "            width: 45%;\n" +
                        "            margin-bottom: 15px;\n" +
                        "            padding: 10px;\n" +
                        "            background-color: #ffffff;\n" + // Fundo branco
                        "            border: 1px solid #e6e6e6;\n" + // Borda cinza claro
                        "            border-radius: 5px;\n" +
                        "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        h1 {\n" +
                        "            color: #333333;\n" + // Texto cinza escuro
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        h3 {\n" +
                        "            margin: 0;\n" +
                        "            padding: 15px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        h4 {\n" +
                        "            font-style: italic;\n" +
                        "        }\n" +
                        "        ul {\n" +
                        "            list-style-type: none;\n" +
                        "            margin: 0 20px;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        li {\n" +
                        "            margin-bottom: 5px;\n" +
                        "        }\n" +
                        "        li:hover {\n" +
                        "            background-color: #e6e6e6;\n" + // Fundo cinza claro ao passar o mouse
                        "        }\n" +
                        "        .bold {\n" +
                        "            font-weight: bold;\n" +
                        "        }\n" +
                        "    </style>";

        public static final String MENSAL_STYLES = "<style>\n" +
                        "        body {\n" +
                        "            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\n" +
                        "            background-color: #f8f8f8;\n" + // Fundo cinza claro
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            margin: auto 20px;\n" +
                        "        }\n" +
                        "        .dia-da-semana {\n" +
                        "            min-width: 180px;\n" +
                        "            border: 1px solid #e6e6e6;\n" + // Borda cinza claro
                        "            background-color: #ff9900;\n" + // Fundo laranja mais forte
                        "            padding: 10px;\n" +
                        "            border-radius: 5px;\n" +
                        "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        .tarefa {\n" +
                        "            width: 100%;\n" +
                        "            margin-bottom: 15px;\n" +
                        "            padding: 10px;\n" +
                        "            background-color: #ffffff;\n" + // Fundo branco
                        "            border: 1px solid #e6e6e6;\n" + // Borda cinza claro
                        "            border-radius: 5px;\n" +
                        "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        h1 {\n" +
                        "            color: #333333;\n" + // Texto cinza escuro
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        h3 {\n" +
                        "            margin: 0;\n" +
                        "            padding: 15px;\n" +
                        "            text-align: left;\n" +
                        "        }\n" +
                        "        h4 {\n" +
                        "            font-style: italic;\n" +
                        "        }\n" +
                        "        ul {\n" +
                        "            list-style-type: none;\n" +
                        "            margin: 0 20px;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        li {\n" +
                        "            margin-bottom: 5px;\n" +
                        "        }\n" +
                        "        li:hover {\n" +
                        "            background-color: #e6e6e6;\n" + // Fundo cinza claro ao passar o mouse
                        "        }\n" +
                        "        .bold {\n" +
                        "            font-weight: bold;\n" +
                        "        }\n" +
                        "    </style>";
}
