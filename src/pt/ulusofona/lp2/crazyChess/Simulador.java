package pt.ulusofona.lp2.crazyChess;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Simulador {

    //File ficheiroInicial;
    List<CrazyPiece> crazyList = new ArrayList<CrazyPiece>();
    int turnoCaptura=0; //contador dos turnos sem captura
    int turno=0; //Turnos do jogo
    int equipaAJogar=0;// 0 pretas 1 brancas
    Integer jodaInvalidaBrancas=0;
    Integer jodaInvalidaPretas=0;
    int jodaValidaBrancas =0 ;
    int jodaValidaPretas =0;
    int tamanhoTabuleiro =0;
    int numeroPecas =0;
    String resultadoFinal;
    List<CrazyPiece> capturas = new ArrayList<CrazyPiece>();
    List<String> autores = new ArrayList<String>();


    public Simulador(){

    }

    public Simulador(List<CrazyPiece> crazyList, int turno, int equipaAJogar, List<CrazyPiece> capturas) {
        //this.ficheiroInicial = ficheiroInicial;
        this.crazyList = crazyList;
        this.turno = turno;
        this.equipaAJogar = equipaAJogar;
        this.capturas = capturas;
    }

    public boolean iniciaJogo(File ficheiroInicial) {
            List<List <String>> firstRow = new ArrayList<List <String>>();
            List<List <String>> mapas = new ArrayList<List <String>>();
            List<List <String>> pecas = new ArrayList<List <String>>();
            try {

                File ficheiro = new File(ficheiroInicial.getName());
                Scanner leitorFicheiro = new Scanner(ficheiro);
                while(leitorFicheiro.hasNextLine()) {
                    List<String> testew = new ArrayList<String>();
                    String linha = leitorFicheiro.nextLine();
                    String dados[] = linha.split(":");
                    testew.addAll(Arrays.asList(dados));
                    firstRow.add(testew);
                }
                leitorFicheiro.close();
                tamanhoTabuleiro=Integer.parseInt(firstRow.get(0).get(0));
                numeroPecas=Integer.parseInt(firstRow.get(1).get(0));
                for(int i=2;i<numeroPecas+2;i++){
                    pecas.add(firstRow.get(i));
                }
                for(int i=2+numeroPecas;i<firstRow.size();i++){
                    mapas.add(firstRow.get(i));
                }
                stringTest(pecas, mapas);
                if (crazyList.size()!=numeroPecas){
                    for (int i=0;i<numeroPecas-crazyList.size()+1;i++){
                        CrazyPiece pie = new CrazyPiece(0,0,0,"0",-1,-1);
                        crazyList.add(pie);
                    }
                }
                System.out.println(crazyList);
                return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    void stringTest(List<List<String>> peca, List<List <String>> mapas){
        for(int i=0; i< peca.size();i++) {
            for (int e = 0; e < mapas.size(); e++) {
                for (int u = 0; u < mapas.get(e).size(); u++) {
                    if (mapas.get(e).get(u).equals(peca.get(i).get(0))) {
                        //peca.get(i).get(0) = id
                        //peca.get(i).get(1) = tipoPecas
                        //peca.get(i).get(2) = equipas
                        //peca.get(i).get(3) = alcunhas
                        //coordenadaX= u;
                        //coordenadaY= e;
                        CrazyPiece piece = new CrazyPiece(peca.get(i).get(0),peca.get(i).get(1) , peca.get(i).get(2), peca.get(i).get(3), u, e);
                        crazyList.add(piece);
                    }
                }
            }
        }
    }

    public int numberInt(String number){
            return Integer.parseInt(number);
    }

    void findCapture(int x, int y, int iD, int equipa){
        turno=turno+1;
        turnoCaptura=turnoCaptura+1;
        if (equipaAJogar==0){
            equipaAJogar=1;
            jodaValidaPretas++;
        }else{
            equipaAJogar=0;
            jodaValidaBrancas++;
        }
        for(CrazyPiece crazy: crazyList){
            if(crazy.getCoordenadaX() == x && crazy.getCoordenadaY() == y ){
                if(crazy.getId()!=iD && crazy.getId()!=0){
                    if(crazy.getIdEquipa() != equipa){
                        capturas.add(crazy);
                        turnoCaptura=0;
                    }
                }
            }
        }
        for(CrazyPiece crazy: crazyList){
            if(crazy.getId()==0){
                capturas.add(crazy);
                turnoCaptura=0;
            }
        }
        removeCrazyList();
    }

    boolean findFriend(int x, int y, int iD, int equipa) {
        for (CrazyPiece crazy : crazyList) {
            if (crazy.getCoordenadaX() == x && crazy.getCoordenadaY() == y) {
                if (crazy.getId() != iD && crazy.getId() != 0) {
                    if(crazy.getIdEquipa() == equipa){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    void removeCrazyList(){
        for(CrazyPiece captured: capturas){
                crazyList.remove(captured);
            }
        }

    public int getTamanhoTabuleiro(){
        return tamanhoTabuleiro;
    }

    public boolean processaJogada(int xO, int yO, int xD, int yD) {
        for (CrazyPiece crazy: crazyList) {
            if (crazy.getCoordenadaX() == xO && crazy.getCoordenadaY() == yO) {
                //king
                //preta
                if (crazy.getIdTipoPeca() == 0 && crazy.getIdEquipa() == equipaAJogar ) {
                    System.out.println(crazy.getIdEquipa());
                    if (xD == xO + 1 && yD == yO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                           return false;
                        }
                        crazy.setCoordenadaX(xD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (xD == xO - 1 && yD == yO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO - 1 && xD == xO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO + 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO + 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO - 1 && xD == xO + 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO - 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO - 1 && xD == xO - 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaPretas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    jodaInvalidaPretas++;
                    return false;
                }

                if (crazy.getIdTipoPeca() == 0 && crazy.getIdEquipa() == equipaAJogar ) {
                    System.out.println(crazy.getIdEquipa());
                    if (xD == xO + 1 && yD == yO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (xD == xO - 1 && yD == yO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO - 1 && xD == xO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO + 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO + 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO - 1 && xD == xO + 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO + 1 && xD == xO - 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }
                    if (yD == yO - 1 && xD == xO - 1) {
                        if(!findFriend(xD, yD, crazy.idPeca, crazy.getIdEquipa())){
                            jodaInvalidaBrancas++;
                            return false;
                        }
                        crazy.setCoordenadaX(xD);
                        crazy.setCoordenadaY(yD);
                        findCapture(xD, yD,crazy.idPeca, crazy.getIdEquipa());
                        return true;
                    }

                    invalidoBranco();
                    return false;
                }
                if (crazy.getIdEquipa() != equipaAJogar ) {
                    if (crazy.getIdEquipa()==0){
                        invalidoPreto();
                    }else{
                        invalidoBranco();
                    }
                }
            }

        }

        return false;
    }

    void invalidoBranco(){
        jodaInvalidaBrancas++;
    }

    void invalidoPreto(){
        jodaInvalidaPretas++;
    }

    public List<CrazyPiece> getPecasMalucas(){
        return crazyList;
    }

    public boolean jogoTerminado(){
        List<CrazyPiece> whiteKing = new ArrayList<CrazyPiece>();
        List<CrazyPiece> blackKing = new ArrayList<CrazyPiece>();
        for(CrazyPiece crazy: crazyList){
            if(crazy.getIdEquipa()==0 && crazy.getIdTipoPeca()==0){
                blackKing.add(crazy);
            }
            if(crazy.getIdEquipa()==1 && crazy.getIdTipoPeca()==0){
                whiteKing.add(crazy);
            }
        }
        if (blackKing.size()==1 && whiteKing.size()==1){
            resultadoFinal="EMPATE";
            return true;
        }
        if (blackKing.size()==0){
            resultadoFinal="VENCERAM AS BRANCAS";
            return true;
        }
        if (whiteKing.size()==0){
            resultadoFinal="VENCERAM AS PRETAS";
            return true;
        }
        if(turnoCaptura==10 && capturas.size()>=1){
            resultadoFinal="EMPATE";
            return true;
        }
        return false;
    }

    public List<String> getAutores(){
        autores.add("André Graça");
        autores.add("João Lionço");
        return autores;
    }

    public int getIDPeca(int x, int y) {
        for(CrazyPiece crazy: crazyList){
            if(crazy.getCoordenadaX() == x && crazy.getCoordenadaY() == y){
                return crazy.getIdPeca();
            }
        }
        return 0;
    }

    public int getIDEquipaAJogar(){
        return equipaAJogar;
    }

    public List<String> getResultados(){
        List<String> resultado = new ArrayList<String>();
        resultado.add("JOGO DE CRAZY CHESS");
        resultado.add("Resultado: " + resultadoFinal );
        resultado.add( "---" );
        resultado.add("Equipa das Pretas");
        resultado.add(contarPecasCapturadas(1)+"");
        resultado.add(jodaValidaPretas+"");
        resultado.add(jodaInvalidaPretas+"");
        resultado.add("Equipa das Brancas");
        resultado.add( contarPecasCapturadas(0)+"" );
        resultado.add(jodaValidaBrancas+"" );
        resultado.add("" + jodaInvalidaBrancas);
        return resultado;
    }

    public int contarPecasCapturadas(int equipa){
        int capturadas=0;
        for (CrazyPiece captured: capturas){
            if(captured.getIdEquipa()==equipa){
                capturadas++;
            }
        }
        return capturadas;
    }

    public List<CrazyPiece> getCrazyList() {
        return crazyList;
    }

    public int getTurno() {
        return turno;
    }

    public List<CrazyPiece> getCapturas() {
        return capturas;
    }

}
