/**
 * Copyright (C) 2011 ArtiVisi Intermedia <info@artivisi.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artivisi.biller.simulator.ui.jsf.controller.pascabayar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.artivisi.biller.simulator.entity.Pelanggan;
import com.artivisi.biller.simulator.entity.TagihanPascabayar;
import com.artivisi.biller.simulator.service.PpobSimulatorService;

@Controller
@Scope("session")
public class PelangganController {
	
	@Autowired PpobSimulatorService ppobSimulatorService;
	
	private List<Pelanggan> semua;
	private Pelanggan pelanggan = new Pelanggan();
	private TagihanPascabayar tagihan = new TagihanPascabayar();
	private DataModel<Pelanggan> listModelPelanggan;
	private DataModel<TagihanPascabayar> listModelTagihan;
	private List<TagihanPascabayar> tagihanPascabayar = new ArrayList<TagihanPascabayar>();
	
	@PostConstruct
	public void initSemuaPelanggan(){
		semua = ppobSimulatorService.findAllPelanggan();
		listModelPelanggan = new ListDataModel<Pelanggan>(semua);
	}
	
	public String simpan(){
		ppobSimulatorService.save(pelanggan);
		pelanggan = new Pelanggan();
		refreshListPelanggan();
		return "list?faces-redirect=true";
	}
	
	public String baru(){
		pelanggan = new Pelanggan();
		return "form?faces-redirect=true";
	}

	public String edit(){
		pelanggan = listModelPelanggan.getRowData();
		return "form?faces-redirect=true";
	}
	
	public void delete(){
		ppobSimulatorService.delete(listModelPelanggan.getRowData());
		refreshListPelanggan();
	}
	
	public String tagihan(){
		pelanggan = listModelPelanggan.getRowData();
		tagihanPascabayar = ppobSimulatorService.findTagihan(pelanggan);
		listModelTagihan = new ListDataModel<TagihanPascabayar>(tagihanPascabayar);
		return "/pascabayar/tagihan/list?faces-redirect=true";
	}
	
	public void deleteTagihan(){
		ppobSimulatorService.delete(listModelTagihan.getRowData());
		refreshListTagihan();
	}
	
	public String simpanTagihan(){
		tagihan.setPelanggan(pelanggan);
		ppobSimulatorService.save(tagihan);
		tagihan = new TagihanPascabayar();
		refreshListTagihan();
		return "list?faces-redirect=true";
	}

	public List<Pelanggan> getSemuaPelanggan(){
		return semua;
	}

	public Pelanggan getPelanggan(){
		return pelanggan;
	}
	
	public TagihanPascabayar getTagihan() {
		return tagihan;
	}

	public List<TagihanPascabayar> getTagihanPascabayar(){
		return tagihanPascabayar;
	}

	public DataModel<Pelanggan> getListModelPelanggan() {
		return listModelPelanggan;
	}

	public DataModel<TagihanPascabayar> getListModelTagihan() {
		return listModelTagihan;
	}

	private void refreshListPelanggan() {
		semua = ppobSimulatorService.findAllPelanggan();
		listModelPelanggan = new ListDataModel<Pelanggan>(semua);
	}
	
	private void refreshListTagihan() {
		tagihanPascabayar = ppobSimulatorService.findTagihan(pelanggan);
		listModelTagihan = new ListDataModel<TagihanPascabayar>(tagihanPascabayar);
	}
	
	
}
