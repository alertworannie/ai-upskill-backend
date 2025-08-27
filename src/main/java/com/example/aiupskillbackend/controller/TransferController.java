package com.example.aiupskillbackend.controller;

import com.example.aiupskillbackend.dto.TransferRequest;
import com.example.aiupskillbackend.model.Transaction;
import com.example.aiupskillbackend.repository.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Tag(name = "Transfer", description = "Money transfer and transaction history API")
public class TransferController {

    private final TransactionRepository transactionRepository;

    public TransferController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money", description = "Transfer money from one user to another")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Transfer request details",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransferRequest.class),
                    examples = @ExampleObject(value = "{ \"fromUserId\": \"LBK001234\", \"toUserId\": \"LBK002345\", \"amount\": 100 }")
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"Transfer successful\", \"transactionId\": 1, \"fromUserId\": \"LBK001234\", \"toUserId\": \"LBK002345\", \"amount\": 100, \"createdAt\": \"2025-08-27T14:45:00\" }")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid transfer request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"Invalid transfer amount\" }")
                    )
            )
    })
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        try {
            // Basic validation
            if (request.getFromUserId() == null || request.getToUserId() == null || request.getAmount() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Missing required fields"));
            }

            if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid transfer amount"));
            }

            if (request.getFromUserId().equals(request.getToUserId())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Cannot transfer to the same user"));
            }

            // Create and save transaction
            Transaction transaction = new Transaction(
                    request.getFromUserId(),
                    request.getToUserId(),
                    request.getAmount()
            );

            Transaction savedTransaction = transactionRepository.save(transaction);

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transfer successful");
            response.put("transactionId", savedTransaction.getId());
            response.put("fromUserId", savedTransaction.getFromUserId());
            response.put("toUserId", savedTransaction.getToUserId());
            response.put("amount", savedTransaction.getAmount());
            response.put("createdAt", savedTransaction.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Transfer failed: " + e.getMessage()));
        }
    }

    @GetMapping("/transactions/recent/{userId}")
    @Operation(summary = "Get recent transactions", description = "Get recent transactions for a specific user (sent or received)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "[{ \"id\": 1, \"fromUserId\": \"LBK001234\", \"toUserId\": \"LBK002345\", \"amount\": 100, \"createdAt\": \"2025-08-27T14:45:00\", \"type\": \"sent\" }]")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User not found or no transactions",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"No transactions found for user\" }")
                    )
            )
    })
    public ResponseEntity<?> getRecentTransactions(@PathVariable String userId) {
        try {
            List<Transaction> transactions = transactionRepository.findTop10RecentTransactionsByUserId(userId);

            if (transactions.isEmpty()) {
                Map<String, Object> emptyResponse = new HashMap<>();
                emptyResponse.put("message", "No transactions found for user");
                emptyResponse.put("transactions", List.of());
                return ResponseEntity.ok(emptyResponse);
            }

            // Transform transactions with type information
            List<Map<String, Object>> transactionList = transactions.stream()
                    .map(transaction -> {
                        String type = transaction.getFromUserId().equals(userId) ? "sent" : "received";
                        Map<String, Object> transactionMap = new HashMap<>();
                        transactionMap.put("id", transaction.getId());
                        transactionMap.put("fromUserId", transaction.getFromUserId());
                        transactionMap.put("toUserId", transaction.getToUserId());
                        transactionMap.put("amount", transaction.getAmount());
                        transactionMap.put("createdAt", transaction.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        transactionMap.put("type", type);
                        return transactionMap;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("transactions", transactionList);
            response.put("count", transactionList.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to retrieve transactions: " + e.getMessage()));
        }
    }
}
