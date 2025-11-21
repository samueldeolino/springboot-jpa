# Spring Boot JPA - API REST

API REST desenvolvida com Spring Boot e JPA para gerenciamento de pedidos, usuários, produtos e categorias.

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.6 ou superior
- PostgreSQL 17 (ou usar Docker)
- Docker e Docker Compose (opcional, mas recomendado)

## 🚀 Como Fazer Deploy da API REST

Este guia apresenta diferentes formas de fazer deploy da sua aplicação Spring Boot.

### Método 1: Executar localmente com JAR

#### Passo 1: Preparar o banco de dados

**Opção A: Usar Docker Compose (Recomendado)**
```bash
docker-compose up -d
```

**Opção B: Usar PostgreSQL instalado localmente**
- Certifique-se de que o PostgreSQL está rodando
- Crie um banco de dados chamado `spring-test`
- Configure usuário `admin` com senha `admin`

#### Passo 2: Compilar a aplicação
```bash
./mvnw clean package -DskipTests
```

#### Passo 3: Executar o JAR
```bash
java -jar target/course-springboot-0.0.1-SNAPSHOT.jar
```

A API estará disponível em: `http://localhost:8080`

### Método 2: Deploy com Docker

#### Passo 1: Criar a imagem Docker
```bash
docker build -t springboot-jpa-api .
```

#### Passo 2: Executar com Docker Compose
```bash
docker-compose up -d
```

Isso irá iniciar tanto o banco de dados PostgreSQL quanto a aplicação.

A API estará disponível em: `http://localhost:8080`

### Método 3: Deploy em Ambiente de Produção

#### Configuração para Produção

1. **Criar um perfil de produção** (`application-prod.yaml`):
```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
```

2. **Definir variáveis de ambiente**:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DATABASE_URL=jdbc:postgresql://seu-servidor:5432/seu-banco
export DATABASE_USERNAME=seu-usuario
export DATABASE_PASSWORD=sua-senha
```

3. **Executar com o perfil de produção**:
```bash
java -jar -Dspring.profiles.active=prod target/course-springboot-0.0.1-SNAPSHOT.jar
```

### Método 4: Deploy em Cloud (Heroku, AWS, Azure)

#### Heroku

1. Criar `Procfile`:
```
web: java -Dserver.port=$PORT -jar target/course-springboot-0.0.1-SNAPSHOT.jar
```

2. Fazer deploy:
```bash
heroku create nome-da-sua-app
heroku addons:create heroku-postgresql
git push heroku main
```

#### AWS Elastic Beanstalk

1. Instalar AWS EB CLI:
```bash
pip install awsebcli
```

2. Inicializar e fazer deploy:
```bash
eb init -p java-21 springboot-api
eb create springboot-api-env
eb deploy
```

#### Azure App Service

```bash
az webapp up --name springboot-api --resource-group meu-grupo --runtime "JAVA:21-java21"
```

### Método 5: Deploy com Kubernetes

1. **Criar imagem Docker**:
```bash
docker build -t seu-registry/springboot-jpa-api:latest .
docker push seu-registry/springboot-jpa-api:latest
```

2. **Criar deployment.yaml**:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: springboot-api
spec:
  replicas: 3
  selector:
    matchLabels:
      app: springboot-api
  template:
    metadata:
      labels:
        app: springboot-api
    spec:
      containers:
      - name: springboot-api
        image: seu-registry/springboot-jpa-api:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: DATABASE_URL
          valueFrom:
            secretKeyRef:
              name: db-secrets
              key: url
```

3. **Aplicar configuração**:
```bash
kubectl apply -f deployment.yaml
```

## 📡 Endpoints da API

Após o deploy, você pode acessar os seguintes endpoints:

### Usuários
- `GET /users` - Listar todos os usuários
- `GET /users/{id}` - Buscar usuário por ID
- `POST /users` - Criar novo usuário
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Deletar usuário

### Produtos
- `GET /products` - Listar todos os produtos
- `GET /products/{id}` - Buscar produto por ID

