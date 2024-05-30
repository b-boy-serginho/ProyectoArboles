/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

import Excepciones.OrdenInvalidoExcepcion;
import Excepciones.ClaveNoEncontrada;

public class TestMVias {
    
    public static void main(String[] args){
        IArbolBusqueda<Integer, String> arbolMV = new ArbolMViasBusqueda<>();
        arbolMV.insertar(50, "A");
        arbolMV.insertar(80, "B");
        arbolMV.insertar(60, "C");
        arbolMV.insertar(40, "D");
        arbolMV.insertar(30, "E");
        arbolMV.insertar(120, "F");
        arbolMV.insertar(150, "G");
        arbolMV.insertar(200, "H");
        arbolMV.insertar(90, "I");
        System.out.println(arbolMV);
        System.out.println("Buscar: "+arbolMV.buscar(270));
        System.out.println("--------------------------------");
        
//        IArbolBusqueda<Integer, String> arbolB = new ArbolB<>();
//        arbolMV.insertar(50, "A");
//        arbolMV.insertar(80, "B");
//        arbolMV.insertar(60, "C");
//        arbolMV.insertar(40, "D");
//        arbolMV.insertar(30, "E");
//        arbolMV.insertar(120, "F");
//        arbolMV.insertar(150, "G");
//        arbolMV.insertar(200, "H");
//        arbolMV.insertar(90, "I");   
//        System.out.println(arbolB);
//        System.out.println("--------------------------");
//        System.out.println(arbol.eliminar(100));
//        System.out.println(arbol.toString());
//       
    }
    
}
