# Um Novo Desafio com EJB

Uma aplicação fullstack completa que integra **Spring Boot**, **EJB remoto**, **Angular 8** e **Material Design** para gerenciar benefícios com operações de CRUD e transferência de valores.

## 📋 Tabla de Conteúdo

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Setup](#instalação-e-setup)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Backend](#backend)
- [Frontend](#frontend)
- [Banco de Dados](#banco-de-dados)
- [Testes](#testes)
- [Como Executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)

---

## Visão Geral

O projeto implementa um sistema de gerenciamento de **Benefícios** com as seguintes características:

✅ **CRUD completo**: Criar, listar, atualizar e deletar benefícios
✅ **Transferência de valores**: Transferir saldo entre benefícios com validações
✅ **Interface responsiva**: Frontend com Material Design compatível com Angular 8
✅ **Testes abrangentes**: 18 testes unitários e de integração
✅ **Arquitetura em camadas**: Database → EJB → Spring Boot → Angular

---

## Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                    FRONTEND (Angular 8)                  │
│         Material Design + Reactive Forms                │
└────────────────────────┬────────────────────────────────┘
                         │ HTTP REST
┌────────────────────────▼────────────────────────────────┐
│              BACKEND (Spring Boot 3.2.5)                │
│      BeneficioController → BeneficioService            │
└────────────────────────┬────────────────────────────────┘
                         │ JNDI/RMI
┌────────────────────────▼────────────────────────────────┐
│           EJB Remote (Serviço Distribuído)             │
│    BeneficioEjbService → BeneficioEntity (JPA)         │
└────────────────────────┬────────────────────────────────┘
                         │ SQL
┌────────────────────────▼────────────────────────────────┐
│              Banco de Dados (H2 / MySQL)               │
│                 Tabela: BENEFICIO                       │
└─────────────────────────────────────────────────────────┘
```

---

## Pré-requisitos

- **Java 17+** (Spring Boot 3.2.5 requer Java 17)
- **Maven 3.8+**
- **Node.js 14+** e **npm 6+** (para Angular)
- **Banco de Dados**: H2 (embutido) ou MySQL 8
- **IDE**: IntelliJ IDEA ou VS Code

---

## Instalação e Setup

### 1. Clone o Repositório

```bash
git clone <url-do-repositorio>
cd um-novo-desafio-com-ejb
```

### 2. Configure o Banco de Dados

Execute os scripts SQL na ordem:

```bash
# Schema
mysql -u root -p < db/schema.sql

# Dados iniciais
mysql -u root -p < db/seed.sql
```

Ou use o H2 embutido (padrão do projeto).

### 3. Build do Backend

```bash
# Instala dependências e compila
mvn clean install

# Apenas compile
mvn clean compile
```

### 4. Setup do Frontend

```bash
cd frontend
npm install
npm start
```

---

## Estrutura do Projeto

```
um-novo-desafio-com-ejb/
├── backend-module/
│   ├── src/
│   │   ├── main/java/com/example/backend/
│   │   │   ├── BackendApplication.java
│   │   │   ├── domain/dto/
│   │   │   │   └── Beneficio.java
│   │   │   ├── rest/
│   │   │   │   └── BeneficioController.java
│   │   │   └── service/
│   │   │       └── BeneficioService.java
│   │   ├── test/java/com/example/backend/
│   │   │   ├── rest/
│   │   │   │   └── BeneficioControllerTest.java (7 testes)
│   │   │   └── service/
│   │   │       └── BeneficioServiceTest.java (9 testes)
│   │   └── resources/
│   │       └── application.yml
│   └── pom.xml
│
├── ejb-module/
│   ├── src/main/java/com/example/ejb/
│   │   ├── entity/
│   │   │   └── Beneficio.java (JPA Entity)
│   │   ├── service/
│   │   │   ├── BeneficioServiceRemote.java (Interface remota)
│   │   │   └── BeneficioEjbService.java (Implementação EJB)
│   │   └── rest/
│   │       └── BeneficioRest.java
│   ├── resources/
│   │   └── META-INF/persistence.xml
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── app.module.ts
│   │   │   ├── app-routing.module.ts
│   │   │   ├── app.component.ts/html/scss
│   │   │   ├── components/
│   │   │   │   ├── beneficio/
│   │   │   │   ├── notification-modal/
│   │   │   │   └── transfer-dialog/
│   │   │   └── services/
│   │   │       ├── beneficio.service.ts
│   │   │       └── notification.service.ts
│   │   ├── index.html
│   │   ├── main.ts
│   │   └── styles.scss
│   ├── angular.json
│   ├── tsconfig.json
│   └── package.json
│
├── db/
│   ├── schema.sql
│   └── seed.sql
│
├── docs/
│   └── README.md (Instruções do desafio)
│
├── pom.xml (POM raiz)
└── README.md (Este arquivo)
```

---

## Backend

### Tecnologias

- **Framework**: Spring Boot 3.2.5
- **Java**: 17 LTS
- **Testing**: JUnit 5 (Jupiter), Mockito, Spring Test
- **ORM**: Spring Data JPA
- **Database**: H2 (embutido) ou MySQL

### Módulos

#### BeneficioController

Endpoints REST para operações CRUD:

```java
@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {
    // Endpoint implementations
}
```

**Endpoints**:
- `GET /api/beneficios` - Listar todos
- `GET /api/beneficios/{id}` - Buscar por ID
- `POST /api/beneficios` - Criar novo
- `PUT /api/beneficios/{id}` - Atualizar
- `DELETE /api/beneficios/{id}` - Deletar
- `POST /api/beneficios/transferir` - Transferir valor

#### BeneficioService

Serviço de negócio que orquestra chamadas ao EJB remoto:

```java
@Service
public class BeneficioService {
    private BeneficioServiceRemote ejbService;
    
    public List<Beneficio> listar() { }
    public Beneficio criar(Beneficio b) { }
    public Beneficio atualizar(Beneficio b) { }
    public Beneficio buscarPorId(Long id) { }
    public void deletar(Long id) { }
    public void transferir(Long origemId, Long destinoId, BigDecimal valor) { }
}
```

### Testes

**BeneficioControllerTest** (7 testes):
- ✅ Retornar lista de benefícios
- ✅ Buscar benefício por ID
- ✅ Retornar 404 quando não encontrado
- ✅ Criar novo benefício
- ✅ Atualizar benefício
- ✅ Deletar benefício
- ✅ Transferir valor entre benefícios

**BeneficioServiceTest** (9 testes):
- ✅ Listar todos os benefícios
- ✅ Criar novo benefício
- ✅ Atualizar benefício
- ✅ Deletar benefício
- ✅ Buscar por ID
- ✅ Transferir valor
- ✅ Transferir com valores corretos
- ✅ Transferir com valor fracionado
- ✅ Passar parâmetros corretos ao buscar

---

## Frontend

### Tecnologias

- **Framework**: Angular 8.2.14
- **TypeScript**: 3.5.3
- **UI Library**: Angular Material 8.0.0 + Angular CDK 8.0.0
- **Styling**: SCSS com CSS Grid
- **Forms**: Reactive Forms com validação

### Componentes

#### BeneficioComponent

Componente principal com table e formulário:

```
┌──────────────────────────────────────────────┐
│          Benefício Management UI              │
├──────────────────────────────────────────────┤
│  Form (Sidebar)          │    Table (Main)   │
│  - Nome                  │  - Nome           │
│  - Descrição             │  - Descrição      │
│  - Valor                 │  - Valor          │
│  - Ativo (Checkbox)      │  - Status         │
│  [Salvar] [Cancelar]     │  - Ações (3 botões)
└──────────────────────────────────────────────┘
```

**Funcionalidades**:
- Listar benefícios em tabela Material
- Criar novo benefício via formulário
- Editar benefício existente
- Deletar benefício com confirmação
- Transferir valor com dialog customizado
- Validação de formulário em tempo real

#### NotificationModalComponent

Modal genérico para exibir mensagens:

```typescript
interface Notification {
  title: string;
  message: string;
  type: 'success' | 'error' | 'warning' | 'info';
}
```

**Tipos de notificação**:
- ✅ **Success** (verde) - Operação concluída
- ❌ **Error** (vermelho) - Falha na operação
- ⚠️ **Warning** (laranja) - Aviso importante
- ℹ️ **Info** (azul) - Informação

#### TransferDialogComponent

Dialog para transferência de valores:

```typescript
interface TransferRequest {
  originId: number;
  destinationId: number;
  amount: BigDecimal;
}
```

**Validações**:
- Não permite transferir para si mesmo
- Valida saldo disponível
- Impede valores negativos
- Requer destinação e valor válidos

### Design Responsivo

Layout adaptativo com CSS Grid:

```scss
// Mobile (padrão)
grid-template-columns: 1fr;

// Tablet/Desktop (1200px+)
grid-template-columns: 350px 1fr;
```

**Breakpoints**:
- Mobile: até 599px (1 coluna)
- Tablet: 600px - 1199px (1 coluna)
- Desktop: 1200px+ (2 colunas)

### Material Design

- **Tema**: indigo-pink
- **Componentes**: MatTable, MatForm, MatDialog, MatCard, MatIcon
- **Ícones**: Google Material Icons (CDN)
- **Tipografia**: Roboto (Google Fonts)

---

## Banco de Dados

### Schema

```sql
CREATE TABLE BENEFICIO (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Dados Iniciais (Seed)

```sql
INSERT INTO BENEFICIO (nome, descricao, valor, ativo) VALUES
('Benefício A', 'Descrição do benefício A', 1000.00, true),
('Benefício B', 'Descrição do benefício B', 2000.00, true),
('Benefício C', 'Descrição do benefício C', 500.00, true);
```

---

## Testes

### Executar Testes

```bash
# Todos os testes
mvn test

# Apenas testes do Controller
mvn test -Dtest=BeneficioControllerTest

# Apenas testes do Service
mvn test -Dtest=BeneficioServiceTest

# Com reporte detalhado
mvn clean test surefire-report:report
mvn surefire-report:report -DskipTests=false
```

### Padrão Arrange-Act-Assert

Todos os testes seguem o padrão BDD:

```java
@Test
void deveRetornarListaBeneficos() throws Exception {
    // Arrange - Preparar dados
    List<Beneficio> beneficios = Arrays.asList(beneficio1, beneficio2);
    when(beneficioService.listar()).thenReturn(beneficios);

    // Act & Assert - Executar e validar
    mockMvc.perform(get("/api/beneficios")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    
    verify(beneficioService, times(1)).listar();
}
```

### Cobertura de Testes

- **BeneficioControllerTest**: Testa todos os 6 endpoints REST
- **BeneficioServiceTest**: Testa lógica de negócio com Mockito
- **Total**: 16 testes com assertions detalhadas

---

## Como Executar

### 1. Backend Spring Boot

```bash
# Terminal 1 - Navegar para backend
cd backend-module

# Build
mvn clean install

# Rodar aplicação
mvn spring-boot:run

# Ou executar direto via JAR
java -jar target/backend-module-0.0.1-SNAPSHOT.jar
```

**Saída esperada**:
```
Started BackendApplication in 3.245 seconds
Application is running on port: 8080
```

**Acesso**: http://localhost:8080

### 2. Frontend Angular

```bash
# Terminal 2 - Navegar para frontend
cd frontend

# Instalar dependências
npm install

# Rodar servidor de desenvolvimento
npm start

# Ou
ng serve
```

**Saída esperada**:
```
 ✔ Compiled successfully.

  Application bundle generation complete
  Listening on http://localhost:4200
```

**Acesso**: http://localhost:4200

### 3. EJB Module

O módulo EJB é empacotado como `.jar` e implantado no servidor de aplicação (JBoss/WildFly).

```bash
cd ejb-module
mvn clean package

# Fazer deploy do EAR/JAR no servidor de aplicação
# Configurar JNDI em aplicação.yml
```

---

## Endpoints da API

### Listar Benefícios

```http
GET /api/beneficios
Content-Type: application/json
```

**Response (200)**:
```json
[
  {
    "id": 1,
    "nome": "Benefício A",
    "descricao": "Descrição do benefício A",
    "valor": 1000.00,
    "ativo": true
  },
  {
    "id": 2,
    "nome": "Benefício B",
    "descricao": "Descrição do benefício B",
    "valor": 2000.00,
    "ativo": true
  }
]
```

### Buscar por ID

```http
GET /api/beneficios/{id}
Content-Type: application/json
```

**Response (200)**:
```json
{
  "id": 1,
  "nome": "Benefício A",
  "descricao": "Descrição do benefício A",
  "valor": 1000.00,
  "ativo": true
}
```

**Response (404)**: Benefício não encontrado

### Criar Benefício

```http
POST /api/beneficios
Content-Type: application/json

{
  "nome": "Novo Benefício",
  "descricao": "Descrição",
  "valor": 500.00,
  "ativo": true
}
```

**Response (200)**:
```json
{
  "id": 4,
  "nome": "Novo Benefício",
  "descricao": "Descrição",
  "valor": 500.00,
  "ativo": true
}
```

### Atualizar Benefício

```http
PUT /api/beneficios/{id}
Content-Type: application/json

{
  "id": 1,
  "nome": "Benefício A Atualizado",
  "descricao": "Nova descrição",
  "valor": 1500.00,
  "ativo": true
}
```

**Response (200)**: Objeto atualizado

### Deletar Benefício

```http
DELETE /api/beneficios/{id}
Content-Type: application/json
```

**Response (204)**: Sem conteúdo

### Transferir Valor

```http
POST /api/beneficios/transferir?origemId=1&destinoId=2&valor=100.00
Content-Type: application/json
```

**Response (200)**:
```json
{
  "origem": {
    "id": 1,
    "valor": 900.00
  },
  "destino": {
    "id": 2,
    "valor": 2100.00
  }
}
```

---

## Funcionalidades

### ✅ Implementadas

- [x] CRUD completo (CREATE, READ, UPDATE, DELETE)
- [x] Transferência de valores entre benefícios
- [x] Validação de formulário no frontend
- [x] Material Design responsivo
- [x] Notificações de sucesso/erro
- [x] Dialog de transferência
- [x] Testes unitários (9 testes Service)
- [x] Testes de integração (7 testes Controller)
- [x] Documentação README

### 🚀 Futuras Melhorias

- [ ] Swagger/OpenAPI para documentação de API
- [ ] Autenticação e autorização (JWT)
- [ ] Histórico de transações/auditoria
- [ ] Paginação na listagem
- [ ] Filtros avançados
- [ ] E2E testes Angular
- [ ] CI/CD com GitHub Actions
- [ ] Docker e Docker Compose

---

## Tecnologias Utilizadas

### Backend

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 17 LTS | Linguagem principal |
| Spring Boot | 3.2.5 | Framework web |
| Spring Data JPA | - | ORM e persistência |
| JUnit 5 | - | Framework de testes |
| Mockito | - | Mocking em testes |
| Maven | 3.8+ | Gerenciador de dependências |
| H2 Database | - | Banco embedded |
| MySQL | 8.0 | Banco relacional (opcional) |

### Frontend

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Angular | 8.2.14 | Framework frontend |
| TypeScript | 3.5.3 | Linguagem de tipagem |
| Material | 8.0.0 | Componentes UI |
| SCSS | - | Estilização avançada |
| RxJS | - | Programação reativa |
| HttpClient | - | Comunicação com API |

### EJB

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java EE | - | Especificação |
| EJB 3.1+ | - | Beans remotos |
| JPA 2.x | - | Persistência |
| JNDI | - | Lookup de recursos |

---

## Configuração

### application.yml (Backend)

O backend-module **não acessa banco de dados diretamente**. Todas as operações de persistência são feitas através do **EJB remoto** (BeneficioEjbService). A configuração é mínima:

```yaml
server:
  port: 8081
```

**Fluxo de dados**:
```
HTTP Request (Frontend)
    ↓
BeneficioController (REST)
    ↓
BeneficioService (Spring Service)
    ↓
BeneficioServiceRemote (JNDI Lookup)
    ↓
BeneficioEjbService (EJB Remoto)
    ↓
JPA/Persistência (Banco de dados real)
```

**Observação**: O banco de dados é gerenciado **exclusivamente pelo módulo EJB**. Para desenvolvimento local, certifique-se que:
1. O servidor de aplicação (JBoss/WildFly) está rodando
2. O módulo EJB está deployado
3. O JNDI lookup está configurado corretamente

### environment.ts (Frontend)

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081/api'
};
```

---

## Troubleshooting

### Backend não conecta ao EJB

**Erro**: `NamingException: Cannot find JNDI binding`

**Solução**:
1. Verificar se EJB está deployado no servidor de aplicação
2. Verificar configuração JNDI em `application.yml`
3. Ajustar lookup path conforme servidor (JBoss/WildFly)

### Frontend não conecta ao Backend

**Erro**: `Connection refused` ou CORS error

**Solução**:
1. Verificar se Backend está rodando em `8080`
2. Adicionar CORS headers no Controller:
```java
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BeneficioController { }
```

### Banco de dados não inicia

**Erro**: `Cannot create pool of connections`

**Solução**:
1. Verificar credenciais de banco em `application.yml`
2. Se using MySQL, criar banco: `CREATE DATABASE beneficios;`
3. Executar `db/schema.sql` e `db/seed.sql`

---

## Contribuindo

1. Faça um fork do projeto
2. Crie um branch: `git checkout -b feature/MinhaFeature`
3. Commit: `git commit -m 'Add MinhaFeature'`
4. Push: `git push origin feature/MinhaFeature`
5. Abra um Pull Request

---

## Licença

Este projeto é fornecido como desafio técnico. Consulte a licença do repositório original.

---

## Contato

Para dúvidas ou sugestões sobre o projeto, abra uma issue no repositório.

---

**Última atualização**: Março 2026  
**Versão**: 1.0.0  
**Status**: ✅ Em Desenvolvimento(MVP pronto)
