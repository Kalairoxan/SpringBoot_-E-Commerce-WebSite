package com.springboot.Ecommerce.Service;

import com.springboot.Ecommerce.Model.Products;
import com.springboot.Ecommerce.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
     ProductRepo repo;

    public List<Products> getAllProducts() {
        return repo.findAll();
    }

    public Products getProductById(int id) {
    return repo.findById(id).orElse(null);
    }

    public Products addProducts(Products product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public Products updateProduct(int id, Products product, MultipartFile imageFile) throws IOException {
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Products> searchProduct(String keyWord) {
        return repo.searchProduct(keyWord);
    }
}
