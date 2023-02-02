package com.NightFury.UserAndCartService.User.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.User.Entity.BaseUserModel;

@Repository
@Transactional
public interface BaseUserRepository extends JpaRepository<BaseUserModel, String>{
	
	@Query(value = "SELECT * FROM user_base_table WHERE email = ?1 ORDER BY user_Type DESC" , nativeQuery = true)
	List<BaseUserModel> getUsersBasedOnEmail(String email);
	
	@Query(value = "SELECT * FROM user_base_table WHERE email = ?1 AND user_Type = 'GUEST'" , nativeQuery = true)
	List<BaseUserModel> getGuestUsersBasedOnEmail(String email);
	
	@Query(value = "SELECT * FROM user_base_table WHERE email = ?1 AND user_Type = 'REGISTERED'" , nativeQuery = true)
	List<BaseUserModel> getRegisteredUsersBasedOnEmail(String email);
	
	@Modifying
	@Query(value = "UPDATE user_base_table SET updated_at = ADDTIME(CURRENT_TIMESTAMP , '05:30:00.0') WHERE email = ?1 ORDER BY user_Type DESC" , nativeQuery = true)
	int updateTimeForExistingUser(String email);
}
