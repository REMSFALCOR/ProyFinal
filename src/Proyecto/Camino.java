package Proyecto;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;


public class Camino extends MIDlet implements CommandListener{
	private List menu;	
	private Command okCommand = new Command("OK", Command.OK, 1);
	private Command backCommand = new Command("Back", Command.BACK, 1);
	private String[] options={"Option A","Option B","Option C","Option D","Option E"};	
	private Display display;
// entrada de los caminos	
	void EPuntoY(String _Opt[]){options=_Opt;}//direccion Y	
	
    public Camino() {    	
		menu= new List("Choose demo", List.IMPLICIT,options, null);		
		menu.setCommandListener(this);	
		display=Display.getDisplay(this);
	}
    public void startApp() throws MIDletStateChangeException {
        display.setCurrent(menu);
    }

    public void pauseApp() {
    }
    public void destroyApp(boolean unconditional) {
        menu=null;
	okCommand = null;
	backCommand = null;
	display=null;
    }

    public void commandAction(Command c, Displayable d) {
	if(d==menu && c==List.SELECT_COMMAND) {
	    switch(menu.getSelectedIndex()) {
	    case 0: //implicit choice list
	        break;
	    case 1: //exclusive choice list
		break;
	    case 2: //multiple choice list
		break;
	    case 3: //cierra el sistema
		//destroyApp(true);
		//notifyDestroyed();
		break;
	    default:
	    }
	}	
	else if(c==backCommand) {//Cuando Oprima Back repintara el menu
	    display.setCurrent(menu);
	}
    }
}