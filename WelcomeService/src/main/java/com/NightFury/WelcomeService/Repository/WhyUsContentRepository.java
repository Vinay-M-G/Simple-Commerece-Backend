package com.NightFury.WelcomeService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.NightFury.WelcomeService.Entity.WelcomePageWhyUsModel;

@Repository
@Transactional
public interface WhyUsContentRepository extends JpaRepository<WelcomePageWhyUsModel, String> {

	@Query(value = "SELECT * FROM why_us_marketing_content ORDER BY w_rank", nativeQuery = true)
	List<WelcomePageWhyUsModel> getWhyContentByOrder();
}