### Categorias
- `GET /categories` - Listar todas as categorias
- `GET /categories/{id}` - Buscar categoria por ID

### Pedidos
- `GET /orders` - Listar todos os pedidos
- `GET /orders/{id}` - Buscar pedido por ID

## 🔧 Configuração

### Perfis Disponíveis

- **postgres**: Usa banco de dados PostgreSQL (padrão)
- **test**: Usa banco H2 em memória para testes

Para alterar o perfil, edite o arquivo `application.properties`:
```properties
spring.profiles.active=postgres
```

## 🐳 Docker

### Dockerfile
O projeto inclui um Dockerfile otimizado para produção que:
- Usa multi-stage build para imagens menores
- Compila a aplicação em um container
- Executa apenas o JAR no container final
- Usa imagem base otimizada do OpenJDK

### Docker Compose
O arquivo `docker-compose.yml` configura:
- PostgreSQL 17 com Alpine Linux
- Volumes persistentes para dados
- Rede interna para comunicação

## 📊 Monitoramento e Logs

### Visualizar logs da aplicação
```bash
# Se executando com Docker
docker logs -f nome-do-container

# Se executando com systemd
journalctl -u springboot-api -f
```

### Endpoints de Health Check
Adicione Spring Boot Actuator para monitoramento:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Endpoints disponíveis:
- `/actuator/health` - Status da aplicação
- `/actuator/info` - Informações da aplicação
- `/actuator/metrics` - Métricas

## 🔒 Segurança

### Recomendações para Produção

1. **Nunca exponha credenciais no código**
   - Use variáveis de ambiente
   - Use serviços de gerenciamento de secrets (AWS Secrets Manager, Azure Key Vault)

2. **Configure HTTPS**
   - Use certificados SSL/TLS
   - Configure reverse proxy (Nginx, Apache)

3. **Limite o acesso ao banco de dados**
   - Use regras de firewall
   - Configure VPC/Security Groups

4. **Desabilite endpoints desnecessários em produção**
   ```properties
   management.endpoints.web.exposure.include=health,info
   ```

## 🛠️ Troubleshooting

### Erro: "Port 8080 already in use"
```bash
# Altere a porta no application.properties
server.port=8081
```

### Erro: "Unable to connect to database"
- Verifique se o PostgreSQL está rodando
- Confirme as credenciais no arquivo de configuração
- Verifique se a URL do banco está correta

### Aplicação não inicia
```bash
# Verifique os logs
java -jar target/course-springboot-0.0.1-SNAPSHOT.jar --debug
```

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais.

---

## 🌍 English Version

# Spring Boot JPA - REST API

REST API developed with Spring Boot and JPA for managing orders, users, products, and categories.

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL 17 (or use Docker)
- Docker and Docker Compose (optional but recommended)

## 🚀 How to Deploy the REST API

### Method 1: Run locally with JAR

1. **Start the database**: `docker-compose up -d`
2. **Build the application**: `./mvnw clean package -DskipTests`
3. **Run the JAR**: `java -jar target/course-springboot-0.0.1-SNAPSHOT.jar`

API will be available at: `http://localhost:8080`

### Method 2: Deploy with Docker

1. **Build Docker image**: `docker build -t springboot-jpa-api .`
2. **Run with Docker Compose**: `docker-compose up -d`

### Method 3: Production Deployment

Create production profile with environment variables:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DATABASE_URL=jdbc:postgresql://your-server:5432/your-db
export DATABASE_USERNAME=your-user
export DATABASE_PASSWORD=your-password
java -jar -Dspring.profiles.active=prod target/course-springboot-0.0.1-SNAPSHOT.jar
```

### Cloud Deployment Options

- **Heroku**: Use Procfile and PostgreSQL addon
- **AWS Elastic Beanstalk**: Use EB CLI
- **Azure App Service**: Use Azure CLI
- **Kubernetes**: Use deployment manifests

For detailed instructions, see the Portuguese section above.

## 📡 API Endpoints

- Users: `/users`
- Products: `/products`
- Categories: `/categories`
- Orders: `/orders`

See the Portuguese section for complete endpoint documentation.
