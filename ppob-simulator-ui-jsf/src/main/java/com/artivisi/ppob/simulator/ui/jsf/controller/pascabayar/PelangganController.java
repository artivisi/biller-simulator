package com.artivisi.ppob.simulator.ui.jsf.controller.pascabayar;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.artivisi.ppob.simulator.entity.Pelanggan;
import com.artivisi.ppob.simulator.service.PpobSimulatorService;

@Controller
@Scope("session")
public class PelangganController {
	
	@Autowired PpobSimulatorService ppobSimulatorService;
	
	private List<Pelanggan> semua;
	private Pelanggan pelanggan = new Pelanggan();
	private DataModel<Pelanggan> model;
	
	@PostConstruct
	public void initSemuaPelanggan(){
		semua = ppobSimulatorService.findAllPelanggan();
		model = new ListDataModel<Pelanggan>(semua);
	}
	
	public List<Pelanggan> getSemuaPelanggan(){
		return semua;
	}

	public Pelanggan getPelanggan(){
		return pelanggan;
	}
	
	public String simpan(){
		ppobSimulatorService.save(pelanggan);
		pelanggan = new Pelanggan();
		refreshList();
		return "list?faces-redirect=true";
	}
	
	public String baru(){
		pelanggan = new Pelanggan();
		return "form?faces-redirect=true";
	}

	public String edit(){
		pelanggan = model.getRowData();
		return "form?faces-redirect=true";
	}
	
	public void delete(){
		ppobSimulatorService.delete(model.getRowData());
		refreshList();
	}

	private void refreshList() {
		semua = ppobSimulatorService.findAllPelanggan();
		model = new ListDataModel<Pelanggan>(semua);
	}

	public DataModel<Pelanggan> getModel() {
		return model;
	}

	public void setModel(DataModel<Pelanggan> model) {
		this.model = model;
	}
	
	
	
}
