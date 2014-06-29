import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Validator<T> {
	void validate(T o) throws Exception;
}

class Pessoa{
	String name;
	
	public Pessoa(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}

class PessoaFisica extends Pessoa {
	public PessoaFisica(String name){
		super(name);
	}
}
class PessoaJuridica extends Pessoa { 
	public PessoaJuridica(String name){
		super(name);
	}
}


class PessoaValidator implements Validator<Pessoa>{
	@Override
	public void validate(Pessoa o) throws Exception {
		System.out.println(o.getName() + '-' + getClass().getSimpleName());
	}
}

class PessoaFisicaValidator implements Validator<PessoaFisica>{
	@Override
	public void validate(PessoaFisica o) throws Exception {
		System.out.println(o.getName() + '-' + getClass().getSimpleName());
	}
}

class PessoaJuridicaValidator implements Validator<PessoaJuridica>{
	@Override
	public void validate(PessoaJuridica o) throws Exception {
		System.out.println(o.getName() + '-' + getClass().getSimpleName());
	}
}

class ValidatorStrategy<T>{
	Map<Class, Validator> map = null;

	public ValidatorStrategy(){
		
		// Cadastro dos vários tipos de validadores
		// para cara tipo de entidade.
		map = new HashMap<Class, Validator>();
		map.put(Pessoa.class, new PessoaValidator());
		map.put(PessoaFisica.class, new PessoaFisicaValidator());
		map.put(PessoaJuridica.class, new PessoaJuridicaValidator());
	}

	public Validator get(Class objectType){
		return map.get(objectType);
	}
}

public class objectValidation {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		pessoas.add(new Pessoa("Uma pessoa simples"));
		pessoas.add(new PessoaFisica("João"));
		pessoas.add(new PessoaFisica("José"));
		pessoas.add(new PessoaJuridica("Maria"));
		
		ValidatorStrategy<Pessoa> validator = new ValidatorStrategy<Pessoa>();
		
		for (Pessoa p: pessoas){
			validator
				.get(p.getClass())
				.validate(p);
		}
	}
}
