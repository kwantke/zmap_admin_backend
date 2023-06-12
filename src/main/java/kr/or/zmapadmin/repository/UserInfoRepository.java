package kr.or.zmapadmin.repository;

import kr.or.zmapadmin.model.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {
    Optional<UserInfoEntity> findById(String toString);

    Optional<UserInfoEntity> findByIdAndPassword(String id, String password);
}
