package com.springboot.Ecommerce.Repo;

import com.springboot.Ecommerce.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products,Integer> {
    @Query("SELECT p FROM Products p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyWord, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyWord, '%'))")
    List<Products> searchProduct(String keyWord);
}
