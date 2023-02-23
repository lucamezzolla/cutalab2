package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.UserDTO;
import com.cutalab.cutalab2.backend.entity.UserEntity;
import com.cutalab.cutalab2.backend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        List<UserEntity> list1 = userRepository.findAll();
        List<UserDTO> list2 = new ArrayList<>();
        for(UserEntity u : list1) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(u, userDTO);
            list2.add(userDTO);
        }
        return list2;
    }

    public UserEntity getById(Integer id) {
        return userRepository.findById(id).get();
    }

    public void insert(UserEntity UserEntity) {
        userRepository.saveAndFlush(UserEntity);
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}
