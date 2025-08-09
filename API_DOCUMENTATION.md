# Uni Connect API Documentation

## Base URL
- **Development**: `http://localhost:9000`
- **Production**: TBD

## Authentication
Most endpoints require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

---

## REST API Endpoints

### 1. Public Endpoints (No Authentication Required)

#### POST /public/login
**Description**: User login

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "success": true
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Invalid email or password"
}
```

#### POST /public/signup
**Description**: User registration

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response**:
```json
{
  "success": true,
  "message": "Verification email sent. Please check your inbox."
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Student with this email already exists"
}
```

#### GET /public/verify
**Description**: Email verification

**Query Parameters**:
- `id`: User ID
- `token`: Verification token

**Response**:
```
"Your email has been verified successfully. Go to login page"
```

**Error Response**:
```json
{
  "success": false,
  "message": "Invalid verification token"
}
```

#### GET /public/oauth2/redirect
**Description**: OAuth2 redirect endpoint for frontend

**Query Parameters**:
- `token`: JWT token

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "success": true
}
```

---

### 2. Protected Endpoints (Authentication Required)

#### GET /student/hi
**Description**: Test endpoint for authenticated users

**Response**:
```
"Hi!"
```

#### POST /notes
**Description**: Upload notes

**Content-Type**: `multipart/form-data`

**Form Data**:
- `files`: List of files (required)
- `email`: User email (required)
- `title`: Note title (required)
- `subject`: Subject name (required)
- `course_code`: Course code (required)
- `tags`: List of tags (required)
- `description`: Note description (required)

**Response**:
```json
{
  "success": true,
  "message": "Notes uploaded successfully",
  "folder_id": "folder_123456",
  "urls": ["url1", "url2"]
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Failed to upload notes: [error details]"
}
```

#### DELETE /notes
**Description**: Delete notes

**Request Body**:
```json
{
  "folderId": "folder_123456"
}
```

**Response**:
```json
{
  "success": true,
  "message": "Notes deleted successfully"
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Failed to delete notes: [error details]"
}
```

#### POST /chat
**Description**: Send chat message to AI agent

**Request Body**:
```json
{
  "query": "What are the key concepts in machine learning?",
  "thread_id": "thread_123456"
}
```

**Response**:
```json
{
  "response": "Machine learning key concepts include supervised learning, unsupervised learning, neural networks..."
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Failed to send chat message: [error details]"
}
```

---

## GraphQL API

### Base Endpoint
- **URL**: `/graphql`
- **GraphiQL Interface**: `/graphiql`

### Schema Types

#### Notes Type
```graphql
type Notes {
  id: String!
  email: String!
  title: String!
  subject: String!
  course_code: String!
  tags: [String!]!
  description: String!
  folder_id: String
  urls: [String!]
  createdAt: String
  isFavorite: Boolean!
  score: Int!
  feedback: String!
}
```

#### FavNotes Type
```graphql
type FavNotes {
  id: String!
  email: String!
  folder_id: String!
  createdAt: String
}
```

---

### GraphQL Queries

#### 1. Get Notes by Email
**Query**:
```graphql
query GetNotesByEmail($email: String!) {
  getNotesByEmail(email: $email) {
    id
    title
    subject
    course_code
    tags
    description
    folder_id
    urls
    createdAt
    isFavorite
    score
    feedback
  }
}
```

**Variables**:
```json
{
  "email": "user@example.com"
}
```

**Response**:
```json
{
  "data": {
    "getNotesByEmail": [
      {
        "id": "note_123",
        "title": "Machine Learning Basics",
        "subject": "Computer Science",
        "course_code": "CS101",
        "tags": ["ML", "AI", "Programming"],
        "description": "Introduction to machine learning concepts",
        "folder_id": "folder_456",
        "urls": ["https://example.com/file1.pdf"],
        "createdAt": "2024-01-15T10:30:00",
        "isFavorite": true,
        "score": 85,
        "feedback": "Well structured notes"
      }
    ]
  }
}
```

#### 2. Search Notes
**Query**:
```graphql
query SearchNotes($keyword: String!) {
  searchNotes(keyword: $keyword) {
    id
    title
    subject
    course_code
    tags
    description
    folder_id
    urls
    createdAt
    isFavorite
    score
    feedback
  }
}
```

**Variables**:
```json
{
  "keyword": "machine learning"
}
```

**Response**:
```json
{
  "data": {
    "searchNotes": [
      {
        "id": "note_123",
        "title": "Machine Learning Basics",
        "subject": "Computer Science",
        "course_code": "CS101",
        "tags": ["ML", "AI", "Programming"],
        "description": "Introduction to machine learning concepts",
        "folder_id": "folder_456",
        "urls": ["https://example.com/file1.pdf"],
        "createdAt": "2024-01-15T10:30:00",
        "isFavorite": false,
        "score": 85,
        "feedback": "Well structured notes"
      }
    ]
  }
}
```

