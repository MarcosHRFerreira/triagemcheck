## <span style="color: lightblue;">MVP - Sistema de Avaliação da Qualidade de Triagem de Pacientes</span>


## <span style="color: lightblue;">Descrição</span>

Este sistema foi desenvolvido para avaliar a qualidade da triagem de pacientes em unidades de saúde. Ele coleta e analisa dados de triagem, compara com os desfechos clínicos dos pacientes e coleta feedback dos profissionais de saúde e dos pacientes. O sistema gera relatórios e visualizações de dados para ajudar na identificação de padrões e áreas de melhoria no processo de triagem.

## <span style="color: lightblue;">Funcionalidades Principais</span>

1. **Registro de Dados de Triagem**: Coleta e armazena dados detalhados de cada triagem, incluindo sintomas relatados, grau de severidade atribuído, tempo de espera e tempo até o atendimento.
2. **Avaliação de Resultados Clínicos**: Compara os resultados de triagem com os desfechos clínicos dos pacientes para avaliar a precisão da triagem.
3. **Feedback dos Profissionais de Saúde**: Permite que os profissionais de saúde forneçam feedback sobre a precisão e eficácia da triagem.
4. **Feedback dos Pacientes**: Coleta a opinião dos pacientes sobre o processo de triagem.
5. **Indicadores de Desempenho**: Utiliza métricas de desempenho para avaliar a qualidade da triagem, como tempo médio de espera e taxas de reavaliação dos pacientes.
6. **Relatórios e Visualização de Dados**: Gera relatórios e visualizações de dados para gestores de saúde.
7. **Comparação entre Unidades de Saúde**: Permite comparar a qualidade da triagem entre diferentes unidades de saúde.

## <span style="color: lightblue;">Benefícios</span>

- **Aprimoramento Contínuo**: O feedback contínuo e a análise dos resultados clínicos ajudam a melhorar a precisão e eficácia da triagem.
- **Transparência e Confiança**: A avaliação transparente do processo de triagem aumenta a confiança dos pacientes no sistema de saúde.
- **Otimização de Recursos**: Identificando áreas onde a triagem pode ser otimizada, os recursos de saúde podem ser usados de maneira mais eficiente.
- **Identificação de Problemas Sistêmicos**: Ajuda a identificar problemas sistêmicos na triagem, permitindo ajustes nos protocolos e treinamentos dos profissionais.

## <span style="color: lightblue;">Implementação</span>

### <span style="color: lightyellow;"> Estrutura do Projeto/span>
Este projeto é um sistema desenvolvido utilizando o Spring Boot e diversas dependências para criação de uma aplicação web robusta e escalável.

### <span style="color: lightblue;"> Dependências</span>
Abaixo estão as principais dependências utilizadas no projeto:

Spring Boot Starter Web: Para desenvolvimento de aplicações web.

Spring Boot Starter Log4j2: Para logging.

Spring Boot Starter Validation: Para validação de dados.

Commons Validator: Para validações adicionais.

Spring Boot Starter JDBC: Para suporte a JDBC.

Spring Boot Starter Data JPA: Para JPA e Repositories.

Springdoc OpenAPI Starter WebMVC UI: Para documentação da API.

Springfox Boot Starter: Para suporte ao Springfox Swagger.

Springfox Swagger 2: Para geração de documentação Swagger.

Springfox Swagger UI: Para exibição da documentação Swagger.

PostgreSQL: Banco de dados utilizado em runtime.

Specification Arg Resolver: Para resolver argumentos de especificações.

Spring Boot Starter Test: Para testes unitários e de integração.

Spring Plugin Core: Suporte a plugins do Spring.

Apache Commons Lang: Utilitários do Apache Commons Lang.

Jackson Datatype Hibernate: Suporte para Jackson e Hibernate.

Jackson Datatype JSR310: Suporte para Jackson e Java 8 Date & Time API.

javax.persistenceAPI: API de persistência Java.

Versões
Java Version: 21

Specification Version: 3.1.0

Build
O projeto é construído utilizando o plugin Maven do Spring Boot.

### <span style="color: lightblue;">Instalação</span>

1. Clone o repositório: `git clone https://github.com/MarcosHRFerreira/triagemcheck.git`
2. Navegue para o diretório do projeto: `cd sistema-triagem`
3. Compile o código: `javac -d bin src/*.java`
4. Execute o sistema: `java -cp bin Main`

### <span style="color: lightblue;">Estrutura do Projeto</span>


#### <span style="color: lightyellow;">--- Descrição da Estrutura ---</span>


#### - src/main/java: Contém o código fonte da aplicação.
#### - controller: Contém as classes de controlador (endpoints REST).
#### - service: Contém as classes de serviço onde a lógica de negócios é implementada.
#### - repository: Contém as classes de repositório para interação com o banco de dados.
#### - model: Contém as classes de modelo (entidades JPA).
#### - dto: Contém as classes de transferência de dados.
#### - config: Contém as classes de configuração.
#### - src/main/resources: Contém arquivos de recursos como o arquivo de propriedades e arquivos estáticos.
#### - application.properties: Arquivo de configuração da aplicação.
#### - static: Contém arquivos estáticos como HTML, CSS, JavaScript.
#### - templates: Contém arquivos HTML para renderização no lado do servidor.
#### - src/test/java: Contém os testes unitários e de integração da aplicação.
#### - src/test/resources: Contém arquivos de recursos para os testes.
#### - target: Diretório onde os artefatos compilados são armazenados (gerado automaticamente pelo Maven).
#### - Dockerfile: Arquivo de configuração do Docker para a construção da imagem do contêiner da aplicação.
#### - docker-compose.yml: Arquivo de configuração do Docker Compose para orquestrar vários contêineres.
#### - pom.xml: Arquivo de configuração do Maven para gerenciamento de dependências e construção do projeto.
#### - README.md: Arquivo de documentação do projeto.


