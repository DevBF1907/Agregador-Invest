package brenno.AgredadorInvestimentos.Controller;

import brenno.AgredadorInvestimentos.Controller.Dto.AccountResponseDto;
import brenno.AgredadorInvestimentos.Controller.Dto.CreateAccountDto;
import brenno.AgredadorInvestimentos.Controller.Dto.UpdateUserDto;
import brenno.AgredadorInvestimentos.Controller.Dto.UserDto;

import brenno.AgredadorInvestimentos.Entity.User;
import brenno.AgredadorInvestimentos.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        var userId = userService.CreateUser(userDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(UUID.fromString(userId));

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<User>> listUsers(){

         var users =userService.listUsers();
         return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("userId") String userId){
        userService.deleById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {

    userService.updateUser(userId, updateUserDto);
    return ResponseEntity.noContent().build();

    }


    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> deleteAccount(@PathVariable("userId") String userId,
                                              @RequestBody CreateAccountDto createAccountDto){
        userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> listAccounts(@PathVariable("userId") String userId){
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

}
