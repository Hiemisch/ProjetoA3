# ProjetoA3

Victor Leme Beltran RA: 819120683
Gabriel Hiemisch Ribeiro Cordeiro RA: 819155925

Para este programa, realizamos as 4 operações de CRUD, no caso, inserção, deleção, atualização de registros, bem como seleção para visualização de registros desejados. Tudo isto é realizado por meio da plataforma com acesso direto à um banco de dados. No caso foi utilizado MySQL (caso seja desejado rodar o programa localmente será necessário ajustar as credenciais do mysql no código para as correspondentes ao seu servidor). 
No caso, para todas as operações, são basicamente realizadas queries com strings formatadas como o esperado.

No banco de dados criado temos 5 tabelas, sendo elas uma tablela para controle de usuários da plataforma, e outras quatro com dados em si acessíveis aos usuários, sendo elas a tipo_grupos (Categorias de Grupos Econômicos), tipo_clientes (Categorias de Clientes), clientes (tablela de clientes) e grupo_clientes(tabela de grupos econômicos dos clientes).

Para a tabela de usuários, armazenamos somente o username, senha e salt registrados para o usuário. No caso, as senhas são armazenadas codificadas por meio da hash MD5, deste modo evitando o armazenamento explícito de senhas no banco de dados. A senha inputada pelo usuário na interface é concatenada com o salt registrado no banco de dados e então é realizada a hash MD5 desta string e validada se a hash é a mesma da senha cadastrada no banco de dados. No primeiro uso da plataforma é necessário se registrar, isso é possível clicando em registrar-se na tela de login

Tela de login:

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/dfbeec34-687f-478a-9bf2-8bea6058763e)

Tela de registro:

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/4f4dc121-dd68-4be4-a6c3-5a4c019726a0)

Após feito o login o usuário é direcionado para um Menu com 4 botões, correspondentes às operações de CRUD possíveis na plataforma. No caso, CREATE, READ, UPDATE e DELETE.

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/d90b2f99-f3c2-4b22-87e4-1ec5ddee2c59)


Na tela de Create, é possível inserir valores no banco de dados, bastando selecionar a tabela por meio do botão na parte superior da tela com o label "Selecionar Tabela", onde o usuário, seleciona qual tabela ele deseja inserir um registro, seja a de clientes, a de grupos econômicos e etc. Para inserir, basta preencher os campos e clicar em inserir

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/aa99c183-8358-48d1-84c2-5d90d2f1c166)


Para a leitura do banco de dados, o processo é o mesmo, onde o usuário pode especificar nos campos o registro que procura (observação é que devido à criação, os campos não funcionam de maneira combinatória, e sim por ordem de prioridade de preenchimento, ou seja, se Nome está preenchido e CNPJ/CPF também, será dada prioridade ao campo Nome na pesquisa e assim por diante).

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/db68af39-61ce-482b-a3fb-691bbd567aaf)

Caso não sejam especificados filtros, será retornada uma leitura completa da tabela, a qual pode ser exportada para csv clicando no botão Exportar para csv na tela de resultados de busca.

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/7df22977-486c-47a1-bc03-db70ef853ef9)


Na tela de update, afim de se realizar a atualização de um campo, deve-se especificar o nome da coluna do campo a ser alterado, o valor que deseja se colocar no lugar do atual (valor atualizado), o campo filtro, ou seja, baseado em qual campo deseja-se atualizar (ex. campo filtro id e um valor do filtro = 1, atualizará todos os registros para os valores desejados em que o id seja = 1).

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/621a1f84-1687-49d8-8332-eb1b95f5131d)


Por fim, temos a possibilidade de deletar registros, esta por sua vez está limitada à somente deletar baseado no ID do registro

![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/d9007e75-7382-4910-a1a6-7bcd3a8093a3)
