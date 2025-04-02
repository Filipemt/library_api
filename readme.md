
# Cadastro de Autor
Deseja-se cadastrar os autores de livros, bem como realizar suas atualizações, consultas e permitir sua exclusão.


# Atores
Somente o Gerente pode cadastrar, atualizar e remover Autores.
O usuário Operador poderá somente consultar os dados dos Autores.

# Campos solicitados pelo negócio
Dados que deverão ser guardados:
- Nome (*)
- Data de Nascimento (*)
- Nacionalidade (*)

campos com (*) são obrigatórios

# Campos lógicos
Dados não solicitados pela equipe de negócio, mas são de controle da aplicação e auditoria:

- ID - UUID
- Data Cadastro
- Data Ultima Atualização
- Usuário Ultima Atualização

# Regras de Negócio
- Não permitir cadastrar um autor com mesmo nome, data de nascimento e nacionalidade, ou seja, se houver 2 autores com mesmos nome, data de nascimento e nacionalidade serão considerados repetidos, não permitir.

- Não permitir excluir um Autor que possuir algum livro.

