/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;


public class TestBinarios {

  
    public static void main(String[] args) {
        IArbolBusqueda<Integer, String> arbol = new ArbolBinarioBusqueda<>();
        arbol.insertar(70, "A");
        arbol.insertar(80, "B");
        arbol.insertar(60, "C");
        arbol.insertar(40, "D");
        arbol.insertar(30, "E");
        arbol.insertar(120, "F");
        arbol.insertar(150, "G");
        arbol.insertar(200, "H");
        arbol.insertar(90, "I");
        System.out.println(arbol.toString());
//        System.out.println("--------------------------------");
//        IArbolBusqueda<Integer, String> arbol = new AVL<>();
//         arbol.insertar(80, "B");
//        arbol.insertar(60, "C");
//        arbol.insertar(40, "D");
//        arbol.insertar(30, "E");
//        arbol.insertar(120, "F");
//        arbol.insertar(150, "G");
//        arbol.insertar(200, "H");
//        arbol.insertar(90, "I");
//        System.out.println(arbolAVL.toString());
//        System.out.println(arbol.eliminar(100));
//        System.out.println("------------------------------------");
//        System.out.println(arbol.toString());
       
    }
    
}
