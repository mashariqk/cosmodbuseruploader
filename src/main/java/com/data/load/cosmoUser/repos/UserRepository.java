package com.data.load.cosmoUser.repos;

import com.data.load.cosmoUser.models.User;
import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DocumentDbRepository<User, String> {
}
