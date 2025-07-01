package Proyecto;

import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordEnumeration;

class Plano extends Canvas implements Runnable, CommandListener {
/*##################  Nuevos Datos ##################*/		
	private Display parentDisplay;	
	private Command  backCommand = new Command("Atras",Command.BACK, 1);
	private Command  SalirPl = new Command("Menu",Command.CANCEL, 2);
	private Command  okey = new Command("Aqui",Command.OK, 1); 
	private Command  Cancelar = new Command("Cancelar",Command.CANCEL, 2); 
	 long startTime;	 
/*///////////////////////// Systema Reciente //////////////////////////////////*/
/*-----------------o Variables de Conexion o------------------*/
	 LeerDatos LeeD;	 
/*-----------------o Variables del Plano o------------------*/	
//		int VectX[],VectY[],Cual[],TamVect=0,TamCual=0;
/*-----------------o Variables de los Caminos o------------------*/
		int CamX[],CamY[],CmCual[],TamCam=0,TamCmCual=0; //Falta el color
/*----------------o Variables de los Servicios o-----------------*/
		//int ServX[],ServY[],TamServ=0;
		int Servicio[]={0,0,1,1,2,3,4,4};	 
	 
/*////////////////////////////////////////////////////////////////////////////*/	 
/*----------------o Variables de los Servicios o-----------------*/
	int ServX[],ServY[],TamServ,TipoServ[];
	String NombServ[];//={"farmacia.png","hospital.png","iglecia.png","mercado.png","teatro.png"};
/*-----------------o Variables Otros o------------------*/
	boolean ServOK[];//={true,true,true,true,true,true};//Farmacia,Hostptal,Local,Cafe Internet,etc
	boolean yes=true;
	boolean Dib=true;int Pint;
	int x=0,y=0;
	Image IMG=null;
	
	Image offscreen;
	
	int PosPX=0,PosPY=0;
	
