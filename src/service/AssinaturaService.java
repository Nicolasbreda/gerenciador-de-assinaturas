package service;

import model.Assinatura;
import model.Categoria;
import model.Frequencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssinaturaService {
    private List<Assinatura> assinaturas = new ArrayList<>();

    public void cadastrar(String nome, double valor, Categoria categoria, Frequencia frequencia, LocalDate dataInicio) {
        assinaturas.add(new Assinatura(nome, valor, categoria, frequencia, dataInicio));
        System.out.println("✅ Assinatura '" + nome + "' cadastrada com sucesso!");
    }

    public void cancelar(String nome) {
        Assinatura encontrada = buscarPorNome(nome);
        if (encontrada != null) {
            encontrada.cancelar();
            System.out.println("❌ Assinatura '" + nome + "' cancelada.");
        } else {
            System.out.println("Assinatura não encontrada: " + nome);
        }
    }

    public void editar(String nome, double novoValor) {
        Assinatura encontrada = buscarPorNome(nome);
        if (encontrada != null) {
            encontrada.setValor(novoValor);
            System.out.println("✏️ Valor de '" + nome + "' atualizado para R$ " + String.format("%.2f", novoValor));
        } else {
            System.out.println("Assinatura não encontrada: " + nome);
        }
    }

    public List<Assinatura> listarAtivas() {
        return assinaturas.stream().filter(Assinatura::isAtiva).toList();
    }

    public List<Assinatura> listarTodas() {
        return assinaturas;
    }

    public List<Assinatura> proximasCobrancas(int diasAdiante) {
        LocalDate limite = LocalDate.now().plusDays(diasAdiante);
        return assinaturas.stream()
                .filter(Assinatura::isAtiva)
                .filter(a -> !a.calcularProximaCobranca().isAfter(limite))
                .toList();
    }

    public double calcularGastoMensal() {
        return assinaturas.stream()
                .filter(Assinatura::isAtiva)
                .mapToDouble(Assinatura::calcularValorMensal)
                .sum();
    }

    private Assinatura buscarPorNome(String nome) {
        return assinaturas.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }
}