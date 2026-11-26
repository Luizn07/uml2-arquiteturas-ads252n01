# Requisitos

## Requisitos funcionais

| ID | Requisito |
| --- | --- |
| RF01 | Permitir o cadastro de alunos de sistema. |
| RF02 | Permitir o cadastro de disciplinas com limite de vagas. |
| RF03 | Permitir matricular um aluno cadastrado em uma disciplina disponível. |
| RF04 | Validar o limite de vagas na camada de Negócio e bloquear a matrícula caso a disciplina esteja lotada. |
| RF05 | Registrar logs das operações realizadas durante a execução do sistema para documentação em LOGS.md. |

## Requisitos não funcionais

| ID | Requisito |
| --- | --- |
| RNF01 | Estruturar o sistema em três camadas (Apresentação, Negócio e Dados) com comunicação estrita entre elas. |
| RNF02 | Persistir os dados da aplicação exclusivamente em arquivos no formato JSON. |
| RNF03 | Executar e orquestrar os serviços em contêineres isolados via docker-compose.yml, garantindo portabilidade entre ambientes  . |
