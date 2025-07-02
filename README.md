<h1 align="center"> <img src="https://github.com/RicharMareno/WebAPiJavaMongoDB/blob/main/LogoSiwat.svg" width=30> ProyFinal J2ME - 2006 <img src="https://github.com/RicharMareno/WebAPiJavaMongoDB/blob/main/LogoSiwat.svg" width=30></h1>

## Prooyecto realizado en Java J2ME, Se Utilizo (microedition (Canvas), IO, Runnable, Thread,  CommandListener, synchronized) 
### [Java](http://www.eclipse.org/platform) , ya no exite la pagina.
### [J2ME Wireless Toolkit](https://jcp.org/en/jsr/summary?id=j2me)
##
## Pequña explicacion de la aplicación
### [Clase Proyecto](https://github.com/REMSFALCOR/ProyFinal/blob/main/src/Proyecto/Proyecto.java)  
``` java
@Service
@RequiredArgsConstructor  //crea un constructor para que la variable se inicialice con ese dato la proxima vez
public class UsuariosService {	
	private final UsuariosRepository usuariosRepository;	
    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }	
	public void save(Usuarios usuario) {
		usuariosRepository.save(usuario);
	}	
	public List<Usuarios> findAll(){
		return usuariosRepository.findAll();
	}
	public Optional<Usuarios> findById(String id){
		return usuariosRepository.findById(id);	
	}	
	public Usuarios getUsuarioById(String id) 
	{ return usuariosRepository.findById(id).orElse(null); }				
	// eliminar una dato por el id
	public void deleteById(String id){
		usuariosRepository.deleteById(id); 		
	}
}
```



## Ver en YouTube
 [![Alt text](https://img.youtube.com/vi/nVrzoEYSTFA/0.jpg)](https://www.youtube.com/watch?v=nVrzoEYSTFA)

## Ver desde Githup 
https://github.com/user-attachments/assets/fbcda9a6-1392-485d-b173-1eace450aff9



