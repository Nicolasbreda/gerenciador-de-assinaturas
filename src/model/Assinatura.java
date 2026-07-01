package model;

import java.time.LocalDate;

public class Assinatura {
    private String nome;
    private double valor;
    private Categoria categoria;
    private Frequencia frequencia;
    private LocalDate dataInicio;
    private boolean ativa;

    public Assinatura(String nome, double valor, Categoria categoria, Frequencia frequencia, LocalDate dataInicio) {
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.frequencia = frequencia;
        this.dataInicio = dataInicio;
        this.ativa = true;
    }

    public LocalDate calcularProximaCobranca() {
        LocalDate hoje = LocalDate.now();
        LocalDate proxima = dataInicio;

        while (!proxima.isAfter(hoje)) {
            proxima = switch (frequencia) {
                case MENSAL -> proxima.plusMonths(1);
                case TRIMESTRAL -> proxima.plusMonths(3);
                case SEMESTRAL -> proxima.plusMonths(6);
                case ANUAL -> proxima.plusYears(1);
            };
        }
        return proxima;
    }

    public double calcularValorMensal() {
        return switch (frequencia) {
            case MENSAL -> valor;
            case TRIMESTRAL -> valor / 3;
            case SEMESTRAL -> valor / 6;
            case ANUAL -> valor / 12;
        };
    }

    public void cancelar() {
        this.ativa = false;
    }

    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public Categoria getCategoria() { return categoria; }
    public Frequencia getFrequencia() { return frequencia; }
    public LocalDate getDataInicio() { return dataInicio; }
    public boolean isAtiva() { return ativa; }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (%s) | Próxima cobrança: %s | Status: %s",
                categoria, nome, valor, frequencia,
                calcularProximaCobranca(), ativa ? "ATIVA" : "CANCELADA");
    }
}