## <span style="color: lightblue;">Sistema de Avaliação da Qualidade de Triagem de Pacientes</span>


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

### <span style="color: lightyellow;">Requisitos</span>

- Java 17 ou superior
### <span style="color: lightyellow;">Biblioteca</span>

- Spring Boot Starter Web
- Spring Boot Starter Log4j2
- Spring Boot Starter Validation
- Commons Validator
- Spring Boot Starter JDBC
- Spring Boot Starter Data JPA
- SpringDoc OpenAPI Starter WebMVC UI
- Springfox Boot Starter
- Springfox Swagger2
- Springfox Swagger UI
- PostgreSQL
- Specification Argument Resolver
- Spring Boot Starter Test
- Spring Plugin Core
- Apache Commons Lang3

### <span style="color: lightblue;">Banco de Dados</span>

- PostgreSQL

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


## <span style="color: lightgreen;">Desenvolvedores</span>


### <span style="color: lightblue;">Integrantes do Grupo 45</span>


**Amanda Cristina Pereira Rodrigues**
- [amandadaylan@gmail.com ](#damandadaylan@gmail.com)

**Marcos Henrique Rosa Ferreira**
- [marcoshrferreira@gmail.com](#marcoshrferreira@gmail.com)
