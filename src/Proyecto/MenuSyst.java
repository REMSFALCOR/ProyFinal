package Proyecto;

import javax.microedition.lcdui.*;

class MenuSyst extends Canvas implements Runnable, CommandListener {
//	------------	
		int CatMiemb;//cantidad de miembros en el menu	
	/*--------------- Datos de la Altura de Panel,Botones,Iconos ----------------------*/		
		int AltoBase=8,AltoTitulo=20,AnScll=10;//15
		int AltoPant=getHeight()-AltoTitulo-AltoBase;
		int AltIcon,AnPanel=30,AltPanel=25;//Altboton,AnBoton,		
	/*--------------- Datos del Menu ----------------------*/	
		int MenuSel[]={0,1,2};
		int Menu[]={0,1,2,3,4,5,6,7};		
////////////////////////////////////////////////////				
		private Command  Elige = new Command("Elegir",Command.OK, 1); 
		private Display parentDisplay; 			
		  int degree = 360;
		  long startTime;
		  int seconds;	 		  
		  Image offscreen;
		  
		   public MenuSyst(Display parentDisplay){//Num de fotos, nombres de las fotos		
			this.parentDisplay = parentDisplay;	  																			
			startTime = System.currentTimeMillis ();    
		    initialize();		
		}	   	   	  	  
		  private void initialize() {
		        addCommand(Elige);
		        setCommandListener(this);
		    } 
		 boolean BandImg=true;		   			  
		  public void paint(Graphics g) {					 
				//g.setGrayScale (255);
				g.setColor(173,206,209);
				g.fillRect(0,0, getWidth(), getHeight());						
				Marco(g,Proyecto.SacarMenu(MenuSel[1]-1),0,0,getWidth(),AltoTitulo,2);
				Marco(g,"",0,AltoTitulo,getWidth(),3*AltoTitulo,2);
				//System.out.println("si esta el run: "+getWidth());
				Marco(g,"",getWidth()/2-AltoTitulo,AltoTitulo,getWidth()/2+AltoTitulo,3*AltoTitulo,1);
				DibujarEspl(g);
				DibujarMenu(g);													
				parentDisplay.callSerially (this);
		}		  
		  int Recorre=0;		 
		  int AlejIcon=180;
		  void DibujarEspl(Graphics g){//Iconos con animacion
			  for(int i=0;i<Menu.length-2;i++)	//Aleja un icono del otro 128
			  g.drawImage(Proyecto.SetSMenu(i),Recorre+getWidth()/2+AlejIcon*i,getHeight()-getHeight()*1/4,Graphics.HCENTER|Graphics.VCENTER);			
		  }		  
		 void DibujarMenu(Graphics g){
			 if(MenuSel[0]!=0)
			 g.drawImage(Proyecto.SetMenu(MenuSel[0]-1),getWidth()*2/10,40,Graphics.HCENTER|Graphics.VCENTER);
			 else{
					g.setColor(255,0,0);	
					g.drawRoundRect(getWidth()*2/10-5,32,15,15,50,50);
					g.drawLine(getWidth()*2/10-5,32,getWidth()*2/10-5+15,32+15);
			 }
			 g.drawImage(Proyecto.SetMenu(MenuSel[1]-1),getWidth()*5/10,40,Graphics.HCENTER|Graphics.VCENTER);//30
			 if(MenuSel[MenuSel.length-1]!=7)//9
			 g.drawImage(Proyecto.SetMenu(MenuSel[2]-1),getWidth()*8/10,40,Graphics.HCENTER|Graphics.VCENTER);
			 else{
					g.setColor(255,0,0);	
					g.drawRoundRect(getWidth()*4/5-5,32,15,15,50,50);
					g.drawLine(getWidth()*4/5-5,32,getWidth()*4/5-5+15,32+15);
			 }
		 }
		 int SigRec=0;
		 public void run() {//Animacion del los iconos
			if(System.currentTimeMillis()-startTime > 1) {
				startTime = System.currentTimeMillis ();
				    Recorre=Recorre+SigRec/2;
					if(SigRec%2==0)						
						SigRec=SigRec/2;						
					else
					if(SigRec<0)	
						SigRec=SigRec/2-1;
					else
						SigRec=SigRec/2+1;						
			}
			this.repaint();
		}	
		//Modo de eleccion del menú 	 	
		protected void keyPressed(int keyCode){
			switch(getGameAction(keyCode)){	
			case Canvas.LEFT: { 						
				if(MenuSel[0]!=0)
				{	SigRec=SigRec+AlejIcon;
					for(int i=0;i<MenuSel.length;i++)
					MenuSel[i]=Menu[MenuSel[i]-1];	
				}
				System.out.println("Medio"+MenuSel[1]);
				break; }
			case Canvas.RIGHT: { 						
				if(MenuSel[MenuSel.length-1]!=7){//7
					SigRec=SigRec-AlejIcon;
					for(int i=0;i<MenuSel.length;i++)
					MenuSel[i]=Menu[MenuSel[i]+1];
				}
				System.out.println("Medio"+MenuSel[1]);
				break; }
			}			
		} 	   	  
		//Los eventos del systema
		  public synchronized void commandAction(Command c, Displayable s) {
		        if (s == this) {
		            if (c == Elige) {
		            	   Proyecto.getInstance().nullPlayer();
		            	if(MenuSel[0]==0)
		            		parentDisplay.setCurrent(Proyecto.getGuard()); //Guardar Lugar
		            	if(MenuSel[0]==1)
		            		parentDisplay.setCurrent(Proyecto.getList1()); //Mirar Servicio
		            	if(MenuSel[0]==2)
		            		parentDisplay.setCurrent(Proyecto.Fotito()); //Mirar Foto
		            	
		            	if(MenuSel[0]==3)
		            		parentDisplay.setCurrent(Proyecto.lineas()); //
		            	
		            	if(MenuSel[0]==4) {  		 
		            		Proyecto.Salir();
		            		parentDisplay.setCurrent(Proyecto.Salir());
		            	}		    
		            	if(MenuSel[0]==5) {  		 		            		
		            		parentDisplay.setCurrent(Proyecto.Acerca());
		            	}		    		            	
		            }  
		        }
		    } 	  
			public void Marco(Graphics g,String Tex,int Pxi,int Pyi,int Pxf,int Pyf,int color){					
				g.setColor(0,0,0);	        g.fillRect(Pxi,Pyi,Pxf-Pxi,Pyf-Pyi);
				g.setColor(255,255,255);	g.fillRect(Pxi+1,Pyi+1,Pxf-Pxi-2,Pyf-Pyi-2);
				if(color==1) g.setColor(150,222,173); else	g.setColor(122,170,199);	g.fillRect(Pxi+2,Pyi+2,Pxf-Pxi-4,Pyf-Pyi-4);		
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
	}