import model.Categoria;
import model.Frequencia;
import service.AssinaturaService;
import service.RelatorioService;
import util.ConsoleUtil;

import java.time.LocalDate;
import java.util.List;
import model.Assinatura;

public class Main {
    public static void main(String[] args) {
        AssinaturaService service = new AssinaturaService();
        RelatorioService relatorio = new RelatorioService();

        // Dados de exemplo pra já ter algo ao abrir
        service.cadastrar("Netflix", 49.90, Categoria.STREAMING, Frequencia.MENSAL, LocalDate.of(2024, 1, 10));
        service.cadastrar("Spotify", 21.90, Categoria.MUSICA, Frequencia.MENSAL, LocalDate.of(2024, 3, 5));
        service.cadastrar("Xbox Game Pass", 44.99, Categoria.JOGOS, Frequencia.MENSAL, LocalDate.of(2024, 6, 1));

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n===== GERENCIADOR DE ASSINATURAS =====");
            System.out.println("1. Cadastrar assinatura");
            System.out.println("2. Listar assinaturas ativas");
            System.out.println("3. Listar todas as assinaturas");
            System.out.println("4. Cancelar assinatura");
            System.out.println("5. Editar valor de assinatura");
            System.out.println("6. Ver próximas cobranças (30 dias)");
            System.out.println("7. Relatório financeiro");
            System.out.println("0. Sair");
            System.out.println("=======================================");
            opcao = ConsoleUtil.lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> {
                    String nome = ConsoleUtil.lerTexto("Nome da assinatura: ");
                    double valor = ConsoleUtil.lerDouble("Valor: R$ ");

                    System.out.println("Categorias: " + java.util.Arrays.toString(Categoria.values()));
                    Categoria categoria = Categoria.valueOf(ConsoleUtil.lerTexto("Categoria: ").toUpperCase());

                    System.out.println("Frequências: " + java.util.Arrays.toString(Frequencia.values()));
                    Frequencia frequencia = Frequencia.valueOf(ConsoleUtil.lerTexto("Frequência: ").toUpperCase());

                    service.cadastrar(nome, valor, categoria, frequencia, LocalDate.now());
                }
                case 2 -> {
                    List<Assinatura> ativas = service.listarAtivas();
                    if (ativas.isEmpty()) System.out.println("Nenhuma assinatura ativa.");
                    else ativas.forEach(System.out::println);
                }
                case 3 -> service.listarTodas().forEach(System.out::println);
                case 4 -> {
                    String nome = ConsoleUtil.lerTexto("Nome da assinatura a cancelar: ");
                    service.cancelar(nome);
                }
                case 5 -> {
                    String nome = ConsoleUtil.lerTexto("Nome da assinatura a editar: ");
                    double novoValor = ConsoleUtil.lerDouble("Novo valor: R$ ");
                    service.editar(nome, novoValor);
                }
                case 6 -> {
                    List<Assinatura> proximas = service.proximasCobrancas(30);
                    if (proximas.isEmpty()) System.out.println("Nenhuma cobrança nos próximos 30 dias.");
                    else proximas.forEach(System.out::println);
                }
                case 7 -> relatorio.exibirRelatorioCompleto(service);
                case 0 -> System.out.println("Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}