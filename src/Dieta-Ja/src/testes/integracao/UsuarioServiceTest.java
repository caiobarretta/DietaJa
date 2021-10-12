package testes.integracao;

import java.util.List;
import java.util.UUID;

import core.Startup;
import core.entities.TipoUsuarioEnum;
import core.entities.Usuario;
import core.interfaces.dao.IUsuarioDAO;
import core.interfaces.repository.IUsuarioRepository;
import core.interfaces.service.IUsuarioService;
import core.ioc.Container;
import infrastructure.dao.DAOFactory;
import infrastructure.dao.UsuarioDAO;
import infrastructure.repository.RepositoryFactory;
import infrastructure.repository.UsuarioRepository;
import junit.framework.TestCase;
import services.ServicesFactory;
import services.UsuarioService;
import testes.DAOConnectionTest;

public class UsuarioServiceTest extends TestCase {

	private UUID uuid;
	private Usuario usuario;
	private Container container;
	private UsuarioService usuarioService;
	
	@Override
	protected void setUp() throws Exception {
		usuario = new Usuario();
		uuid = UUID.randomUUID();
		container = new Startup(DAOConnectionTest.getConnection(), new DAOFactory(), 
				new RepositoryFactory(), new ServicesFactory()).getContainer();
		usuarioService = (UsuarioService)container.resolve(IUsuarioService.class);
	}

	public void testIsUsuario() {
		int codigoUsuario = usuarioService.getLoginUsuario("Teste login", "1234");
		if(codigoUsuario == 0)
			fail("Servi�o usu�rio n�o est� retornando c�digo de usu�rio ou n�o est� achando login.");
	}

	public void testAdd() {
		usuario.setNome(uuid.toString());
		usuario.setDescricao("Teste Desc");
		usuario.setLogin("Teste login");
		usuario.setSenha("1234");
		usuario.setTipoUsuario(TipoUsuarioEnum.Paciente);
		usuario.setDietaID(1);
		Integer rows = usuarioService.add(usuario);
		if(rows != 1)
			fail("O servi�o de usu�rio est� inserindo eroneamento um usu�rio.");
	}

	public void testDelete() {
		Integer rows = usuarioService.delete(1);
		if(rows != 1)
			fail("O servi�o de usu�rio est� deletando eroneamento um usu�rio.");
	}

	public void testGetIntegerInteger() {
		List<Usuario> lst = usuarioService.get(0, 10);
		if(lst.isEmpty())
			fail("O servi�o de usu�rio n�o est� p�ginando eroneamento um usu�rio.");
	}

	public void testGetInteger() {
		usuario.setNome(uuid.toString());
		usuario.setDescricao("Teste Desc");
		usuario.setLogin("Teste login");
		usuario.setSenha("1234");
		usuario.setTipoUsuario(TipoUsuarioEnum.Paciente);
		usuario.setAtivo(true);
		usuario.setDietaID(1);
		Integer rows = usuarioService.add(usuario);
		
		List<Usuario> lst = usuarioService.search(uuid.toString());
		Usuario usrSearch = lst.get(0);
		
		Usuario usr = usuarioService.get(usrSearch.getID());
		if(usr == null)
			fail("O servi�o de usu�rio n�o retornando usu�rio pesquisado por id");
	}

	public void testSearch() {
		List<Usuario> lst = usuarioService.search("");
		if(lst.isEmpty())
			fail("O servi�o de usu�rio n�o retornando usu�rio pesquisado por nome");
	}

	public void testUpdate() {
		usuario.setAtivo(true);
		usuario.setDescricao("Descri��o");
		usuario.setID(1);
		usuario.setLogin("Login");
		usuario.setNome("Nome");
		usuario.setSenha("1234");
		usuario.setDietaID(1);
		usuario.setTipoUsuario(TipoUsuarioEnum.Paciente);
		Integer rows = usuarioService.update(usuario);
		if(rows != 1)
			fail("O servi�o de usu�rio n�o atualizar usu�rio pesquisado por nome");
	}

}
