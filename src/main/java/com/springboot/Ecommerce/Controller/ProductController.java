package com.springboot.Ecommerce.Controller;

import com.springboot.Ecommerce.Model.Products;
import com.springboot.Ecommerce.Service.ProductService;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable int id){

        Products product = productService.getProductById(id);
        if(product != null)
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProducts(@RequestPart Products product,
                                         @RequestPart MultipartFile imageFile) {
        try {
            Products products1 = productService.addProducts(product, imageFile);
            return new ResponseEntity<>(products1, HttpStatus.CREATED);
        }catch (Exception E)
        {
            return new ResponseEntity<>(E.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId)
    {
        Products products = productService.getProductById(productId);
        byte[] imageDate = products.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(products.getImageType())).body(imageDate);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Products product,
                                                @RequestPart MultipartFile imageFile)
    {
        Products products = null;
        try {
            products = productService.updateProduct(id,product,imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(products != null) return new ResponseEntity<>("Updated",HttpStatus.OK);
        else return new ResponseEntity<>("Failed To Update",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deletewProduct(@PathVariable int id)
    {
        Products product =  productService.getProductById(id);
        if(product != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Products>> searchProducts(@RequestParam String keyWord)
    {
        System.out.println("Searching Product :" + keyWord);
        List<Products> products = productService.searchProduct(keyWord);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
