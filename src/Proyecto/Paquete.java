package Proyecto;

//orde de entrada de datos  Tipo de servicio,el nombre del servicio,Pocicion en x,Pocicion en y
public class Paquete{
	private String NombLug;
	private int TipServ,P_X,P_Y;
	public Paquete( int TServ, String nom, int Px,  int Py) {
		TipServ = TServ;
		NombLug = nom;
		P_X = Px;
		P_Y = Py;
	}
	public Paquete(byte[] data) {
		unpack(new String(data));
		
	}	
	public void unpack(String data) {
		int start = 0, end = data.indexOf(';');
		TipServ = Integer.parseInt(data.substring(start, end));
		start = end + 1;
		
		end = data.indexOf(';', start);
		NombLug = data.substring(start, end);
		start = end + 1;
		
		end = data.indexOf(';', start);
		P_X = Integer.parseInt(data.substring(start, end));
		start = end + 1;
				
		P_Y= Integer.parseInt(data.substring(start, data.length()));
	}	
	public String pack() {
		return (((String)Integer.toString(TipServ)) + ';' + NombLug + ';' +((String)Integer.toString(P_X)) + ';' +
				((String)Integer.toString(P_Y)));
	}
	public String getNombL() {
		return NombLug;
	}
	public int getTipServ() {
		return TipServ;
	}
	public int getPtX() {
		return P_X;
	}
	public int getPtY() {
		return P_Y;
	}
}
