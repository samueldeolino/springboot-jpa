#!/bin/bash

# Script de Deploy da API Spring Boot
# Este script facilita o processo de deploy da aplicação

set -e

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para imprimir mensagens coloridas
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[AVISO]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERRO]${NC} $1"
}

# Verificar se o Java está instalado
check_java() {
    print_info "Verificando instalação do Java..."
    if command -v java &> /dev/null; then
        # Get Java version - works with both old (1.8) and new (9+) version formats
        # Extract version and handle both "1.8" format and "21.0.1" format
        JAVA_VERSION_STRING=$(java -version 2>&1 | grep -i version | head -n 1 | sed 's/.*version "\(.*\)".*/\1/')
        # Remove '1.' prefix for old versions (1.8 -> 8), keep as-is for new versions (21 -> 21)
        if [[ "$JAVA_VERSION_STRING" == 1.* ]]; then
            JAVA_VERSION=$(echo "$JAVA_VERSION_STRING" | sed 's/^1\.//' | cut -d'.' -f1)
        else
            JAVA_VERSION=$(echo "$JAVA_VERSION_STRING" | cut -d'.' -f1)
        fi
        print_info "Java versão $JAVA_VERSION encontrado"
        if [ "$JAVA_VERSION" -lt 21 ]; then
            print_error "Java 21 ou superior é necessário. Versão atual: $JAVA_VERSION"
            exit 1
        fi
    else
        print_error "Java não está instalado. Por favor, instale Java 21 ou superior."
        exit 1
    fi
}

# Verificar se o Maven está instalado
check_maven() {
    print_info "Verificando instalação do Maven..."
    if command -v mvn &> /dev/null; then
        print_info "Maven encontrado"
    elif [ -f "./mvnw" ]; then
        print_info "Maven Wrapper encontrado"
    else
        print_error "Maven não está instalado e Maven Wrapper não foi encontrado."
        exit 1
    fi
}

# Compilar a aplicação
build_app() {
    print_info "Compilando a aplicação..."
    if [ -f "./mvnw" ]; then
        chmod +x mvnw
        ./mvnw clean package -DskipTests
    else
        mvn clean package -DskipTests
    fi
    
    if [ $? -eq 0 ]; then
        print_info "Compilação concluída com sucesso!"
    else
        print_error "Falha na compilação"
        exit 1
    fi
}

# Iniciar banco de dados com Docker
start_database() {
    print_info "Iniciando banco de dados PostgreSQL..."
    if command -v docker-compose &> /dev/null || command -v docker &> /dev/null; then
        docker-compose up -d postgres
        print_info "Aguardando o banco de dados iniciar..."
        sleep 10
        print_info "Banco de dados iniciado!"
    else
        print_warning "Docker não encontrado. Certifique-se de que o PostgreSQL está rodando manualmente."
    fi
}

# Executar a aplicação
run_app() {
    print_info "Iniciando a aplicação..."
    JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" | head -n 1)
    
    if [ -z "$JAR_FILE" ]; then
        print_error "Arquivo JAR não encontrado. Execute a compilação primeiro."
        exit 1
    fi
    
    print_info "Executando: $JAR_FILE"
    java -jar "$JAR_FILE"
}

# Deploy com Docker
deploy_docker() {
    print_info "Fazendo deploy com Docker..."
    
    if ! command -v docker &> /dev/null; then
        print_error "Docker não está instalado."
        exit 1
    fi
    
    print_info "Construindo imagem Docker..."
    docker build -t springboot-jpa-api:latest .
    
    print_info "Iniciando containers..."
    docker-compose up -d
    
    print_info "Deploy concluído! Aplicação disponível em http://localhost:8080"
    print_info "Para ver os logs: docker logs -f springboot-api"
}

# Parar a aplicação Docker
stop_docker() {
    print_info "Parando containers Docker..."
    docker-compose down
    print_info "Containers parados"
}

# Função de ajuda
show_help() {
    echo "Script de Deploy da API Spring Boot"
    echo ""
    echo "Uso: ./deploy.sh [OPÇÃO]"
    echo ""
    echo "Opções:"
    echo "  local       - Compilar e executar localmente com JAR"
    echo "  docker      - Fazer deploy usando Docker"
    echo "  build       - Apenas compilar a aplicação"
    echo "  db          - Apenas iniciar o banco de dados"
    echo "  stop        - Parar containers Docker"
    echo "  help        - Mostrar esta ajuda"
    echo ""
    echo "Exemplos:"
    echo "  ./deploy.sh local   # Deploy local"
    echo "  ./deploy.sh docker  # Deploy com Docker"
    echo "  ./deploy.sh build   # Apenas compilar"
}

# Menu principal
case "$1" in
    local)
        check_java
        check_maven
        build_app
        start_database
        run_app
        ;;
    docker)
        deploy_docker
        ;;
    build)
        check_maven
        build_app
        ;;
    db)
        start_database
        ;;
    stop)
        stop_docker
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        print_error "Opção inválida: $1"
        echo ""
        show_help
        exit 1
        ;;
esac
