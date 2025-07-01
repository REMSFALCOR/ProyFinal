package Proyecto;

import java.lang.Thread; 
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Proyecto extends MIDlet implements CommandListener,Runnable {
/*-----------------  Variables de conexion externa  ------------------*/	
	private static LeerDatos LeeDat;
	private static Inter InTeR;
	private static Plano plano;	
	private static MirarFoto mirarfoto;	
	private static MenuSyst MenU;
/*-----------------  Variables de commando ------------------*/	
	private Command No = new Command("No", Command.CANCEL, 1);
	private Command okCommand = new Command("Ok", Command.OK, 1);
	private Command Ubicar = new Command("Ubicar", Command.OK, 1);
	private Command OKlineMovil=new Command("okMan",Command.OK,1);
	private Command backCommand = new Command("Atras", Command.BACK, 2);
/*-----------------  Variables de las Listas ------------------*/		
	private static List  GardaLug;
	private static List  List_camino;
	private static List  Fotos;
	private static List  MirarServ;
	//private static TextBox t1;
	private static Form  Consulta,AcD,lineaTrufis;
	private static Form  Fin;
	private static List milista;
	String[] lineas={"Taxi Quillacollo","Taxi Copacabana","Taxi Flash","Taxi Linea 7","Taxi Lider"};	
	private String[] Camp1={"4262626","4266626","4267890","4244424","4266666"};	
	
	public static String[] options;	
	private Display display;
	
	int index = 0;//Elige que clase debe estar en el hilo 
	
	private static String MENU[];
	private static String Fot[]={"Paisajes","Plazas"};
	
	private static Proyecto instance = null;
	
	private static boolean[] selected;
	private static Image FotoMenu[]=new Image[8];
	private static Image FotoSubMenu[]=new Image[8];
	private static Image FotoServ[]=new Image[5];
	private static Image IMG[]=new Image[6];
	private static TextField TextNomb;
	String Alg[];
	String Servi=null;
	private static boolean  UbiSer;
/*-----------------  Variables del Plano ------------------*/		
	private static int VectX[],VectY[],Cual[],TamVect=0,TamCual=0;
	private static int MedX[],MedY[],DistX[],DistY[];
/*-----------------  Variables del CAMINO ------------------*/		
	private static int INTERS[],CualInt[],TamInter=0,TcualInt=0;
	String NombCalle[];
	private static int CamX[],CamY[],CualCam[],TamCam=0,TamCualCam=0;
	
/*-----------------  Variables de las Base de datos ------------------*/	
 //  private static Vector images=new Vector(1);	
/*-----------------  Variables Otras ------------------*/		
/*-----------------o  funciones de Proceso o------------------*/
/*-----------------o  funciones de Entrada o------------------*/
/*-----------------o  funciones de Salida o------------------*/	
	boolean si;
	void CargaMenu(int Men,String Tex){	
		si=true;
		LeeDat=new LeerDatos();	
		LeeDat.LeerText(Tex);		
    	LeeDat.ForCadena(false);    	
    	Alg=LeeDat.CADENA();       	    	
    	for(int a=0;a<LeeDat.TamCadena();a++){
    		
    		if(Men==0){	if(si)MENU=new String[LeeDat.TamCadena()];
    		MENU[a]=Alg[a];
    		}
    		    		   		
    		if(Men==1){	if(si)options=new String[LeeDat.TamCadena()];
    		System.out.println("options: "+LeeDat.TamCadena());
    		options[a]=Alg[a];
    		}
    		if(Men==2){	if(si)NombCalle=new String[LeeDat.TamCadena()];
    		NombCalle[a]=Alg[a];
    		}
    		/*if(Men==3){	if(si)otro=new List("Que onda Hueys",List.IMPLICIT,lineas,null);
    		NombCalle[a]=Alg[a];
    		}*/
    		
    		si=false;
		}
	}
	public void LeerTexto(char Id,String Texto) {
		LeeDat=new LeerDatos();
		LeeDat.LeerText(Texto);						
				if(Id=='A')
				{	
					LeeDat.ForCadena(true);					
					TamVect=LeeDat.TamPt();
					VectX=new int[TamVect];
					VectX=LeeDat.PUNTO();			
					Cual=new int[TamCual];
					Cual=LeeDat.CUADRA();
					MedX=new int[TamCual];
					MedX=LeeDat.MEDIO();
					DistX=new int[TamCual];
					DistX=LeeDat.DIST();					
				}
				else if(Id=='B'){
					LeeDat.ForCadena(true);										
					VectY=new int[TamVect];
					VectY=LeeDat.PUNTO();
					TamCual=LeeDat.TamCual();
					MedY=new int[TamCual];
					MedY=LeeDat.MEDIO();
					DistY=new int[TamCual];
					DistY=LeeDat.DIST();	
				}		
				else if(Id=='C'){
					LeeDat.ForCadena(true);
					TamInter=LeeDat.TamPt();
					INTERS=new int[TamInter];
					INTERS=LeeDat.PUNTO();
					TcualInt=LeeDat.TamCual();
					CualInt=new int[TcualInt];//TamCualCam
					CualInt=LeeDat.CUADRA();					
				}				
				else if(Id=='D'){
					LeeDat.ForCadena(true);
					TamCam=LeeDat.TamPt();
					CamX=new int[TamCam];
					CamX=LeeDat.PUNTO();
					TamCualCam=LeeDat.TamCual();
					CualCam=new int[TamCualCam];
					CualCam=LeeDat.CUADRA();					
				}
				else if(Id=='E'){
					LeeDat.ForCadena(true);
					CamY=new int[TamCam];
					CamY=LeeDat.PUNTO();				
				}				
			return;
	 }		
/*########################## Variavles de conexcion ##########################*/		
	public synchronized void nullPlayer() {	
		TextNomb.setString("");
	mirarfoto = null;   
		plano = null;						 	
    }	
	static public  synchronized Image getImagen(int cual) {		
		return IMG[cual];
    }	
	/*
	static public synchronized void CargaMapa(Image Dat[]) {		
		//(Image)images.elementAt(0);
		}*/
	/*
	static public  synchronized Image getImagVe(int cual) {		
		return (Image)images.elementAt(0);
    }*/
		
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
	
	static public Proyecto getInstance() {
		return instance;
    } 	
//	--------------- conexcioncon del Plano -----------------	
	static public  synchronized int PtX(int cual) {				
		return VectX[cual];
	}
	static public  synchronized int PtY(int cual) {				
		return VectY[cual];
	}
	static public  synchronized int MEDX(int cual) {				
		return MedX[cual];
	}
	static public  synchronized int MEDY(int cual) {				
		return MedY[cual];
	}
	static public  synchronized int DISTANX(int cual) {				
		return DistX[cual];
	}
	static public  synchronized int DISTANY(int cual) {				
		return DistY[cual];
	}	
	static public  synchronized int TamM() {				
		return TamVect;
	}
	static public  synchronized int TamC() {				
		return TamCual;
	}
	static public  synchronized int CUAL(int cual) {				
		return Cual[cual];
	}	
//	--------------- conexcioncon del Camino -----------------		
	static public  synchronized int CAM_X(int cual) {				
		return CamX[cual];
	}	
	static public  synchronized int CAM_Y(int cual) {				
		return CamY[cual];
	}	
	static public  synchronized int TCam() {				
		return TamCam;
	}
	static public  synchronized int TCamC() {				
		return TamCualCam;
	}
	static public  synchronized int CamCual(int cual) {				
		return CualCam[cual];
	}	
//--------------- conexcioncon las listas -----------------
	static public MenuSyst getMenu() {	//Lista Guardar	
		return MenU;
    } 		
	static public List getGuard() {	//Lista Guardar	
		return GardaLug;
    } 		
	static public List getList() {//Lista Mirar Foto		
		return List_camino;
    } 		
	static public List Fotito() {//Lista Mirar Foto		
		return  Fotos;
    }
	static public List lineas() {//		
		return  milista;
    } 			
	static public List getList1() {		
		return MirarServ;
    } 	
	static public Form Salir() {
       return Fin;
    } 		
	static public Form Acerca() {
	       return AcD;
	    } 		
	
/*########################## ---------LLenado la informacion del sistema------------ ##########################*/		
	void LLenado(){
		CargaMenu(0,"MENU/MenuPrincipal.txt");
    	CargaMenu(1,"MENU/MenuServicio.txt");
    	CargaMenu(2,"CALLE/Nombre.txt");    	
    	LeerTexto('A',"PLANOS/PuntosX.txt");
		LeerTexto('B',"PLANOS/PuntosY.txt");
		LeerTexto('C',"CALLE/Inter.txt");
		LeerTexto('D',"CALLE/CaminoX.txt");
		LeerTexto('E',"CALLE/CaminoY.txt");
    	for(int i=0;i<8;i++){
    		try {    	    			
    		    if(i<5)					  
				  FotoServ[i]=Image.createImage ("/FOTOS/Serv"+(i+1)+".png");
    			if(i<6)
      			  IMG[i] = Image.createImage("/ALBUN/Foto"+(i+1)+".png");	
    		    if(i<6)
				  FotoMenu[i]=Image.createImage ("/MENU/Men"+(i+1)+".png");
    		    if(i<6)
  				  FotoSubMenu[i]=Image.createImage ("/MENU/SMen"+(i+1)+".png");    		    
		  } catch (Exception e) { System.err.println("error: IMAGEN: " + e);}	
    	}    
    	//images.addElement(Image.createImage ("/ALBUN/Foto"+(i+1)+".png"));
    	//Image a=(Image)images.elementAt(1);
	}				
    public Proyecto() { 
    	instance = this; 
    	LLenado();			
		//create an implicit list
		GardaLug= new List(MENU[0],List.IMPLICIT,options, FotoServ);		
		GardaLug.addCommand(backCommand);
		GardaLug.setCommandListener(this);
		
		//create an exclusive list
		List_camino= new List(MENU[1],List.IMPLICIT,NombCalle,null);//FotoServ		
		List_camino.addCommand(backCommand);
		List_camino.setCommandListener(this);
		
		Fotos= new List(MENU[1],List.IMPLICIT,Fot,null);
		Fotos.addCommand(backCommand);
		Fotos.setCommandListener(this);
		
		//create a mutiple choice list			
		MirarServ= new List(MENU[2],List.MULTIPLE, options, FotoServ);	
		MirarServ.addCommand(okCommand);
		MirarServ.addCommand(backCommand);
		MirarServ.setCommandListener(this);
		System.out.println("LIst: 3");
		
        //create a mutiple choice list
		milista=new List(MENU[3],List.IMPLICIT,lineas,null);
		milista.addCommand(OKlineMovil);
		milista.addCommand(backCommand);
		milista.setCommandListener(this);
		
        //create a mutiple choice list
		/*---------o la consulta o--------*/		
		Consulta= new Form("Registrar");
		
		Consulta.addCommand(Ubicar);
		Consulta.addCommand(backCommand);
		Consulta.append(new StringItem("Registrando: ",Servi));
		Consulta.append(new ImageItem("",FotoServ[0],ImageItem.LAYOUT_CENTER,"no exite la Imagen"));
		TextNomb=new TextField("Nombre del Lugar:", "", 50, TextField.ANY);
		Consulta.append(TextNomb);
		Consulta.setCommandListener(this);
		
		lineaTrufis=new Form("");
		lineaTrufis.append(new StringItem("Taxi: ",Servi));
		lineaTrufis.append(new StringItem("Telf: ",Servi));
		lineaTrufis.addCommand(backCommand);
		lineaTrufis.setCommandListener(this);
		
		Fin= new Form("Cerrara el Sistema");
		Fin.addCommand(okCommand);
		Fin.addCommand(No);
		//Fin.append(new StringItem("Realizado por: ","Richard E. Mareño Salvatierra"));		
		Fin.append(new StringItem("Ojo: ","Desea salis Realmente"));
		Fin.append(new ImageItem("",FotoSubMenu[5],ImageItem.LAYOUT_CENTER,"no exite la Imagen"));
		Fin.append(new StringItem(":>","precione si para salir"));				
		Fin.setCommandListener(this);
		
		
		
		AcD= new Form("Acerca De..");
		AcD.addCommand(okCommand);
		AcD.append(new StringItem("Realizado por: ","Richard E. Mareño Salvatierra"));		
		AcD.append(new StringItem("Año: ","2006"));
		AcD.append(new ImageItem("",FotoMenu[7],ImageItem.LAYOUT_CENTER,"no exite la Imagen"));
		AcD.append(new StringItem("Acradece a: ","Lic. Henrry Villarroel"));
		AcD.append(new StringItem("         .","Ing. Richard Ayoroa"));
		AcD.append(new StringItem("         .","Ing. Diego Rivera"));
		AcD.setCommandListener(this);
				
		MenU=new MenuSyst(display);
			
		display=Display.getDisplay(this);		
			
	}       
    public void startApp() throws MIDletStateChangeException {
    	index = 0; UbiSer=false;
        (new Thread(this)).start();    	
    }
    public void pauseApp() {
    }
    public synchronized void destroyApp(boolean unconditional) {     
	GardaLug=null;
	List_camino=null;
	MirarServ=null;
	plano=null;
	okCommand = null;
	backCommand = null;
	display=null;	
    }

    int a,num;      
    public void run() {  
    	if(index==0){
        	MenU = new MenuSyst(display);
        	if (MenU!= null) {       		
        	display.setCurrent(MenU);     
        	}
        }    
        if(index==1){
        	mirarfoto = new MirarFoto(display);
        	if (mirarfoto!= null) {       		
        	display.setCurrent(mirarfoto);     
        	}
        }
        if(index==2){
        	plano = new Plano(display);
			 plano.EPuntoX(_x);
			 plano.EPuntoY(_y);
        	if (plano!= null) {       		
        	display.setCurrent(plano);     
        	}
        }             
        System.out.println("Mirando: ");
    }      
    void Distribuidor(){}
    int C1,C2, ar=0,index1=0,Ini=0;
    int _x=0,_y=0;
    boolean Listado=true;    
    
    public void commandAction(Command c, Displayable d) {
    if(d==GardaLug && c==List.SELECT_COMMAND) {
		a=GardaLug.getSelectedIndex();
		 System.out.println("Selecciona: "+a);
		Consulta.set(0,new ImageItem("Servicio: "+options[a],null,ImageItem.LAYOUT_CENTER,"no exite la Imagen"));
		Consulta.set(1,new ImageItem("",FotoServ[a],ImageItem.LAYOUT_CENTER,"no exite la Imagen"));		
		display.setCurrent(Consulta);		
	}    
	else if(d==Consulta && c==Ubicar) {
		display.setCurrent(List_camino);
	}
	
	else if(c==OKlineMovil) {
	   num=milista.getSelectedIndex();
	   lineaTrufis.set(0,new ImageItem("Taxi: "+lineas[num],null,ImageItem.LAYOUT_CENTER,"no exite la Imagen"));
	   lineaTrufis.set(1,new ImageItem("Telf.: "+Camp1[num],null,ImageItem.LAYOUT_CENTER,"no exite la Imagen"));		
		display.setCurrent(lineaTrufis);
	}
    
	else if(d==Fotos && c==List.SELECT_COMMAND) {
		int ind= List_camino.getSelectedIndex();
		synchronized (this) {
            if (plano!= null )
                return;    
            index = 1;
            (new Thread(this)).start();
		}			
	}    
///////////////////////////////////////////////////////////////////////////////    
	else if(d==List_camino && c==List.SELECT_COMMAND) {			
		if(Listado){
			Ini=0; 
			int index1 = List_camino.getSelectedIndex();		
			for(int o=0;o<NombCalle.length;o++)
				List_camino.delete(0);
			C1=index1;	
			if(index1>0)
				Ini=CualInt[index1-1]+1;		
			for(int o=Ini;o<=CualInt[index1];o++)
			{List_camino.insert(ar,NombCalle[INTERS[o]-1],null);			 
			ar++;
			}	
			Listado=false;
			display.setCurrent(List_camino);
		}
		else{
			Listado=true;
			int index1 = List_camino.getSelectedIndex();
			C2=INTERS[Ini+index1]-1;//		
			for(int o=0;o<ar;o++)
				List_camino.delete(0);
			for(int o=0;o<NombCalle.length;o++)  
					List_camino.insert(o,NombCalle[o],null);			    		    
		  System.out.println("C1: "+(C1+1)+" C2: "+(C2+1));
			ar=0;				
			System.out.println("TInters: "+TcualInt+" TCamino: "+TamCualCam);
			int var=0,j=0;
			
			int LineX1[],LineY1[],LineX2[],LineY2[],TL1=0,TL2=0;
			if(C1>0)
				var=CualCam[C1-1]+1;
				TL1=CualCam[C1]+1-var;				
				LineX1=new int[TL1];	LineY1=new int[TL1];
				for(int o=var;o<CualCam[C1]+1;o++)
				{ LineX1[j]=CamX[o]; LineY1[j]=CamY[o]; j++;
				System.out.println("Dato1: "+CamX[o]+" : "+CamY[o]);	
				}					
				//System.out.println("RESTA: "+(CualCam[C1]+1-var));				
				var=0;j=0;
			if(C2>0)	
				var=CualCam[C2-1]+1;
				TL2=CualCam[C2]+1-var;
				LineX2=new int[TL2];
				LineY2=new int[TL2];
				for(int o=var;o<CualCam[C2]+1;o++) 
				{LineX2[j]=CamX[o]; LineY2[j]=CamY[o]; j++;
				System.out.println("Dato2: "+CamX[o]+" : "+CamY[o]);				
				}

				 InTeR=new Inter();
				 try {    	    			
					 InTeR.Cam1Cam2(LineX1,LineY1,TL1,LineX2,LineY2,TL2);
				  } catch (Exception e) { System.err.println("error: IMAGEN: " + e);}				 
				 System.out.println("Xinter: "+InTeR.NewX() +" Yinter"+InTeR.NewY());	
			_x=InTeR.NewX();
			_y=InTeR.NewY();				 
					synchronized (this) {
			            if (plano!= null )
			                return;    
			            index = 2; UbiSer=false;
			            (new Thread(this)).start();
					}				 
		}						        		
	}
	else if(d==MirarServ && c==okCommand) { //seleccion de los servicio que deseas ver
	    selected= new boolean[options.length];
	    ((List)d).getSelectedFlags(selected);
	    synchronized (this) {
            if (plano!= null )
                return;    
            index = 2; UbiSer=true;
            (new Thread(this)).start(); 
		} 	    	    	    
	}
	else if(d==AcD && c==okCommand) {//Cuando Oprima Back repintara el menu		
		(new Thread(this)).start(); 		
	}
    
	else if(d==Consulta && c==backCommand) {//Cuando Oprima Back repintara el menu		
	    display.setCurrent(GardaLug);			
	}	
	else if(d==plano && c==backCommand) {//Cuando Oprima Back repintara el menu		
	    display.setCurrent(MirarServ);			
	}
	else if(d==lineaTrufis && c==backCommand) {//Cuando Oprima Back repintara el menu		
	    display.setCurrent(milista);			
	}
	else if(c==backCommand||c==No) {//Cuando Oprima Back repintara el menu	
    	index = 0; UbiSer=false;
        (new Thread(this)).start();  
		//display.setCurrent(menu);			
	}
	else if(d==Fin && c==okCommand) {//Cuando Oprima Back repintara el menu	
		destroyApp(true);
		notifyDestroyed();		
	}
	}
  }      
