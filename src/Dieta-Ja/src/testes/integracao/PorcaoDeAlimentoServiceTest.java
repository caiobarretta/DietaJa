package testes.integracao;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Startup;
import core.entities.PorcaoDeAlimento;
import core.entities.TipoMedidaEnum;
import core.interfaces.dao.IPorcaoDeAlimentoDAO;
import core.interfaces.repository.IPorcaoDeAlimentoRepository;
import core.interfaces.service.IPorcaoDeAlimentoService;
import core.ioc.Container;
import infrastructure.dao.DAOFactory;
import infrastructure.dao.PorcaoDeAlimentoDAO;
import infrastructure.repository.PorcaoDeAlimentoRepository;
import infrastructure.repository.RepositoryFactory;
import junit.framework.TestCase;
import services.PorcaoDeAlimentoService;
import services.ServicesFactory;
import testes.DAOConnectionTest;

public class PorcaoDeAlimentoServiceTest extends TestCase {

	private UUID uuid;
	private Container container;
	private PorcaoDeAlimento porcaoDeAlimento;
	private PorcaoDeAlimentoService porcaoDeAlimentoService;
	
	@Override
	protected void setUp() throws Exception {
		uuid = UUID.randomUUID();
		porcaoDeAlimento = new PorcaoDeAlimento();
		container = new Startup(DAOConnectionTest.getConnection(), new DAOFactory(), 
				new RepositoryFactory(), new ServicesFactory()).getContainer();
		porcaoDeAlimentoService = (PorcaoDeAlimentoService)container.resolve(IPorcaoDeAlimentoService.class);
	}
	
	public void testAssociarPorcaoDeAlimentoDiasDaSemanaDietaRefeicao(){
		List<Integer> listDiaDaSemana = new ArrayList<Integer>();
		listDiaDaSemana.add(1);
		List<Integer> listIdRefeicao = new ArrayList<Integer>();
		listIdRefeicao.add(1);
		
		int rows = porcaoDeAlimentoService.associarPorcaoDeAlimentoDiasDaSemanaDietaRefeicao(1, listDiaDaSemana, listIdRefeicao, 1);
		if(rows < listDiaDaSemana.size())
			fail("por��es de alimento n�o est� associando aos dias da semana");
	}

	public void testRetornaPorcaoDeAlimentoPeloIdDaDieta() {
		List<Integer> porcao = porcaoDeAlimentoService.retornaIdDaDietaPeloIdPorcaoDeAlimento(1);
		if(porcao == null || porcao.isEmpty())
			fail("Erro ao pesquisa uma Por��es de alimento v�lida"); // TODO
	}

	public void testAdd() {
		porcaoDeAlimento.setNome(uuid.toString());
		porcaoDeAlimento.setDescricao(uuid.toString());
		porcaoDeAlimento.setQuantidade(1);
		porcaoDeAlimento.setTipoMedida(TipoMedidaEnum.g);
		Integer rows = porcaoDeAlimentoService.add(porcaoDeAlimento);
		if(rows != 1)
			fail("Por��es de alimentos n�o est� sendo inserida no banco");
		
		Integer idInserted = porcaoDeAlimentoService.getLastIdInserted();
		if(idInserted < 0)
			fail("Por��es de alimentos n�o retornando o �ltimo id inserido no banco");
	}

	public void testDelete() {
		Integer rows = porcaoDeAlimentoService.delete(1);
		if(rows != 1)
			fail(String.format("Por��es de alimentos n�o est� sendo deletada corretamente, rows: %d", rows));
	}

	public void testGetTakeSkip() {
		List<PorcaoDeAlimento> lst = porcaoDeAlimentoService.get(0, 10);
		if(lst.isEmpty())
			fail("Listagem Por��es de Alimento n�o est� retornando valores");
	}

	public void testGetInteger() {
		PorcaoDeAlimento porcao = porcaoDeAlimentoService.get(0, 10).get(0);
		if(porcao == null)
			fail("por��o de alimento n�o est� carregando por ID");
	}

	public void testSearch() {
		StringBuilder sb = new StringBuilder();
		sb.append(UUID.randomUUID().toString());
		sb.append(UUID.randomUUID().toString());
		List<PorcaoDeAlimento> porcao = porcaoDeAlimentoService.search(sb.toString());
		if(!porcao.isEmpty())
			fail("A pesquisa da por��o de alimento n�o est� funcionando corretamente");
	}

	public void testUpdate() {
		porcaoDeAlimento.setID(1);
		porcaoDeAlimento.setNome("Update teste");
		porcaoDeAlimento.setDescricao("Update teste");
		porcaoDeAlimento.setQuantidade(1);
		porcaoDeAlimento.setTipoMedida(TipoMedidaEnum.g);
		Integer rows = porcaoDeAlimentoService.update(porcaoDeAlimento);
		if(rows != 1)
			fail(String.format("Por��es de alimentos n�o est� sendo atualizada corretamente, rows: %d", rows));
	}
	
	public void testRetornaDiasDaSemanaPeloIdPorcaoDeAlimento(){
		List<PorcaoDeAlimento> lst = porcaoDeAlimentoService.get(0, 10);
		PorcaoDeAlimento porcao = lst.get(0);
		if(porcao == null)
			fail("A pesquisa da por��o de alimento n�o est� carregando corretamente");
		
		List<Integer> listDiaDaSemana = new ArrayList<Integer>();
		listDiaDaSemana.add(1);
		List<Integer> listIdRefeicao = new ArrayList<Integer>();
		listIdRefeicao.add(1);
		
		porcaoDeAlimentoService.associarPorcaoDeAlimentoDiasDaSemanaDietaRefeicao(porcao.getID(), listDiaDaSemana, listIdRefeicao, 1);
		List<String> diasDaSemana = porcaoDeAlimentoService.retornaDiasDaSemanaPeloIdPorcaoDeAlimento(porcao.getID());
		if(diasDaSemana.isEmpty())
			fail("A pesquisa da por��o de alimento n�o est� carregando os dias da semana corretamente");	
	}
	
	public void testRetornaRefeicaoPeloIdPorcaoDeAlimento(){
		PorcaoDeAlimento porcao = porcaoDeAlimentoService.get(1);
		List<String> refeicao = porcaoDeAlimentoService.retornaRefeicaoPeloIdPorcaoDeAlimento(porcao.getID());
		if(refeicao.isEmpty())
			fail("A pesquisa da por��o de alimento n�o est� carregando as refei��es corretamente");
	}
	
	

}
