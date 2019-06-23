/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3metaheuristicas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import p3metaheuristicas.AlgoritmoGeneticoGeneracional.algoritmo;

import p3metaheuristicas.BusquedaLocal.tipoDato;

/**
 *
 * @author anton & Juan Fra
 */


public class P3Metaheuristicas {

    private static Integer tamano;
    
    private static final ArrayList<ArrayList<Integer>> matrizDistancias = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> matrizFlujos = new ArrayList<>();
    
    private static final ArrayList<Integer> semillas = new ArrayList<>();
   
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
    
       StringBuilder str=new StringBuilder();
       String opcion = "0";
       long startTime;
       long endTime;
       while (!opcion.equals("11")) {
           System.out.println("---------------Menú Practica 2 -------------------------");
           System.out.println("--- 1. Carga de datos ----------------------------------");
           System.out.println("--- 2. Seleccion de semilla ----------------------------");
           System.out.println("--- 3. Seleccion de Algoritmo AME_2_100 ----------------");
           System.out.println("--- 4. Seleccion de Algoritmo AME_2_500-----------------");
           System.out.println("--- 5. Seleccion de Algoritmo AMG_3_100-----------------");
           System.out.println("--- 6. Seleccion de Algoritmo AMG_3_500-----------------");
           System.out.println("--- 7. Seleccion de Algoritmo AMG_10_100 ---------------");
           System.out.println("--- 8. Seleccion de Algoritmo AMG_10_500 ---------------");
           System.out.println("--- 9. Seleccion de Algoritmo AMG_ALL_100 --------------");
           System.out.println("--- 10. Seleccion de Algoritmo AMG_ALL_500 -------------");
           System.out.println("--- 11. Finalizar Programa -----------------------------");
           System.out.println("--------------------------------------------------------");
           System.out.println("Introduce opción: ");
           
           Scanner scanner = new Scanner(System. in);
           opcion = scanner. nextLine();
           System.out.println("Opcion elegida: "+ opcion);
            switch (opcion){
                case "1":
                   System.out.println("Has seleccionado la opción de cargar datos");
                   cargaDatos("./archivos/cnf02.dat");
                   break;
                case "2":
                   System.out.println("Has seleccionado la opción de seleccionar semillas");
                   System.out.println("¿Cuantas semillas desea introducir?");
                   Reader entradaNumeroSemillas=new InputStreamReader(System.in);
                   opcion=Character.toString((char)entradaNumeroSemillas.read());
                   Integer tamanoSemilla = Integer.parseInt(opcion);
                   Integer contador = 1;
                   while (tamanoSemilla > 0){
                        System.out.println("Introduzca la semilla numero " + contador);
                        Reader entradaSemillas=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaSemillas.read());
                        semillas.add(Integer.parseInt(opcion));
                        tamanoSemilla--;
                        contador++;
                   }
                   break;
                case "3":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el Algoritmo AME_2_100.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el Algoritmo AME_2_100.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AME_2_100");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoEstacionario algoritmoEstacionario = new AlgoritmoGeneticoEstacionario();
                    HerramientasAuxiliares herramientasAuxiliares = new HerramientasAuxiliares();
                    herramientasAuxiliares.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliares.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliares.setTamano(tamano);
                    herramientasAuxiliares.setNumeroCromosomasE(2);
                    herramientasAuxiliares.setEvaluaciones(0);
                    algoritmoEstacionario.setHerramientasAuxiliares(herramientasAuxiliares);
                    algoritmoEstacionario.evolucion(true, tipoDato.CIEN);
                    algoritmoEstacionario.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                  case "4":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el Algoritmo AME_2_500");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el Algoritmo AME_2_500.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AME_2_500");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoEstacionario algoritmoEstacionarioE = new AlgoritmoGeneticoEstacionario();
                    HerramientasAuxiliares herramientasAuxiliaresE = new HerramientasAuxiliares();
                    herramientasAuxiliaresE.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresE.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresE.setTamano(tamano);
                    herramientasAuxiliaresE.setNumeroCromosomasE(2);
                    herramientasAuxiliaresE.setEvaluaciones(0);
                    algoritmoEstacionarioE.setHerramientasAuxiliares(herramientasAuxiliaresE);
                    algoritmoEstacionarioE.evolucion(true, tipoDato.QUINIENTOS);
                    algoritmoEstacionarioE.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                case "5":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el Algoritmo AMG_3_100");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el Algoritmo AMG_3_100");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AMG_3_100");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoGeneracional algoritmoGeneracional= new AlgoritmoGeneticoGeneracional();
                    HerramientasAuxiliares herramientasAuxiliaresAMG3 = new HerramientasAuxiliares();
                    herramientasAuxiliaresAMG3.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresAMG3.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresAMG3.setTamano(tamano);
                    herramientasAuxiliaresAMG3.setNumeroCromosomasG(2);
                    herramientasAuxiliaresAMG3.setNumeroCromosomasE(2);
                    herramientasAuxiliaresAMG3.setEvaluaciones(1);
                    herramientasAuxiliaresAMG3.setProbabilidadCruce(Float.MAX_VALUE);
                    herramientasAuxiliaresAMG3.setProbabilidadMutacion(Float.MAX_VALUE);
                    algoritmoGeneracional.setHerramientasAuxiliares(herramientasAuxiliaresAMG3);
                    algoritmoGeneracional.evolucion(true,algoritmo.AMG_3 ,tipoDato.CIEN);
                    algoritmoGeneracional.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                 case "6":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el Algoritmo AMG_3_500");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el Algoritmo AMG_3_500.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AMG_3_500");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoGeneracional algoritmoGeneracionalAMG3 = new AlgoritmoGeneticoGeneracional();
                    HerramientasAuxiliares herramientasAuxiliaresAMG3Q = new HerramientasAuxiliares();
                    herramientasAuxiliaresAMG3Q.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresAMG3Q.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresAMG3Q.setTamano(tamano);
                    herramientasAuxiliaresAMG3Q.setNumeroCromosomasG(2);
                    herramientasAuxiliaresAMG3Q.setNumeroCromosomasE(2);
                    herramientasAuxiliaresAMG3Q.setEvaluaciones(1);
                    herramientasAuxiliaresAMG3Q.setProbabilidadCruce(Float.MAX_VALUE);
                    herramientasAuxiliaresAMG3Q.setProbabilidadMutacion(Float.MAX_VALUE);
                    algoritmoGeneracionalAMG3.setHerramientasAuxiliares(herramientasAuxiliaresAMG3Q);
                    algoritmoGeneracionalAMG3.evolucion(true,algoritmo.AMG_3 ,tipoDato.QUINIENTOS);
                    algoritmoGeneracionalAMG3.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                case "7":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el Algoritmo AMG_10_100.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el algoritmo Algoritmo AMG_10_100.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AMG_10_100");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoGeneracional algoritmoGeneracionalAMG10 = new AlgoritmoGeneticoGeneracional();
                    HerramientasAuxiliares herramientasAuxiliaresAMG10 = new HerramientasAuxiliares();
                    herramientasAuxiliaresAMG10.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresAMG10.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresAMG10.setTamano(tamano);
                    herramientasAuxiliaresAMG10.setNumeroCromosomasG(2);
                    herramientasAuxiliaresAMG10.setEvaluaciones(1);
                    herramientasAuxiliaresAMG10.setProbabilidadCruce(Float.MAX_VALUE);
                    herramientasAuxiliaresAMG10.setProbabilidadMutacion(Float.MAX_VALUE);
                    algoritmoGeneracionalAMG10.setHerramientasAuxiliares(herramientasAuxiliaresAMG10);
                    algoritmoGeneracionalAMG10.evolucion(true,algoritmo.AMG_10 ,tipoDato.CIEN);
                    algoritmoGeneracionalAMG10.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                case "8":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                       if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el  Algoritmo AMG_10_500.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el algoritmo de  Algoritmo AMG_10_500.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AMG_10_500");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoGeneracional algoritmoGeneracionalAMG10Q = new AlgoritmoGeneticoGeneracional();
                    HerramientasAuxiliares herramientasAuxiliaresAMG10Q = new HerramientasAuxiliares();
                    herramientasAuxiliaresAMG10Q.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresAMG10Q.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresAMG10Q.setTamano(tamano);
                    herramientasAuxiliaresAMG10Q.setNumeroCromosomasG(2);
                    herramientasAuxiliaresAMG10Q.setNumeroCromosomasE(2);
                    herramientasAuxiliaresAMG10Q.setEvaluaciones(1);
                    herramientasAuxiliaresAMG10Q.setProbabilidadCruce(Float.MAX_VALUE);
                    herramientasAuxiliaresAMG10Q.setProbabilidadMutacion(Float.MAX_VALUE);
                    algoritmoGeneracionalAMG10Q.setHerramientasAuxiliares(herramientasAuxiliaresAMG10Q);
                    algoritmoGeneracionalAMG10Q.evolucion(true,algoritmo.AMG_10 ,tipoDato.QUINIENTOS);
                    algoritmoGeneracionalAMG10Q.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                case "9":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el Algoritmo AMG_ALL_100.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el Algoritmo AMG_ALL_100.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AMG_ALL_100");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoGeneracional algoritmoGeneracionalAMGA = new AlgoritmoGeneticoGeneracional();
                    HerramientasAuxiliares herramientasAuxiliaresAMGA = new HerramientasAuxiliares();
                    herramientasAuxiliaresAMGA.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresAMGA.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresAMGA.setTamano(tamano);
                    herramientasAuxiliaresAMGA.setNumeroCromosomasG(2);
                    herramientasAuxiliaresAMGA.setNumeroCromosomasE(2);
                    herramientasAuxiliaresAMGA.setEvaluaciones(1);
                    herramientasAuxiliaresAMGA.setProbabilidadCruce(Float.MAX_VALUE);
                    herramientasAuxiliaresAMGA.setProbabilidadMutacion(Float.MAX_VALUE);
                    algoritmoGeneracionalAMGA.setHerramientasAuxiliares(herramientasAuxiliaresAMGA);
                    algoritmoGeneracionalAMGA.evolucion(true,algoritmo.AMG_ALL ,tipoDato.CIEN);
                    algoritmoGeneracionalAMGA.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                case "10":
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaIn.read());
                        if (opcion.equals("S")){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo, greedy.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=Character.toString((char)entradaNumeroSemillasR.read());
                        Integer tamanoSemillaESB =  Integer.parseInt(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=Character.toString((char)entradaSemillas.read());
                            semillas.add(Integer.parseInt(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion.equals("0")){
                            System.out.println("Sin semilla no podemos lanzar el algoritmo de Enfriamiento simulado Boltzmann.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    System.out.println("Has seleccionado la opción del Algoritmo AMG_ALL_500");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGeneticoGeneracional algoritmoGeneracionalAMGAQ = new AlgoritmoGeneticoGeneracional();
                    HerramientasAuxiliares herramientasAuxiliaresAMGAQ = new HerramientasAuxiliares();
                    herramientasAuxiliaresAMGAQ.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresAMGAQ.setMatrizFlujos(matrizFlujos);
                    herramientasAuxiliaresAMGAQ.setTamano(tamano);
                    herramientasAuxiliaresAMGAQ.setNumeroCromosomasG(2);
                    herramientasAuxiliaresAMGAQ.setNumeroCromosomasE(2);
                    herramientasAuxiliaresAMGAQ.setEvaluaciones(1);
                    herramientasAuxiliaresAMGAQ.setProbabilidadCruce(Float.MAX_VALUE);
                    herramientasAuxiliaresAMGAQ.setProbabilidadMutacion(Float.MAX_VALUE);
                    algoritmoGeneracionalAMGAQ.setHerramientasAuxiliares(herramientasAuxiliaresAMGAQ);
                    algoritmoGeneracionalAMGAQ.evolucion(true,algoritmo.AMG_ALL ,tipoDato.QUINIENTOS);
                    algoritmoGeneracionalAMGAQ.mostrarSolucion();
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                                     
                   break;
                case "11":
                   System.out.println("Ha decidido salir del programa, muchisimas gracias por usarlo.");
                   System.out.println("Hasta pronto!");
                   break;
                default:
                   System.out.println("Esta opción no esta contemplada.");
                   System.out.println("Elija otra. ¡Gracias!.");
                   break;
           }
               
       }

    }
 
     public static void cargaDatos(String archivo) throws FileNotFoundException, IOException {
      String cadena;
      FileReader f = new FileReader(archivo);
      Boolean primeravez=true;
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
                // Cogemos el primer caracter para representar el tamaño de la matriz (AXA)
                if (primeravez) {
                    System.out.println("tamaño de la matriz es:" + cadena + "X" + cadena);
                    tamano = new Integer(cadena);
                    primeravez = false;
                } else {
                    //A partir de hay nos queda coger los numeros de cada matriz y meterlos en una EEDD, una matriz es buena
                    //Ya que conociendo el tamaño podemos separar sus filas y sus columnas
                    if (!cadena.isEmpty()){
                        if (matrizDistancias.isEmpty()){
                             matrizDistancias.add(new ArrayList<>(tamano));
                        }
                        
                        if (matrizFlujos.isEmpty()){
                               matrizFlujos.add(new ArrayList<>(tamano));
                        }
                        
                        if (matrizFlujos.size() <= tamano){
                            String[] cadfila = cadena.split(" ");
                            for (String cadfila1 : cadfila) {
                                Integer num = new Integer(cadfila1);
                                matrizFlujos.get(matrizFlujos.size()-1).add(num);
                            }
                            matrizFlujos.add(new ArrayList<>(tamano));
                            
                        } else {
                            String[] cadfila = cadena.split(" ");
                            for (String cadfila1 : cadfila) {
                                Integer num = new Integer(cadfila1);
                                matrizDistancias.get(matrizDistancias.size()-1).add(num);
                            }
                            matrizDistancias.add(new ArrayList<>(tamano));   
                        }
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Excepcion leyendo fichero "+ archivo + ": " + e);
        }
        System.out.println("Carga realizada de manera correcta");
        int tamDistancias = matrizDistancias.size() -1;
        int tamFlujos = matrizFlujos.size() -1;
        System.out.println("El tamaño de la matriz de flujos es: " + tamFlujos);
        System.out.println("El tamaño de la matriz de distancias es: " + tamDistancias);
    }
}

