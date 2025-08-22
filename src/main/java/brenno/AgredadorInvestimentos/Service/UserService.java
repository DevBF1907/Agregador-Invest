package brenno.AgredadorInvestimentos.Service;


import brenno.AgredadorInvestimentos.Controller.Dto.AccountResponseDto;
import brenno.AgredadorInvestimentos.Controller.Dto.CreateAccountDto;
import brenno.AgredadorInvestimentos.Controller.Dto.UpdateUserDto;
import brenno.AgredadorInvestimentos.Controller.Dto.UserDto;
import brenno.AgredadorInvestimentos.Entity.Account;
import brenno.AgredadorInvestimentos.Entity.BillingAddress;
import brenno.AgredadorInvestimentos.Entity.User;
import brenno.AgredadorInvestimentos.Repository.AccountRepository;
import brenno.AgredadorInvestimentos.Repository.BillingAddressRepository;

import brenno.AgredadorInvestimentos.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService  {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;


    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }
    public UUID CreateUser(UserDto userDto) {
        // DTO -> ENTITY
        User entity = new User(
                userDto.username(),
                userDto.email(),
                userDto.password()
        );

        User userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(UUID userId){
        return  userRepository.findById(UUID.fromString(userId.toString()));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void deleById(String userId){
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if(userExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUser(String userId, UpdateUserDto updateUserDto){

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if (updateUserDto.username() != null ){user.setUsername(updateUserDto.username());
           }
           if (updateUserDto.password() != null ){
               user.setPassword(updateUserDto.password());
           }
            userRepository.save(user);
        }
    }
    @Transactional
    public void createAccount(String userId, CreateAccountDto createAccountDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        var account = new Account(
                null,
                createAccountDto.description(),
                user,
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                null,
                createAccountDto.street(),
                createAccountDto.number(),
                accountCreated
        );

        accountCreated.setBillingAddress(billingAddress);

        billingAddressRepository.save(billingAddress);

        // Opcional, sincroniza o relacionamento no BD, pode evitar problemas em cascata
        accountRepository.save(accountCreated);
    }

    public List<AccountResponseDto> listAccounts(String userId) {


        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

            return  user.getAccounts()
                       .stream()
                       .map(ac ->
                               new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                       .toList();

    }
}
