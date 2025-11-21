# Guia Rápido de Deploy / Quick Start Guide

## 🚀 Deploy Rápido (Português)

### Opção 1: Script Automatizado (Recomendado)

```bash
# Deploy local
./deploy.sh local

# Deploy com Docker
./deploy.sh docker

# Apenas compilar
./deploy.sh build
```

### Opção 2: Manual com Docker

```bash
# Iniciar tudo de uma vez
docker-compose up -d

# Acessar a API
curl http://localhost:8080/users
```

### Opção 3: Manual Local

```bash
# 1. Iniciar banco de dados
docker-compose up -d postgres

# 2. Compilar
./mvnw clean package -DskipTests

# 3. Executar
java -jar target/course-springboot-0.0.1-SNAPSHOT.jar
```

## 📝 Testando a API

```bash
# Listar usuários
curl http://localhost:8080/users

# Listar produtos
curl http://localhost:8080/products

# Listar categorias
curl http://localhost:8080/categories

# Listar pedidos
curl http://localhost:8080/orders
```

## 🐳 Comandos Docker Úteis

```bash
# Ver logs da aplicação
docker logs -f springboot-api

# Ver logs do banco de dados
docker logs -f postgresDB

# Parar tudo
docker-compose down

# Parar e remover volumes
docker-compose down -v

# Reconstruir e iniciar
docker-compose up -d --build
```

## 🔧 Variáveis de Ambiente

Para produção, configure estas variáveis:

```bash
export SPRING_PROFILES_ACTIVE=prod
export DATABASE_URL=jdbc:postgresql://seu-host:5432/seu-banco
export DATABASE_USERNAME=seu-usuario
export DATABASE_PASSWORD=sua-senha
export PORT=8080
```

## 📊 Verificar Status

```bash
# Verificar se a aplicação está rodando
curl http://localhost:8080/actuator/health

# Ver métricas (se actuator estiver habilitado)
curl http://localhost:8080/actuator/metrics
```

## 🛠️ Solução de Problemas

### Porta 8080 em uso
```bash
# Mudar porta no application.properties
server.port=8081
```

### Erro de conexão com banco
```bash
# Verificar se o PostgreSQL está rodando
docker ps | grep postgres

# Ver logs do banco
docker logs postgresDB
```

### Aplicação não inicia
```bash
# Ver logs detalhados
docker logs springboot-api

# Ou se rodando local
java -jar target/*.jar --debug
```

---

## 🚀 Quick Deploy (English)

### Option 1: Automated Script (Recommended)

```bash
# Local deployment
./deploy.sh local

# Docker deployment
./deploy.sh docker

# Build only
./deploy.sh build
```

### Option 2: Manual with Docker

```bash
# Start everything at once
docker-compose up -d

# Access the API
curl http://localhost:8080/users
```

### Option 3: Manual Local

```bash
# 1. Start database
docker-compose up -d postgres

# 2. Build
./mvnw clean package -DskipTests

# 3. Run
java -jar target/course-springboot-0.0.1-SNAPSHOT.jar
```

## 📝 Testing the API

```bash
# List users
curl http://localhost:8080/users

# List products
curl http://localhost:8080/products

# List categories
curl http://localhost:8080/categories

# List orders
curl http://localhost:8080/orders
```

For more details, see the [README.md](README.md) file.
