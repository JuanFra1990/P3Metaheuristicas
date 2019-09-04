/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3metaheuristicas;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Cruce {
    private Integer tamano;
    ArrayList<Integer> padreUno = new ArrayList<>();
    ArrayList<Integer> padreDos = new ArrayList<>();
    ArrayList<Integer> hijoUno = new ArrayList<>();
    ArrayList<Integer> hijoDos = new ArrayList<>();
    
     /**
     * @param _tamano Integer que sustituye el valor de tamano
     * @description función para editar el valor del Integer tamano
     */
    public void setTamano(Integer _tamano){
        tamano = _tamano;
    }
    
     /**
     * @description función para devolver el Integer tamano
     * @return Integer contenido del Integer tamano
     */
    public Integer getTamano(){
        return tamano;
    }
    
    /**
     * @param _padreUno ArrayList que sustituye el valor de padreUno
     * @description función para editar el valor del array padreUno
     */
    public void setPadreUno(ArrayList<Integer> _padreUno){
        padreUno = _padreUno;
    }
    
    /**
     * @description función para ver el array padreUno
     * @return padreUno contenido del array
     */
    public ArrayList<Integer> getPadreUno(){
        return padreUno;
    }
    
     /**
     * @param _padreDos ArrayList que sustituye el valor de padreDos
     * @description función para editar el valor del array padreDos
     */
    public void setPadreDos(ArrayList<Integer> _padreDos){
        padreUno = _padreDos;
    }
    
    /**
     * @description función para ver el array padreDos
     * @return padreDos contenido del array
     */
    public ArrayList<Integer> getPadreDos(){
        return padreDos;
    }
    
    /**
     * @param _hijoUno ArrayList que sustituye el valor de hijoUno
     * @description función para editar el valor del array hijoUno
     */
    public void setHijoUno(ArrayList<Integer> _hijoUno){
        hijoUno = _hijoUno;
    }
    
    /**
     * @description función para ver el array hijoUno
     * @return hijoUno contenido del array
     */
    public ArrayList<Integer> getHijoUno(){
        return hijoUno;
    }
    
    /**
     * @param _hijoDos ArrayList que sustituye el valor de hijoDos
     * @description función para editar el valor del array hijoDos
     */
    public void setHijoDos(ArrayList<Integer> _hijoDos){
        hijoUno = _hijoDos;
    }
    
    /**
     * @description función para ver el array hijoDos
     * @return hijoDos contenido del array
     */
    public ArrayList<Integer> getHijoDos(){
        return hijoDos;
    }
    /**
     * @description Función para lanzar el cruce de tipo OX
     * @param _padreUno el array que sustituye el valor en la clase cruce del padreUno
     * @param _padreDos el array que sustituye el valor en la clase cruce del padreDos
     */
    
    public void OX (ArrayList<Integer> _padreUno, ArrayList<Integer> _padreDos){
        padreUno = _padreUno;
        padreDos = _padreDos;
        
        Random random = new Random();
        
        Integer rangoUno = random.nextInt(tamano-1);
        Integer rangoDos = random.nextInt(tamano-1);
        
        while(rangoUno == (rangoDos=random.nextInt(tamano-1)));
        
        if (rangoUno > rangoDos){
            Integer auxiliar = rangoUno;
            rangoUno = rangoDos;
            rangoDos = auxiliar;
        }
        
        hijoUno = new ArrayList<Integer>(tamano);
        hijoDos = new ArrayList<Integer>(tamano);
        
       for(int i=0; i<tamano; i++){
            if(i>=rangoUno && i<=rangoDos){
                hijoUno.set(i,padreUno.get(i));
                hijoDos.set(i,padreDos.get(i));
            }
        }
       
        Integer contador1 = (rangoDos+1)%tamano;
        Integer contador2 = (rangoDos+1)%tamano;
        Integer i = rangoDos+1;
        Integer contador = 0;
        Integer posicion = null;
        
        while(contador < tamano){
            boolean estaUno=busca(padreUno,padreDos.get(i%tamano),rangoUno,rangoDos,posicion);
            boolean estaDos=busca(padreDos,padreUno.get(i%tamano),rangoUno,rangoDos,posicion);
            
            if(estaUno == false){
                hijoUno.set(contador1%tamano, padreDos.get(i%tamano));
                contador1++;
            }
            
            if(estaDos == false){
                hijoDos.set(contador2%tamano, padreUno.get(i%tamano));
                contador2++;
            }
            i++;
            contador++;
        }
    }
    
    /**
     * @description Función para buscar si esta o no está en la lista el elemento que nosotros queremos buscar
     * @param lista lista donde buscaremos el dato
     * @param elemento elemento que estamos buscando
     * @param rango1 tamaño mínimo del rango
     * @param rango2 tamaño máximo del rango
     * @param pos variable donde almacenamos la posicion del elemento buscado
     * @return true o false según si el dato ha sido encontrado o no
     */
    public boolean busca(ArrayList<Integer> lista, Integer elemento, Integer rango1, Integer rango2, Integer pos){
        for (int i=rango1; i<=rango2; i++){
            if(Objects.equals(elemento, lista.get(i))){
                pos=i;
                return true;
            }
        }
        return false;
    }
    
    public void PMX(ArrayList<Integer> _padreUno, ArrayList<Integer> _padreDos){
        padreUno = _padreUno;
        padreDos = _padreDos;
        Integer rangoUno,rangoDos;
        Integer posicion=null;
        
        Random random = new Random();
        rangoUno = random.nextInt(tamano-1);
        rangoDos = ThreadLocalRandom.current().nextInt((rangoUno+1)%tamano, tamano-1); // Esto es para que la posicion de corte no sea la misma para los dos rangos
        
        if(rangoUno>rangoDos){
            Integer auxiliar = rangoUno;
            rangoUno = rangoDos;
            rangoDos = auxiliar;
        }
        
        hijoUno = new ArrayList<Integer>(tamano);
        hijoDos = new ArrayList<Integer>(tamano);
        
        for(int i=0;i<tamano;i++){
            if(i>=rangoUno && i<=rangoDos){
                hijoUno.set(hijoUno.get(i),padreDos.get(i));
                hijoDos.set(hijoDos.get(i),padreUno.get(i));
            }
        }
        
        // Parte de reparación anterior
        for(int i = 0; i < rangoUno; i++){
            Integer elemento=padreUno.get(i);
            while(busca(hijoUno,elemento,rangoUno,rangoDos,posicion)){
                elemento = hijoDos.get(posicion);
            }
            
            hijoUno.set(i,elemento);
            elemento = padreDos.get(i);
            
            while(busca(hijoDos,elemento,rangoUno,rangoDos,posicion)){
                elemento = hijoUno.get(posicion);
            }
            
            hijoDos.set(i, elemento);
        }
        
        // Parte de reparación posterior
        for(int i = rangoDos+1; i < tamano; i++){
            Integer elemento = padreUno.get(i);
            while(busca(hijoUno,elemento,rangoUno,rangoDos,posicion)){
                elemento = hijoDos.get(posicion);
            }
            hijoUno.set(i, elemento);
            elemento = padreDos.get(i);
            
            while(busca(hijoDos,elemento,rangoUno,rangoDos,posicion)){
                elemento = hijoUno.get(posicion);
            }
            hijoDos.set(i,elemento);
        }
        
    }
}
