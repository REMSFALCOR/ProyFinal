package Proyecto;

import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordEnumeration;

class Presentacion extends Canvas implements Runnable, CommandListener {
/*##################  Nuevos Datos ##################*/		
	private Display parentDisplay;	
	private Command  backCommand = new Command("Atraz",Command.BACK, 1);
	private Command  SalirPl = new Command("Menu",Command.CANCEL, 2);
	private Command  okey = new Command("Aqui",Command.OK, 1); 
	private Command  Cancelar = new Command("Cancelar",Command.CANCEL, 2); 
	 long startTime;	 
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
	Presentacion(Display parentDisplay){
	this.parentDisplay = parentDisplay;	
	Dib=Proyecto.Ubicar();
	CargarServ();
	initialize();
	SaltarPuntos();
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
	  	if(Puerta){
	  	PosPX=g.getTranslateX()-x;
	  	PosPY=g.getTranslateY()-y;	  	
	  	}	  		  	
	  	g.translate(PosPX,PosPY);
		GrafMapa(g);	
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
	public void GrafMapa(Graphics g){
		/*
		for(int j=0;j<4;j++)
			for(int i=0;i<5;i++)		
				g.drawImage (Proyecto.ImgMap(1),100+i*200,100+200*j, Graphics.HCENTER|Graphics.VCENTER);
		*/
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
			case Canvas.DOWN: { if(!Puerta)	y=PosPY;
				y = y+4; Puerta=true;
			break; }
			case Canvas.LEFT: { if(!Puerta) x=PosPX; x = x-4; Puerta=true;
			break;}
			case Canvas.UP: { if(!Puerta) y=PosPY; y = y-4; Puerta=true;
			break;}
			case Canvas.RIGHT:{ if(!Puerta) x=PosPX; x = x+4; Puerta=true;
			break;}
			case Canvas.FIRE:{
				if(TamSalt>0)
				if(Dib){ //enserrar en cuadrado				
					xSalt++;
					if(xSalt>=TamSalt)
						xSalt=0;	
						System.out.println("_PosPX: "+PosPX+" _PosPY: "+PosPY);							
						PosPX=getWidth()/2-ServX[Saltar[xSalt]];				
						PosPY=getHeight()/2-ServY[Saltar[xSalt]];				
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
        		Proyecto.getInstance().nullPlayer();
        		Paquete paquete = new Paquete(Proyecto.SetUbicar(),Proyecto.SacarNomb(),getWidth()/2-PosPX,getHeight()/2-PosPY);
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
	public boolean SiNoDibuja(Graphics g,int Xa1,int Ya1,int Xa2,int Ya2 ){					
		if((Xa1>POS_X(g)&&Ya1>POS_Y(g))&&(Xa1<(POS_X(g)+getWidth())&&Ya1<(POS_Y(g)+getHeight())))
				return true;
		if((Xa2>POS_X(g)&&Ya2>POS_Y(g))&&(Xa2<(POS_X(g)+getWidth())&&Ya2<(POS_Y(g)+getHeight())))
				return true;				
		return false;
	}
	public int POS_X(Graphics g){return -g.getTranslateX();}
	public int POS_Y(Graphics g){return -g.getTranslateY();}		
	
	public void dibujar(Graphics g,int x1,int x2, int y1,int y2){
		g.drawLine(x1,y1,x2,y2);
	}	
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
}