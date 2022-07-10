package com.example.learnswagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ExampleRestController {
    private List<User> userList = new ArrayList<>();

    @GetMapping()
    public List<User> getAllUsers() {
        return userList;
    }

    @GetMapping("/{name}")
    public User getUser(@PathVariable("name") String name) {
        for (User user: userList) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @Operation(
            summary = "Добавление пользователя",
            description = "По переданному объекту записывает нового пользователя в базу данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно добавлен в базу данных"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким именем уже существует")
            })
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user) {
        for (User currentUser: userList) {
            if (currentUser.getName().equals(user.getName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }

        userList.add(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public void deleteUser(@PathVariable("name") String name) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(name)) {
                userList.remove(i);
                throw new ResponseStatusException(HttpStatus.OK);
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
