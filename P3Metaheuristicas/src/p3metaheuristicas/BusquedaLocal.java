/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3metaheuristicas;

import java.util.ArrayList;
import java.util.Collections;
import p3metaheuristicas.AlgoritmoGeneticoGeneracional.algoritmo;
import static p3metaheuristicas.BusquedaLocal.tipoDato.CIEN;

public class BusquedaLocal {
    
    public enum tipoDato{
        CIEN,
        QUINIENTOS  
    };
    
    private HerramientasAuxiliares herramientas;
    private ArrayList<Integer> solucionAnterior;
    private ArrayList<Integer> solucionFinal;
    
    BusquedaLocal(HerramientasAuxiliares h, ArrayList<Integer> array){
        herramientas = h;
        solucionAnterior = array;
        solucionFinal = array;
    }
    /**
     * @param SolucionAnterior ArrayList que contiene la solucion anterior
     * @description Esta funcion nos permite modificar el valor de nuestro parametro solucionAnterior.
     */
    public void setSolucionAnterior(ArrayList<Integer> SolucionAnterior){
        solucionAnterior = SolucionAnterior;
    }
    
    /**
     * @param SolucionFinal ArrayList que contiene la ultima solucion
     * @description Esta funcion nos permite modificar el valor de nuestro parametro solucionFinal.
     */
    public void setSolucionFinal(ArrayList<Integer> SolucionFinal){
        solucionFinal = SolucionFinal;
    }
    
    /**
     * @return ArrayList<Integer> con la solucion final del algoritmo
     * @description Esta funcion nos permite obtener la solucionFinal.
     */
    public ArrayList<Integer> getSolucionFinal(){
        return solucionFinal;
    }
    
    /**
     * @param herramientasAux Clase Herramientas que nos permitirá hacer el calculo de la Busqueda Local
     * @description Esta funcion nos permite modificar el valor de nuestro parametro herramientas.
     */
    public void setHerramientas(HerramientasAuxiliares herramientasAux){
        herramientas = herramientasAux;
    }
    
   
     /**
     * @param array ArrayList de entrada y salida donde vamos a cambiar las posiciones
     * @param origen Integer que indica la posicion del elemento origen
     * @param destino Integer que indica la posicion del elemento destino
     * @description Funcion de apoyo para la Búsqueda Local, que permite hacer intercambio de elementos de diferentes posiciones,
     * dado el Array y su posicion origen y destino.
     */
    
    public static void intercambioPosiciones(ArrayList<Integer> array, Integer origen, Integer destino ){
        Integer auxiliar = array.get(origen);
        array.set(origen, array.get(destino));
        array.set(destino, auxiliar);
    }
    
     /**
     * @description En esta funcion el objetivo es obtener la solución greedy mediante nuestros parametros de entrada, calculados previamente
     * y con distintas variables apoyo como son vectorIndice1, vectorIndice2, posicion, flujoMaximo y distanciaMaxima, el calculo consiste en
     * recorrer cada Array y comparar tanto la distancia minima como el flujo maximo, de superar los umbrales marcados se actualiza el valor y
     * la posicion del Array y se almacena en nuestros vectoresIndice, que más tarde la unión de estos saldra nuestro vectorSolucion.
     */
    public Integer AlgoritmoBusquedaLocal(algoritmo al){
        
         Integer coste = herramientas.costeTotal(solucionAnterior, al);
        Integer tamanoSolAnterior = solucionAnterior.size();
        ArrayList<Integer> dlb = new ArrayList<>(tamanoSolAnterior);
        for (int i = 0; i<tamanoSolAnterior; i++){
            dlb.add(0);
        }
        Integer contador = 0;
        boolean mejora = true;
        boolean parada = false;

        while (mejora && contador < 50000) {
            mejora = false;
            contador++;        
        
            for (int i = 0; i < tamanoSolAnterior && !mejora; i++) {
                if (dlb.get(i) == 0) {
                    parada = false;
                    for (int j = 0; j < tamanoSolAnterior && !mejora; j++) {
                        Integer CosteFactorial = herramientas.costeFactorial(solucionAnterior, i, j, coste, al);
                        if (CosteFactorial < coste) {
                            coste = CosteFactorial;
                            // Usamos Swap si vemos que no funciona bien deberiamos usar la funcion Intercambia posiciones de arriba
                            Collections.swap(solucionAnterior,i,j);
                            dlb.set(i, 0);
                            dlb.set(j, 0);
                            parada = true;
                            mejora = true;
                        }
                    }
                    if (parada == false) {
                        dlb.set(i, 1);
                    }
                }
            }
        }
        solucionFinal=solucionAnterior;
        return coste;
    }
    
    
    public Integer algoritmoBusquedaLocalUno(tipoDato a, algoritmo al){
        Integer tamanoSolAnterior = solucionAnterior.size();
        Integer coste = herramientas.costeTotal(solucionAnterior, al);
        ArrayList<Integer> dlb = new ArrayList<Integer> (tamanoSolAnterior);
        Integer contador = 0;
        boolean mejora = true;
        boolean parada = false;
        Integer ite;
        
        if(a.equals(CIEN)){
            ite=100;
        }else{
            ite=500;
        }
        
        while(mejora && contador < ite){
            mejora=false;
            contador++;
            
            for(int i=0; i<tamanoSolAnterior && !mejora; i++){
                if(dlb.get(i) == 0){
                    parada = false;
                    for(int j=0; j<tamanoSolAnterior && !mejora; j++){
                        Integer costeFactorial = herramientas.costeFactorial(solucionAnterior, i, j, coste, al);
                        if(costeFactorial < coste){
                            coste = costeFactorial;
                            intercambioPosiciones(solucionAnterior, i, j);
                            dlb.set(i, 0);
                            dlb.set(j, 0);
                            parada = true;
                            mejora = true;
                        }
                    }
                    
                    if(parada == false){
                        dlb.set(i, 1);
                    }
                }
            }
        }
        
        solucionFinal = solucionAnterior;
        return coste;
    }
    
    /**
     * @description Esta funcion nos permite convertir todos los elementos de un array en un string.
     */
    public String ConversorArrayString(){
        String Palabra = "";
        for (Integer i = 0; i < herramientas.getTamano(); i++) {
            String auxiliar= Integer.toString(solucionFinal.get(i));
            Palabra+=" "+auxiliar;
        }
        return Palabra;
    }
}
