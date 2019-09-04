/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3metaheuristicas;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import p3metaheuristicas.BusquedaLocal.tipoDato;


public class AlgoritmoGeneticoEstacionario {
    ArrayList<ArrayList<Integer>> poblacion;
    ArrayList<Integer> costePoblacion = new ArrayList<>();
    ArrayList<Integer> evolucionCoste = new ArrayList<>();
    Integer posicionPrimeroMejor;
    Integer posicionSegundoMejor;
    Integer posicionPrimeroPeor;
    Integer posicionSegundoPeor;
    HerramientasAuxiliares herramientasAux;
    
    /**
     * @description Funcion para devolver la evolucion de los costes
     * @return ArrayList array que tiene los costes
     */
    public ArrayList<Integer> getEvolucionCoste(){
        return evolucionCoste;
    }
    
      /**
     * @param hA Valor que queremos darle a herramientasAux
     * @description Funcion que permite darle valor a herramientasAux
     */
    public void setHerramientasAuxiliares(HerramientasAuxiliares hA){
        herramientasAux = hA;
    }
    
     /**
     * @param NuevaPoblacion Valor que queremos darle a poblacion
     * @description Funcion que permite darle valor a poblacion
     */
    public void setPoblacion(ArrayList<ArrayList<Integer>> NuevaPoblacion){
        poblacion = NuevaPoblacion;
    }
    
    
    /**
     * @description Función para mostrar los datos de la población que estamos estudiando
     * y cual es el primero mejor y el primero y segundo peores
     */
    public void mostrarPoblacion(){
        for(int i=0; i<herramientasAux.getNumeroCromosomasE();i++){
            System.out.println(""+i+"---");
            for (int j=0; j<herramientasAux.getTamano(); j++){
                System.out.println("" + poblacion.get(i).get(j) + " ");
            }
            
            System.out.println(" ---" + costePoblacion.get(i));
        }
        
        System.out.println("El mejor es: " + posicionPrimeroMejor + " con un coste de: " + costePoblacion.get(posicionPrimeroMejor));
        System.out.println("El peor es: " + posicionPrimeroPeor + " con un coste de: " + costePoblacion.get(posicionPrimeroPeor));
        System.out.println("El segundo peor es: " + posicionSegundoPeor + " con un coste de: " + costePoblacion.get(posicionSegundoPeor));
    
    }
    
    public void evolucion (boolean tipoCruce, tipoDato ti){
        evolucionCoste.clear();
        Integer generaciones = herramientasAux.getGeneraciones();
        Integer tamano = herramientasAux.getTamano();
        Integer numeroCromosomas = herramientasAux.getNumeroCromosomasE();
        Float probabilidadMutacion = herramientasAux.getProbabilidadMutacion();
        
        ArrayList<Integer> padreUno, padreDos;
        ArrayList<Integer> hijoUno, hijoDos;
        
        Integer costeHUno, costeHDos;
        Integer contador = 0;
        Integer repite = 0;
        poblacion = new ArrayList<>(numeroCromosomas);
        for (int i = 0; i < numeroCromosomas; i++){
            poblacion.set(i, new ArrayList<>(tamano));                   
        }
        costePoblacion = new ArrayList<>(numeroCromosomas);
        
        for (int i = 0; i < numeroCromosomas; i++){
            herramientasAux.cargarVector(poblacion.get(i));
            costePoblacion.set(i, herramientasAux.costeTotal(poblacion.get(i)));
            if (i == 0){
                posicionPrimeroMejor = i;
                posicionPrimeroPeor = i;
            }else{
                if(costePoblacion.get(i) < costePoblacion.get(posicionPrimeroMejor)){
                    posicionPrimeroMejor = i;
                }
                if(costePoblacion.get(i) > costePoblacion.get(posicionPrimeroPeor)){
                    posicionSegundoPeor = posicionPrimeroPeor;
                    posicionPrimeroPeor = i;
                }else{
                    if(costePoblacion.get(i) > costePoblacion.get(posicionSegundoPeor)){
                        posicionSegundoPeor = i;
                    }
                }
            }
        }
        
        Integer p1, p2, p3, p4;
        Cruce cruce = new Cruce();
        cruce.setTamano(tamano);
        Random random = new Random();
        
        while(contador < generaciones){
            
            p1=random.nextInt(numeroCromosomas-1);
            p2=random.nextInt(numeroCromosomas-1);
            p3=random.nextInt(numeroCromosomas-1);
            p4=random.nextInt(numeroCromosomas-1);
            
            if(costePoblacion.get(p1) < costePoblacion.get(p2)){
                padreUno = poblacion.get(p1);
            } else {
                padreUno = poblacion.get(p2);
            }
            
            if(costePoblacion.get(p3) < costePoblacion.get(p4)){
                padreDos = poblacion.get(p3);
            } else {
                padreDos = poblacion.get(p4);
            }
            
            if(tipoCruce==true){
                cruce.OX(padreUno, padreDos);
            } else {
                cruce.PMX(padreUno, padreDos);
            }
            
            hijoUno = cruce.getHijoUno();
            hijoDos = cruce.getHijoDos();
            
            funcionMutacion(hijoUno, hijoDos);
            costeHUno = herramientasAux.costeTotal(hijoUno);
            costeHDos = herramientasAux.costeTotal(hijoDos);
            
            if (repite == 50){
                
                BusquedaLocal b = new BusquedaLocal();
                b.setHerramientas(herramientasAux);
                b.setSolucionAnterior(hijoUno);
                
                BusquedaLocal b2 = new BusquedaLocal();
                b2.setHerramientas(herramientasAux);
                b2.setSolucionAnterior(hijoDos);
                
                costeHUno = b.algoritmoBusquedaLocalUno(ti);
                hijoUno = b.getSolucionFinal();
                
                costeHDos = b2.algoritmoBusquedaLocalUno(ti);
                hijoDos = b2.getSolucionFinal();
                
                repite = 0;
            }
            
            funcionReemplazo(hijoUno,hijoDos,costeHUno,costeHDos);
            
            posicionPrimeroPeor = 0;
            posicionSegundoPeor = 0;
            
            
             for(int i=0; i<numeroCromosomas; i++){
                if(costePoblacion.get(i) < costePoblacion.get(posicionPrimeroMejor)){
                    posicionPrimeroMejor = i;
                }
                if(costePoblacion.get(i) < costePoblacion.get(posicionPrimeroPeor)){
                    posicionSegundoPeor = posicionPrimeroPeor;
                    posicionPrimeroPeor = i;
                }else{
                    if(costePoblacion.get(i) < costePoblacion.get(posicionSegundoPeor)){
                    posicionSegundoPeor = i;
                }
                }
            }
            
            repite++;
            evolucionCoste.add(costePoblacion.get(posicionPrimeroMejor));
        }
    }
    
