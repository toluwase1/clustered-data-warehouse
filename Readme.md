# ProgressSoft FXDeals api

## A Simple REST API to Save FX Deal Details to Database

### Requirements
To run this application, ensure that Docker and `make` are installed and active. Thereafter, follow these steps:

1. Clone the repository: (git@github.com:toluwase1/clustered-data-warehouse.git)
2. Navigate to the `clustered-data-warehouse` directory.
3. Run `make all` command in the terminal
4. The app will run on port 8099.

### API Reference
All URIs are relative to http://localhost:8099.

#### Data API

**Method**: [POST]

**HTTP request**: `POST /deals/import`

**Description**: Import a deal.

**Sample Request Body and expected Response Structure**:
```json
Request Body:
{
  "dealUniqueId": "FXD12345",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "dealTimestamp": "2023-07-29T15:30:00",
  "dealAmount": 1500.75
}

Response Body:
{
    "success": true,
    "error": false,
    "responseData": {
        "dealUniqueId": "FXD12345",
        "fromCurrency": "USD",
        "toCurrency": "EUR",
        "dealTimestamp": "2023-07-29T15:30:00",
        "dealAmount": 1500.75
    },
    "statusCode": 201
}
