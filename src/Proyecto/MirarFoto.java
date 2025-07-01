package Proyecto;

import javax.microedition.lcdui.*;
class MirarFoto extends Canvas implements Runnable, CommandListener {
/*--------------- Datos del Menu ---------------------*/
	public String estado[]=new String[4];
	public String EstoyMenu;
//------------	
	int CatMiemb;//cantidad de miembros en el menu
/*--------------- Datos del Scroll ---------------------*/	 
	int PosScroll=32,HArrAbaj=10;
	int HIniScroll,HFinScroll;//33,80	
/*--------------- Datos de la Altura de Panel,Botones,Iconos ----------------------*/		
	int AltoBase=8,AltoTitulo=20,AnScll=10;
	int AltoPant=getHeight()-AltoTitulo-AltoBase;
	int AltIcon,AnPanel=30,AltPanel=25;//Altboton,AnBoton,		
/*--------------- Datos de la foto ----------------------*/
	int AnchoFoto=40,AltoFoto=130;//50
	Image IMG[]=new Image[6]; 
	boolean BanFoto;
	int PtArriba=0,PtAbajo=0;
	int PtActY=0,PtSigtY=0,PtActX,PtProcY=0;
		
/*--------------- Datos de las Pociciones ----------------------*/	
	public int VarSelec;	
	public int NumSelec;	
	public String NombSelec;
	int x=0,y=0;
////////////////////////////////////////////////////	
	//DirectGraphics.etPixels
	private Command  backCommand = new Command("Back",Command.BACK, 1); 
	private Command  menu= new Command("Menu",Command.OK, 1); 
	private Display parentDisplay; 			
	  int degree = 360;
	  long startTime;
	  int seconds;	 
	  Image offscreen;
	  
	   public MirarFoto(Display parentDisplay){//Num de fotos, nombres de las fotos		
		this.parentDisplay = parentDisplay;	  
		startTime = System.currentTimeMillis ();
		CatMiemb=6;
		NumSelec=0;					

		PtArriba=AltoPant/2+AltoTitulo;
		PtAbajo=AltoPant/2+AltoTitulo-AltoPant*(CatMiemb-1);
		
		System.out.println("Pt Arriba, Abajo: "+PtArriba+" : "+PtAbajo);
		
		PtActY=AltoPant/2+AltoTitulo;
		PtProcY=AltoPant/2+AltoTitulo;
		PtSigtY=AltoPant/2+AltoTitulo;
		PtActX=getWidth()/2;
		
				
		//Altboton=AltoTitulo;//getHeight()/6;//h=h/6		
		//AltIcon=AltoPant/4;//Altboton+AltSepBoton;//j=h+l
		
		HIniScroll=AltoTitulo+HArrAbaj;
		HFinScroll=AltoTitulo+AltoPant;
		//AnBoton=getWidth()-AnPanel-AnScll;
					
		startTime = System.currentTimeMillis ();    
	    initialize();		
	}	   	   	  	  
	  private void initialize() {
	        addCommand(backCommand);
	        addCommand(menu);	        
	        setCommandListener(this);
	    } 
	 boolean BandImg=true;
	  private void DibujaFoto(Graphics g) {
		  for(int i=0;i<6;i++){	
			  PtActY=PtProcY+AltoPant*i;
			  if(PtActY>-50&&PtActY<getHeight()+50)				  
				  g.drawImage (Proyecto.getImagen(i),PtActX,PtActY, Graphics.HCENTER|Graphics.VCENTER);		     	      
			  } 
		  BandImg=false;
	    } 	  	 
	  
	  public void paint(Graphics g) {					 
			g.setGrayScale (255);				
			g.fillRect(0,0, getWidth(), getHeight());			
			DibujaFoto(g);			
			
			System.out.println("Tmamaño Foto: AltoPantalla: "+AltoPant);
			System.out.println("Tmamaño Foto: Alto: "+(getHeight()-AltoTitulo-AltoBase)+" Ancho: "+(getWidth()-AnScll-AnScll)+" AltoPant:"+AltoPant);			
			Marco(g,"Menu Principal",0,0,getWidth(),AltoTitulo);//no tiene preoblemas 				
			Marco(g,"",0,getHeight()-AltoBase,getWidth(),getHeight());//Base
			Marco(g,"",0,AltoTitulo,AnScll,getHeight()-AltoBase);//Lado Izquerdo
			Marco(g,"",getWidth()-AnScll,AltoTitulo,getWidth(),getHeight()-AltoBase);//Lado Derecho		
			Scroy(g,PosScroll);			
			parentDisplay.callSerially (this);
	}
	 