#### 3. Get Favorite Notes
**Query**:
```graphql
query GetFavoriteNotes($email: String!) {
  getFavoriteNotes(email: $email) {
    id
    title
    subject
    course_code
    tags
    description
    folder_id
    urls
    createdAt
    isFavorite
    score
    feedback
  }
}
```

**Variables**:
```json
{
  "email": "user@example.com"
}
```

**Response**:
```json
{
  "data": {
    "getFavoriteNotes": [
      {
        "id": "note_123",
        "title": "Machine Learning Basics",
        "subject": "Computer Science",
        "course_code": "CS101",
        "tags": ["ML", "AI", "Programming"],
        "description": "Introduction to machine learning concepts",
        "folder_id": "folder_456",
        "urls": ["https://example.com/file1.pdf"],
        "createdAt": "2024-01-15T10:30:00",
        "isFavorite": true,
        "score": 85,
        "feedback": "Well structured notes"
      }
    ]
  }
}
```

---

### GraphQL Mutations

#### 1. Add to Favorites
**Mutation**:
```graphql
mutation AddToFavorites($email: String!, $folder_id: String!) {
  addToFavorites(email: $email, folder_id: $folder_id) {
    id
    email
    folder_id
    createdAt
  }
}
```

**Variables**:
```json
{
  "email": "user@example.com",
  "folder_id": "folder_456"
}
```

**Response**:
```json
{
  "data": {
    "addToFavorites": {
      "id": "fav_789",
      "email": "user@example.com",
      "folder_id": "folder_456",
      "createdAt": "2024-01-15T10:30:00"
    }
  }
}
```

**Error Response**:
```json
{
  "errors": [
    {
      "message": "This note is already in favorites",
      "path": ["addToFavorites"]
    }
  ]
}
```

#### 2. Remove from Favorites
**Mutation**:
```graphql
mutation RemoveFromFavorites($email: String!, $folder_id: String!) {
  removeFromFavorites(email: $email, folder_id: $folder_id)
}
```

**Variables**:
```json
{
  "email": "user@example.com",
  "folder_id": "folder_456"
}
```

**Response**:
```json
{
  "data": {
    "removeFromFavorites": true
  }
}
```

**Error Response**:
```json
{
  "errors": [
    {
      "message": "This note is not in favorites",
      "path": ["removeFromFavorites"]
    }
  ]
}
```

---

## Error Handling

### REST API Errors
- **400 Bad Request**: Invalid request data
- **401 Unauthorized**: Missing or invalid authentication
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

### GraphQL Errors
GraphQL returns errors in the `errors` array with the following structure:
```json
{
  "errors": [
    {
      "message": "Error description",
      "path": ["fieldName"],
      "extensions": {
        "code": "ERROR_CODE"
      }
    }
  ]
}
```

---

## Authentication Flow

### 1. Email/Password Authentication
1. User registers via `POST /public/signup`
2. User receives verification email
3. User verifies email via `GET /public/verify`
4. User logs in via `POST /public/login`
5. Server returns JWT token
6. Client includes token in Authorization header for subsequent requests

### 2. OAuth2 Authentication (Google)
1. User initiates OAuth2 flow via frontend
2. User redirected to Google for authentication
3. Google redirects back to `/login/oauth2/code/google`
4. Server processes OAuth2 callback and creates/updates user
5. Server redirects to frontend with JWT token
6. Client stores token and uses for subsequent requests

---

## Rate Limiting
- No rate limiting currently implemented
- Future implementation may include:
  - 100 requests per minute per user
  - 1000 requests per hour per IP

---

## CORS Configuration
- Allowed Origins: `http://localhost:9000` (configurable)
- Allowed Methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`
- Allowed Headers: `*`
- Credentials: `true`

---

## Development Notes

### Running the Application
```bash
./mvnw spring-boot:run
```

### Environment Variables
- `GOOGLE_CLIENT_ID`: Google OAuth2 client ID
- `GOOGLE_CLIENT_SECRET`: Google OAuth2 client secret
- `MONGODB_URI`: MongoDB connection string
- `SMTP_USERNAME`: Email service username
- `SMTP_PASSWORD`: Email service password

### Database Collections
- `users`: User accounts and profiles
- `notes`: Notes/documents uploaded by users
- `favNotes`: User favorite notes relationships
- `universities`: University information

---

## Future Enhancements
- Pagination for large result sets
- Advanced search filters
- Real-time notifications
- File upload progress tracking
- Bulk operations
- User profiles and settings
- University-specific features
