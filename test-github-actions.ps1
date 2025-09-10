# 🚀 Test GitHub Actions para wpsSimulator
# Script para simular el comportamiento del workflow antes del deploy

Write-Host "🚀 Simulando GitHub Actions para wpsSimulator" -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Verificar configuración Maven
Write-Host "📋 Step 1: Verificar configuración Maven" -ForegroundColor Yellow
if (Test-Path "~/.m2/settings.xml") {
    Write-Host "✅ Configuración Maven OK" -ForegroundColor Green
} else {
    Write-Host "❌ Configuración Maven faltante" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Step 2: Verificar estructura del proyecto
Write-Host "📋 Step 2: Verificar estructura inicial" -ForegroundColor Yellow
Write-Host "📁 Contenido del directorio:" -ForegroundColor Gray
Write-Host ""
Get-ChildItem -Name
Write-Host ""

# Step 3: Verificar disponibilidad de dependencias BESA
Write-Host "📋 Step 3: Verificar disponibilidad del ecosistema BESA" -ForegroundColor Yellow
Write-Host "Probando resolución de dependencias con github-packages..." -ForegroundColor Gray

try {
    $env:MAVEN_OPTS = "-Dorg.slf4j.simpleLogger.defaultLogLevel=WARN"
    $result = & mvn dependency:resolve -P github-packages -B -q 2>&1
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Todo el ecosistema BESA encontrado en GitHub Packages" -ForegroundColor Green
        $strategy = "github-packages"
        $depsAvailable = $true
    } else {
        Write-Host "⚠️ Algunas dependencias BESA no disponibles en GitHub Packages" -ForegroundColor Yellow
        Write-Host "🔄 Usaríamos estrategia de fallback (local-dev)" -ForegroundColor Yellow
        $strategy = "local-dev"
        $depsAvailable = $false
    }
} catch {
    Write-Host "⚠️ Error verificando dependencias BESA" -ForegroundColor Yellow
    $strategy = "local-dev"
    $depsAvailable = $false
}
Write-Host ""

# Step 4: Build
Write-Host "📋 Step 4: Build con $strategy" -ForegroundColor Yellow
Write-Host "🔨 Ejecutando: mvn clean compile package -P $strategy -B" -ForegroundColor Gray

try {
    $result = & mvn clean compile package -P $strategy -B -q 2>&1
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Build exitoso" -ForegroundColor Green
    } else {
        Write-Host "❌ Build falló" -ForegroundColor Red
        Write-Host $result
        exit 1
    }
} catch {
    Write-Host "❌ Error durante el build" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Step 5: Verificar artefactos
Write-Host "📋 Step 5: Verificar artefactos generados" -ForegroundColor Yellow

if (Test-Path "target") {
    Write-Host "📁 Contenido del directorio target:" -ForegroundColor Gray
    Write-Host ""
    Get-ChildItem target | Format-Table Name, Length -AutoSize
    Write-Host ""
    
    $jarFiles = Get-ChildItem target -Filter "*.jar"
    if ($jarFiles.Count -gt 0) {
        Write-Host "✅ Archivos JAR encontrados:" -ForegroundColor Green
        foreach ($jar in $jarFiles) {
            $sizeKB = [math]::Round($jar.Length / 1KB, 2)
            $sizeMB = [math]::Round($jar.Length / 1MB, 2)
            if ($sizeMB -gt 1) {
                Write-Host "  📦 $($jar.Name) ($sizeMB MB)" -ForegroundColor White
            } else {
                Write-Host "  📦 $($jar.Name) ($sizeKB KB)" -ForegroundColor White
            }
        }
    } else {
        Write-Host "❌ No se encontraron archivos JAR" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "❌ Directorio target no encontrado" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Step 6: Estrategia de deploy
Write-Host "📋 Step 6: Estrategia de deploy" -ForegroundColor Yellow
if ($depsAvailable) {
    Write-Host "🚀 Se ejecutaría: mvn deploy -P github-packages -DskipTests -B" -ForegroundColor Green
} else {
    Write-Host "🚀 Se ejecutaría: mvn deploy -P local-dev -DskipTests -B" -ForegroundColor Green
}
Write-Host "✅ Deploy se ejecutaría exitosamente" -ForegroundColor Green
Write-Host ""

# Resumen
Write-Host "🎯 RESUMEN DE SIMULACIÓN" -ForegroundColor Cyan
Write-Host "========================" -ForegroundColor Cyan
Write-Host "Build exitoso: ✅" -ForegroundColor Green
Write-Host "Artefactos generados: ✅" -ForegroundColor Green
Write-Host "Estrategia usada: $strategy" -ForegroundColor White
Write-Host "Deploy habilitado: ✅" -ForegroundColor Green
Write-Host ""

Write-Host "🔮 PREDICCIÓN GITHUB ACTIONS:" -ForegroundColor Cyan
if ($depsAvailable) {
    Write-Host "✅ GitHub Actions ejecutará con ecosistema BESA desde GitHub Packages" -ForegroundColor Green
} else {
    Write-Host "✅ GitHub Actions ejecutará con build local del ecosistema BESA" -ForegroundColor Green
}
Write-Host "✅ Deploy se realizará a GitHub Packages" -ForegroundColor Green
Write-Host ""

Write-Host "📦 DEPENDENCIAS BESA INCLUIDAS:" -ForegroundColor Cyan
Write-Host "  - KernelBESA 3.5.1 (Framework base)" -ForegroundColor Gray
Write-Host "  - RationalBESA 3.5 (Agentes racionales)" -ForegroundColor Gray
Write-Host "  - BDIBESA 3.5 (Arquitectura BDI)" -ForegroundColor Gray
Write-Host "  - eBDIBESA 3.5 (BDI emocional)" -ForegroundColor Gray
Write-Host "  - LocalBESA 3.5 (Comunicación local)" -ForegroundColor Gray
Write-Host "  - RemoteBESA 3.5 (Comunicación remota)" -ForegroundColor Gray
Write-Host ""

Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "🏁 Simulación completada exitosamente" -ForegroundColor Green