## <span style="color: yellow;">Ferramentas Utilizadas</span>
<div style="display: flex; gap: 15px">
<a href="https://www.java.com" target="_blank"> 
    <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="40" height="40"/> 
</a>

<a href="https://spring.io/" target="_blank"> 
    <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring" width="40" height="40"/> 
</a>

<a href="https://www.postman.com/" target="_blank"> 
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postman/postman-original.svg" alt="Postman" width="40" /> 
</a>

<a href="https://www.postgresql.org/" target="_blank"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-plain.svg" width="40"/> </a>

</div>


## <span style="color: lightgreen;">Sobre a Aplicação</span>

A aplicação foi arquitetada com base nos princípios do padrão MVC (Model-View-Controller). Ela foi desenvolvida para ser consumida como uma API Rest, facilitando a integração com outras aplicações e serviços. Esta abordagem modular permite uma separação clara das responsabilidades, promovendo uma manutenção mais eficiente e um desenvolvimento mais escalável.


## <span style="color: lightgreen;">Consultas Possiveis Disponíveis</span>

#### - Triagens por Data de Criação
**Objetivo:** Obter todas as triagens criadas em um intervalo de datas específico.

#### - Triagens por Severidade
**Objetivo:** Obter todas as triagens com uma severidade específica.

#### - Triagens por Especialidade do Profissional
**Objetivo:** Obter todas as triagens realizadas por profissionais de uma especialidade específica.

#### - Feedbacks de Pacientes
**Objetivo:** Obter todos os feedbacks fornecidos pelos pacientes.

#### - Feedbacks dos Profissionais
**Objetivo:** Obter todos os feedbacks fornecidos pelos profissionais.

#### - Relatório Completo de Triagens
**Objetivo:** Obter um relatório completo das triagens, incluindo informações dos pacientes, profissionais, diagnósticos e feedbacks.

#### - Contagem de Triagens por Severidade
**Objetivo:** Obter a contagem de triagens agrupadas por severidade.

#### - Triagens por CPF do Paciente
**Objetivo:** Obter todas as triagens relacionadas a um paciente específico com base no CPF.

#### - Triagens por Nome do Profissional
**Objetivo:** Obter todas as triagens realizadas por um profissional específico.

#### - Triagens por Nome do Paciente
**Objetivo:** Obter todas as triagens relacionadas a um paciente específico com base no nome.

#### - Triagens por Cor do Protocolo
**Objetivo:** Obter todas as triagens com uma cor de protocolo específica.

#### - Triagens por Data de Alteração
**Objetivo:** Obter todas as triagens alteradas em um intervalo de datas específico.

#### - Diagnósticos por Data
**Objetivo:** Obter todos os diagnósticos registrados em um intervalo de datas específico.

#### - Pacientes por Bairro
**Objetivo:** Obter todos os pacientes que residem em um bairro específico.

#### - Profissionais por Status Operacional
**Objetivo:** Obter todos os profissionais com um status operacional específico.

#### - Feedbacks de Pacientes por Avaliação
**Objetivo:** Obter todos os feedbacks fornecidos pelos pacientes com uma avaliação específica.

#### - Feedbacks de Profissionais por Avaliação de Eficácia
**Objetivo:** Obter todos os feedbacks fornecidos pelos profissionais com uma avaliação de eficácia específica.

#### - Triagens por Sintomas
**Objetivo:** Obter todas as triagens que contêm um sintoma específico.

#### - Pacientes por Cidade
**Objetivo:** Obter todos os pacientes que residem em uma cidade específica.

#### - Diagnósticos por Desfecho
**Objetivo:** Obter todos os diagnósticos com um desfecho específico.

---

#### - Triagens por Nome do Enfermagem
**Objetivo:** Obter todas as triagens realizadas por um enfermeiro(a) específico(a).

#### - Feedbacks de Pacientes por Data de Criação
**Objetivo:** Obter todos os feedbacks fornecidos pelos pacientes em um intervalo de datas específico.

#### - Feedbacks dos Profissionais por Data de Criação
**Objetivo:** Obter todos os feedbacks fornecidos pelos profissionais em um intervalo de datas específico.

#### - Profissionais por Especialidade
**Objetivo:** Obter todos os profissionais de uma especialidade específica.

#### - Pacientes por Data de Nascimento
**Objetivo:** Obter todos os pacientes nascidos em um intervalo de datas específico.

#### - Diagnósticos por Paciente
**Objetivo:** Obter todos os diagnósticos relacionados a um paciente específico.

#### - Triagens por CRM do Profissional
**Objetivo:** Obter todas as triagens realizadas por um profissional específico com base no CRM.

#### - Feedbacks de Pacientes por Nome
**Objetivo:** Obter todos os feedbacks fornecidos pelos pacientes com base no nome.

#### - Feedbacks dos Profissionais por Nome
**Objetivo:** Obter todos os feedbacks fornecidos pelos profissionais com base no nome.

#### - Triagens por Avaliação de Feedback do Paciente
**Objetivo:** Obter todas as triagens que possuem uma avaliação específica no feedback do paciente.


## <span style="color: lightgreen;">Desenvolvedores</span>


### <span style="color: lightblue;">Integrantes do Grupo 45</span>


**Amanda Cristina Pereira Rodrigues**
- [amandadaylan@gmail.com ](#damandadaylan@gmail.com)

**Marcos Henrique Rosa Ferreira**
- [marcoshrferreira@gmail.com](#marcoshrferreira@gmail.com)
