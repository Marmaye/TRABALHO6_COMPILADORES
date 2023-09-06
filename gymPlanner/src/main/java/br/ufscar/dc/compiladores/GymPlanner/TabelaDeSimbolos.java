package br.ufscar.dc.compiladores.GymPlanner;

//import br.ufscar.dc.compiladores.gymPlanner.TiposPlanner.TipoPlanner;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TabelaDeSimbolos<T> {
    private final Map<String, T> tabela;

    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }

    public void adicionar(String nome, T etds) {
        tabela.put(nome, etds);
    }

    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }

    public T verificar(String nome) {
        return tabela.get(nome);
    }
}
