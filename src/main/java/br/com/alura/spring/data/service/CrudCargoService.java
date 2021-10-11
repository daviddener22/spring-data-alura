package br.com.alura.spring.data.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private Boolean system = true;

	private final CargoRepository cargoRepository;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Qual ação de cargo deseja executar?");
			System.out.println("0 - Sair\n1 - Salvar\n2 - Atualizar\n3 - Deletar\n4 - Listar");
			int acao = scanner.nextInt();

			switch (acao) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;

			case 3:
				deletar(scanner);
				break;

			case 4:
				listar();
				break;

			default:
				system = false;
				break;
			}
		}

	}

	private void salvar(Scanner scanner) {
		Cargo cargo = new Cargo();
		System.out.println("Descrição do Cargo:\n");
		String desc = scanner.next();
		desc += scanner.nextLine();
		cargo.setDescricao(desc);
		cargoRepository.save(cargo);
		System.out.println("Salvo!");

	}

	private void atualizar(Scanner scanner) {
		List<Cargo> cargos = (List<Cargo>) cargoRepository.findAll();
		System.out.println("ID do cargo que deseja atualizar?\n");
		System.out.println(cargos);
		Long id = scanner.nextLong();
		System.out.println("Nova descrição?\n");
		String desc = scanner.next();
		desc += scanner.nextLine();

		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setDescricao(desc);
		cargoRepository.save(cargo);

		System.out.println("Registro atualizado!");

	}

	private void deletar(Scanner scanner) {
		List<Cargo> cargos = (List<Cargo>) cargoRepository.findAll();
		System.out.println("ID do cargo que deseja deletar?\n");
		System.out.println(cargos);
		Long id = scanner.nextLong();
		Optional<Cargo> optional = cargoRepository.findById(id);

		if (optional.isPresent()) {
			Cargo cargo = optional.get();
			cargoRepository.delete(cargo);
			System.out.println("Cargo excluído");
		} else {
			optional.orElseThrow(() -> new RuntimeException("Cargo de ID " + id + " não localizado"));
		}
	}

	private void listar() {
		List<Cargo> cargos = (List<Cargo>) cargoRepository.findAll();
		System.out.println(cargos);
	}
}
