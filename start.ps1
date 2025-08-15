# Squad Ride Application Startup Script

Write-Host "Starting Squad Ride Application..." -ForegroundColor Green
Write-Host ""

# Start Docker Compose
docker-compose up -d

# Wait for services to be ready
Write-Host "Waiting for services to start..." -ForegroundColor Yellow

# Wait for backend with multiple checks
$backendReady = $false
$attempts = 0
$maxAttempts = 20

do {
    Start-Sleep -Seconds 3
    $attempts++
    
    try {
        # Check health endpoint
        $healthResponse = Invoke-RestMethod -Uri "http://localhost:8080/actuator/health" -TimeoutSec 10
        
        # Additional check - try to hit a basic endpoint to ensure Spring is fully loaded
        $basicResponse = Invoke-WebRequest -Uri "http://localhost:8080/" -TimeoutSec 10
        
        $backendReady = $true
        Write-Host "Backend health check passed (attempt $attempts)" -ForegroundColor Green
    }
    catch {
        Write-Host "Backend not ready yet... (attempt $attempts)" -ForegroundColor Yellow
        $backendReady = $false
    }
} while (-not $backendReady -and $attempts -lt $maxAttempts)

# Wait for frontend
$frontendReady = $false
$frontendAttempts = 0

do {
    Start-Sleep -Seconds 2
    $frontendAttempts++
    
    try {
        $frontendResponse = Invoke-WebRequest -Uri "http://localhost:3000" -TimeoutSec 2
        $frontendReady = $true
        Write-Host "Frontend ready (attempt $frontendAttempts)" -ForegroundColor Green
    }
    catch {
        Write-Host "Frontend not ready yet... (attempt $frontendAttempts)" -ForegroundColor Yellow
        $frontendReady = $false
    }
} while (-not $frontendReady -and $frontendAttempts -lt 15)

# Give Spring Boot a bit more time to finish all initialization
Write-Host "Giving services final moment to complete startup..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

# Clear screen and display success message
Clear-Host

# Display success message and links
Write-Host ""
Write-Host "====================================" -ForegroundColor Green
Write-Host "   Squad Ride Application Ready!    " -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Green
Write-Host ""
Write-Host "Application URLs:" -ForegroundColor Cyan
Write-Host "   Frontend:    http://localhost:3000" -ForegroundColor White
Write-Host "   Backend:     http://localhost:8080" -ForegroundColor White
Write-Host "   H2 Console:  http://localhost:8080/h2-console" -ForegroundColor White
Write-Host ""
Write-Host "Useful Endpoints:" -ForegroundColor Cyan
Write-Host "   Health:      http://localhost:8080/actuator/health" -ForegroundColor White
Write-Host "   Metrics:     http://localhost:8080/actuator/metrics" -ForegroundColor White
Write-Host ""
Write-Host "To stop: docker-compose down" -ForegroundColor Red
Write-Host ""
Write-Host "====================================" -ForegroundColor Green
Write-Host ""