/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3metaheuristicas;

import java.util.ArrayList;
import java.util.Random;
import static p3metaheuristicas.AlgoritmoGeneticoGeneracional.algoritmo.AMG_10;
import static p3metaheuristicas.AlgoritmoGeneticoGeneracional.algoritmo.AMG_3;
import static p3metaheuristicas.AlgoritmoGeneticoGeneracional.algoritmo.AMG_ALL;
import p3metaheuristicas.BusquedaLocal.tipoDato;


public class AlgoritmoGeneticoGeneracional {
    public enum algoritmo{
        AMG_ALL,
        AMG_10,
        AMG_3
    };
    
    ArrayList<ArrayList<Integer>> poblacion;
    ArrayList<ArrayList<Integer>> poblacionNueva;
    ArrayList<Integer> evolucionCoste;
    ArrayList<Integer> costePoblacion;
    Integer posicionPrimeroMejor;
    ArrayList<Integer> mejor;
    HerramientasAuxiliares herramientasAux;
    
      /**
     * @param hA Valor que queremos darle a herramientasAux
     * @description Funcion que permite darle valor a herramientasAux
     */
    public void setHerramientasAuxiliares(HerramientasAuxiliares hA){
        herramientasAux = hA;
    }
    
    
    
    /**
     * @description Funcion para devolver la evolucion de los costes
     * @return ArrayList<Integer> array de los costes almacenados
     */
    public ArrayList<Integer>  getEvolucionCoste(){
        return evolucionCoste;
    }
    
    /**
     * @description Función para mostrar la población y el primero mejor y su coste
     */
    public void mostrarPoblacion(){
        for(int i=0; i<herramientasAux.getNumeroCromosomasG();i++){
            System.out.println(" "+i+"---");
            for(int j=0; j<herramientasAux.getTamano(); j++){
                System.out.println(" "+poblacion.get(i).get(j)+" ");
            }
            System.out.println(" ---"+ costePoblacion.get(i) + " ");
        }
        
        System.out.println("El mejor es: " + posicionPrimeroMejor + " con un coste de: " + costePoblacion.get(posicionPrimeroMejor)+ ".");
    }
    
    /**
     * @description Función para crear numeros aleatorios en un rango introducido por dos números en la función
     * @param min el número mínimo del rango
     * @param max el número máximo del rango
     * @return Devuelve los números creado aleatoriamente
     */
    private static int RandomEnRango(int min, int max) {
        if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    
    /**
     * @description Función para crear numeros aleatorios en un rango introducido por dos números en la función
     * @param min el número mínimo del rango
     * @param max el número máximo del rango
     * @return Devuelve los números creado aleatoriamente
     */
    private static Double RandomEnRangoDouble(Double min, Double max) {
        if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
        }

        Double r = new Random().nextDouble();
        Double resultado = min + (r * (max - min));
        return resultado;
    }
    
    /**
     * @description Funcion para intercambiar dos valores entre ellos
     * @param uno valor uno introducido
     * @param dos valor dos introducido
     */
    private void swap(Integer uno, Integer dos){
        Integer aux;
        aux = uno;
        uno = dos;
        dos = aux;
    }
    
