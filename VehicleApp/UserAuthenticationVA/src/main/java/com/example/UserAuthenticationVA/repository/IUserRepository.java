    package com.example.UserAuthenticationVA.repository;

    import com.example.UserAuthenticationVA.domain.User;
    import org.springframework.data.repository.CrudRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface IUserRepository extends CrudRepository<User, String> {

        User findByEmailIdAndPassword(String emailId, String password);
    }
