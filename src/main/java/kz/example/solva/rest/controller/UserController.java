package kz.example.solva.rest.controller;

import kz.example.solva.data.dto.LimitDto;
import kz.example.solva.data.dto.TransactionDto;
import kz.example.solva.data.dto.UserDto;
import kz.example.solva.rest.request.LimitRequest;
import kz.example.solva.rest.request.TransactionRequest;
import kz.example.solva.service.LimitService;
import kz.example.solva.service.TransactionService;
import kz.example.solva.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final LimitService limitService;
    private final TransactionService transactionService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/{id}/limit_exceeded_transactions")
    public ResponseEntity<List<TransactionDto>> getLimitExceededTransactions(@PathVariable("id") int id) {
        return ResponseEntity.ok(transactionService.getLimitExceededTransactions(id));
    }

    @PostMapping("/limit")
    public ResponseEntity<LimitDto> createLimit(@RequestBody LimitRequest model) {
        return ResponseEntity.ok(limitService.create(model));
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionDto> transaction(@RequestBody TransactionRequest model) {
        return ResponseEntity.ok(transactionService.create(model));
    }
}
