package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class EncryptionController {

    @Autowired
    private TripleDesUtils tripleDesUtils;

    @GetMapping("/api/encrypt/{data}")
    public Mono<String> encryptData(@PathVariable("data") String data) {
        return Mono.fromCallable(() -> tripleDesUtils.encrypt(data));
    }

    @GetMapping("/api/decrypt/{data}")
    public Mono<String> decryptData(@PathVariable("data") String data) {
        return Mono.fromCallable(() -> tripleDesUtils.decrypt(data));
    }
}
