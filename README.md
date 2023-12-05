# ProjetoA3

Victor Leme Beltran RA: 819120683
Gabriel Hiemisch Ribeiro Cordeiro RA: 819155925

Para este programa, realizamos 3 operações de CRUD, no caso, inserção, deleção, atualização de registros, bem como seleção para visualização de registros desejados. Tudo isto é realizado por meio da plataforma com acesso direto à um banco de dados. No caso foi utilizado MySQL.

Para a inserção, detemos 4 tabelas passíveis de inserção, selecionáveis pela selectbox na parte superior da interface. Para a inserção é necessário se atentar aos valores nos campos obrigatórios, os quais variam de tabela para tabela. No caso mais simples temos a de tipo clientes, onde no caso inserimos um novo tipo com o nome "novo_tipo!"
![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/1701c8af-ab1f-459d-8c77-ab0b87422999)

Para as operações de deleção, basta especificar o campo de referência para filtragem (Campo) e o valor esperado para o campo em ordem de deletar somente o registro desejado, conforme no exemplo abaixo:
![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/30097646-1c25-430a-89d9-ba3ef5d5d396)

Para o update, é necessário especificar o campo que se deseja atualizar, o valor que se deseja colocar no lugar e o campo de referência para o filtro, no caso, id_cliente para o caso de cliente, onde seu valor é especificado no campo cliente. Como exemplo, têm se a estrutura abaixo:
![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/d0362dfe-8910-478b-a3d1-bda935053a05)

Por fim, temos a possibilidade de leitura, onde colocamos o valor no campo desejado como filtragem (no caso só funciona com id e no caso das tabelas com cpf, com id e cpf) e caso não sejam especificados, a tabela como um todo será retornada. No retorno será exibida uma tela secundária onde se pode visualizar os resultados e exportá-los como um .csv
![image](https://github.com/Hiemisch/ProjetoA3/assets/77108992/a07f4c32-a08e-4855-9a01-bcf7361a28dd)

