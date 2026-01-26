package com.ecom.app.service;

import com.ecom.app.dto.AddressDTO;
import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.Address;
import com.ecom.app.repository.UserRepository;
import com.ecom.app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
        }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user , userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                  .map(this::mapToUserResponse);
    }
    public boolean updateUser(Long id , UserRequest updatedUserRequest){
                    return userRepository.findById(id)
                    .map(existingUser ->{
                      updateUserFromRequest(existingUser, updatedUserRequest);
                        userRepository.save(existingUser);
                        return true;
                    }).orElse(false);
    }
    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {

            Address address = new Address();

            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user){
         UserResponse userResponse = new UserResponse();
         userResponse.setId(String.valueOf(user.getId()));
         userResponse.setFirstname(user.getFirstname());
         userResponse.setLastname(user.getLastname());
         userResponse.setEmail(user.getEmail());
         userResponse.setPhone(user.getPhone());
         userResponse.setRole(user.getRole());

         if (user.getAddress() != null){
             AddressDTO address = new AddressDTO();
             address.setStreet(user.getAddress().getStreet());
             address.setCity(user.getAddress().getCity());
             address.setState(user.getAddress().getState());
             address.setCountry(user.getAddress().getCountry());
             address.setZipcode(user.getAddress().getZipcode());
             userResponse.setAddress(address);
         }
         return userResponse;
    }

}

