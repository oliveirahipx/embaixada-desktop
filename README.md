# Embaixada Desktop

Sistema desktop em Java (Swing) que simula o atendimento de uma embaixada: o cidadão cria uma conta, faz login, solicita passaporte e visto e consulta os próprios documentos. Os dados ficam num banco MySQL.

Projeto acadêmico do curso de Ciência da Computação, feito para praticar interfaces Swing, JDBC e modelagem relacional.

## Como rodar

Você precisa de Java 8 ou mais novo e de um MySQL com as tabelas do projeto. O jeito mais rápido de subir o banco é com Docker:

```bash
git clone https://github.com/oliveirahipx/embaixada-desktop.git
cd embaixada-desktop
docker compose up -d
```

O `docker-compose.yml` sobe um MySQL 8.4 na porta 3306, cria o banco `embaixadaBrasil` com o usuário `embaixada` (senha `embaixada`) e executa o `bdEmbaixada.sql`. Se a porta 3306 já estiver ocupada por outro MySQL, use outra porta e aponte a aplicação para ela:

```bash
MYSQL_PORT=3307 docker compose up -d
export EMBAIXADA_DB_URL=jdbc:mysql://localhost:3307/embaixadaBrasil
```

Depois baixe `embaixada-desktop-1.0.0.jar` na [página de releases](https://github.com/oliveirahipx/embaixada-desktop/releases/latest) e rode:

```bash
java -jar embaixada-desktop-1.0.0.jar
```

Se o banco não estiver acessível, a aplicação mostra um aviso com o endereço que tentou usar.

### Usando um MySQL próprio

Rode o `bdEmbaixada.sql` no seu servidor (atenção: ele começa com `DROP DATABASE IF EXISTS embaixadaBrasil`) e configure a conexão por variáveis de ambiente:

| Variável | Padrão |
|---|---|
| `EMBAIXADA_DB_URL` | `jdbc:mysql://localhost:3306/embaixadaBrasil` |
| `EMBAIXADA_DB_USER` | `embaixada` |
| `EMBAIXADA_DB_PASSWORD` | `embaixada` |

No Windows (PowerShell): `$env:EMBAIXADA_DB_PASSWORD = "minha-senha"` antes do `java -jar`.

## Funcionalidades

- Cadastro de usuário com dados pessoais (RG, CPF, data de nascimento, nacionalidade, CEP, telefone, estado civil)
- Login por nome de usuário e senha
- Menu principal com saudação ao usuário logado, logout e contato de suporte
- Solicitação de passaporte (número, data de emissão e autoridade emissora), gravada para o usuário logado
- Tela "Exibir Documentos" com os passaportes e vistos do usuário
- Tela com os dados pessoais cadastrados

## Banco de dados

Três tabelas, definidas em [`bdEmbaixada.sql`](bdEmbaixada.sql):

- `usuario`: dados pessoais e credenciais
- `passaporte`: passaportes do usuário (`idusuario` como chave estrangeira)
- `visto`: vistos ligados a um usuário e a um passaporte

## Compilar a partir do código

Precisa de um JDK 9 ou mais novo (testado com o JDK 25); o Maven vem pelo wrapper.

```bash
./mvnw package          # Linux/macOS
mvnw.cmd package        # Windows
```

O jar com o driver MySQL incluído fica em `target/embaixada-desktop-1.0.0.jar`.

## Estrutura

```
src/
  Main.java                 ponto de entrada (testa a conexão e abre o login)
  Database/Conexao.java     conexão JDBC com o MySQL
  Telas/                    telas Swing (login, cadastro, menu, passaporte, visto, documentos, dados pessoais)
bdEmbaixada.sql             criação do banco e das tabelas
docker-compose.yml          MySQL local para desenvolvimento
```

## Limitações conhecidas

- A senha é gravada em texto puro na tabela `usuario`.
- A tela "Solicitar Visto" ainda grava o pedido na tabela `passaporte` (com autoridade "Embaixada"), e não na tabela `visto`; por isso a seção de vistos em "Exibir Documentos" fica vazia.
- As telas acessam o banco diretamente, sem uma camada DAO separada.
