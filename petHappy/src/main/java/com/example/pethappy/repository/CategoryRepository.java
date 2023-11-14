package com.example.pethappy.repository;

import com.example.pethappy.model.entity.Category;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(CategoryNameEnum nameEnum);
}
