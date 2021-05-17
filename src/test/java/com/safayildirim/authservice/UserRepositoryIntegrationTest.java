package com.safayildirim.authservice;

import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.repos.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryIntegrationTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    List<String> mockedList;

    @Test
    public void whenCalledSave_thenCorrectNumberOfUsers() {
        userRepository.save(new User("Bob", "123456", "bob@domain.com"));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }
}