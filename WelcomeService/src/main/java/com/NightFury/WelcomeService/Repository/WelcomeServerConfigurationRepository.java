package com.NightFury.WelcomeService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.NightFury.WelcomeService.Entity.WelcomePageConfigurationSchema;

@Repository
@Transactional
public interface WelcomeServerConfigurationRepository extends JpaRepository<WelcomePageConfigurationSchema, String> {
	

}
