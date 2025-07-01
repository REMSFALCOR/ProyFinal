package Proyecto;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

//import javax.microedition.rms.*;

public class DataBase {
	RecordStore recordStore = null;
	public DataBase(String name) {
		try {
			recordStore = open(name);
		}
		catch(RecordStoreException e) {
			e.printStackTrace();
		}
	}
	public RecordStore open(String fileName) throws RecordStoreException {
		return RecordStore.openRecordStore(fileName, true);
	}
	public void close() throws RecordStoreNotOpenException,
	RecordStoreException {
		if (recordStore.getNumRecords() == 0) {
			//String fileName = recordStore.getName();
			recordStore.closeRecordStore();
			//recordStore.deleteRecordStore(fileName);
		}
		else {
			recordStore.closeRecordStore();//cerrara la DB al terminar
		}
	}	
	//saca el contacto de la base de datos
	public Paquete SecarServicio(int id) {
		try {
			return (new Paquete(recordStore.getRecord(id)));
		}
		catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return null;
	}	
	public synchronized void ModifServ(int id, String record) {
		byte[] bytes = record.getBytes();
		try {
			recordStore.setRecord(id, bytes, 0, bytes.length);
		}
		catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}
	public synchronized int UnServMas(String record) {
		byte[] bytes = record.getBytes();
		try {
			return recordStore.addRecord(bytes, 0, bytes.length);
		}
		catch (RecordStoreException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public synchronized void deleteContactRecord(int id) {
		try {
			recordStore.deleteRecord(id);
		}
		catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}
	public synchronized RecordEnumeration enumerateContactRecords()
		throws RecordStoreNotOpenException {
		return recordStore.enumerateRecords(null, null, false);	
	}
}
