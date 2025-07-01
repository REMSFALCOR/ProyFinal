package Proyecto;
public class Inter {
	int a1,b1,c1;//recta 1
	int a2,b2,c2;//recta 2
	int X,Y;
	int CamX1[],CamX2[],CamY1[],CamY2[];
	int Tam1,Tam2;
	public void Cam1Cam2(int []C1x,int []C1y,int TC1,int []C2x,int []C2y,int TC2){
		Tam1=TC1;
		CamX1=new int[Tam1];		CamX1=C1x;			
		CamY1=new int[Tam1];		CamY1=C1y;
		Tam2=TC2;
		CamX2=new int[Tam2];		CamX2=C2x;
		CamY2=new int[Tam2];		CamY2=C1x;
		try{
		for(int a=0;a<Tam1-1;a++)
			for(int b=0;b<Tam2-1;b++)
				if(R1R2(CamX1[a],CamY1[a],CamX1[a+1],CamY1[a+1],CamX2[b],CamY2[b],CamX2[b+1],CamY2[b+1]))
					{ System.out.println("--EXITO--");
					break;}
	  } catch (Exception e) { System.err.println("error: IMAGEN: " + e);}
	}		
	 public boolean R1R2(int R1x1,int R1y1,int R1x2,int R1y2,int R2x1,int R2y1,int R2x2,int R2y2){
		
		a1=R1y2-R1y1; b1=R1x1-R1x2; c1=R1x1*R1y2-R1x2*R1y1;//c1= R1x2*R1y1-R1x1*R1y2;
		System.out.println("f:"+a1+"X"+b1+"Y"+c1+"=0");
		a2=R2y2-R2y1; b2=R2x1-R2x2; c2=R2x1*R2y2-R2x2*R2y1;//c2= R2x2*R2y1-R2x1*R2y2;
		System.out.println("f:"+a2+"X"+b2+"Y"+c2+"=0");
		Y=(a1*c2-a2*c1)/(a1*b2-a2*b1);//Y=(a2*c1+a1*c2)/(a2*b1+a1*b2);
		X=(c1*b2-c2*b1)/(a1*b2-a2*b1);//X=(-b1*Y+c1)/a1;
		
		 /*
		a1=R1y1-((R1y2-R1y1)/(R1x2-R1x1))*R1x1;
		b1=(R1y2-R1y1)/(R1x2-R1x1);
		a2=R2y1-((R2y2-R2y1)/(R2x2-R2x1))*R2x1;
		b2=(R2y2-R2y1)/(R2x2-R2x1);
		
		X=(a2-a1)/(b1-b2); Y=b1*X+a1;
			*/	 
		System.out.println("X_x: "+X+" Y_y"+Y);
		if(R1y1<R1y2&&R1x1<R1x2)
			if(Y>R1y1&&Y<R1y2&&X>R1x1&&X<R1x2)
				return true;
		else if(R1y1>R1y2&&R1x1>R1x2)
			if(Y<R1y1&&Y>R1y2&&X<R1x1&&X>R1x2)
				return true; 
		else if(R1y1<R1y2&&R1x1>R1x2)
				if(Y>R1y1&&Y<R1y2&&X<R1x1&&X>R1x2)
					return true; 
		else if(R1y1>R1y2&&R1x1<R1x2)
				if(Y<R1y1&&Y>R1y2&&X>R1x1&&X<R1x2)
					return true; 
		return false;
	}
	 public int NewX(){ return X;}
	 public int NewY(){ return Y;}
}
