package by.company.cryptocurrencywatcher.rest;

import by.company.cryptocurrencywatcher.dto.cryptocurrency.CryptoCurrencyUserDTO;
import by.company.cryptocurrencywatcher.mapper.cryptocurrency.CryptoCurrencyUserMapper;
import by.company.cryptocurrencywatcher.mapper.user.UserMapper;
import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import by.company.cryptocurrencywatcher.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;
    private final UserMapper userMapper;
    private final CryptoCurrencyUserMapper cryptoCurrencyUserMapper;

    @PostMapping("/registration")
    public ResponseEntity<CryptoCurrencyUserDTO> notify(@RequestBody @Valid CryptoCurrencyUserDTO cryptoCurrencyUserDTO) throws JsonProcessingException {
        if (service.existByUsername(cryptoCurrencyUserDTO.getUser().getUsername())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cryptocurrency cryptocurrency = cryptoCurrencyUserMapper.toCryptocurrency(cryptoCurrencyUserDTO);
        cryptocurrency.setUser(userMapper.toUser(cryptoCurrencyUserDTO.getUser()));

        service.registration(cryptocurrency);

        return new ResponseEntity<>(cryptoCurrencyUserDTO, HttpStatus.CREATED);
    }
}