	public void Subir(){ PosScroll=PosScroll-(AltoPant-2*HArrAbaj)/CatMiemb;}
	public void Bajar(){ PosScroll=PosScroll+(AltoPant-2*HArrAbaj)/CatMiemb;}	    
	int SigRec=0;	
	 public void run() {
		if(System.currentTimeMillis()-startTime > 1) {
			startTime = System.currentTimeMillis ();
				
			if(PtProcY<PtSigtY)
					PtProcY=PtProcY+20;
				else if(PtProcY>PtSigtY)
					PtProcY=PtProcY-20;	
				/*
				PtProcY=PtProcY+PtSigtY/2;
				if(SigRec%2==0)						
					PtSigtY=PtSigtY/2;						
				else
				if(PtSigtY<0)	
					PtSigtY=PtSigtY/2-1;
				else
					PtSigtY=PtSigtY/2+1;					
				*/				
				System.out.println("PtSigtY: "+PtSigtY);				
				System.out.println("PtProcY: "+PtProcY);
		}
		this.repaint();
	}	
	 	 	
	protected void keyPressed(int keyCode){
		switch(getGameAction(keyCode)){	
		case Canvas.DOWN: { 						
			if(PtSigtY>PtAbajo)
				PtSigtY=PtSigtY-AltoPant;
			else{
				PtSigtY=PtAbajo;
			}			
		if (NumSelec<(CatMiemb-1)){			
			Bajar();
			NumSelec++;	
			}		
		break; }
		case Canvas.UP: { 					
			if(PtSigtY<PtArriba)
				PtSigtY=PtSigtY+AltoPant;
			else{
				PtSigtY=PtArriba;
			}												
			if (NumSelec>0){								
				Subir();
				NumSelec--;														
			}				
		break;}
		case Canvas.FIRE:{
			break;}	
		}			
	} 	   	  
	  public synchronized void commandAction(Command c, Displayable s) {
	        if (s == this) {
	            if (c == backCommand) {	
	            		Proyecto.getInstance().nullPlayer();
	 	               parentDisplay.setCurrent(Proyecto.Fotito());       		            		            
	            }  
	            else if (c ==  menu){
	            	Proyecto.getInstance().nullPlayer();
	 	            parentDisplay.setCurrent(Proyecto.getMenu()); 
	            }
	            
	        }
	    } 	  
		public void Marco(Graphics g,String Tex,int Pxi,int Pyi,int Pxf,int Pyf){					
			g.setColor(0,0,0);	        g.fillRect(Pxi,Pyi,Pxf-Pxi,Pyf-Pyi);
			g.setColor(255,255,255);	g.fillRect(Pxi+1,Pyi+1,Pxf-Pxi-2,Pyf-Pyi-2);
			g.setColor(150,222,173);	g.fillRect(Pxi+2,Pyi+2,Pxf-Pxi-4,Pyf-Pyi-4);		
			g.setColor(0,0,0);	
			g.drawRoundRect(Pxi+3,Pyi+3,3,3,20,20);//CLABO 1
			g.drawRoundRect(Pxi+3,Pyf-7,3,3,20,20);//CLABO 2
			g.drawRoundRect(Pxf-6,Pyi+3,3,3,20,20);//CLABO 3
			g.drawRoundRect(Pxf-6,Pyf-7,3,3,20,20);//CLABO 4		
			if(Tex!=null){	
				g.setColor(66,72,147);	
				Font fuente = Font.getFont (Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,Font.SIZE_MEDIUM);
				g.setFont(fuente);	 
				g.drawString(Tex,(Pxi+Pxf)/2,(Pyi+Pyf)/2+4,Graphics.BASELINE|Graphics.HCENTER);
			}
		}		
		public void Scroy(Graphics g,int Py){
			g.setColor(73,109,194);				
			if(CatMiemb>AltoPant/25)//definir el alto del panel y el boton
				g.fillRect (getWidth()-7,Py, 4,(AltoPant-2*HArrAbaj)/(CatMiemb));		
		}	
		public void DibFoto(Graphics g,String Foto,int PosX,int PosY){
					 		 		
		}	     	  
}