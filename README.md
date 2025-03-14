# ERP Simplificado - Microservices com Spring Boot

## Descrição
Este projeto é um **ERP Simplificado** para pequenas empresas, permitindo o gerenciamento de clientes, fornecedores, produtos, pedidos e pagamentos em uma arquitetura de microservices.

## 🛠️ Tecnologias
- **Spring Boot** (para os microservices)
- **Spring Cloud Gateway** (API Gateway)
- **Keycloak** (Autenticação e Autorização)
- **PostgreSQL** (Pessoas e Produtos)
- **MongoDB** (Pedidos e Pagamentos)
- **Kafka/RabbitMQ** (Mensageria para eventos)
- **Grafana + Prometheus** (Observabilidade)
- **Docker + Kubernetes** (Orquestração de containers)

## 🏛️ Arquitetura
📌 **Serviços principais:**
1. **API Gateway** → Entrada única para os serviços
2. **Autenticação** → Keycloak para segurança JWT
3. **Pessoas (Clientes e Fornecedores)** → PostgreSQL para armazenar dados estruturados
4. **Produtos (Cadastro e controle de estoque)** → PostgreSQL para cadastros e controle de estoque
5. **Pedidos** → MongoDB para flexibilidade e escalabilidade
6. **Pagamentos** → MongoDB para armazenar transações financeiras
7. **Notificações** → Kafka/RabbitMQ para eventos assíncronos
8. **Relatórios** → OpenSearch + Grafana para monitoramento

## � Roadmap de Desenvolvimento - Inicio previsto dia 20/02/2025

### 🔥 Sprint 1 - Base do Projeto
✅ Criar o **API Gateway** com Spring Cloud Gateway
✅ Configurar **Keycloak** para autenticação e autorização
✅ Criar o serviço de **Pessoas** com CRUD básico (PostgreSQL)
✅ Criar o serviço de **Produtos** (PostgreSQL)

### 🔥 Sprint 2 - Processamento de Pedidos
✅ Criar o serviço de **Pedidos** com MongoDB
✅ Criar eventos de pedidos criados/atualizados com **Kafka/RabbitMQ**
✅ Conectar Pedidos ao serviço de **Clientes e Produtos**

### 🔥 Sprint 3 - Pagamentos
✅ Criar o serviço de **Pagamentos** integrado a um Serviço de pagamento ex. Mercado Pago, PayPal (MongoDB)
✅ Criar mecanismo de notificações para transações aprovadas/recusadas
✅ Publicar eventos de pagamento concluído para atualizar pedidos

### 🔥 Sprint 4 - Observabilidade e Relatórios
✅ Implementar logs estruturados com **Elastic/OpenSearch**
✅ Criar painéis de métricas com **Grafana + Prometheus**
✅ Monitoramento de eventos e falhas em serviços

Esse README pode ser expandido conforme o projeto evolui. 🚀