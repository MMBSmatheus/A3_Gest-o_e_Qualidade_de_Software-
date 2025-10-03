
# 🚀 TaskMaster - Sistema de Gestão de Tarefas

O **TaskMaster** é um sistema de gerenciamento de tarefas desenvolvido em **Java** com interface gráfica via **Swing** e persistência em banco de dados **MySQL**. Ele permite a gestão eficiente de atividades em equipe, com controle de usuários, visualização e acompanhamento do progresso das tarefas.

---

## 🎯 Objetivos

- Automatizar o controle de tarefas.
- Melhorar a distribuição, acompanhamento e atualização das atividades.
- Aumentar a produtividade, organização e transparência em equipes.
- Substituir métodos como planilhas, quadros físicos ou e-mails.

---

## 🧠 Funcionalidades

- ✔️ Login seguro com controle de acesso (Usuário ou Administrador).
- ✔️ Cadastro de usuários com verificação de permissão para administrador.
- ✔️ CRUD completo de tarefas para administradores.
- ✔️ Usuários podem visualizar suas tarefas e alterar apenas o **status** (`Pendente`, `Em andamento`, `Concluída`, `Atrasada`).
- ✔️ Interface gráfica intuitiva construída com Java Swing.
- ✔️ Banco de dados relacional para armazenamento de usuários e tarefas.

---

## 🗄️ Modelagem do Banco de Dados

- **Tabela: usuarios**
  - id_usuario, nome, senha, email, cargo

- **Tabela: tarefas**
  - id_tarefa, titulo, descricao, data, lembrete, prioridade, status, responsavel

> 🔗 Script do banco disponível em [`bd_tarefas2.sql`](./bd_tarefas2.sql)

---

## 🏗️ Tecnologias e Ferramentas

- Java (Swing, JDBC)
- MySQL
- NetBeans (ou qualquer IDE Java)

---

## 💻 Como Executar o Projeto

1. Clone este repositório:
```bash
git clone https://github.com/JoaoGobbi/TaskMaster.git
```

2. Abra o projeto em sua IDE Java (NetBeans, IntelliJ, Eclipse).

3. Configure o banco de dados MySQL:
   - Crie um banco de dados chamado `sistema_tarefa` (ou altere o nome no arquivo `ConexaoDB.java`).
   - Execute o script SQL `bd_tarefas2.sql`.

4. No arquivo `ConexaoDB.java`, configure seu usuário e senha do MySQL:
```java
String url = "jdbc:mysql://localhost:3306/sistema_tarefa";
String usuario = "seu_usuario";
String senha = "sua_senha";
```

5. Execute o arquivo `Main.java` para iniciar o sistema.

---

## 📸 Telas do Sistema

- Tela de Login
- Tela de Cadastro de Usuário
- Dashboard de Tarefas (Usuário)
- Dashboard de Tarefas (Administrador - CRUD completo)

*(Imagens podem ser adicionadas aqui para enriquecer o projeto.)*

---

## 🚀 Melhorias Futuras

- 🔧 Hash de senha no banco de dados.
- 🔧 Filtros de busca por prioridade, status ou data.
- 🔧 Dashboard com gráficos (tarefas por status, por usuário, etc.).
- 🔧 Migração para aplicação web (ex: Spring Boot + React).

---

## 👨‍💻 Desenvolvedores

- João Gobbi
- Matheus Minakawa
- Gustavo Melo

---

## 📜 Licença

Uso livre para fins acadêmicos, estudos e projetos pessoais.
Sinta-se livre para sugerir melhorias e contribuir.
