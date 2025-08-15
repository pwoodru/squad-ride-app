# Squad Ride API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
Currently, the API uses simple user ID-based authentication. In production, implement proper JWT or OAuth2.

## API Endpoints

### Health Check
```http
GET /api/health
```
Returns the health status of the API.

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2024-10-15T10:30:00",
  "service": "Squad Ride API",
  "version": "1.0.0"
}
```

### Users

#### Get User Details
```http
GET /api/users/{id}
```

**Response:**
```json
{
  "id": 1,
  "username": "marcus_j",
  "email": "marcus.j@example.com",
  "displayName": "Marcus J.",
  "firstName": "Marcus",
  "lastName": "Johnson",
  "balance": 2124.75,
  "todayChange": 287.00,
  "createdAt": "2024-10-01T10:00:00",
  "updatedAt": "2024-10-15T10:30:00"
}
```

#### Get User Statistics
```http
GET /api/users/{id}/stats
```

**Response:**
```json
{
  "activeSquads": 6,
  "liveBets": 12,
  "winRate": 82,
  "totalWinnings": 1250.50,
  "totalBetAmount": 980.00,
  "totalBets": 45,
  "wonBets": 37,
  "lostBets": 8
}
```

#### Update User Balance
```http
PUT /api/users/{id}/balance
```

**Request Body:**
```json
{
  "amount": 100.00,
  "operation": "ADD",
  "reason": "Deposit"
}
```

**Operations:** `ADD`, `SUBTRACT`, `SET`

### Squads

#### Get User Squads
```http
GET /api/users/{userId}/squads
```

#### Get Squad Details
```http
GET /api/squads/{id}
```

#### Create Squad
```http
POST /api/squads?userId={userId}
```

**Request Body:**
```json
{
  "name": "Championship Squad",
  "description": "Betting on championship games"
}
```

#### Update Squad
```http
PUT /api/squads/{id}
```

#### Delete Squad
```http
DELETE /api/squads/{id}
```

### Bets

#### Get User Bets
```http
GET /api/users/{userId}/bets
```

#### Get Bet Details
```http
GET /api/bets/{id}
```

#### Create Bet
```http
POST /api/bets?userId={userId}
```

**Request Body:**
```json
{
  "gameId": "game_001",
  "teamName": "Chiefs",
  "amount": 50.00,
  "odds": 1.85,
  "betType": "MONEYLINE",
  "squadId": 1
}
```

**Bet Types:** `MONEYLINE`, `SPREAD`, `OVER_UNDER`, `PROP`

#### Update Bet Status
```http
PUT /api/bets/{id}/status?status={status}&actualPayout={amount}
```

**Bet Statuses:** `PENDING`, `WON`, `LOST`, `CANCELLED`, `PUSHED`

### Games

#### Get All Games
```http
GET /api/games
```

#### Get Game Details
```http
GET /api/games/{id}
```

#### Get Games by Status
```http
GET /api/games/status/{status}
```

**Game Statuses:** `SCHEDULED`, `LIVE`, `COMPLETED`, `CANCELLED`, `POSTPONED`

#### Get Games by Week and Season
```http
GET /api/games/week/{week}/season/{season}
```

#### Get Live Scores
```http
GET /api/scores/live
```

## Error Responses

All endpoints return appropriate HTTP status codes:

- `200 OK` - Success
- `201 Created` - Resource created
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

**Error Response Format:**
```json
{
  "timestamp": "2024-10-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request parameters",
  "path": "/api/bets"
}
```

## Data Models

### User
```json
{
  "id": "number",
  "username": "string",
  "email": "string",
  "displayName": "string",
  "firstName": "string",
  "lastName": "string",
  "balance": "number",
  "todayChange": "number",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Squad
```json
{
  "id": "number",
  "name": "string",
  "description": "string",
  "status": "ACTIVE|INACTIVE|COMPLETED",
  "userId": "number",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Bet
```json
{
  "id": "number",
  "gameId": "string",
  "teamName": "string",
  "amount": "number",
  "odds": "number",
  "betType": "MONEYLINE|SPREAD|OVER_UNDER|PROP",
  "status": "PENDING|WON|LOST|CANCELLED|PUSHED",
  "potentialPayout": "number",
  "actualPayout": "number",
  "userId": "number",
  "squadId": "number",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Game
```json
{
  "id": "string",
  "homeTeam": "string",
  "awayTeam": "string",
  "homeScore": "number",
  "awayScore": "number",
  "gameTime": "datetime",
  "status": "SCHEDULED|LIVE|COMPLETED|CANCELLED|POSTPONED",
  "week": "number",
  "season": "string",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

## Rate Limiting
Currently no rate limiting is implemented. For production, consider implementing rate limiting based on your requirements.

## WebSocket Support (Future Enhancement)
Consider implementing WebSocket connections for real-time updates on:
- Live game scores
- Bet status changes
- Balance updates