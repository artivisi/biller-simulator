package com.artivisi.ppob.simulator.ui.jsf.controller.pascabayar;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;

@Controller
@Scope("request")
public class PelangganController {
	
	@Autowired PpobSimulatorService ppobSimulatorService;
	
	private List<Pelanggan> semua;
	private Pelanggan pelanggan = new Pelanggan();
	
	@PostConstruct
	public void initSemuaPelanggan(){
		semua = ppobSimulatorService.findAllPelanggan();
	}
	
	public List<Pelanggan> getSemuaPelanggan(){
		return semua;
	}

	public Pelanggan getPelanggan(){
		return pelanggan;
	}
	
	public String simpan(){
		return "list?faces-redirect=true";
	}
}
