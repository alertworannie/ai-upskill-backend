# Transfer Flow API Documentation

This document outlines the REST endpoints supporting the money transfer flow in the AI Upskill Backend. All endpoints are secured through application logic and validated using basic checks. For request/response schema details and examples, please refer to the built-in Swagger UI.

## POST /transfer

Transfers an amount from one user to another.

- **URL**: `/transfer`
- **Method**: `POST`
- **Request Body** (application/json):
  ```json
  {
    "fromUserId": "LBK001234",
    "toUserId": "LBK002345",
    "amount": 100
  }
  ```

- **Responses**:
  - **200 OK**
    ```json
    {
      "message": "Transfer successful",
      "transactionId": 1,
      "fromUserId": "LBK001234",
      "toUserId": "LBK002345",
      "amount": 100,
      "createdAt": "2025-08-27T14:45:00"
    }
    ```
  - **400 Bad Request**
    ```json
    {
      "error": "Invalid transfer amount"
    }
    ```

## GET /transactions/recent/{userId}

Retrieves up to the 10 most recent transactions for a given user, including both sent and received.

- **URL**: `/transactions/recent/{userId}`
- **Method**: `GET`
- **Path Parameter**:
  - `userId` (string) – the identifier of the user whose transactions to fetch

- **Responses**:
  - **200 OK**
    - **When transactions exist**:
      ```json
      {
        "userId": "LBK001234",
        "transactions": [
          {
            "id": 1,
            "fromUserId": "LBK001234",
            "toUserId": "LBK002345",
            "amount": 100,
            "createdAt": "2025-08-27T14:45:00",
            "type": "sent"
          }
        ],
        "count": 1
      }
      ```
    - **When no transactions found**:
      ```json
      {
        "message": "No transactions found for user",
        "transactions": []
      }
      ```
  - **400 Bad Request**
    ```json
    {
      "error": "Failed to retrieve transactions: <error message>"
    }
    ```

