/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

import java.util.List;
import Excepciones.OrdenInvalidoExcepcion;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * 
 */

public class ArbolMViasBusqueda <K extends Comparable<K>, V> implements IArbolBusqueda<K, V>{
    
    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected static final int ORDEN_MINIMO = 3;
    protected static final int POSICION_INVALIDA = -1;
        
    
    public ArbolMViasBusqueda() {
        this.orden = ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws OrdenInvalidoExcepcion {
            if (orden < ArbolMViasBusqueda.ORDEN_MINIMO) {
                    throw new OrdenInvalidoExcepcion();
            }
            this.orden = orden;
    }
        
        
    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave invalida, no se aceptan claves vacias");
        }
        
        if (valorAsociado == null) {
            throw new IllegalArgumentException("Valor invalido, no se aceptan valores nulos");
        }
        
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
            return;
        }
        
        NodoMVias<K,V> nodoAux = this.raiz;
        do{
            int posicionDeClaveAInsertar = this.buscarPosicionDeClave(nodoAux, claveAInsertar);
            if(posicionDeClaveAInsertar != ArbolMViasBusqueda.POSICION_INVALIDA){
                nodoAux.setValor(posicionDeClaveAInsertar, valorAsociado);
                nodoAux = NodoMVias.nodoVacio();
            }
            else if(nodoAux.esHoja()){
                if(nodoAux.clavesLLena()){
                    NodoMVias<K,V> nodoNuevo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                    int posicionDondeEnlazar = this.buscarPosicionPorDondeBajar(nodoAux, claveAInsertar);
                    nodoAux.setHijo(posicionDondeEnlazar, nodoNuevo);
                }
                else{
                    this.InsertarClaveYValorOrdenado(nodoAux, claveAInsertar, valorAsociado);
                }
                nodoAux = NodoMVias.nodoVacio();
            }
            else{
                int posicionDondeEnlazar = this.buscarPosicionPorDondeBajar(nodoAux, claveAInsertar);
                if(nodoAux.esHijoVacio(posicionDondeEnlazar)){
                    NodoMVias<K,V> nodoNuevo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                    nodoAux.setHijo(posicionDondeEnlazar, nodoNuevo);
                    nodoAux = NodoMVias.nodoVacio();
                } 
                else{
                    nodoAux = nodoAux.getHijo(posicionDondeEnlazar);
                }
            }
        } while(!NodoMVias.esNodoVacio(nodoAux));
        
    }
    
    protected int buscarPosicionDeClave(NodoMVias<K, V> nodoActual,K claveABuscar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                K claveEnTurno = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveEnTurno) == 0) {
                        return i;
                }
        }
        return ArbolMViasBusqueda.POSICION_INVALIDA;
    }
    
    protected int buscarPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveAInsertar) {
		int i = 0;
		boolean llegoAlFinal = false;
		while (i < nodoActual.nroDeClavesNoVacias()) {
			K claveActual = nodoActual.getClave(i);
			if (claveActual.compareTo(claveAInsertar) < 0) {
				i++;
			} else {
				break;
			}
		}
		if (nodoActual.getClave(nodoActual.nroDeClavesNoVacias()
				- 1).compareTo(claveAInsertar) < 0) {
			return nodoActual.nroDeClavesNoVacias();
		}
		return i;
    }
    
    protected void InsertarClaveYValorOrdenado(NodoMVias<K, V> nodoActual,K claveAInsertar, V valorAsociado) {
		boolean insertado = false;
		for (int i = nodoActual.nroDeClavesNoVacias() - 1; i >= 0 && !insertado; i--) {
			K claveActual = nodoActual.getClave(i);
			if (claveAInsertar.compareTo(claveActual) > 0) { // claveAInsertar > claveActual
				nodoActual.setClave(i + 1, claveAInsertar);
				nodoActual.setValor(i + 1, valorAsociado);
				insertado = true;
				//return;
			} else {
				nodoActual.setClave(i + 1, claveActual);
				nodoActual.setValor(i + 1, nodoActual.getValor(i));
			}
		}
		if (!insertado) {
			nodoActual.setClave(0, claveAInsertar);
			nodoActual.setValor(0, valorAsociado);
		}
    }
    
    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("Clave invalida,no se aceptan claves vacias");
        }
        
        V valorAsociado = buscar(claveAEliminar);
        if (valorAsociado == null) {
            throw new IllegalArgumentException("Valor invalida,no se aceptan valores vacios");
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAsociado;
    }

    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveEnTurno = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveEnTurno) == 0) {
                if (nodoActual.esHoja()) {
                        eliminarClaveDePosicion(nodoActual, i);
                        if (!nodoActual.hayClavesNoVacias()) {
                                return NodoMVias.nodoVacio();
                        } else {
                                return nodoActual;
                        }
                } else {
                    K claveDeReemplazo;
                    if (hayHijosNoVaciosMasAdelante(nodoActual, i)) {
                            claveDeReemplazo = obtenerSucesorInOrden(nodoActual, claveAEliminar);  //obtenerSucesorInOrden(nodoActual, i);
                    } else {
                            claveDeReemplazo = obtenerPredecesorInOrden(nodoActual, claveAEliminar);  //obtenerPredecesorInOrden(nodoActual, i);
                    }

                    V valorDeReemplazo = buscar(claveDeReemplazo);
                    nodoActual = eliminar(nodoActual, claveDeReemplazo);
                    nodoActual.setClave(i, claveDeReemplazo);
                    nodoActual.setValor(i, valorDeReemplazo);
                    return nodoActual;
                }
            } 

            if (claveAEliminar.compareTo(claveEnTurno) < 0) {
                    NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(i), claveAEliminar);
                    nodoActual.setHijo(i, supuestoNuevoHijo);
                    return nodoActual;
            }
        } 
        NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(
                        nodoActual.nroDeClavesNoVacias()), claveAEliminar);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }

    protected void eliminarClaveDePosicion(NodoMVias<K, V> nodoActual, int posicion) {
            for (int i = posicion; i <= nodoActual.nroDeClavesNoVacias() - 1; i++) {
                    nodoActual.setClave(i, nodoActual.getClave(i + 1));
                    nodoActual.setValor(i, nodoActual.getValor(i + 1));
            }
    }

	protected boolean hayHijosNoVaciosMasAdelante(NodoMVias<K, V> nodoActual, int posicion) {
		boolean existe = false;
		for (int i = posicion + 1; i <= nodoActual.nroDeClavesNoVacias() && !existe; i++) {
			if (!nodoActual.esHijoVacio(i)) {
				existe = true;
			}
		}
		return existe;
	}

	private K obtenerPredecesorInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {   //int i) {
		int posicion = buscarPosicionDeClave(nodoActual, claveAEliminar);
		K claveDeRetorno = (K) NodoMVias.datoVacio();
		if (!nodoActual.esHijoVacio(posicion)) {
			NodoMVias<K, V> nodoAuxiliar = nodoActual.getHijo(posicion);
			while (!NodoMVias.esNodoVacio(nodoAuxiliar)) {
				claveDeRetorno = nodoAuxiliar.getClave(nodoAuxiliar.nroDeClavesNoVacias() - 1);
				nodoAuxiliar = nodoAuxiliar.getHijo(0);
			}
			return claveDeRetorno;
		} else {
			return nodoActual.getClave(posicion-1);
		}
	}

	private K obtenerSucesorInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {   //int i) {
		int posicion = buscarPosicionDeClave(nodoActual, claveAEliminar);
		K claveDeRetorno = (K) NodoMVias.datoVacio();
		if (!nodoActual.esHijoVacio(posicion + 1)) {
			//int posicion = obtenerPosicionPorDondeBajar(nodoActual, claveAEliminar);

			NodoMVias<K, V> nodoAuxiliar = nodoActual.getHijo(posicion + 1);

			while (!NodoMVias.esNodoVacio(nodoAuxiliar)) {
				claveDeRetorno = nodoAuxiliar.getClave(0);
				nodoAuxiliar = nodoAuxiliar.getHijo(0);
			}
			return claveDeRetorno;
		} else {
			return nodoActual.getClave(posicion + 1);
		}

	}

    @Override
    public V buscar(K claveABuscar) {
        if (!this.esArbolVacio()) {
            NodoMVias<K, V> nodoActual = this.raiz;
            do {
                    boolean cambiaElNodoActual = false;
                    for (int i=0; i<nodoActual.nroDeClavesNoVacias()&& cambiaElNodoActual==false; i++) {
                            K claveDelNodoActual = nodoActual.getClave(i);
                            if (claveABuscar.compareTo(claveDelNodoActual) == 0) {
                                    return nodoActual.getValor(i);
                            }
                            if (claveABuscar.compareTo(claveDelNodoActual) < 0) {
                                    nodoActual = nodoActual.getHijo(i);
                                    cambiaElNodoActual = true;
                            }
                    }
                    if (cambiaElNodoActual==false) {
                            nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
                    }
            } while (!NodoMVias.esNodoVacio(nodoActual));
        }
        return null;
    }
    
    protected V buscarClave(K claveABuscar){
        if(this.esArbolVacio()){
            return null;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        boolean bandera = false;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias() && bandera==false; i++) {
                if(claveABuscar.compareTo(nodoActual.getClave(i)) == 0){
                    return nodoActual.getValor(i);
                }
                if(claveABuscar.compareTo(nodoActual.getClave(i)) < 0){
                    nodoActual = nodoActual.getHijo(i);
                    bandera = true;
                }

            }
            if(!bandera){
                nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
            }
        }
        return null;
    }
    
    public V encontrarClave(K claveABuscar){
        return encontrarClave(this.raiz, claveABuscar);
    }
    
    protected V encontrarClave(NodoMVias<K,V> nodoActual, K claveABuscar){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return null;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if(claveABuscar.compareTo(nodoActual.getClave(i)) == 0){
                return nodoActual.getValor(i);
            }
            V valorEncontrado = (V) encontrarClave(nodoActual.getHijo(i), claveABuscar);
            if(valorEncontrado != null){
                return valorEncontrado;
            }
        }
        return (V) encontrarClave(nodoActual.getHijo(orden-1), claveABuscar);
    }
    @Override
    public boolean contiene(K claveAVerificar) {
        return this.buscar(claveAVerificar) != null;
    }

    @Override
    public int size() {
        int cantidadDeNodos = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                cantidadDeNodos++;
                for (int i = 0; i < orden; i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
            } while (!colaDeNodos.isEmpty());
        }
        return cantidadDeNodos;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
                return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < this.orden; i++) {
                int alturaDeHijoActual = altura(nodoActual.getHijo(i));
                if (alturaDeHijoActual > alturaMayor) {
                        alturaMayor = alturaDeHijoActual;
                }
        }
        return alturaMayor + 1;
    }

    public int alturaIterativo() {
        if (NodoMVias.esNodoVacio(this.raiz)) {
            return 0;
        }
        Queue<NodoMVias<K, V>> cola = new LinkedList<>();
        cola.offer(this.raiz);
        int altura = 0;
        while (!cola.isEmpty()) {
            int size = cola.size();
            for (int i = 0; i < size; i++) {
                NodoMVias<K, V> nodoActual = cola.poll();
                for (int j = 0; j < this.orden; j++) {
                    NodoMVias<K, V> hijo = nodoActual.getHijo(j);
                    if (!NodoMVias.esNodoVacio(hijo)) {
                        cola.offer(hijo);
                    }
                }
            }
            altura++;
        }
        return altura;
    }
    
    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }
    
    @Override
    public int nivel() {
        return altura() - 1;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
            if (NodoMVias.esNodoVacio(nodoActual)) {
                    return;
            } 
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
                    recorrido.add(nodoActual.getClave(i));                
            }
            recorridoEnInOrden(nodoActual.getHijo(this.orden-1), recorrido);
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrden(raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoEnPreOrden(NodoMVias<K,V> nodoActual, List<K>recorrido){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                recorrido.add(nodoActual.getClave(i));
                recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
            }
            recorridoEnPreOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),recorrido);
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
		recorridoEnPostOrden(this.raiz, recorrido);
		return recorrido;
	}

    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
            if (NodoMVias.esNodoVacio(nodoActual)) {
                    return;
            }
            recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
                    recorrido.add(nodoActual.getClave(i));
            }
	
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {               
                NodoMVias<K, V> nodoActual = colaDeNodos.poll(); 
                for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    recorrido.add(nodoActual.getClave(i));
                    if (!nodoActual.esHijoVacio(i)) {
                            colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }
    


    public String toString() {
        return toStringNodo(raiz, "");
    }

    private String toStringNodo(NodoMVias<K,V> nodoActual, String ant) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            sb.append(ant).append("║\n");
            sb.append(ant).append("╠═Clave: ").append(nodoActual.getClave(i)).append(", Valor: ").append(nodoActual.getValor(i)).append("\n");
            sb.append(ant).append("║\n");
            sb.append(ant).append("╠═Hijo ").append(i).append(": ").append(toStringNodo(nodoActual.getHijo(i), ant + "║  ")).append("\n");
        }
        sb.append(ant).append("║\n");
        sb.append(ant).append("╚═Hijo ").append(nodoActual.nroDeClavesNoVacias()).append(": ").append(toStringNodo(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), ant + "║  "));
        return sb.toString();
    }
    
    //CANTIDAD DE NODOS VACIOS EN TODO EL ARBOL
    public int cantidadHijosVacios(){
        return cantHijosVacios(this.raiz);
    }
    
    private int cantHijosVacios(NodoMVias<K,V> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < this.orden; i++) {
           if(nodoActual.esHijoVacio(i)){
               cantidad++;
           } 
           else{
               cantidad = cantidad + cantHijosVacios(nodoActual.getHijo(i));
           }
        }
        return cantidad;
    }
    
    //CANTIDAD DE HIJOS VACIOS HASTA EL NIVEL N
    public int cantidadHijosVaciosHastaNivel(int nivel){
        return cantHijosVaciosHastaNivel(this.raiz, nivel, 0);
    }
    
    private int cantHijosVaciosHastaNivel(NodoMVias<K,V> nodoActual, int nivelFin, int nivelActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int cant = 0;
        if(nivelActual <= nivelFin){
            for (int i = 0; i < this.orden; i++) {
                if(nodoActual.esHijoVacio(i)){
                    cant++;
                }
                else{
                    cant += cantHijosVaciosHastaNivel(nodoActual.getHijo(i),
                            nivelFin, nivelActual +1);
                }
            }
        }
        return cant;
    }
    
    //CANTIDAD DE CLAVES VACIAS ANTES DEL NIVEL N
    public int cantClavesVaciasAntesNivel(int n) {
        if (this.esArbolVacio()) {
            return 0;
        }
        if (n <= 0) {
            throw new IllegalArgumentException("El nivel debe ser mayor que 0");
        }
        return cantClavesVaciasAntesNivelRecursivo(this.raiz, 0, n);
    }

    private int cantClavesVaciasAntesNivelRecursivo(NodoMVias<K, V> nodoActual, int nivelActual, int n) {
        if (nodoActual == null || nivelActual == n) {
            return 0;
        }
        int cantidadClavesVacias = nodoActual.cantClavesVacia();
        for (int i = 0; i < this.orden; i++) {
            if (!nodoActual.esHijoVacio(i)) {
                cantidadClavesVacias += cantClavesVaciasAntesNivelRecursivo(
                        nodoActual.getHijo(i), nivelActual + 1, n);
            }
        }
        return cantidadClavesVacias;
    }

    public int cantClavesVaciasAntesNivel2(int n) {
        if (this.esArbolVacio()) {
            return 0;
        }
        if (n <= 0) {
            throw new IllegalArgumentException("El nivel debe ser mayor que 0");
        }
        Queue<NodoMVias<K, V>> colaNodos = new LinkedList<>();
        Queue<Integer> colaNiveles = new LinkedList<>();
        colaNodos.offer(this.raiz);
        colaNiveles.offer(0);
        int cantidadClavesVacias = 0;
        while (!colaNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaNodos.poll();
            int nivelActual = colaNiveles.poll();
            if (nivelActual >= n) {
                break;
            }
            cantidadClavesVacias += nodoActual.cantClavesVacia();
            for (int i = 0; i < this.orden; i++) {
                if (!nodoActual.esHijoVacio(i)) {
                    colaNodos.offer(nodoActual.getHijo(i));
                    colaNiveles.offer(nivelActual + 1);
                }
            }
        }

        return cantidadClavesVacias;
    }

    public int obtenerNivel(K clave) {
        return obtenerNivel(this.raiz, clave, 0);
    }

    private int obtenerNivel(NodoMVias<K,V> nodoActual, K clave, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return -1; // La clave no se encuentra en el árbol
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if (clave.compareTo(nodoActual.getClave(i)) == 0) {
                return nivelActual;
            }
            if (clave.compareTo(nodoActual.getClave(i)) < 0) {
                int nivelEnHijo = obtenerNivel(nodoActual.getHijo(i), clave, nivelActual + 1);
                if (nivelEnHijo != -1) {
                    return nivelEnHijo;
                }
            }
        }
        // Revisar el último hijo si la clave es mayor que todas las claves en el nodo
        return obtenerNivel(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), clave, nivelActual + 1);
    }

    
    public int obtenerNivel2(K clave) {
        if (this.esArbolVacio()) {
            return -1; // El árbol está vacío
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        int nivelActual = 0;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean encontrado = false;
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                if (clave.compareTo(nodoActual.getClave(i)) == 0) {
                    return nivelActual; // Clave encontrada, retornar el nivel actual
                }
                if (clave.compareTo(nodoActual.getClave(i)) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    nivelActual++;
                    encontrado = true;
                    break; // Salir del bucle `for` y continuar con el siguiente nivel
                }
            }
            if (!encontrado) {
                // Si la clave es mayor que todas las claves en el nodo, revisar el último hijo
                nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
                nivelActual++;
            }
        }
        return -1; // La clave no se encuentra en el árbol
    }

    

    public static void main(String[] args){
        ArbolMViasBusqueda<Integer, String> arbolMV = new ArbolMViasBusqueda<>();
        arbolMV.insertar(100, "Sergio");
        arbolMV.insertar(200, "David");
        arbolMV.insertar(60, "Camila");
        arbolMV.insertar(80, "Eunice");
        arbolMV.insertar(90, "Keila");
        arbolMV.insertar(95, "Sara");
        arbolMV.insertar(92, "Juam");
        arbolMV.insertar(94, "Juam");
        arbolMV.insertar(150, "Juam");
        arbolMV.insertar(180, "Juam");
        arbolMV.insertar(170, "Lilian");
        arbolMV.insertar(185, "Juam");
        arbolMV.insertar(500, "Carlos");
        arbolMV.insertar(700, "Juam");
        arbolMV.insertar(300, "Juam");
        arbolMV.insertar(400, "Juam");
        arbolMV.insertar(450, "Juam");
//        System.out.println(arbolMV);
        //System.out.println("Hijo Vacio: "+arbolMV.cantidadHijosVaciosHastaNivel(2));
        System.out.println("altura: "+arbolMV.alturaIterativo());
        System.out.println("--------------------------------");
    
    }
    
    
}
