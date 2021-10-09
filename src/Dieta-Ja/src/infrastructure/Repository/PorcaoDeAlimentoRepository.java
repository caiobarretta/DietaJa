package infrastructure.repository;

import java.util.List;
import core.entities.PorcaoDeAlimento;
import core.interfaces.dao.IPorcaoDeAlimentoDAO;
import core.interfaces.dao.base.IDAO;
import core.interfaces.repository.IPorcaoDeAlimentoRepository;
import infrastructure.repository.base.DefaultRepositoryTemplateMethod;

public class PorcaoDeAlimentoRepository extends DefaultRepositoryTemplateMethod<PorcaoDeAlimento> implements IPorcaoDeAlimentoRepository{
	
	protected final IPorcaoDeAlimentoDAO _idao;
	public PorcaoDeAlimentoRepository(IPorcaoDeAlimentoDAO dao) {
		this._idao = dao;
	}

    public List<Integer> retornaIdDaDietaPeloIdPorcaoDeAlimento(Integer id){
    	return this._idao.retornaIdDaDietaPeloIdPorcaoDeAlimento(id);
    }

	@Override
	public IDAO<PorcaoDeAlimento> getDAO() {
		return this._idao;
	}

	@Override
	public List<String> retornaDiasDaSemanaPeloIdPorcaoDeAlimento(Integer id) {
		return this._idao.retornaDiasDaSemanaPeloIdPorcaoDeAlimento(id);
	}

	@Override
	public List<String> retornaRefeicaoPeloIdPorcaoDeAlimento(Integer id) {
		return this._idao.retornaRefeicaoPeloIdPorcaoDeAlimento(id);
	}

	@Override
	public Integer getLastIdInserted() {
		return this._idao.getLastIdInserted();
	}

	@Override
	public List<String> retornaDiaDaSemanaPeloIDPorcaoDeAlimento(Integer id) {
		return this._idao.retornaDiaDaSemanaPeloIDPorcaoDeAlimento(id);
	}

	@Override
	public Integer associarPorcaoDeAlimentoDiasDaSemanaDietaRefeicao(Integer IdPorcaoDeAlimento,
			List<Integer> listDiaDaSemana, List<Integer> listIdRefeicao, Integer dietaID) {
		return this._idao.associarPorcaoDeAlimentoDiasDaSemanaDietaRefeicao(IdPorcaoDeAlimento, listDiaDaSemana, listIdRefeicao, dietaID);
	}
}