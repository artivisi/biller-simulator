package com.artivisi.ppob.simulator.ui.jsf.controller.pascabayar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.artivisi.ppob.simulator.entity.Pelanggan;

@Controller
@Scope("request")
public class PelangganController {
	public List<Pelanggan> getSemuaPelanggan(){
		List<Pelanggan> result = new ArrayList<Pelanggan>();
		
		result.add(createPelanggan());
		result.add(createPelanggan());
		result.add(createPelanggan());
		result.add(createPelanggan());
		result.add(createPelanggan());
		result.add(createPelanggan());
		
		return result;
	}

	private Pelanggan createPelanggan() {
		Pelanggan p = new Pelanggan();
		p.setIdpel("123456789012");
		p.setNama("Pelanggan Dum'my");
		p.setPowerConsumingCategory("1200");
		p.setSubscriberSegmentation("R1");
		p.setServiceUnit("51");
		p.setServiceUnitPhone("1234567890");
		return p;
	}
	
	public Pelanggan getPelanggan(){
		return createPelanggan();
	}
	
	public String simpan(){
		return "list?faces-redirect=true";
	}
}
