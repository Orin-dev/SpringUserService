package ru.orindev.BostonGeneTest.SpringUserService;

import ru.orindev.BostonGeneTest.SpringUserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Orin on 12.04.2018.
 */

@Service
public class EmailChecker {
    @Autowired
    UserRepository userRepository;

    public boolean check(String eamil) {
        if (userRepository.getUserByEmail(eamil) == null)
            return true;
        else return false;
    }
}
