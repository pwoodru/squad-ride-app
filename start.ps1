# Squad Ride Application Startup Script
param(
    [switch]$Dev,
    [switch]$Production
)

if ($Dev) {
    Write-Host "Starting in DEVELOPMENT mode (with hot reload)..." -ForegroundColor Cyan
    $composeFile = "docker-compose.dev.yml"
} elseif ($Production) {
    Write-Host "Starting in PRODUCTION mode..." -ForegroundColor Green  
    $composeFile = "docker-compose.yml"
} else {
    Write-Host "Choose mode:" -ForegroundColor Yellow
    Write-Host "  [D] Development (hot reload)" -ForegroundColor Cyan
    Write-Host "  [P] Production" -ForegroundColor Green
    Write-Host "  [Enter] Development (default)" -ForegroundColor Gray
    
    $choice = Read-Host "Mode"
    
    if ($choice -eq 'P' -or $choice -eq 'p') {
        Write-Host "Starting in PRODUCTION mode..." -ForegroundColor Green
        $composeFile = "docker-compose.yml"
    } else {
        Write-Host "Starting in DEVELOPMENT mode (with hot reload)..." -ForegroundColor Cyan
        $composeFile = "docker-compose.dev.yml"
    }
}

Write-Host ""

# Start Docker Compose
docker-compose -f $composeFile up -d

# Wait for services to be ready
Write-Host "Waiting for services to start..." -ForegroundColor Yellow

# Wait for backend with multiple checks
$backendReady = $false
$attempts = 0
$maxAttempts = 30

do {
    Start-Sleep -Seconds 3
    $attempts++
    
    try {
        # Check health endpoint
        $healthResponse = Invoke-RestMethod -Uri "http://localhost:8080/actuator/health" -TimeoutSec 2
        
        # Additional check - try to hit a basic endpoint to ensure Spring is fully loaded
        $basicResponse = Invoke-WebRequest -Uri "http://localhost:8080/" -TimeoutSec 2
        
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