/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

/**
 *
 * @author Asrock
 */
public class CargarSintomas {

		IArbolBusqueda arbol;

		public CargarSintomas(IArbolBusqueda nuevoArbol) {
				this.arbol = nuevoArbol;
				/*
				
				arbol.insertar(89, "Termico");
				arbol.insertar(51, "Gato");*/
				
				arbol.insertar("Fiebre","La fiebre es el aumento temporal en la temperatura del "
                                                        + "cuerpo en respuesta a alguna enfermedad o padecimiento");
				arbol.insertar("Diarrea","La diarrea es la evacuación intestinal de "
                                                        + "heces flojas y líquidas tres o más veces al día");
				arbol.insertar("Dolores musculares", "Las enfermedades neuromusculares dañan "
                                                                   + "los nervios que controlan a los músculos");
				arbol.insertar("Fatiga", "olestia ocasionada por un esfuerzo más o menos prolongado"
                                                          + " o por otras causas, y que en ocasiones produce alteraciones físicas.");
				arbol.insertar("Tos", "Movimiento convulsivo y sonoro del aparato respiratorio "
                                                     + "de las personas y de algunos animales.");
				//arbol.insertar("Vomito",""); //
//				
		}
}
