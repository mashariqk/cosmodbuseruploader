package com.data.load.cosmoUser.jobz;

import com.data.load.cosmoUser.models.User;
import com.data.load.cosmoUser.repos.UserRepository;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Resource;
import java.util.List;

public class UserWriter implements ItemWriter<User> {

    @Resource
    private UserRepository userRepository;
    
    @Override
    public void write(List<? extends User> users) throws Exception {
        for (User user:users){
            userRepository.save(user);
        }
    }
}
