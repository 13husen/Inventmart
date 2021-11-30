package com.inventmart.service;

import org.springframework.stereotype.Repository;

import com.inventmart.model.Brand;

/**********************************************/
//   BRAND  SERVICE (hanya berisi function intervace, yang akan menyambungkan dengan repository brand , model brand, dan interaksi dengan database)
/**********************************************/

@Repository("brandService")
public interface BrandService extends IBaseService<Brand> {

}
