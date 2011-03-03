package com.artivisi.ppob.simulator.ui.jsf.controller.pascabayar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.artivisi.ppob.simulator.dto.GeneratorTagihanPascabayar;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;

@Controller
@Scope("request")
public class TagihanController {
	@Autowired private PpobSimulatorService ppobSimulatorService;
	private GeneratorTagihanPascabayar generator = new GeneratorTagihanPascabayar();
	
	@Autowired PelangganController pelangganController;
	
	public String generate(){
		ppobSimulatorService.generatePascabayar(generator);
		generator = new GeneratorTagihanPascabayar();
		pelangganController.initSemuaPelanggan();
		return "/pascabayar/pelanggan/list?faces-redirect=true";
	}
	
	public GeneratorTagihanPascabayar getGenerator() {
		return generator;
	}
}
