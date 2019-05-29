package com.restdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityConfigTest {

    @Autowired
    private TestRestTemplate template;


    @Test
    public void shouldBeStatus_200() {
        ResponseEntity<String> result = template.withBasicAuth("Jack", "123")
                .getForEntity("/students", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void shouldBeStatus_401() {
        ResponseEntity<String> result = template.withBasicAuth("Mike", "abc")
                .getForEntity("/students", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}