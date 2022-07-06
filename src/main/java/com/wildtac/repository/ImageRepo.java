package com.wildtac.repository;

import com.wildtac.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {

    List<Image> findByParentId(Long parentId);

}
