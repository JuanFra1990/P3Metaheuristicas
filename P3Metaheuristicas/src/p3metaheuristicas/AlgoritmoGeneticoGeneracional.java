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
    
    ArrayList<ArrayList<Integer>> poblacion = new ArrayList<>();
    ArrayList<ArrayList<Integer>> poblacionNueva = new ArrayList<>();
    ArrayList<Integer> evolucionCoste = new ArrayList<>();
    ArrayList<Integer> costePoblacion = new ArrayList<>();
    Integer posicionPrimeroMejor;
    ArrayList<Integer> mejor = new ArrayList<>();
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
        ArrayList<Integer> pob = new ArrayList<>(tamano);
         
        for (int i = 0; i<numeroCromosomas; i++) {
            poblacion.add(pob);
        }
        

        for (int i = 0; i < numeroCromosomas; i++) {
            herramientasAux.cargarVector(poblacion.get(i));
            if (costePoblacion.size()-1 > i) {
                costePoblacion.set(i, herramientasAux.costeTotal(poblacion.get(i)));
            } else {
                costePoblacion.add(herramientasAux.costeTotal(poblacion.get(i)));
            }
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
        Cruce cruce = new Cruce();
        cruce.setTamano(numeroCromosomas);
        Integer evaluaciones = 100;
        Integer totalevaluaciones = herramientasAux.getGeneraciones();
        Integer repeticiones = 0;
        
        while(evaluaciones<totalevaluaciones){

             posicionPrimeroMejor = 0;
            for (int i = 0; i < numeroCromosomas; i++) {
                int posicion2 = 0;
                poblacionNueva.add(new ArrayList<>(numeroCromosomas));
                while (i==posicion2){
                    posicion2 =  (int) (Math.random() * numeroCromosomas);
               }
               if (posicion2 == numeroCromosomas) {
                    posicion2--;
                }
                if (costePoblacion.get(i) < costePoblacion.get(Math.round(posicion2))) {
                    poblacionNueva.set(i, poblacion.get(i));
                    costes.add(i, (float)costePoblacion.get(i));
                } else {
                    poblacionNueva.set(i, poblacion.get(Math.round(posicion2)));
                    costes.add(i, (float)costePoblacion.get(Math.round(posicion2)));
                }
                if (costes.get(i) < costes.get(posicionPrimeroMejor)) {
                    posicionPrimeroMejor = i;
                }
                
            }

            Float costeMejor;
            costeMejor = costes.get(posicionPrimeroMejor);
            mejor = poblacionNueva.get(posicionPrimeroMejor);

            ArrayList<Boolean> flagsPadres = new ArrayList<>(numeroCromosomas); 
            for (int i = 0; i < numeroCromosomas; i++){
                flagsPadres.add(false);
            }
            Double p;
            Integer elemento = 0;
            for (int i = 0; i < numeroCromosomas; i++) {
                Random r = new Random();
                p = r.nextFloat() * (1.0 - 0.0) + 0.0;
                if (p < probabilidadCruce) {
                    while (i==elemento){
                            elemento =  (int) (Math.random() * numeroCromosomas);
                    }
                    if (elemento == numeroCromosomas) {
                        elemento--;
                    }
                    if (tipoCruce == true) {
                        cruce.OX(poblacionNueva.get(i), poblacionNueva.get(elemento));
                    } else {
                        cruce.PMX(poblacionNueva.get(i), poblacionNueva.get(elemento));
                    }
                    flagsPadres.add(i, true);
                    flagsPadres.add(elemento, true);
                    poblacionNueva.set(i, cruce.getHijoUno());
                    poblacionNueva.set(i, cruce.getHijoDos());
                }
            }

           funcionMutacion(flagsPadres, (int) Math.round(probabilidadMutacion));
            
            Boolean elitismo = false; 
            
            for (int i = 0; i < numeroCromosomas-1; i++) {
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
                        BusquedaLocal busquedaLocal = new BusquedaLocal();
                        busquedaLocal.setHerramientas(herramientasAux);
                        busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
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
                            BusquedaLocal busquedaLocal = new BusquedaLocal();
                            busquedaLocal.setHerramientas(herramientasAux);
                            busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
//                            busquedaLocal.setHerramientas(herramientasAux);
//                            busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
                            costes.set(i,(float)busquedaLocal.AlgoritmoBusquedaLocal());
                            poblacionNueva.set(i, busquedaLocal.getSolucionFinal());
                        }
                    }
                }
                if (al.equals(AMG_3)) {
                  for (int i = 0; i < numeroCromosomas; i++) {
                        Random r = new Random();
                        float f =  (float) (r.nextFloat() * (1.0 - 0.0) + 0.0);
                        if (f < 0.1) {
                            BusquedaLocal busquedaLocal = new BusquedaLocal();
                            busquedaLocal.setHerramientas(herramientasAux);
                            busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
//                            busquedaLocal.setHerramientas(herramientasAux);
//                            busquedaLocal.setSolucionAnterior(poblacionNueva.get(i));
                            costes.set(i,(float)busquedaLocal.AlgoritmoBusquedaLocal());
                            poblacionNueva.set(i, busquedaLocal.getSolucionFinal());
                        }
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
                       
             posicionPrimeroMejor=posicionMejor;
            mejor = poblacionNueva.get(posicionMejor);
            costeMejor = costes.get(posicionMejor);
            
            if(evolucionCoste.size() == 9){
                int i = 234;
            }
       
            
        
            poblacion=poblacionNueva;
            
            for(int i=0; i<costes.size(); i++){
                costePoblacion.add(costes.get(i).intValue());
            }

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
                        while(j == (genetico=RandomEnRango(0, poblacionNueva.get(i).size()-1))){
                            //System.out.println(poblacionNueva.get(i).size());
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