    public void funcionMutacion(ArrayList<Integer> hijoUno,ArrayList<Integer> hijoDos){
        Integer tamano = herramientasAux.getTamano();
        Double p;
        Double pMutacion = 0.001 * tamano;
        Integer genetico1;
        Random random = new Random();
        
        for(int i = 0; i < tamano; i++){
            p = RandomEnRangoDouble(0.0, 1.0);
            if(p < pMutacion){
                genetico1 = random.nextInt(tamano-1);
                while(i == (genetico1 = random.nextInt(tamano-1))){
                    Integer Auxiliar = hijoUno.get(i);
                    hijoUno.set(i, hijoUno.get(genetico1));
                    hijoUno.set(genetico1, Auxiliar);
                };
            }
        }
        
        for(int i=0; i<tamano; i++){
            p = RandomEnRangoDouble(0.0, 1.0);
            if(p < pMutacion){
                genetico1 = random.nextInt(tamano-1);
                while(i == (genetico1 = random.nextInt(tamano-1))){
                   Integer Auxiliar = hijoDos.get(i);
                   hijoDos.set(i, hijoDos.get(genetico1));
                   hijoDos.set(genetico1, Auxiliar);
                };
            }
        }
    }
    
    public void funcionReemplazo(ArrayList<Integer> hijoUno, ArrayList<Integer> hijoDos, Integer costeUno, Integer costeDos){
        Integer peor;
        Integer mejor;
        
        if(costeUno < costeDos){
            mejor = costeUno;
            peor = costeDos;
        }else{
            mejor = costeDos;
            peor = costeUno;
        }
        
        if(peor < costePoblacion.get(posicionSegundoPeor)){ // se cambiarían los dos
            poblacion.set(posicionSegundoPeor, hijoUno);
            costePoblacion.set(posicionSegundoPeor, costeUno);
            poblacion.set(posicionPrimeroPeor, hijoDos);
            costePoblacion.set(posicionPrimeroPeor, costeDos);
        }else if(peor < costePoblacion.get(posicionPrimeroPeor)){ // Se cambiaría el por por el primero peor y el mejor por el segundo peor
            if(Objects.equals(peor, costeUno)){
                poblacion.set(posicionPrimeroPeor, hijoUno);
                costePoblacion.set(posicionPrimeroPeor, costeUno);
                poblacion.set(posicionSegundoPeor, hijoDos);
                costePoblacion.set(posicionSegundoPeor, costeDos);
            }else{
                poblacion.set(posicionPrimeroPeor, hijoDos);
                costePoblacion.set(posicionPrimeroPeor, costeDos);
                poblacion.set(posicionSegundoPeor, hijoUno);
                costePoblacion.set(posicionSegundoPeor, costeUno);
            }
        }else if(mejor < costePoblacion.get(posicionPrimeroPeor)){ // Solo se cambia el mejor por el peor
            if(Objects.equals(mejor, costeUno)){
                poblacion.set(posicionPrimeroPeor, hijoUno);
                costePoblacion.set(posicionPrimeroPeor, costeUno);
            }else{
                poblacion.set(posicionPrimeroPeor, hijoDos);
                costePoblacion.set(posicionPrimeroPeor, costeDos);
            }
        }else if(mejor < costePoblacion.get(posicionSegundoPeor)){ // Solo se cambia el segundo peor por el mejor
            if(Objects.equals(mejor, costeUno)){
                poblacion.set(posicionSegundoPeor, hijoUno);
                costePoblacion.set(posicionSegundoPeor, costeUno);
            }else{
                poblacion.set(posicionSegundoPeor, hijoDos);
                costePoblacion.set(posicionSegundoPeor, costeDos);
            }
        }
    }
    
    public void mostrarSolucion(){
//        int tamano = herramientasAux.getTamano();
//        for(int i=0; i<tamano; i++){
//            System.out.println(" "+poblacion.get(i)+" ");
//        }
//        
        System.out.println("Coste: " + costePoblacion.get(posicionPrimeroMejor));
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
}
