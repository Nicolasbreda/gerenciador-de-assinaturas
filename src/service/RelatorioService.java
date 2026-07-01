package service;

import model.Assinatura;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioService {

    public void exibirRelatorioCompleto(AssinaturaService service) {
        System.out.println("\n========== RELATÓRIO FINANCEIRO ==========");

        List<Assinatura> ativas = service.listarAtivas();

        if (ativas.isEmpty()) {
            System.out.println("Nenhuma assinatura ativa encontrada.");
            return;
        }

        System.out.println("\n📋 Assinaturas ativas:");
        ativas.forEach(System.out::println);

        System.out.println("\n📊 Gasto por categoria:");
        Map<String, Double> porCategoria = ativas.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getCategoria().toString(),
                        Collectors.summingDouble(Assinatura::calcularValorMensal)
                ));
        porCategoria.forEach((cat, total) ->
                System.out.printf("  %-15s R$ %.2f/mês%n", cat, total));

        System.out.printf("%n💰 Total gasto por mês: R$ %.2f%n", service.calcularGastoMensal());
        System.out.printf("💸 Total gasto por ano: R$ %.2f%n", service.calcularGastoMensal() * 12);
        System.out.println("==========================================");
    }
}