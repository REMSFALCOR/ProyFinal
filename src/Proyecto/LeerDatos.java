package Proyecto;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LeerDatos  {
//------------------o Vectores de contenido o---------------------	
	int Punto[]=new int[2600],CualEs[]=new int[2600];
	String Cadena[]=new String[600];
	int Medio[]=new int[600],Distancia[]=new int[600];
//------------------o Tamaños de Vectores o-------------------------	
	int TamPunto=0,TamCadena=0,Indice=0;
	InputStream is ;
	InputStreamReader r;
	StringBuffer BUFER; //se registra el StringBuffer		
/*---------------------------o Metodos de Entrada o----------------------------------*/
	public void LeerText(String Texto) {
		System.out.println("Entre al conflicto");
		 try {			 	
				is = getClass( ).getResourceAsStream(Texto);
				r = new InputStreamReader(is);
				char[] buffer = new char[1000];
				BUFER = new StringBuffer( );
				int count;
				while ((count = r.read(buffer, 0, buffer.length)) > -1) {
					BUFER.append(buffer, 0, count);	
				}				
			} catch (IOException ex) {
				System.out.println("Error: Coneccion con el texto");			
			} 
			System.out.println("Sali del conflicto");	
	}	
/*---------------------------o Metodos de Salida o----------------------------------*/	
	public String[] CADENA(){ return Cadena;}//Pertenece a los nombres del Menu,Sevicios,
	public int[] PUNTO(){return Punto;}//Salida para puntos del Plano,Servicios,Caminos. etc.
	public int[] CUADRA(){return CualEs;}//Pertenece a los puntos del Plano,Camino
	public int TamPt(){return Indice;}//Pertenece a los puntos del Plano,Camino
	public int TamCual(){return TamPunto;}//Pertenece a los puntos del Plano,Camino
	public int TamCadena(){return TamCadena;}
	public int[] DIST(){return Distancia;}
	public int[] MEDIO(){return Medio;}	
	
/*---------------------------o Metodos de Proceso o----------------------------------*/	
	public void ForCadena(boolean id){	int T=0,t=0;	
		String Puntos=BUFER.toString();		
		while(T<Puntos.length()){
			t=Puntos.indexOf('X',T);
			Cadena[TamCadena]=new String(Puntos.substring(T,t));			
			if(id)
				ForPunto(Puntos.substring(T,t));
		T=t+1;	TamCadena++;														
		}
	}	 
	public void ForPunto(String Cade){int I=0,J=0,Mayor=0,Menor=10000000;	
	while(I<Cade.length()){					
		J=Cade.indexOf('Y',I);										
		Punto[Indice]=Integer.parseInt(Cade.substring(I,J));
		System.out.println("perfecto: "+Punto[Indice]);
		Mayor=max(Mayor,Punto[Indice]);
		Menor=min(Menor,Punto[Indice]);
			Indice++;													
		I=J+1;									
		}	
		Medio[TamPunto]=(Mayor+Menor)/2;
		Distancia[TamPunto]=Mayor-Medio[TamPunto];
		CualEs[TamPunto]=Indice-1;
		TamPunto++;				
	}	
	int min(int a,int b){if(a<b)return a;else return b;}
	int max(int a,int b){if(a>b)return a;else return b;}
}
