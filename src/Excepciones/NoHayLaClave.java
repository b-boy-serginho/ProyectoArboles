/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;


public class NoHayLaClave extends Exception {

    public NoHayLaClave() {
        super("Clave no existe en el arbol");
    }

    public NoHayLaClave(String message) {
        super(message);
    }
}
