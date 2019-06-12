/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3metaheuristicas;

import java.util.ArrayList;

public class AlgoritmoGreedy {
    private Integer tamano;
    ArrayList<Integer> finalUno;
    ArrayList<Integer> finalDos;
    HerramientasAuxiliares herramientasAux;
    public ArrayList<Integer> arraySolucion;
    
    /**
     * @description Función para obtener nuestro primer array final
     * @return ArrayList<Integer> que nos devuelve el array final
     */
    public ArrayList<Integer> getFinalUno(){
        return finalUno;
    }
    
    /**
     * @description Función para obtener nuestro segundo array final
     * @return ArrayList<Integer> que nos devuelve el array final
     */
    public ArrayList<Integer> getFinalDos(){
        return finalDos;
    }
    
    /**
     * @description Función para mostrar el array solución completo
     */
    public void mostrarArraySolucion(){
        for(int i=0; i<tamano; i++){
            System.out.println(" "+arraySolucion.get(i) + " ");
        }
    }
    
    /**
     * @description Esta funcion nos permite convertir todos los elementos de un array en un string.
     */
    public String ConversorArrayString(){
        String Palabra = "";
        for (Integer i = 0; i < herramientasAux.getTamano(); i++) {
            String auxiliar= Integer.toString(arraySolucion.get(i));
            Palabra+=" "+auxiliar;
        }
        return Palabra;
    }
    
    /**
     * @description Funcion para calcular el número de filas de cada array introducido
     * @param arrayUno primer array que vamos a estudiar sus filas
     * @param arrayDos segundo array que vamos a estudiar sus filas
     */
    public void calculoFilas(ArrayList<ArrayList<Integer>> arrayUno, ArrayList<ArrayList<Integer>> arrayDos){
        Integer resultado = 0;
        for(int i=0; i<tamano; i++){
            for(int j=0; j<tamano; j++){
                resultado += arrayUno.get(i).get(j);
            }
            finalUno.set(i, resultado);
            resultado=0;
        }
        
        for(int i=0; i<tamano; i++){
            for(int j=0; j<tamano; j++){
                resultado += arrayDos.get(i).get(j);
            }
            finalDos.set(i, resultado);
            resultado=0;
        }
    }
    
    /**
     * @description Funcion para calcular el algoritmo greedy teniendo en cuenta 
     * el flujo y la distancia en los arrays correspondientes
     * @return ArrayList<Integer> devolvemos un array solución que contiene los 
     * datos de mayor flujo con menor distancia entre ellos
     */
    public ArrayList<Integer> algoritmoGreedy(){
        ArrayList<Integer> arraySolucionGreedy = new ArrayList<>(tamano);
        Integer posicion = 0;
        ArrayList<Integer> arrayIndicesUno = new ArrayList<>();
        ArrayList<Integer> arrayIndicesDos = new ArrayList<>();
        
        Integer flujoMaximo = -5;
        Integer distanciaMinima = 1000000;
        
        //Creamos el array de indices de mayor a menor por posicion -> flujos
        for(int j=0; j<tamano; j++){
            for(int i=0; i<tamano; i++){
                if(flujoMaximo < finalUno.get(i)){
                    flujoMaximo = finalUno.get(i);
                    posicion=i;
                }
            }
            flujoMaximo = -5;
            finalUno.set(-5, finalUno.get(posicion));
            arrayIndicesUno.add(posicion);
        }
        
        //Creamos el array de indices de menor a mayor por posicion -> distancia
        for(int j=0; j<tamano; j++){
            for(int i=0; i<tamano; i++){
                if(distanciaMinima < finalDos.get(i)){
                    distanciaMinima = finalDos.get(i);
                    posicion=i;
                }
            }
            distanciaMinima = 1000000;
            finalDos.set(1000000, finalDos.get(posicion));
            arrayIndicesDos.add(posicion);
        }
        
        
        //Creamos array solucion -> mayor flujo con menor distancia
        for(int i=0; i<tamano; i++){
            arraySolucionGreedy.set(arrayIndicesDos.get(i), arrayIndicesUno.get(i));
        }
        
        arraySolucion = arraySolucionGreedy;
        
        return arraySolucion;
    }
    
}