	int Saltar[]=new int[50],TamSalt=0,xSalt=0;;
	boolean BanSalt=true,Puerta=true;
/*-----------------o Variables de la base de datos o------------------*/	
	private static DataBase db;	
	private Vector contactIDs = new Vector();		
/*-----------------o Datos del constructor o------------------*/	
	Plano(Display parentDisplay){
	this.parentDisplay = parentDisplay;	
	Dib=Proyecto.Ubicar();
	CargarServ();
	System.out.println("Carge Servicios");
	initialize();
	System.out.println("inicialize");
	if(Dib)
	SaltarPuntos();
	System.out.println("Saltar Puntos");
	}	
/*-----------------o Datos de Entrada o------------------*/
	void CargarServ(){
		try {
			db = new DataBase("Serv"); 
		}
		catch(Exception e) {
			System.err.println("EXCEPTION: Problem opening the database.");
		}
		RecordEnumeration records = null;		
		try {     
			records = db.enumerateContactRecords();
			while(records.hasNextElement())
				contactIDs.addElement(new Integer(records.nextRecordId()));
		}
		catch(Exception e) {
			System.err.println("Error: problemas al recobrar los datos.");
		}	
		records.reset();
		try {
			
			TamServ=contactIDs.size();
			int rec=TamServ-1;
			ServX=new int[TamServ];									
			ServY=new int[TamServ];											
			TipoServ=new int[TamServ];
			NombServ=new String[TamServ];	
			System.out.println("TamServ: "+TamServ);
			while(records.hasNextElement()) {
				Paquete paquete = new Paquete(records.nextRecord());				
				System.out.println("P_X: "+paquete.getPtX()+" P_Y: "+paquete.getPtY()+" ServN: "+(paquete.getTipServ()+1)+" El Texto: "+paquete.getNombL());
				TipoServ[rec]=paquete.getTipServ();
				ServX[rec]=paquete.getPtX();									
				ServY[rec]=paquete.getPtY();
				NombServ[rec]=paquete.getNombL();	
				rec--;
			}
		}
		catch(Exception e) {
			System.err.println("Error: Problemas al leer los datos");
		}			
	}			
/*-----------------o Datos de Salida o------------------*/
	private void initialize() {
			if(Dib){
				addCommand(backCommand);
				addCommand(SalirPl);}
			else{
				addCommand(okey);
				addCommand(Cancelar);
			}
	        setCommandListener(this);
	}			
/*-----------------o Datos de Proceso o------------------*/	
	public void paint(Graphics g) {			
		g.setColor(190,205,198);
	  	g.fillRect(0,0, getWidth(), getHeight());
	  	
	  //	if(yes){SelecTexto();	yes=false; 	}		  	
	  	if(Puerta){
	  	PosPX=g.getTranslateX()-x;
	  	PosPY=g.getTranslateY()-y;	  	
	  	}	  		  	
	  	g.translate(PosPX,PosPY);
	  	GrafPlano(g,0);		
	  	GrafCamino(g,0);
	  	if(!Puerta){
	  	 Marco(g,ServX[Saltar[xSalt]]-8,ServY[Saltar[xSalt]]-8,ServX[Saltar[xSalt]]+8,ServY[Saltar[xSalt]]+8);
	  	 DibText(g,Proyecto.NombreServ(TipoServ[xSalt]),ServX[Saltar[xSalt]],ServY[Saltar[xSalt]]-10);
	  	 DibText(g,NombServ[Saltar[xSalt]],ServX[Saltar[xSalt]],ServY[Saltar[xSalt]]+14);
	  	}
        if(Dib){
        	DibServicio(g);
         }
         else
        	g.drawImage (Proyecto.SetServ(Proyecto.SetUbicar()),getWidth()/2-PosPX,getHeight()/2-PosPY, Graphics.HCENTER|Graphics.VCENTER);          	
         	parentDisplay.callSerially (this);	         	
 }			
public void run() {
	if(System.currentTimeMillis()-startTime > 1) {
			startTime = System.currentTimeMillis ();
		}
	this.repaint();
}	 				
////////////////////////////_Teclado_/////////////////////////////	
	protected void keyPressed(int keyCode) {				
		switch(getGameAction(keyCode)){	
			case Canvas.DOWN: { if(!Puerta)	y=PosPY; y = y+4; Puerta=true;
			System.out.println("_PosPX: "+PosPX+" _PosPY: "+PosPY);	
			break; }
			case Canvas.LEFT: { if(!Puerta) x=PosPX; x = x-4; Puerta=true;
			System.out.println("_PosPX: "+PosPX+" _PosPY: "+PosPY);	
			break;}
			case Canvas.UP: { if(!Puerta) y=PosPY; y = y-4; Puerta=true;
			System.out.println("_PosPX: "+PosPX+" _PosPY: "+PosPY);	
			break;}
			case Canvas.RIGHT:{ if(!Puerta) x=PosPX; x = x+4; Puerta=true;
			System.out.println("_PosPX: "+PosPX+" _PosPY: "+PosPY);	
			break;}
			case Canvas.FIRE:{
				if(TamSalt>0)
				if(Dib){ //enserrar en cuadrado				
					xSalt++;
					if(xSalt>=TamSalt)
						xSalt=0;	
						System.out.println("_PosPX: "+PosPX+" _PosPY: "+PosPY);
						//x=getWidth()/2-ServX[Saltar[xSalt]];
						PosPX=getWidth()/2-ServX[Saltar[xSalt]];
						x=0;
						//y=getHeight()/2-ServY[Saltar[xSalt]];
						PosPY=getHeight()/2-ServY[Saltar[xSalt]];
						y=0;
						Puerta=false;
				}
			break;}
		}		
	}		
	protected void keyRepeated(int keyCode) {
		this.keyPressed(keyCode);
	}		
	public synchronized void commandAction(Command c, Displayable s) {
        if (s == this) {
            if (c == backCommand&&Dib) {	
            		Proyecto.getInstance().nullPlayer();
            		try {
            			db.close();
            		}	
            		catch(Exception e) {
            			System.err.println("Error: Problemas al cerrar la conexion.");
            		}
 	           parentDisplay.setCurrent(Proyecto.getList1());       		            		            
            }  
            if (c == okey) {	            	
            	Paquete paquete = new Paquete(Proyecto.SetUbicar(),Proyecto.SacarNomb(),getWidth()/2-PosPX,getHeight()/2-PosPY);
        		Proyecto.getInstance().nullPlayer();        		
        		db.UnServMas(paquete.pack());        		        		
        		try {
        			db.close();
        		}	
        		catch(Exception e) {
        			System.err.println("Error: Problemas al cerrar la conexion.");
        		}
	           parentDisplay.setCurrent(Proyecto.getMenu());       		            		            
            }
            if (c == SalirPl) {	
        		Proyecto.getInstance().nullPlayer();
	                parentDisplay.setCurrent(Proyecto.getMenu());       		            		            
            }                   
            if (c == Cancelar) {	
        		Proyecto.getInstance().nullPlayer();
	                parentDisplay.setCurrent(Proyecto.getConsulta());       		            		            
            }                        
        }
    } 							

	public int POS_X(Graphics g){return -g.getTranslateX();}
	public int POS_Y(Graphics g){return -g.getTranslateY();}		
	
