<h1 align="center"> <img src="https://github.com/RicharMareno/WebAPiJavaMongoDB/blob/main/LogoSiwat.svg" width=30> ProyFinal J2ME - 2006 <img src="https://github.com/RicharMareno/WebAPiJavaMongoDB/blob/main/LogoSiwat.svg" width=30></h1>

## Prooyecto realizado en Java J2ME, Se Utilizo (microedition (Canvas), IO, Runnable, Thread,  CommandListener, synchronized) 
### [Java](http://www.eclipse.org/platform) , ya no exite la pagina.
### [J2ME Wireless Toolkit](https://jcp.org/en/jsr/summary?id=j2me)
##
## Pequña explicacion de la aplicación
### [Clase Proyecto](https://github.com/REMSFALCOR/ProyFinal/blob/main/src/Proyecto/Proyecto.java)  
## Librerias Usadas y clases que implementamosen el Proyecto
``` java
import java.lang.Thread; 
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Proyecto extends MIDlet implements CommandListener,Runnable {
//***
//***
}
```
## Sincronizado de imagenes y maneras
``` java
public synchronized void nullPlayer() {	
		TextNomb.setString("");
		mirarfoto = null;   
		plano = null;						 	
    }
static public  synchronized Image getImagen(int cual) {		
		return IMG[cual];
    }			
static public String SacarNomb() {		
		return TextNomb.getString();
    } 
static public  synchronized int SetUbicar() {		
		return GardaLug.getSelectedIndex();
    } 		
static public synchronized boolean Ubicar() {		
		return UbiSer;
    }
static public Form getConsulta() {
	return Consulta;
   }
```
## Sincronizado de imagenes del menu
``` java
static public  synchronized String SacarMenu(int cual) {		
		return MENU[cual];
    }	
static public  synchronized String NombreServ(int cual) {		
		return options[cual];
    }	
static public  synchronized boolean SetSelec(int cual) {		
		return selected[cual];
    } 	
static public  synchronized Image SetMenu(int cual) {				
		return FotoMenu[cual];
	}	         
static public  synchronized Image SetSMenu(int cual) {				
		return FotoSubMenu[cual];
	}		
static public  synchronized Image SetServ(int cual) {		
		return FotoServ[cual];
    } 
```

## Ver en YouTube
 [![Alt text](https://img.youtube.com/vi/nVrzoEYSTFA/0.jpg)](https://www.youtube.com/watch?v=nVrzoEYSTFA)

## Ver desde Githup 
https://github.com/user-attachments/assets/fbcda9a6-1392-485d-b173-1eace450aff9



