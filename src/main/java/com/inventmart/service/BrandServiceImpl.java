package com.inventmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.inventmart.model.Brand;
import com.inventmart.repository.BrandRepository;

/**********************************************/
//   Implementasi Brand Service (untuk kebutuhan penggunaan brand repository, yang terkoneksi dari brand service)
/**********************************************/

@Service("brandService")
public class BrandServiceImpl extends BaseCrudService<Brand, JpaRepository<Brand,Long>> implements BrandService {
	
	private BrandRepository brandRepository;
	
	@Autowired
	public BrandServiceImpl(BrandRepository brandRepository) {
		super(brandRepository);
		
		this.brandRepository = brandRepository;
	}

	public BrandRepository getBrandRepository() {
		return brandRepository;
	}

	public void setBrandRepository(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}
	
}
