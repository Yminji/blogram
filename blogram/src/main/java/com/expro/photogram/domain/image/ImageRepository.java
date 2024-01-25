package com.expro.photogram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	@Query(value="SELECT * FROM image", nativeQuery = true)
	Page<Image> mStory(int principalId, Pageable pageable);
}