	public void Saltar(int Val){
		Saltar[TamSalt]=Val;
		TamSalt++;
	}	
	void SaltarPuntos(){
		for(int a=0;a<TamServ;a++)
			for(int b=0;b<5;b++)
			if(Proyecto.SetSelec(b))
				if(TipoServ[a]==b)
					Saltar(a);						
	}
	public void DibServicio(Graphics g){
		for(int t=0;t<TamSalt;t++)
		if(SiNoDibuja(g,ServX[Saltar[t]],ServY[Saltar[t]],ServX[Saltar[t]],ServY[Saltar[t]] ))						
			g.drawImage (Proyecto.SetServ(TipoServ[Saltar[t]]),ServX[Saltar[t]],ServY[Saltar[t]], Graphics.HCENTER|Graphics.VCENTER);
	}		
	public void DibText(Graphics g,String Text,int PosX,int PosY){
		g.setColor(0,0,0);
	  	Font fuente = Font.getFont (Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_MEDIUM);
		g.setFont(fuente);	 
		g.drawString(Text,PosX,PosY,Graphics.BASELINE|Graphics.HCENTER); 
	}	
	public void Marco(Graphics g,int Pxi,int Pyi,int Pxf,int Pyf){					
		g.setColor(0,0,0);	        g.fillRect(Pxi,Pyi,Pxf-Pxi,Pyf-Pyi);
		g.setColor(255,255,255);	g.fillRect(Pxi+1,Pyi+1,Pxf-Pxi-2,Pyf-Pyi-2);
		g.setColor(150,222,173);	g.fillRect(Pxi+2,Pyi+2,Pxf-Pxi-4,Pyf-Pyi-4);							
	}
	
//////////////////////// Funciones encargadas de dibujar el plano //////////////////
	public boolean SiNoDibuja(Graphics g,int Xa1,int Ya1,int Xa2,int Ya2 ){					
		if((Xa1>POS_X(g)&&Ya1>POS_Y(g))&&(Xa1<(POS_X(g)+getWidth())&&Ya1<(POS_Y(g)+getHeight())))
				return true;
		if((Xa2>POS_X(g)&&Ya2>POS_Y(g))&&(Xa2<(POS_X(g)+getWidth())&&Ya2<(POS_Y(g)+getHeight())))
				return true;				
		return false;
	}
	
	public void dibujar(Graphics g,int x1,int x2, int y1,int y2){
		g.drawLine(x1,y1,x2,y2);
	}	
	public void GrafPlano(Graphics g,int a){
		int ver=0;
		int May=0;
			g.setColor(18,124,121);
			for(int s=0;s<(Proyecto.TamM()-1);s++){
				if(Proyecto.CUAL(ver)==s)
				 {	s++; ver++; }	
			if(Proyecto.DISTANX(ver)>Proyecto.DISTANY(ver))
				May=Proyecto.DISTANX(ver);
			else May=Proyecto.DISTANY(ver);
			if((Proyecto.MEDX(ver)>(POS_X(g)-May))&&(Proyecto.MEDY(ver)>(POS_Y(g)-May))&&(Proyecto.MEDX(ver)<(POS_X(g)+getWidth()+May))&&(Proyecto.MEDY(ver)<(POS_Y(g)+getHeight()+May)))
			{						
				if(SiNoDibuja(g,Proyecto.PtX(s),Proyecto.PtY(s),Proyecto.PtX(s+1),Proyecto.PtY(s+1)))	{
					g.drawLine(Proyecto.PtX(s),Proyecto.PtY(s),Proyecto.PtX(s+1),Proyecto.PtY(s+1));
					}
			}
			}					
	}
	public void GrafCamino(Graphics g,int a){
		int ver=0;
		int May=0;
		int col=0;
			for(int s=0;s<(Proyecto.TCam()-1);s++){
				if(Proyecto.CamCual(ver)==s)
				 {	s++; ver++;
				 col=255*ver/(10+ver);
				 }	
					g.setColor(col+30,col,col+90);
					g.drawLine(Proyecto.CAM_X(s)-1,Proyecto.CAM_Y(s)-1,Proyecto.CAM_X(s+1)-1,Proyecto.CAM_Y(s+1)-1);
					g.drawLine(Proyecto.CAM_X(s),Proyecto.CAM_Y(s),Proyecto.CAM_X(s+1),Proyecto.CAM_Y(s+1));
					g.drawLine(Proyecto.CAM_X(s)+1,Proyecto.CAM_Y(s)+1,Proyecto.CAM_X(s+1)+1,Proyecto.CAM_Y(s+1)+1);
					//}
			//}
			}		
						 
	}		
//////////////////////////// Entrar a un Punto/////////////////////////////////	
	void EPuntoX(int _x){x=_x;}// direccion X
	void EPuntoY(int _y){y=_y;}//direccion Y			
}