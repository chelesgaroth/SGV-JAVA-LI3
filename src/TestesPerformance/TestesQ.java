package TestesPerformance;

import Model.*;


/**
 * Classe de Teste para cada Query  e medição do seu tempo.
 */
public class TestesQ {
    private IGestaoVendas model;
    private Crono crono;

    public TestesQ(){
        model = new GestaoVendas();
        crono = new Crono();
    }

    public void setModel(IGestaoVendas model) {
        this.model = model;
    }

    public void queriesTeste(){
        crono.start();
        model.query_1();
        System.out.println("\n\nQUERY 1: " + crono.getTImeString());
        crono.start();
        model.query_2(6);
        System.out.println("QUERY 2: " + crono.getTImeString());
        crono.start();
        model.query_3("Z5000");
        System.out.println("QUERY 3: " + crono.getTImeString());
        crono.start();
        model.query_4("AF1184");
        System.out.println("QUERY 4: " + crono.getTImeString());
        crono.start();
        model.query_5("Z5000");
        System.out.println("QUERY 5: " + crono.getTImeString());
        crono.start();
        model.query_6(5);
        System.out.println("QUERY 6: " + crono.getTImeString());
        crono.start();
        model.query_7();
        System.out.println("QUERY 7: " + crono.getTImeString());
        crono.start();
        model.query_8(5);
        System.out.println("QUERY 8: " + crono.getTImeString());
        crono.start();
        model.query_9("AF1184",5);
        System.out.println("QUERY 9: " + crono.getTImeString());
        crono.start();
        model.query_10();
        System.out.println("QUERY 10: " + crono.getTImeString());
        crono.start();
        model.filesData();
        System.out.println("\n" + "Estatística-> Leitura de Ficheiros:  " + crono.getTImeString());
        crono.start();
        model.estadoData();
        System.out.println("Estatística-> Load de Estados:  " + crono.getTImeString());
    }
}