    @SuppressWarnings("empty-statement")
    public void evolucion (boolean tipoCruce, algoritmo al, tipoDato tD){
        evolucionCoste.clear();
        Integer tamano = herramientasAux.getTamano();
        Integer numeroCromosomas = herramientasAux.getNumeroCromosomasG();
        

        for (int i = 0; i < numeroCromosomas; i++) {
            herramientasAux.cargarVector(poblacion.get(i));
            costePoblacion.set(i, herramientasAux.costeTotal(poblacion.get(i)));
            if (i == 0) {
                posicionPrimeroMejor = i;
            } else {
                if (costePoblacion.get(i) < costePoblacion.get(posicionPrimeroMejor)) {
                    posicionPrimeroMejor = i;
                }
            }
        }
    
        Float probabilidadCruce = herramientasAux.getProbabilidadCruce(); 
        Float probabilidadMutacion = herramientasAux.getProbabilidadMutacion() * tamano; 
        
        ArrayList<Float> costes = new ArrayList<>(numeroCromosomas); // donde se iran guardando los costes de los ganadores del los torneos
        Cruce cruce = new Cruce(numeroCromosomas);
        Integer generaciones = 1000;
        Integer contador = 0;
        Integer repeticiones = 0;

    
        while (contador < generaciones) {

            //Torneo;
            posicionPrimeroMejor = 0;
            for (int i = 0; i < numeroCromosomas; i++) {
                Integer posicion2;

                while (i == (posicion2 = (int) (Math.random() * numeroCromosomas -1) + 1));
                if (costePoblacion.get(i) < costePoblacion.get(posicion2)) {
                    poblacionNueva.set(i, poblacion.get(i));
                    costes.set(i, (float)costePoblacion.get(i));
                } else {
                    poblacionNueva.set(i, poblacion.get(posicion2));
                    costes.set(i, (float)costePoblacion.get(posicion2));
                }

                if (costes.get(i) < costes.get(posicionPrimeroMejor)) {
                    posicionPrimeroMejor = i;
                }
            }

            Float costeMejor;// guardamos el mejor coste por si se reempleza tenerlo a mano
            // y así no tener que calcularlo de nuevo
            // copiamos el coste del mejor del torneo y copio el vector;
            costeMejor = costes.get(posicionPrimeroMejor);
            mejor = poblacionNueva.get(posicionPrimeroMejor);

            ArrayList<Boolean> flagsPadres = new ArrayList<>(numeroCromosomas); 
            Double p;
            Integer elemento;
            for (int i = 0; i < numeroCromosomas; i++) {
                Random r = new Random();
                p = r.nextFloat() * (1.0 - 0.0) + 0.0;
                if (p < probabilidadCruce) {
                    while (i == (elemento = (r.nextInt() * numeroCromosomas -1)));
                    if (tipoCruce == true) {
                        cruce.OX(poblacionNueva.get(i), poblacionNueva.get(elemento));
                    } else {
                        cruce.PMX(poblacionNueva.get(i), poblacionNueva.get(elemento));
                    }
                    flagsPadres.set(i, true);
                    flagsPadres.set(elemento, true);
                    poblacionNueva.set(i, cruce.hijoUno());
                    poblacionNueva.set(i, cruce.hijoDos());
                }
            }

            funcionMutacion(flagsPadres, (int) Math.round(probabilidadMutacion));
            Boolean elitismo = false; 
            
            for (int i = 0; i < numeroCromosomas; i++) {
                if (flagsPadres.get(i) == true) {
                    if (i == posicionPrimeroMejor) {
                        elitismo = true;
                    }
                    costes.set(i, (float)herramientasAux.costeTotal(poblacionNueva.get(i)));
                    //evaluaciones++;if(evaluaciones==totalevaluaciones)break;
                }
            }
            
            Integer posicionPeor = 0;
            
            for (int i = 1; i < numeroCromosomas; i++) {
                if (costes.get(i) > costes.get(posicionPeor)) {
                    posicionPeor = i;
                }
            }
            
            if (elitismo == true) {
                poblacionNueva.set(posicionPeor, mejor);
                costes.set(posicionPeor, costeMejor);
            }
            if (repeticiones == 10) {
                if (al.equals(AMG_ALL)) {
                    for (int i = 0; i < numeroCromosomas; i++) {
                        BusquedaLocal busquedaLocal = new BusquedaLocal(herramientasAux,poblacionNueva.get(i));
//                        busquedaLocal.setHerramientas(herramientasAux);
//                        busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
                        costes.set(i, (float)busquedaLocal.AlgoritmoBusquedaLocal());
                        poblacionNueva.set(i, busquedaLocal.getSolucionFinal());
                    }
                }
                if (al.equals(AMG_10)) {
                    for (int i = 0; i < numeroCromosomas; i++) {
                        Random r = new Random();
                        float f =  (float) (r.nextFloat() * (1.0 - 0.0) + 0.0);
                        if (f < 0.1) {
                            BusquedaLocal busquedaLocal = new BusquedaLocal(herramientasAux,poblacionNueva.get(i));
//                            busquedaLocal.setHerramientas(herramientasAux);
//                            busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
                            costes.set(i,(float)busquedaLocal.AlgoritmoBusquedaLocal());
                            poblacionNueva.set(i, busquedaLocal.getSolucionFinal());
                        }
                    }
                }
                if (al.equals(AMG_3)) {
                    Float auxiliar = Float.MAX_VALUE;
                    ArrayList<Integer> pos = new ArrayList<>(3);
                    ArrayList<Float> calculoCostes = new ArrayList<>();
                    calculoCostes.addAll(costes);
                    for (int i = 0; i < 3; i++) {
                        auxiliar = Float.MAX_VALUE;
                        for (int j = 0; j < numeroCromosomas; j++) {
                            if (calculoCostes.get(j) < auxiliar) {
                                auxiliar = calculoCostes.get(j);
                                pos.set(i, j);
                            }
                        }
                        calculoCostes.set(pos.get(i), Float.MAX_VALUE);

                    }
                    for (int i = 0; i < 3; i++) {
                        BusquedaLocal busquedaLocal = new BusquedaLocal(herramientasAux, poblacionNueva.get(pos.get(i)));
                        // busquedaLocal.setHerramientas(herramientasAux);
                        // busquedaLocal.setSolucionAnterior(poblacionNueva.get(pos.get(i)));
                        
                        costes.set(pos.get(i), (float)busquedaLocal.AlgoritmoBusquedaLocal());
                        poblacionNueva.set(pos.get(i), busquedaLocal.getSolucionFinal());
                    }
                }
                repeticiones = 0;
            }
            
            Integer posicionMejor = 0;
            for (int i = 1; i < numeroCromosomas; i++) {
                if (costes.get(i) < costes.get(posicionMejor)) {
                    posicionMejor = i;
                }
            }
            
            posicionPrimeroMejor = posicionMejor;
            mejor = poblacionNueva.get(posicionMejor);
            costeMejor = costes.get(posicionMejor);

            poblacion = poblacionNueva;
            costes.forEach((n) -> costePoblacion.add(Math.round(n))); 
            contador++;
            repeticiones++;
            evolucionCoste.add(costePoblacion.get(posicionPrimeroMejor));
        }
    }
    
    public void funcionMutacion(ArrayList<Boolean> marcaje, int pMutacion){
        Integer tamano = herramientasAux.getTamano();
        Integer numeroCromosomas = herramientasAux.getNumeroCromosomasG();
        Double p;
        Integer genetico;
        
        for(int i=0; i<numeroCromosomas; i++){
            for(int j=0; j<tamano; j++){
                if(marcaje.get(i) == true){
                    p = RandomEnRangoDouble(0.0, 1.0);
                    if(p<pMutacion){
                        while(j == (genetico=RandomEnRango(0, tamano-1))){
                            swap(poblacionNueva.get(i).get(j),poblacionNueva.get(i).get(genetico));
                        }
                    }
                }
            }
        }
    }
    
    public void mostrarSolucion(){
//        int tamano = herramientasAux.getTamano();
//        for(int i=0; i<tamano; i++){
//            System.out.println(" " + poblacion.get(i) + " ");
//        }
        
        System.out.println("Coste: "+costePoblacion.get(posicionPrimeroMejor));
    }
}
